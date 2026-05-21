package com.klef.fsad.sdp.service;

import com.klef.fsad.sdp.dto.EmployeeResponseDTO;
import com.klef.fsad.sdp.model.Employee;
import com.klef.fsad.sdp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JavaMailSender mailSender;

    public boolean processForgotPassword(String email) {
        Employee employee = employeeRepository.findByEmail(email);
        if (employee != null) {
            // Generate a 6-digit numeric code
            String otpCode = String.valueOf(100000 + new Random().nextInt(900000));

            employee.setResetToken(otpCode); // Reusing the token field for the code
            employee.setTokenExpiryDate(LocalDateTime.now().plusMinutes(10)); // Shorter expiry for codes

            employeeRepository.save(employee);
            sendResetEmail(employee.getEmail(), otpCode);
            return true;
        }
        return false;
    }

    private void sendResetEmail(String email, String otpCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("llsoicampus27@gmail.com");
        message.setTo(email);
        message.setSubject("OTP for Password Reset - LLSOI Campus HR");

        message.setText("Your password reset code is: " + otpCode +
                "\n\nPlease enter this code on the reset page to verify your identity." +
                "\n\nThis code will expire in 10 minutes.");

        mailSender.send(message);
    }

    /**
     * Updated to verify code first
     */
    public boolean verifyResetCode(String email, String code) {
        Employee employee = employeeRepository.findByEmail(email);
        return employee != null &&
                code.equals(employee.getResetToken()) &&
                employee.getTokenExpiryDate().isAfter(LocalDateTime.now());
    }

    public boolean updatePasswordWithCode(String email, String code, String newPassword) {
        Employee employee = employeeRepository.findByEmail(email);

        if (employee != null && code.equals(employee.getResetToken())
                && employee.getTokenExpiryDate().isAfter(LocalDateTime.now())) {

            employee.setPassword(newPassword);
            employee.setResetToken(null);
            employee.setTokenExpiryDate(null);

            employeeRepository.save(employee);
            return true;
        }
        return false;
    }

    // ---------------- CRUD LOGIC ----------------

    public Employee createEmployee(Employee employee) {
        if (employee.getRole() == null || employee.getRole().isEmpty()) {
            employee.setRole("EMPLOYEE");
        }
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<EmployeeResponseDTO> getAllEmployeesDTO() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().map(emp -> {
            EmployeeResponseDTO dto = new EmployeeResponseDTO();
            dto.setId(emp.getId());
            dto.setEmployeeId(emp.getEmployeeId());
            dto.setName(emp.getName());
            dto.setDob(emp.getDob());
            dto.setGender(emp.getGender());
            dto.setUsername(emp.getUsername());
            dto.setEmail(emp.getEmail());
            dto.setRole(emp.getRole());
            dto.setJob(emp.getJob());
            dto.setJobType(emp.getJobType());
            dto.setContactNumber(emp.getContactNumber());
            dto.setDepartment(emp.getDepartment());
            dto.setNic(emp.getNic());
            dto.setDateJoined(emp.getDateJoined());
            return dto;
        }).collect(Collectors.toList());
    }

    public Optional<Employee> getEmployeeByEmployeeId(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee updateEmployee(Long id, Employee newData) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

        existing.setName(newData.getName());
        existing.setAddress(newData.getAddress());
        existing.setContactNumber(newData.getContactNumber());
        existing.setRole(newData.getRole());
        existing.setJob(newData.getJob());
        existing.setJobType(newData.getJobType());
        existing.setUsername(newData.getUsername());
        existing.setEmail(newData.getEmail());
        existing.setNic(newData.getNic());
        existing.setDob(newData.getDob());
        existing.setGender(newData.getGender());
        existing.setDepartment(newData.getDepartment());
        existing.setDateJoined(newData.getDateJoined());

        if (newData.getPassword() != null && !newData.getPassword().isEmpty()) {
            existing.setPassword(newData.getPassword());
        }

        return employeeRepository.save(existing);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found with ID: " + id);
        }
        employeeRepository.deleteById(id);
    }

    public long getTotalEmployeeCount() {
        return employeeRepository.count();
    }

    public Employee login(String username, String password) {
        return employeeRepository.findByUsername(username)
                .filter(emp -> emp.getPassword().equals(password))
                .orElse(null);
    }
}