package com.klef.fsad.sdp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "salary_table")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "salary_month", nullable = false)
    private int month; // 1-12

    @Column(name = "salary_year", nullable = false)
    private int year;

    @Column(name = "basic_salary", nullable = false)
    private double basicSalary;

    @Column(name = "allowances")
    private double allowances;

    @Column(name = "overtime_pay")
    private double overtimePay;

    @Column(name = "total_days_worked")
    private int totalDaysWorked;

    @Column(name = "total_leaves")
    private int totalLeaves;

    @Column(name = "epf_deduction")
    private double epfDeduction; // Employee Provident Fund (8%)

    @Column(name = "etf_deduction")
    private double etfDeduction; // Employee Trust Fund (3%)

    @Column(name = "other_deductions")
    private double otherDeductions;

    @Column(name = "gross_salary")
    private double grossSalary;

    @Column(name = "net_salary", nullable = false)
    private double netSalary;

    @Column(name = "status")
    private String status; // PENDING, APPROVED, PAID

    @Column(name = "generated_date")
    private LocalDate generatedDate;

    @Column(name = "generated_by")
    private String generatedBy; // username of who generated

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "approved_date")
    private LocalDate approvedDate;

    // Constructors
    public Salary() {
        this.generatedDate = LocalDate.now();
        this.status = "PENDING";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public double getAllowances() {
        return allowances;
    }

    public void setAllowances(double allowances) {
        this.allowances = allowances;
    }

    public double getOvertimePay() {
        return overtimePay;
    }

    public void setOvertimePay(double overtimePay) {
        this.overtimePay = overtimePay;
    }

    public int getTotalDaysWorked() {
        return totalDaysWorked;
    }

    public void setTotalDaysWorked(int totalDaysWorked) {
        this.totalDaysWorked = totalDaysWorked;
    }

    public int getTotalLeaves() {
        return totalLeaves;
    }

    public void setTotalLeaves(int totalLeaves) {
        this.totalLeaves = totalLeaves;
    }

    public double getEpfDeduction() {
        return epfDeduction;
    }

    public void setEpfDeduction(double epfDeduction) {
        this.epfDeduction = epfDeduction;
    }

    public double getEtfDeduction() {
        return etfDeduction;
    }

    public void setEtfDeduction(double etfDeduction) {
        this.etfDeduction = etfDeduction;
    }

    public double getOtherDeductions() {
        return otherDeductions;
    }

    public void setOtherDeductions(double otherDeductions) {
        this.otherDeductions = otherDeductions;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(LocalDate generatedDate) {
        this.generatedDate = generatedDate;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(String generatedBy) {
        this.generatedBy = generatedBy;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDate getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(LocalDate approvedDate) {
        this.approvedDate = approvedDate;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "id=" + id +
                ", month=" + month +
                ", year=" + year +
                ", netSalary=" + netSalary +
                ", status='" + status + '\'' +
                '}';
    }
}