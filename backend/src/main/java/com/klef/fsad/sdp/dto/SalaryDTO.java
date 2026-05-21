package com.klef.fsad.sdp.dto;

import java.time.LocalDate;

public class SalaryDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private int month;
    private int year;
    private double basicSalary;
    private double allowances;
    private double overtimePay;
    private int totalDaysWorked;
    private int totalLeaves;
    private double epfDeduction;
    private double etfDeduction;
    private double otherDeductions;
    private double grossSalary;
    private double netSalary;
    private String status;
    private LocalDate generatedDate;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public double getBasicSalary() { return basicSalary; }
    public void setBasicSalary(double basicSalary) { this.basicSalary = basicSalary; }
    public double getAllowances() { return allowances; }
    public void setAllowances(double allowances) { this.allowances = allowances; }
    public double getOvertimePay() { return overtimePay; }
    public void setOvertimePay(double overtimePay) { this.overtimePay = overtimePay; }
    public int getTotalDaysWorked() { return totalDaysWorked; }
    public void setTotalDaysWorked(int totalDaysWorked) { this.totalDaysWorked = totalDaysWorked; }
    public int getTotalLeaves() { return totalLeaves; }
    public void setTotalLeaves(int totalLeaves) { this.totalLeaves = totalLeaves; }
    public double getEpfDeduction() { return epfDeduction; }
    public void setEpfDeduction(double epfDeduction) { this.epfDeduction = epfDeduction; }
    public double getEtfDeduction() { return etfDeduction; }
    public void setEtfDeduction(double etfDeduction) { this.etfDeduction = etfDeduction; }
    public double getOtherDeductions() { return otherDeductions; }
    public void setOtherDeductions(double otherDeductions) { this.otherDeductions = otherDeductions; }
    public double getGrossSalary() { return grossSalary; }
    public void setGrossSalary(double grossSalary) { this.grossSalary = grossSalary; }
    public double getNetSalary() { return netSalary; }
    public void setNetSalary(double netSalary) { this.netSalary = netSalary; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getGeneratedDate() { return generatedDate; }
    public void setGeneratedDate(LocalDate generatedDate) { this.generatedDate = generatedDate; }
}