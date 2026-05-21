package com.klef.fsad.sdp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "leave_table")
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Column(columnDefinition = "LONGTEXT")
    @JsonAlias({"file"})
    private String attachment;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private String status = "PENDING";

    @Column(name = "leave_type", nullable = false)
    @JsonAlias({"leave_type", "type", "typeOfLeave"})
    private String leaveType;

    @ManyToOne
    @JoinColumn(name = "emp_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties("leaves")
    private Employee employee;

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @JsonProperty("leave_type")
    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public void setType(String type) {
        if (this.leaveType == null || this.leaveType.isEmpty()) {
            this.leaveType = type;
        }
    }

    public void setTypeOfLeave(String typeOfLeave) {
        if (this.leaveType == null || this.leaveType.isEmpty()) {
            this.leaveType = typeOfLeave;
        }
    }

    public void setLeave_type(String leave_type) {
        if (this.leaveType == null || this.leaveType.isEmpty()) {
            this.leaveType = leave_type;
        }
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public void setFile(String file) {
        if (this.attachment == null || this.attachment.isEmpty()) {
            this.attachment = file;
        }
    }

    @Override
    public String toString() {
        return "Leave{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate




                +
                ", attachment='" + (attachment != null ? "HAS_FILE" : "NULL") + '\'' +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +



                ", leaveType='" + leaveType + '\'' +
                '}';
    }
}