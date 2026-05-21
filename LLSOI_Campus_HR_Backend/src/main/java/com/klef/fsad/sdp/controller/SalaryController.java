package com.klef.fsad.sdp.controller;

import com.klef.fsad.sdp.model.Salary;
import com.klef.fsad.sdp.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/salary")
@CrossOrigin(origins = "http://localhost:3000") // This allows your Next.js app to connect
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @GetMapping("/all")
    public ResponseEntity<List<Salary>> getAllSalaries() {
        List<Salary> salaries = salaryService.findAll();

        // If the list is null, return an empty array [] so Next.js doesn't crash
        if (salaries == null) {
            return ResponseEntity.ok(new ArrayList<>());
        }

        return ResponseEntity.ok(salaries);
    }
}