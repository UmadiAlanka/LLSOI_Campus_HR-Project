package com.klef.fsad.sdp.controller;

import com.klef.fsad.sdp.dto.ApiResponse;
import com.klef.fsad.sdp.dto.HRDashboardDTO;
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
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "http://localhost:5173"}, allowCredentials = "true")
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
    @Autowired
    private HRDashboardService hrDashboardService;

    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAdminDashboard() {
        Map<String, Object> stats = new HashMap<>();

        try {
            // 1. Total Employees
            long totalEmployees = employeeService.getTotalEmployeeCount();
            stats.put("totalEmployees", totalEmployees);

            // 2. Attendance Stats (Today)
            long todayAttendance = attendanceService.getAttendanceByDate(LocalDate.now()).size();
            stats.put("todayAttendance", todayAttendance);

            // 3. Salary Logic
            List<Salary> pendingSalariesList = salaryService.getSalariesByStatus("PENDING");
            double totalPendingAmount = pendingSalariesList.stream()
                    .mapToDouble(Salary::getNetSalary)
                    .sum();
            stats.put("pendingSalaries", totalPendingAmount);

            // 4. Other stats
            stats.put("pendingLeaveRequests", leaveService.getPendingLeaves().size());

            return ResponseEntity.ok(new ApiResponse<>(true, "Admin dashboard data retrieved", stats));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>(false, "Error: " + e.getMessage()));
        }
    }

    @GetMapping("/hr-staff")
    public ResponseEntity<ApiResponse<HRDashboardDTO>> getHRDashboard() {
        try {
            HRDashboardDTO dashboardData = hrDashboardService.getHRDashboardStats();
            return ResponseEntity.ok(new ApiResponse<>(true, "HR Dashboard data retrieved successfully", dashboardData));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>(false, "Error retrieving HR dashboard: " + e.getMessage()));
        }
    }
}