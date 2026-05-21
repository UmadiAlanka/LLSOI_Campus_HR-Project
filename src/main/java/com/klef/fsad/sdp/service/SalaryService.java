package com.klef.fsad.sdp.service;

import com.klef.fsad.sdp.model.Salary;
import com.klef.fsad.sdp.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    // --- ADD THIS METHOD HERE ---
    public Salary saveSalary(Salary salary) {
        // This is the magic line that actually writes to your database
        return salaryRepository.save(salary);
    }

    // --- Existing Methods for Attendance/Payroll Page ---

    public List<Salary> getAllSalaries() {
        return salaryRepository.findAll();
    }

    public void approveSalary(Long id) {
        Optional<Salary> salaryOpt = salaryRepository.findById(id);
        if (salaryOpt.isPresent()) {
            Salary salary = salaryOpt.get();
            salary.setStatus("APPROVED");
            salaryRepository.save(salary);
        } else {
            throw new RuntimeException("Salary record not found for ID: " + id);
        }
    }

    // --- Methods for DashboardController ---

    public List<Salary> getSalariesByMonthAndYear(int month, int year) {
        return salaryRepository.findByMonthAndYear(month, year);
    }

    public List<Salary> getSalariesByStatus(String status) {
        return salaryRepository.findByStatus(status);
    }

    public List<Salary> getEmployeeSalaries(Long employeeId) {
        return salaryRepository.findByEmployeeId(employeeId);
    }

    // --- Merged Utility & Update Methods ---

    public Salary getSalaryById(Long id) {
        return salaryRepository.findById(id).orElse(null);
    }

    public void updateSalaryDetails(Long id, Double basic, Double net) {
        Salary s = salaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
        s.setBasicSalary(basic);
        s.setNetSalary(net);
        salaryRepository.save(s);
    }

    public Salary getSalaryByEmployeeAndDate(Long employeeId, int month, int year) {
        List<Salary> salaries = salaryRepository.findByEmployeeId(employeeId);
        return salaries.stream()
                .filter(s -> s.getMonth() == month && s.getYear() == year)
                .findFirst()
                .orElse(null);
    }

    public Salary getLatestSalary(Long employeeId) {
        List<Salary> salaries = salaryRepository.findByEmployeeId(employeeId);
        return salaries.stream()
                .sorted((s1, s2) -> {
                    if (s1.getYear() != s2.getYear()) {
                        return Integer.compare(s2.getYear(), s1.getYear());
                    }
                    return Integer.compare(s2.getMonth(), s1.getMonth());
                })
                .findFirst()
                .orElse(null);
    }

    public void deleteSalaryById(Long id) {
        if (salaryRepository.existsById(id)) {
            salaryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Salary record not found with ID: " + id);
        }
    }
}