package com.klef.fsad.sdp.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "salary_anomaly_table")
public class SalaryAnomaly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "salary_id", nullable = false)
    private Salary salary;

    @ManyToOne
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee employee;

    @Column(name = "detected_date", nullable = false)
    private LocalDate detectedDate;

    @Column(name = "anomaly_type", nullable = false)
    private String anomalyType; // SUDDEN_INCREASE, SUDDEN_DECREASE, MISSING_ATTENDANCE, UNUSUAL_DEDUCTION

    @Column(name = "current_amount", nullable = false)
    private double currentAmount;

    @Column(name = "previous_amount")
    private double previousAmount;

    @Column(name = "deviation_percentage")
    private double deviationPercentage;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "status", nullable = false)
    private String status; // PENDING, REVIEWED, RESOLVED, IGNORED

    @Column(name = "reviewed_by")
    private String reviewedBy; // username of HR/Admin

    @Column(name = "reviewed_date")
    private LocalDate reviewedDate;

    @Column(name = "resolution_notes", length = 2000)
    private String resolutionNotes;

    @Column(name = "severity")
    private String severity; // LOW, MEDIUM, HIGH, CRITICAL

    // Constructors
    public SalaryAnomaly() {
        this.detectedDate = LocalDate.now();
        this.status = "PENDING";
    }

    public SalaryAnomaly(Salary salary, Employee employee, String anomalyType,
                         double currentAmount, double previousAmount) {
        this.salary = salary;
        this.employee = employee;
        this.anomalyType = anomalyType;
        this.currentAmount = currentAmount;
        this.previousAmount = previousAmount;
        this.detectedDate = LocalDate.now();
        this.status = "PENDING";

        // Calculate deviation
        if (previousAmount > 0) {
            this.deviationPercentage = ((currentAmount - previousAmount) / previousAmount) * 100;
        }

        // Determine severity
        determineSeverity();
    }

    // Helper method to determine severity based on deviation
    private void determineSeverity() {
        double absDeviation = Math.abs(deviationPercentage);
        if (absDeviation >= 50) {
            this.severity = "CRITICAL";
        } else if (absDeviation >= 30) {
            this.severity = "HIGH";
        } else if (absDeviation >= 20) {
            this.severity = "MEDIUM";
        } else {
            this.severity = "LOW";
        }
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getDetectedDate() {
        return detectedDate;
    }

    public void setDetectedDate(LocalDate detectedDate) {
        this.detectedDate = detectedDate;
    }

    public String getAnomalyType() {
        return anomalyType;
    }

    public void setAnomalyType(String anomalyType) {
        this.anomalyType = anomalyType;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public double getPreviousAmount() {
        return previousAmount;
    }

    public void setPreviousAmount(double previousAmount) {
        this.previousAmount = previousAmount;
    }

    public double getDeviationPercentage() {
        return deviationPercentage;
    }

    public void setDeviationPercentage(double deviationPercentage) {
        this.deviationPercentage = deviationPercentage;
        determineSeverity();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public LocalDate getReviewedDate() {
        return reviewedDate;
    }

    public void setReviewedDate(LocalDate reviewedDate) {
        this.reviewedDate = reviewedDate;
    }

    public String getResolutionNotes() {
        return resolutionNotes;
    }

    public void setResolutionNotes(String resolutionNotes) {
        this.resolutionNotes = resolutionNotes;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "SalaryAnomaly{" +
                "id=" + id +
                ", anomalyType='" + anomalyType + '\'' +
                ", deviationPercentage=" + deviationPercentage +
                ", severity='" + severity + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

