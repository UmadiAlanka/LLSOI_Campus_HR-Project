package com.klef.fsad.sdp.service;

import com.klef.fsad.sdp.model.Employee;
import com.klef.fsad.sdp.model.Salary;
import com.klef.fsad.sdp.model.SalaryAnomaly;
import com.klef.fsad.sdp.repository.SalaryAnomalyRepository;
import com.klef.fsad.sdp.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SalaryAnomalyService {

    @Autowired
    private SalaryAnomalyRepository anomalyRepository; // Remove 'static'

    @Autowired
    private SalaryRepository salaryRepository;

    private static final double ANOMALY_THRESHOLD = 20.0;

    // Detect anomalies and save them
    public List<SalaryAnomaly> detectAnomalies(Salary currentSalary) {
        Employee employee = currentSalary.getEmployee();
        List<SalaryAnomaly> detectedAnomalies = new java.util.ArrayList<>();

        // Ensure salaryRepository has this custom query
        List<Salary> previousSalaries = salaryRepository.findPreviousSalaries(
                employee,
                currentSalary.getYear(),
                currentSalary.getMonth()
        );

        if (!previousSalaries.isEmpty()) {
            Salary previousSalary = previousSalaries.get(0);
            double currentNet = currentSalary.getNetSalary();
            double previousNet = previousSalary.getNetSalary();

            if (previousNet > 0) {
                double deviation = ((currentNet - previousNet) / previousNet) * 100;
                if (Math.abs(deviation) >= ANOMALY_THRESHOLD) {
                    SalaryAnomaly anomaly = new SalaryAnomaly(
                            currentSalary,
                            employee,
                            deviation > 0 ? "SUDDEN_INCREASE" : "SUDDEN_DECREASE",
                            currentNet,
                            previousNet
                    );
                    detectedAnomalies.add(anomalyRepository.save(anomaly));
                }
            }
        }
        return detectedAnomalies;
    }

    public List<SalaryAnomaly> getAllAnomalies() {
        return anomalyRepository.findAll();
    }
}