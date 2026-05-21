package com.klef.fsad.sdp.controller;

import com.klef.fsad.sdp.dto.ApiResponse;
import com.klef.fsad.sdp.model.Leave;
import com.klef.fsad.sdp.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "*")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @GetMapping
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

    // FIX: Changed @PathVariable String to Long
    @PostMapping("/{employeeId}")
    public ResponseEntity<ApiResponse<Leave>> requestLeave(
            @PathVariable Long employeeId,
            @RequestBody Leave leave) {
        try {
            Leave savedLeave = leaveService.requestLeave(employeeId, leave);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Leave request submitted", savedLeave));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Error: " + e.getMessage()));
        }
    }

    // FIX: Changed @PathVariable String to Long
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
}