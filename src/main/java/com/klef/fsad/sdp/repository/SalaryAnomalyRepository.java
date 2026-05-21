package com.klef.fsad.sdp.repository;

import com.klef.fsad.sdp.model.Employee;
import com.klef.fsad.sdp.model.Salary;
import com.klef.fsad.sdp.model.SalaryAnomaly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryAnomalyRepository extends JpaRepository<SalaryAnomaly, Long> {

    // Required for the dashboard statistics
    long countByStatus(String status);

    List<SalaryAnomaly> findByStatus(String status);
    List<SalaryAnomaly> findBySeverity(String severity);
    List<SalaryAnomaly> findByEmployeeOrderByDetectedDateDesc(Employee employee);
    List<SalaryAnomaly> findBySalary(Salary salary);
    List<SalaryAnomaly> findByStatusOrderByDetectedDateDesc(String status);
}