package com.klef.fsad.sdp.service;

import com.klef.fsad.sdp.model.Employee;
import com.klef.fsad.sdp.model.Salary;
import com.klef.fsad.sdp.model.SalaryAnomaly;
import com.klef.fsad.sdp.repository.SalaryAnomalyRepository;
import com.klef.fsad.sdp.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class SalaryAnomalyService {

    @Autowired
    private SalaryAnomalyRepository anomalyRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    private static final double ANOMALY_THRESHOLD = 10.0;

    // ─── Stats ────────────────────────────────────────────────────────────────

    public long getTotalCount() {
        return anomalyRepository.count();
    }

    public long getResolvedCount() {
        return anomalyRepository.countByStatus("RESOLVED");
    }

    // ─── Single record ────────────────────────────────────────────────────────

    public SalaryAnomaly getAnomalyById(Long id) {
        return anomalyRepository.findById(id).orElse(null);
    }

    // ─── Resolve (updates the linked Salary record as well) ──────────────────

    @Transactional
    public SalaryAnomaly resolveAnomaly(Long id, double previousAmount, double currentAmount) {
        SalaryAnomaly anomaly = anomalyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anomaly not found with ID: " + id));

        anomaly.setStatus("RESOLVED");
        anomaly.setReviewedDate(LocalDate.now());

        // Only overwrite amounts if the caller actually sent non-zero values
        if (previousAmount > 0) anomaly.setPreviousAmount(previousAmount);
        if (currentAmount  > 0) anomaly.setCurrentAmount(currentAmount);

        SalaryAnomaly saved = anomalyRepository.save(anomaly);

        // --- NEW: sync the corrected net salary back into the salary table ---
        if (anomaly.getSalary() != null && currentAmount > 0) {
            Salary salary = anomaly.getSalary();
            salary.setNetSalary(currentAmount);
            salaryRepository.save(salary);
        }

        return saved;
    }

    // ─── Ignore (mark RESOLVED without touching salary amounts) ──────────────

    @Transactional
    public SalaryAnomaly ignoreAnomaly(Long id) {
        SalaryAnomaly anomaly = anomalyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anomaly not found with ID: " + id));

        anomaly.setStatus("RESOLVED");
        anomaly.setReviewedDate(LocalDate.now());
        anomaly.setResolutionNotes("Dismissed by reviewer — no salary change applied.");

        return anomalyRepository.save(anomaly);
    }

    // ─── Detection ────────────────────────────────────────────────────────────

    @Transactional
    public void detectAnomalies() {
        List<Salary> allSalaries = salaryRepository.findAll();

        for (Salary currentSalary : allSalaries) {
            Employee employee = currentSalary.getEmployee();
            if (employee == null) continue;

            List<Salary> previousSalaries = salaryRepository.findPreviousSalaries(
                    employee,
                    currentSalary.getYear(),
                    currentSalary.getMonth()
            );

            if (!previousSalaries.isEmpty()) {
                Salary previousSalary = previousSalaries.get(0);
                double currentNet  = currentSalary.getNetSalary();
                double previousNet = previousSalary.getNetSalary();

                if (previousNet > 0) {
                    double deviation = ((currentNet - previousNet) / previousNet) * 100;

                    if (Math.abs(deviation) >= ANOMALY_THRESHOLD) {
                        String anomalyType = deviation > 0 ? "SUDDEN_INCREASE" : "SUDDEN_DECREASE";

                        boolean alreadyFlagged = anomalyRepository
                                .findBySalary(currentSalary)
                                .stream()
                                .anyMatch(a -> a.getAnomalyType().equals(anomalyType));

                        if (!alreadyFlagged) {
                            SalaryAnomaly anomaly = new SalaryAnomaly();
                            anomaly.setSalary(currentSalary);
                            anomaly.setEmployee(employee);
                            anomaly.setAnomalyType(anomalyType);
                            anomaly.setCurrentAmount(currentNet);
                            anomaly.setPreviousAmount(previousNet);
                            anomaly.setDeviationPercentage(deviation);
                            anomaly.setStatus("PENDING");
                            anomaly.setDetectedDate(LocalDate.now());
                            anomaly.setDescription(
                                    "Salary variation of " + String.format("%.2f", deviation) + "% detected."
                            );

                            double abs = Math.abs(deviation);
                            if      (abs >= 50) anomaly.setSeverity("CRITICAL");
                            else if (abs >= 30) anomaly.setSeverity("HIGH");
                            else if (abs >= 20) anomaly.setSeverity("MEDIUM");
                            else                anomaly.setSeverity("LOW");

                            anomalyRepository.save(anomaly);
                        }
                    }
                }
            } else {
                boolean alreadyFlagged = !anomalyRepository.findBySalary(currentSalary).isEmpty();

                if (!alreadyFlagged) {
                    SalaryAnomaly anomaly = new SalaryAnomaly();
                    anomaly.setSalary(currentSalary);
                    anomaly.setEmployee(employee);
                    anomaly.setAnomalyType("NO_HISTORY");
                    anomaly.setCurrentAmount(currentSalary.getNetSalary());
                    anomaly.setPreviousAmount(0);
                    anomaly.setDeviationPercentage(100);
                    anomaly.setStatus("PENDING");
                    anomaly.setDetectedDate(LocalDate.now());
                    anomaly.setSeverity("LOW");
                    anomaly.setDescription("No previous salary record found for comparison.");
                    anomalyRepository.save(anomaly);
                }
            }
        }
    }

    // ─── List ─────────────────────────────────────────────────────────────────

    public List<SalaryAnomaly> getAllAnomalies() {
        return anomalyRepository.findAll();
    }
}