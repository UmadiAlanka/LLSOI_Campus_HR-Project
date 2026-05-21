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
// Allowing multiple possible local ports to avoid CORS blocks
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
            response.setMessage("An error occurred during login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Step 1 - Send reset link to email
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Email is required."));
        }

        boolean emailSent = employeeService.processForgotPassword(email);

        if (emailSent) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Reset link sent to your email."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", "Email not found."));
        }
    }

    // Step 2 - Process the actual password change
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");

        if (token == null || newPassword == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Token and password are required."));
        }

        boolean success = employeeService.updatePassword(token, newPassword);

        if (success) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Password updated successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Invalid or expired token."));
        }
    }
}