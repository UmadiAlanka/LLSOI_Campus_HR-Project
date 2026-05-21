package com.klef.fsad.sdp.controller;

import com.klef.fsad.sdp.dto.LoginRequest;
import com.klef.fsad.sdp.dto.LoginResponse;
import com.klef.fsad.sdp.model.Employee;
import com.klef.fsad.sdp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "http://localhost:5173"})
public class AuthController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = new LoginResponse();
        try {
            Employee employee = employeeService.login(request.getUsername(), request.getPassword());
            if (employee != null) {
                response.setSuccess(true);
                response.setMessage("Login successful");
                response.setUserId(employee.getEmployeeId());
                response.setUsername(employee.getUsername());
                response.setName(employee.getName());
                response.setRole(employee.getRole());
                return ResponseEntity.ok(response);
            }
            response.setSuccess(false);
            response.setMessage("Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // --- STEP 1: Send 6-Digit Code ---
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Email is required."));
        }

        boolean codeSent = employeeService.processForgotPassword(email);

        if (codeSent) {
            return ResponseEntity.ok(Map.of("success", true, "message", "A 6-digit OTP has been sent to your email."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", "Email not found."));
        }
    }

    // --- STEP 2: Verify OTP Code (Optional but good for UI flow) ---
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");

        boolean isValid = employeeService.verifyResetCode(email, code);

        if (isValid) {
            return ResponseEntity.ok(Map.of("success", true, "message", "OTP verified."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Invalid or expired OTP."));
        }
    }

    // --- STEP 3: Reset Password using the Code ---
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email"); // Now using email + code
        String code = request.get("code");
        String newPassword = request.get("newPassword");

        if (email == null || code == null || newPassword == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Email, Code, and Password are required."));
        }

        boolean success = employeeService.updatePasswordWithCode(email, code, newPassword);

        if (success) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Password updated successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Invalid OTP or session expired."));
        }
    }
}