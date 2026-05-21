package com.klef.fsad.sdp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "employee_table")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "dob")
    private String dob;

    @Column(name = "gender")
    private String gender;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "nic")
    private String nic;

    @Column(name = "date_joined")
    private String dateJoined;

    @Column(name = "role")
    private String role;

    @Column(name = "job")
    private String job;

    @Column(name = "job_type")
    private String jobType;

    @Column(name = "department")
    private String department;

    @Column(name = "address")
    private String address;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "token_expiry_date")
    private LocalDateTime tokenExpiryDate;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "employee_type")
    private String type;

    // ── Attendance: cascade delete ──────────────────────────────────────────
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendances;

    // ── Salary: cascade delete ──────────────────────────────────────────────
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Salary> salaries;

    // ── Leave: cascade delete ───────────────────────────────────────────────
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Leave> leaves;

    // ── SalaryAnomaly: cascade delete ───────────────────────────────────────
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<SalaryAnomaly> salaryAnomalies;

    // ── HR relationship (ManyToOne — no cascade needed) ─────────────────────
    @ManyToOne
    @JoinColumn(name = "hr_id")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private HR hr;

    // ── ID logic ─────────────────────────────────────────────────────────────

    public String getEmployeeId() {
        return (this.id == null) ? null : String.valueOf(this.id);
    }

    public void setEmployeeId(String employeeId) {
        if (employeeId != null && !employeeId.isEmpty()) {
            try {
                this.id = Long.parseLong(employeeId.replace("EMP", ""));
            } catch (NumberFormatException e) {
                // silent fallback
            }
        }
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNic() { return nic; }
    public void setNic(String nic) { this.nic = nic; }

    public String getDateJoined() { return dateJoined; }
    public void setDateJoined(String dateJoined) { this.dateJoined = dateJoined; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getJob() { return job; }
    public void setJob(String job) { this.job = job; }

    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getResetToken() { return resetToken; }
    public void setResetToken(String resetToken) { this.resetToken = resetToken; }

    public LocalDateTime getTokenExpiryDate() { return tokenExpiryDate; }
    public void setTokenExpiryDate(LocalDateTime tokenExpiryDate) { this.tokenExpiryDate = tokenExpiryDate; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public List<Attendance> getAttendances() { return attendances; }
    public void setAttendances(List<Attendance> attendances) { this.attendances = attendances; }

    public List<Salary> getSalaries() { return salaries; }
    public void setSalaries(List<Salary> salaries) { this.salaries = salaries; }

    public List<Leave> getLeaves() { return leaves; }
    public void setLeaves(List<Leave> leaves) { this.leaves = leaves; }

    public List<SalaryAnomaly> getSalaryAnomalies() { return salaryAnomalies; }
    public void setSalaryAnomalies(List<SalaryAnomaly> salaryAnomalies) { this.salaryAnomalies = salaryAnomalies; }

    public HR getHr() { return hr; }
    public void setHr(HR hr) { this.hr = hr; }
}