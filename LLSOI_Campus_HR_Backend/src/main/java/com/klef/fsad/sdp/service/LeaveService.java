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

    // Instance method - Removed static to access autowired repositories
    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    public List<Leave> getPendingLeaves() {
        return leaveRepository.findByStatus("PENDING");
    }

    public List<Leave> getApprovedLeaves() {
        return leaveRepository.findByStatus("APPROVED");
    }

    // Uses Long employeeId to match the Controller and Repository
    public Leave requestLeave(Long employeeId, Leave leave) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        leave.setEmployee(employee);
        leave.setStatus("PENDING");
        return leaveRepository.save(leave);
    }

    // Crucial for DashboardController line 78
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