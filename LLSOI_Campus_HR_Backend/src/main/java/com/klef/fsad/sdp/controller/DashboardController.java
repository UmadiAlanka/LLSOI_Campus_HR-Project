package com.klef.fsad.sdp.controller;

import com.klef.fsad.sdp.dto.ApiResponse;
import com.klef.fsad.sdp.model.Salary;
import com.klef.fsad.sdp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class DashboardController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private HRService hrService;
    @Autowired
    private LeaveService leaveService;
    @Autowired
    private SalaryService salaryService;
    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAdminDashboard() {
        Map<String, Object> stats = new HashMap<>();

        // 1. Total Employees
        long totalEmployees = employeeService.getTotalEmployeeCount();
        stats.put("totalEmployees", totalEmployees);

        // 2. Attendance Stats (Today)
        long todayAttendance = attendanceService.getAttendanceByDate(LocalDate.now()).size();
        stats.put("todayAttendance", todayAttendance);

        // 3. Salary Logic (Summing netSalary from your model)
        List<Salary> pendingSalariesList = salaryService.getSalariesByStatus("PENDING");
        double totalPendingAmount = pendingSalariesList.stream()
                .mapToDouble(Salary::getNetSalary) // Matches your model getter
                .sum();
        stats.put("pendingSalaries", totalPendingAmount);

        // 4. Other stats for cards
        stats.put("pendingLeaveRequests", leaveService.getPendingLeaves().size());

        return ResponseEntity.ok(new ApiResponse<>(true, "Admin dashboard data retrieved", stats));
    }
}