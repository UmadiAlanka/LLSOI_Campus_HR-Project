package com.klef.fsad.sdp.repository;

import com.klef.fsad.sdp.model.Employee;
import com.klef.fsad.sdp.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

    List<Salary> findByStatus(String status);

    List<Salary> findByMonthAndYear(int month, int year);

    List<Salary> findByEmployee(Employee employee);
    List<Salary> findByEmployee_Id(Long employeeId);

    @Query("""
        SELECT s FROM Salary s 
        WHERE s.employee = :employee 
        AND (s.year < :year OR (s.year = :year AND s.month < :month))
    """)
    List<Salary> findPreviousSalaries(
            @Param("employee") Employee employee,
            @Param("month") int month,
            @Param("year") int year
    );

    List<Salary> findByEmployeeId(Long employeeId);
}