package com.klef.fsad.sdp.model;


import jakarta.persistence.*;

@Entity
@Table(name="duty_table")
public class Duty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable=false)
    private String title;
    @Column(nullable=false,length=3000)
    private String description;

    @ManyToOne
    @JoinColumn(name="emp_id")
    private Employee employee;
    @ManyToOne
    @JoinColumn(name="assignedByHR")
    private HR assignedByHR;
    @ManyToOne
    @JoinColumn(name="assignedByAdmin")
    private Admin assignedByAdmin;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public HR getAssignedByHR() {
        return assignedByHR;
    }

    public void setAssignedByHR(HR assignedByHR) {
        this.assignedByHR = assignedByHR;
    }

    public Admin getAssignedByAdmin() {
        return assignedByAdmin;
    }

    public void setAssignedByAdmin(Admin assignedByAdmin) {
        this.assignedByAdmin = assignedByAdmin;
    }

    @Override
    public String toString() {
        return "Duty{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", employee=" + employee +
                ", assignedByHR=" + assignedByHR +
                ", assignedByAdmin=" + assignedByAdmin +
                '}';
    }
}
