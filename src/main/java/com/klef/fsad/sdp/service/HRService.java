package com.klef.fsad.sdp.service;

import com.klef.fsad.sdp.model.Employee;
import com.klef.fsad.sdp.model.HR;
import com.klef.fsad.sdp.repository.HrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HRService {

    @Autowired
    private HrRepository hrRepository; // FIX: Removed 'static'

    // Create new HR
    public HR createHR(HR hr) {
        return hrRepository.save(hr);
    }

    // FIX: Renamed to getAllHR() and removed 'static'
    // This resolves the "cannot find symbol" error in your Controller
    public List<HR> getAllHR() {
        return hrRepository.findAll();
    }

    // Get HR by ID
    public Optional<HR> getHRById(Long id) {
        return hrRepository.findById(id);
    }

    // Update HR
    public HR updateHR(Long id, HR hrDetails) {
        Optional<HR> hrOpt = hrRepository.findById(id);
        if (hrOpt.isEmpty()) {
            throw new RuntimeException("HR not found");
        }

        HR hr = hrOpt.get();
        hr.setName(hrDetails.getName());
        hr.setEmail(hrDetails.getEmail());
        hr.setUsername(hrDetails.getUsername());
        hr.setDepartment(hrDetails.getDepartment());
        hr.setContact(hrDetails.getContact());

        if (hrDetails.getPassword() != null && !hrDetails.getPassword().isEmpty()) {
            hr.setPassword(hrDetails.getPassword());
        }

        return hrRepository.save(hr);
    }

    // Delete HR
    public void deleteHR(Long id) {
        hrRepository.deleteById(id);
    }

    // Get HR by username
    public Optional<HR> getHRByUsername(String username) {
        return hrRepository.findAll().stream()
                .filter(hr -> hr.getUsername().equals(username))
                .findFirst();
    }

    // Get HR by email
    public Optional<HR> getHRByEmail(String email) {
        return hrRepository.findAll().stream()
                .filter(hr -> hr.getEmail().equals(email))
                .findFirst();
    }

    // Get HR by department
    public List<HR> getHRByDepartment(String department) {
        return hrRepository.findAll().stream()
                .filter(hr -> hr.getDepartment().equalsIgnoreCase(department))
                .toList();
    }

    // Verify HR credentials
    public Optional<HR> verifyCredentials(String username, String password) {
        return hrRepository.findAll().stream()
                .filter(hr -> hr.getUsername().equals(username) &&
                        hr.getPassword().equals(password))
                .findFirst();
    }

    // Check if username exists
    public boolean existsByUsername(String username) {
        return hrRepository.findAll().stream()
                .anyMatch(hr -> hr.getUsername().equals(username));
    }

    // Check if email exists
    public boolean existsByEmail(String email) {
        return hrRepository.findAll().stream()
                .anyMatch(hr -> hr.getEmail().equals(email));
    }

    // Get employees managed by HR
    public List<Employee> getManagedEmployees(Long hrId) {
        Optional<HR> hrOpt = hrRepository.findById(hrId);
        if (hrOpt.isEmpty()) {
            throw new RuntimeException("HR not found");
        }
        return hrOpt.get().getEmployees();
    }
}