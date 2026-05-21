package com.klef.fsad.sdp.service;

import com.klef.fsad.sdp.model.Admin;
import com.klef.fsad.sdp.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private static AdminRepository adminRepository;

    // Create new admin
    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    // Get all admins
    public static List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // Get admin by ID - CHANGED int to String
    public Optional<Admin> getAdminById(String id) {
        return adminRepository.findById(Integer.valueOf(id));
    }

    // Update admin - CHANGED int to String
    public Admin updateAdmin(String id, Admin adminDetails) {
        Optional<Admin> adminOpt = adminRepository.findById(Integer.valueOf(id));
        if (adminOpt.isEmpty()) {
            throw new RuntimeException("Admin not found");
        }

        Admin admin = adminOpt.get();
        admin.setUsername(adminDetails.getUsername());
        admin.setEmail(adminDetails.getEmail());
        if (adminDetails.getPassword() != null && !adminDetails.getPassword().isEmpty()) {
            admin.setPassword(adminDetails.getPassword());
        }

        return adminRepository.save(admin);
    }

    // Delete admin - CHANGED int to String
    public void deleteAdmin(String id) {
        adminRepository.deleteById(Integer.valueOf(id));
    }

    // The rest of your methods (existsByUsername, verifyCredentials, etc.)
    // are fine because they don't use the ID.

    public Optional<Admin> verifyCredentials(String username, String password) {
        return adminRepository.findAll().stream()
                .filter(admin -> admin.getUsername().equals(username) &&
                        admin.getPassword().equals(password))
                .findFirst();
    }
}