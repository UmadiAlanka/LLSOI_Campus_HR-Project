package com.klef.fsad.sdp.service;

import com.klef.fsad.sdp.model.Salary;
import com.klef.fsad.sdp.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    // Fix for the DashboardController error
    public List<Salary> getSalariesByStatus(String status) {
        return salaryRepository.findByStatus(status);
    }

    public List<Salary> findAll() {
        return salaryRepository.findAll();
    }
}