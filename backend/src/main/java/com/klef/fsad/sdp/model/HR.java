package com.klef.fsad.sdp.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "hr_table")
public class HR {

    @Id
    @Column(name = "hr_id")
    private Long id;


    private String name;
    private String email;
    private String username;
    private String password;
    private String department;
    private String contact;

    @OneToMany(mappedBy = "hr", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonManagedReference
    private List<Employee> employees;

    // Getters & Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public List<Employee> getEmployees() { return employees; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }
}
