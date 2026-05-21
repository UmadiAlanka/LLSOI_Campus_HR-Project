package com.klef.fsad.sdp.controller;

import com.klef.fsad.sdp.dto.EmployeeResponseDTO;
import com.klef.fsad.sdp.model.Employee;
import com.klef.fsad.sdp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "http://localhost:5173"}, allowCredentials = "true")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Helper: Convert Entity -> DTO
    private EmployeeResponseDTO mapToDTO(Employee emp) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setEmployeeId(emp.getEmployeeId());
        dto.setName(emp.getName());
        dto.setUsername(emp.getUsername());
        dto.setEmail(emp.getEmail());
        dto.setRole(emp.getRole());
        dto.setJob(emp.getJob());
        dto.setJobType(emp.getJobType());
        dto.setContactNumber(emp.getContactNumber());
        dto.setNic(emp.getNic());
        dto.setDob(emp.getDob());
        dto.setGender(emp.getGender());
        dto.setDepartment(emp.getDepartment());
        dto.setDateJoined(emp.getDateJoined());
        return dto;
    }

    // ================== GET ALL EMPLOYEES ==================
    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            List<EmployeeResponseDTO> dtoList = employees.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Employees fetched successfully",
                    "data", dtoList
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "message", "Error fetching employees: " + e.getMessage()
            ));
        }
    }

    // ================== GET ONE EMPLOYEE ==================
    // FIX: Changed @PathVariable String to Long
    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getEmployeeByEmployeeId(@PathVariable Long employeeId) {
        Optional<Employee> employee = employeeService.getEmployeeByEmployeeId(employeeId);

        if (employee.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of(
                    "success", false,
                    "message", "Employee not found"
            ));
        }

        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", mapToDTO(employee.get())
        ));
    }

    // ================== CREATE EMPLOYEE ==================
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        try {
            Employee saved = employeeService.createEmployee(employee);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Employee created successfully",
                    "data", mapToDTO(saved)
            ));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    // ================== UPDATE EMPLOYEE ==================
    // FIX: Changed @PathVariable String to Long and removed manual Long.valueOf conversion
    @PutMapping("/{employeeId}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Long employeeId,
            @RequestBody Employee employee) {
        try {
            Employee updated = employeeService.updateEmployee(employeeId, employee);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Employee updated successfully",
                    "data", mapToDTO(updated)
            ));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    // ================== DELETE EMPLOYEE ==================
    // FIX: Changed @PathVariable String to Long
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long employeeId) {
        try {
            employeeService.deleteEmployee(employeeId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Employee deleted successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    // ================== GET ALL EMPLOYEES (FOR HR STAFF DTO) ==================
    /**
     * Specifically for HR Staff page to fetch data using the DTO service method.
     */
    @GetMapping("/all-dto")
    public ResponseEntity<?> getAllEmployeesForHR() {
        try {
            // Calling the new method you added in EmployeeService
            List<EmployeeResponseDTO> employees = employeeService.getAllEmployeesDTO();

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Employees fetched successfully",
                    "data", employees
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "message", "Error: " + e.getMessage()
            ));
        }
    }
}