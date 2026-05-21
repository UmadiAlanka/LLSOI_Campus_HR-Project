package com.klef.fsad.sdp.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private LocalDate date;
    private LocalTime clockInTime;
    private LocalTime clockOutTime;
    private String status;
    private Double workingHours;
    private String remarks;
    private String department;
    private String jobType;

    // Constructors, Getters, Setters
    public AttendanceDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getClockInTime() { return clockInTime; }
    public void setClockInTime(LocalTime clockInTime) { this.clockInTime = clockInTime; }
    public LocalTime getClockOutTime() { return clockOutTime; }
    public void setClockOutTime(LocalTime clockOutTime) { this.clockOutTime = clockOutTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getWorkingHours() { return workingHours; }
    public void setWorkingHours(Double workingHours) { this.workingHours = workingHours; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    // Getters and Setters
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }
}