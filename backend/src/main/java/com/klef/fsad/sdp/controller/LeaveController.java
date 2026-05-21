package com.klef.fsad.sdp.controller;

import com.klef.fsad.sdp.dto.ApiResponse;
import com.klef.fsad.sdp.model.Leave;
import com.klef.fsad.sdp.service.LeaveService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@RestController
@RequestMapping({"/api/leaves", "/api/leave"})
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "http://localhost:5173"}, allowCredentials = "true")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @GetMapping({"", "/all"})
    public ResponseEntity<ApiResponse<List<Leave>>> getAllLeaves() {
        try {
            List<Leave> leaves = leaveService.getAllLeaves();
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Leaves retrieved successfully", leaves)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error: " + e.getMessage()));
        }
    }

    @PostMapping("/request/{employeeId}")
    public ResponseEntity<ApiResponse<Leave>> requestLeave(
            @PathVariable Long employeeId,
            @ModelAttribute Leave leave,
            HttpServletRequest request) {
        try {
            // Handle multipart file upload only if request is actually multipart
            if (request instanceof MultipartHttpServletRequest multipartRequest) {
                multipartRequest.getFileMap().forEach((key, file) -> {
                    if (file != null && !file.isEmpty() &&
                            (leave.getAttachment() == null || leave.getAttachment().isEmpty())) {
                        leave.setAttachment(file.getOriginalFilename());
                    }
                });
            }

            // Handle parameters generically (fallback for type/attachment)
            request.getParameterMap().forEach((key, values) -> {
                if (values != null && values.length > 0) {
                    String value = values[0];
                    if (value != null && !value.isEmpty()) {
                        if (key.equalsIgnoreCase("type") ||
                                key.equalsIgnoreCase("leave_type") ||
                                key.equalsIgnoreCase("typeOfLeave")) {
                            if (leave.getLeaveType() == null || leave.getLeaveType().isEmpty()) {
                                leave.setLeaveType(value);
                            }
                        }
                        if (key.equalsIgnoreCase("attachment") &&
                                (leave.getAttachment() == null || leave.getAttachment().isEmpty())) {
                            leave.setAttachment(value);
                        }
                    }
                }
            });

            // Fallback: Try to resolve employeeId from nested employee object
            Long resolvedId = employeeId;
            if ((resolvedId == null || resolvedId == 0) && leave.getEmployee() != null) {
                if (leave.getEmployee().getId() != null) {
                    resolvedId = leave.getEmployee().getId();
                } else if (leave.getEmployee().getUsername() != null) {
                    com.klef.fsad.sdp.model.Employee emp =
                            leaveService.getEmployeeByUsername(leave.getEmployee().getUsername());
                    if (emp != null) resolvedId = emp.getId();
                }
            }

            if (resolvedId == null || resolvedId == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(false, "Error: Valid Employee ID or Username is required"));
            }

            Leave savedLeave = leaveService.requestLeave(resolvedId, leave);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Leave request processed successfully", savedLeave));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Error: " + e.getMessage()));
        }
    }

    @PostMapping("/{employeeId}")
    public ResponseEntity<ApiResponse<Leave>> requestLeaveLegacy(
            @PathVariable Long employeeId,
            @ModelAttribute Leave leave,
            HttpServletRequest request) {
        return requestLeave(employeeId, leave, request);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Leave>> requestLeaveBody(@RequestBody Leave leave) {
        try {
            Long employeeId = null;

            // Try to get ID from nested employee object
            if (leave.getEmployee() != null) {
                employeeId = leave.getEmployee().getId();
            }

            // If ID is still null, but we have a username (from the "ID" field in UI)
            if (employeeId == null && leave.getEmployee() != null &&
                    leave.getEmployee().getUsername() != null) {
                com.klef.fsad.sdp.model.Employee emp =
                        leaveService.getEmployeeByUsername(leave.getEmployee().getUsername());
                if (emp != null) {
                    employeeId = emp.getId();
                }
            }

            if (employeeId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(false,
                                "Error: Employee identification (ID or Username) is required"));
            }

            Leave savedLeave = leaveService.requestLeave(employeeId, leave);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Leave request submitted", savedLeave));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Error: " + e.getMessage()));
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<Leave>>> getEmployeeLeaves(
            @PathVariable Long employeeId) {
        try {
            List<Leave> leaves = leaveService.getLeavesByEmployee(employeeId);
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Employee leaves retrieved", leaves)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error: " + e.getMessage()));
        }
    }

    @PutMapping("/{leaveId}/approve")
    public ResponseEntity<ApiResponse<Leave>> approveLeave(@PathVariable int leaveId) {
        try {
            Leave leave = leaveService.approveLeave(leaveId);
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Leave approved successfully", leave)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    @PutMapping("/{leaveId}/reject")
    public ResponseEntity<ApiResponse<Leave>> rejectLeave(@PathVariable int leaveId) {
        try {
            Leave leave = leaveService.rejectLeave(leaveId);
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Leave rejected", leave)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<Leave>>> getPendingLeaves() {
        try {
            List<Leave> leaves = leaveService.getPendingLeaves();
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Pending leaves retrieved", leaves)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error: " + e.getMessage()));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Leave>>> getLeavesByStatus(@PathVariable String status) {
        try {
            List<Leave> leaves = leaveService.getLeavesByStatus(status);
            return ResponseEntity.ok(
                    new ApiResponse<>(true, status + " leaves retrieved", leaves)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error: " + e.getMessage()));
        }
    }

    @GetMapping("/history/{employeeId}")
    public ResponseEntity<ApiResponse<List<Leave>>> getLeaveHistory(@PathVariable Long employeeId) {
        return getEmployeeLeaves(employeeId);
    }
}