package com.klef.fsad.sdp.service;

import com.klef.fsad.sdp.model.Employee;
import com.klef.fsad.sdp.model.Leave;
import com.klef.fsad.sdp.repository.EmployeeRepository;
import com.klef.fsad.sdp.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee getEmployeeByUsername(String username) {
        return employeeRepository.findByUsername(username).orElse(null);
    }

    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    public List<Leave> getPendingLeaves() {
        return leaveRepository.findByStatus("PENDING");
    }

    public List<Leave> getApprovedLeaves() {
        return leaveRepository.findByStatus("APPROVED");
    }

    public List<Leave> getLeavesByStatus(String status) {
        return leaveRepository.findByStatus(status);
    }

    public Leave requestLeave(Long employeeId, Leave leave) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        // Always set employee
        leave.setEmployee(employee);

        // Always ensure status is set — never null
        if (leave.getStatus() == null || leave.getStatus().isEmpty()) {
            leave.setStatus("PENDING");
        }

        // Always ensure leaveType is set — never null
        if (leave.getLeaveType() == null || leave.getLeaveType().isEmpty()) {
            // Try to extract from reason field if formatted as "Type: reason"
            String reason = leave.getReason();
            if (reason != null && reason.contains(":")) {
                String potentialType = reason.split(":")[0].trim();
                if (potentialType.toLowerCase().contains("leave")) {
                    leave.setLeaveType(potentialType);
                } else {
                    leave.setLeaveType("Annual Leave"); // safe default
                }
            } else {
                leave.setLeaveType("Annual Leave"); // safe default
            }
        }

        // Always ensure reason is set — never null
        if (leave.getReason() == null || leave.getReason().isEmpty()) {
            leave.setReason("No reason provided");
        }

        // Always ensure startDate is set — never null
        if (leave.getStartDate() == null) {
            throw new RuntimeException("Start date is required");
        }

        // Always ensure endDate is set — never null
        if (leave.getEndDate() == null) {
            throw new RuntimeException("End date is required");
        }

        // If it's an update (existing leave), preserve fields not provided
        if (leave.getId() != 0) {
            Leave existingLeave = leaveRepository.findById(leave.getId()).orElse(null);
            if (existingLeave != null) {
                if (leave.getAttachment() == null || leave.getAttachment().isEmpty()) {
                    leave.setAttachment(existingLeave.getAttachment());
                }
                if (leave.getLeaveType() == null || leave.getLeaveType().isEmpty()) {
                    leave.setLeaveType(existingLeave.getLeaveType());
                }
            }
        }

        return leaveRepository.save(leave);
    }

    public List<Leave> getLeavesByEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
        return leaveRepository.findByEmployee(employee);
    }

    public Leave approveLeave(Integer id) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave record not found"));
        leave.setStatus("APPROVED");
        return leaveRepository.save(leave);
    }

    public Leave rejectLeave(Integer id) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave record not found"));
        leave.setStatus("REJECTED");
        return leaveRepository.save(leave);
    }
}