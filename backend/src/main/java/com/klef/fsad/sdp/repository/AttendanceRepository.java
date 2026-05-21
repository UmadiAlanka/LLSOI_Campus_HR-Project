package com.klef.fsad.sdp.repository;

import com.klef.fsad.sdp.model.Attendance; // Ensure this path is correct
import com.klef.fsad.sdp.model.Employee;   // Ensure this path is correct
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByEmployee(Employee employee);

    Optional<Attendance> findByEmployeeAndDate(Employee employee, LocalDate date);

    List<Attendance> findByDate(LocalDate date);

    List<Attendance> findByEmployeeAndDateBetween(Employee employee, LocalDate startDate, LocalDate endDate);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.employee = :employee " +
            "AND YEAR(a.date) = :year AND MONTH(a.date) = :month AND a.status = 'PRESENT'")
    int countWorkingDays(@Param("employee") Employee employee, @Param("year") int year, @Param("month") int month);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.employee = :employee " +
            "AND YEAR(a.date) = :year AND MONTH(a.date) = :month AND a.status = 'LEAVE'")
    int countLeaves(@Param("employee") Employee employee, @Param("year") int year, @Param("month") int month);

    // --- Custom Filter for Attendance Verification ---

    /**
     * Filters attendance records based on Date, Department, and Job Type.
     * Uses JOIN to access department and jobType fields from the Employee entity.
     * Handles null parameters to allow optional filtering.
     */
    @Query("SELECT a FROM Attendance a JOIN a.employee e WHERE " +
            "(:date IS NULL OR a.date = :date) AND " +
            "(:dept IS NULL OR e.department = :dept) AND " +
            "(:type IS NULL OR e.jobType = :type)")
    List<Attendance> findByFilters(@Param("date") LocalDate date,
                                   @Param("dept") String dept,
                                   @Param("type") String type);

    /**
     * Searches attendance records by Employee Name or Employee ID.
     * Performs a case-insensitive search using LOWER().
     */
    @Query("SELECT a FROM Attendance a JOIN a.employee e WHERE " +
            "LOWER(e.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(e.id AS string) LIKE CONCAT('%', :search, '%')")
    List<Attendance> searchAttendance(@Param("search") String search);

    long countByDateAndStatus(LocalDate date, String status);
}