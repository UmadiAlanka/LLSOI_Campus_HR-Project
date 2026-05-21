package com.klef.fsad.sdp.repository;

import com.klef.fsad.sdp.model.Attendance;
import com.klef.fsad.sdp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Custom query to return data sorted by most recent ID
    @Query("SELECT a FROM Attendance a ORDER BY a.id DESC")
    List<Attendance> findAllOrderByIdDesc();

    List<Attendance> findByEmployee(Employee employee);

    // Using List to prevent "NonUniqueResultException" seen in image_f18118.jpg
    List<Attendance> findByEmployeeAndDate(Employee employee, LocalDate date);

    List<Attendance> findByDate(LocalDate date);
}