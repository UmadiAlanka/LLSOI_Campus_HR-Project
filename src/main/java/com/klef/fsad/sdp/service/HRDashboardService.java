package com.klef.fsad.sdp.service;

import com.klef.fsad.sdp.dto.HRDashboardDTO;
import com.klef.fsad.sdp.repository.EmployeeRepository;
import com.klef.fsad.sdp.repository.AttendanceRepository;
import com.klef.fsad.sdp.repository.LeaveRepository;
import com.klef.fsad.sdp.repository.SalaryAnomalyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class HRDashboardService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private SalaryAnomalyRepository salaryAnomalyRepository;

    /**
     * Retrieves aggregated statistics specifically for the HR Staff Dashboard.
     * Uses various repositories to count relevant records for the current state.
     */
    public HRDashboardDTO getHRDashboardStats() {
        // Fetch total count of all registered employees
        long total = employeeRepository.count();

        // Fetch count of employees marked as 'Present' for today's date
        // Note: Ensure 'Present' matches the exact string stored in your DB
        long present = attendanceRepository.countByDateAndStatus(LocalDate.now(), "Present");

        // Fetch count of leave requests that are currently in 'Pending' status
        long leaves = leaveRepository.countByStatus("Pending");

        // Fetch total count of reported salary anomalies
        long anomalies = salaryAnomalyRepository.count();

        return new HRDashboardDTO(total, present, leaves, anomalies);
    }
}
