package com.klef.fsad.sdp.service;

import com.klef.fsad.sdp.model.Employee;
import com.klef.fsad.sdp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JavaMailSender mailSender;

    // ---------------- FORGOT PASSWORD LOGIC ----------------

    public boolean processForgotPassword(String email) {
        // Ensure your repository has findByEmail
        Employee employee = employeeRepository.findByEmail(email);
        if (employee != null) {
            String token = UUID.randomUUID().toString();
            employee.setResetToken(token);
            employee.setTokenExpiryDate(LocalDateTime.now().plusMinutes(15));

            employeeRepository.save(employee);
            sendResetEmail(employee.getEmail(), token);
            return true;
        }
        return false;
    }

    private void sendResetEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("llsoicampus27@gmail.com");
        message.setTo(email);
        message.setSubject("Password Reset Request - LLSOI Campus HR");

        String resetLink = "http://localhost:3000/reset-password?token=" + token;

        message.setText("To reset your password, click the link below:\n" + resetLink +
                "\n\nThis link will expire in 15 minutes.");

        mailSender.send(message);
    }

    public boolean updatePassword(String token, String newPassword) {
        // Ensure your repository has findByResetToken
        Employee employee = employeeRepository.findByResetToken(token);
        if (employee != null && employee.getTokenExpiryDate() != null
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

    /**
     * FIX: This now saves to employee_table.
     * Note: If you want separate tables, you'd call AdminRepository.save() here too.
     */
    public Employee createEmployee(Employee employee) {
        // Setting a default role if none provided
        if (employee.getRole() == null || employee.getRole().isEmpty()) {
            employee.setRole("EMPLOYEE");
        }
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
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
        existing.setRole(newData.getRole()); // Updates "ADMIN"/"HR" status
        existing.setJob(newData.getJob());
        existing.setJobType(newData.getJobType());
        existing.setUsername(newData.getUsername());
        existing.setEmail(newData.getEmail());

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

    /**
     * FIX: Validates login against the employee_table credentials
     */
    public Employee login(String username, String password) {
        return employeeRepository.findByUsername(username)
                .filter(emp -> emp.getPassword().equals(password))
                .orElse(null);
    }
}