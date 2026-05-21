package com.klef.fsad.sdp.controller;

import com.klef.fsad.sdp.dto.ApiResponse;
import com.klef.fsad.sdp.model.Salary;
import com.klef.fsad.sdp.service.SalaryService;
import com.klef.fsad.sdp.service.PdfService; // ADD THIS IMPORT
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders; // ADD THIS IMPORT
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType; // ADD THIS IMPORT
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payroll")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "http://localhost:5173"}, allowCredentials = "true")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private PdfService pdfService; // INJECT THE PDF SERVICE

    // --- NEW DOWNLOAD ENDPOINT ---

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadPaySlip(@PathVariable Long id) {
        try {
            Salary salary = salaryService.getSalaryById(id);
            if (salary == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Generate the PDF bytes using the service we created
            byte[] pdfContents = pdfService.generatePaySlip(salary);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            // This filename is what the user sees when saving the file
            String filename = "PaySlip_" + salary.getEmployee().getEmployeeId() + ".pdf";
            headers.setContentDispositionFormData("attachment", filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(pdfContents, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // --- NEW SAVE ENDPOINT (Fixes the POST error) ---
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Salary>> saveSalary(@RequestBody Salary salary) {
        try {
            // This calls your service to save the record to the database
            Salary savedSalary = salaryService.saveSalary(salary);
            return ResponseEntity.ok(new ApiResponse<>(true, "Salary record added successfully", savedSalary));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to save: " + e.getMessage()));
        }
    }


    // --- GET ALL RECORDS ---
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Salary>>> getAllSalaries() {
        try {
            List<Salary> salaries = salaryService.getAllSalaries();
            return ResponseEntity.ok(new ApiResponse<>(true, "Payroll records retrieved", salaries));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error: " + e.getMessage()));
        }
    }

    // --- GET BY ID ---
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Salary>> getSalaryById(@PathVariable Long id) {
        try {
            Salary salary = salaryService.getSalaryById(id);
            if (salary != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Salary record found", salary));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Salary not found with ID: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error: " + e.getMessage()));
        }
    }

    // --- UPDATE RECORD ---
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<String>> updateSalary(@PathVariable Long id, @RequestBody Salary updatedData) {
        try {
            salaryService.updateSalaryDetails(id, updatedData.getBasicSalary(), updatedData.getNetSalary());
            return ResponseEntity.ok(new ApiResponse<>(true, "Salary updated successfully", "Record " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Update failed: " + e.getMessage()));
        }
    }


    @PutMapping("/approve/{id}")
    public ResponseEntity<ApiResponse<String>> approveSalary(@PathVariable Long id) {
        try {
            salaryService.approveSalary(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Payroll approved", "Status: APPROVED"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    // --- ADDITIONAL DASHBOARD & FILTERING ENDPOINTS (Kept for integration) ---

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<Salary>>> getEmployeeSalaries(@PathVariable Long employeeId) {
        try {
            List<Salary> salaries = salaryService.getEmployeeSalaries(employeeId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Employee salaries retrieved", salaries));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<List<Salary>>> getSalarySummary(@RequestParam int month, @RequestParam int year) {
        try {
            List<Salary> salaries = salaryService.getSalariesByMonthAndYear(month, year);
            return ResponseEntity.ok(new ApiResponse<>(true, "Summary retrieved", salaries));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    // Check that this is exactly your mapping in SalaryController
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSalaryRecord(@PathVariable Long id) {
        try {
            salaryService.deleteSalaryById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Deleted successfully", "ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Delete failed: " + e.getMessage()));
        }
    }

}