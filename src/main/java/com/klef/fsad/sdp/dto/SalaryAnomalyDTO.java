package com.klef.fsad.sdp.dto;

import java.time.LocalDate;

public class SalaryAnomalyDTO {
    private Long id;
    private Long salaryId;
    private Long employeeId;
    private String employeeName;
    private LocalDate detectedDate;
    private String anomalyType;
    private double currentAmount;
    private double previousAmount;
    private double deviationPercentage;
    private String description;
    private String status;
    private String severity;
    private String resolutionNotes;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getSalaryId() { return salaryId; }
    public void setSalaryId(Long salaryId) { this.salaryId = salaryId; }
    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
    public LocalDate getDetectedDate() { return detectedDate; }
    public void setDetectedDate(LocalDate detectedDate) { this.detectedDate = detectedDate; }
    public String getAnomalyType() { return anomalyType; }
    public void setAnomalyType(String anomalyType) { this.anomalyType = anomalyType; }
    public double getCurrentAmount() { return currentAmount; }
    public void setCurrentAmount(double currentAmount) { this.currentAmount = currentAmount; }
    public double getPreviousAmount() { return previousAmount; }
    public void setPreviousAmount(double previousAmount) { this.previousAmount = previousAmount; }
    public double getDeviationPercentage() { return deviationPercentage; }
    public void setDeviationPercentage(double deviationPercentage) { this.deviationPercentage = deviationPercentage; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public String getResolutionNotes() { return resolutionNotes; }
    public void setResolutionNotes(String resolutionNotes) { this.resolutionNotes = resolutionNotes; }
}