package com.klef.fsad.sdp.controller;

import com.klef.fsad.sdp.model.SalaryAnomaly;
import com.klef.fsad.sdp.service.SalaryAnomalyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/anomalies")
@CrossOrigin(origins = "http://localhost:3000") // Matches your Next.js port
public class SalaryAnomalyController {

    @Autowired
    private SalaryAnomalyService anomalyService;

    @GetMapping("/all")
    public ResponseEntity<List<SalaryAnomaly>> getAllAnomalies() {
        List<SalaryAnomaly> anomalies = anomalyService.getAllAnomalies();

        // Returning an empty ArrayList [] instead of null ensures Next.js receives an array
        if (anomalies == null) {
            return ResponseEntity.ok(new ArrayList<>());
        }

        return ResponseEntity.ok(anomalies);
    }
}