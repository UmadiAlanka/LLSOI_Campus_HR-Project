package com.klef.fsad.sdp.controller;

import com.klef.fsad.sdp.model.SalaryAnomaly;
import com.klef.fsad.sdp.service.SalaryAnomalyService;
import com.klef.fsad.sdp.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/anomalies")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "http://localhost:5173"}, allowCredentials = "true")
public class SalaryAnomalyController {

    @Autowired
    private SalaryAnomalyService anomalyService;

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getStats() {
        try {
            Map<String, Long> stats = new HashMap<>();
            stats.put("total", anomalyService.getTotalCount());
            stats.put("resolved", anomalyService.getResolvedCount());
            return ResponseEntity.ok(new ApiResponse<>(true, "Stats retrieved successfully", stats));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, "Error: " + e.getMessage(), null));
        }
    }

    @PostMapping("/detect")
    public ResponseEntity<ApiResponse<String>> runDetection() {
        try {
            anomalyService.detectAnomalies();
            long total = anomalyService.getTotalCount();
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Detection complete. Total anomalies: " + total, "SUCCESS")
            );
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, "Detection failed: " + e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<SalaryAnomaly>>> getAllAnomalies() {
        try {
            List<SalaryAnomaly> anomalies = anomalyService.getAllAnomalies();
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Anomalies retrieved successfully", anomalies)
            );
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, "Error: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SalaryAnomaly>> getAnomalyById(@PathVariable Long id) {
        try {
            SalaryAnomaly anomaly = anomalyService.getAnomalyById(id);
            if (anomaly == null) {
                return ResponseEntity.status(404)
                        .body(new ApiResponse<>(false, "Anomaly not found with ID: " + id, null));
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "Anomaly found", anomaly));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, "Error: " + e.getMessage(), null));
        }
    }

    /**
     * PUT resolve — marks RESOLVED, saves edited amounts, and updates the salary table.
     */
    @PutMapping("/{id}/resolve")
    public ResponseEntity<ApiResponse<SalaryAnomaly>> resolveAnomaly(
            @PathVariable Long id,
            @RequestBody Map<String, Object> payload) {
        try {
            double previousAmount = payload.containsKey("previousAmount")
                    ? Double.parseDouble(payload.get("previousAmount").toString()) : 0;
            double currentAmount = payload.containsKey("currentAmount")
                    ? Double.parseDouble(payload.get("currentAmount").toString()) : 0;

            SalaryAnomaly resolved = anomalyService.resolveAnomaly(id, previousAmount, currentAmount);
            return ResponseEntity.ok(new ApiResponse<>(true, "Anomaly resolved and salary updated", resolved));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, "Error resolving anomaly: " + e.getMessage(), null));
        }
    }

    /**
     * PUT ignore — marks RESOLVED without changing any salary amounts.
     */
    @PutMapping("/{id}/ignore")
    public ResponseEntity<ApiResponse<SalaryAnomaly>> ignoreAnomaly(@PathVariable Long id) {
        try {
            SalaryAnomaly ignored = anomalyService.ignoreAnomaly(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Anomaly dismissed", ignored));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, "Error ignoring anomaly: " + e.getMessage(), null));
        }
    }
}