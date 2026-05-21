package com.klef.fsad.sdp.repository;

import com.klef.fsad.sdp.model.Employee;
import com.klef.fsad.sdp.model.Salary;
import com.klef.fsad.sdp.model.SalaryAnomaly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryAnomalyRepository extends JpaRepository<SalaryAnomaly, Long> {

    // Find anomalies by status
    List<SalaryAnomaly> findByStatus(String status);

    // Find anomalies by severity
    List<SalaryAnomaly> findBySeverity(String severity);

    // Find anomalies by employee
    List<SalaryAnomaly> findByEmployeeOrderByDetectedDateDesc(Employee employee);

    // Find anomalies by salary
    List<SalaryAnomaly> findBySalary(Salary salary);

    // Find pending anomalies
    List<SalaryAnomaly> findByStatusOrderByDetectedDateDesc(String status);

    // Find anomalies by status and severity
    List<SalaryAnomaly> findByStatusAndSeverityOrderByDetectedDateDesc(String status, String severity);
}