package com.klef.fsad.sdp.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendance_table")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FIX: JsonIgnoreProperties stops Employee from loading the list of Attendances again.
    @ManyToOne
    @JoinColumn(name = "emp_id", nullable = false)
    @JsonIgnoreProperties("attendances")
    private Employee employee;

    @Column(name = "attendance_date", nullable = false)
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime clockInTime;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime clockOutTime;

    private String status;
    private String type;
    private String course;
    private Double workingHours;
    private String remarks;
    private String markedBy;
    private LocalDate lastModified;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getClockInTime() { return clockInTime; }
    public void setClockInTime(LocalTime clockInTime) { this.clockInTime = clockInTime; }
    public LocalTime getClockOutTime() { return clockOutTime; }
    public void setClockOutTime(LocalTime clockOutTime) { this.clockOutTime = clockOutTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    public Double getWorkingHours() { return workingHours; }
    public void setWorkingHours(Double workingHours) { this.workingHours = workingHours; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public String getMarkedBy() { return markedBy; }
    public void setMarkedBy(String markedBy) { this.markedBy = markedBy; }
    public LocalDate getLastModified() { return lastModified; }
    public void setLastModified(LocalDate lastModified) { this.lastModified = lastModified; }
}