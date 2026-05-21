package com.klef.fsad.sdp;

import com.klef.fsad.sdp.model.Employee;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Junit Testing 05-HR Add Employee

public class HrAddEmployeeTest {

    @Test
    void addEmployeeTest() {

        // HR creates employee
        Employee employee =
                new Employee();

        employee.setName("John Silva");
        employee.setAddress("Colombo");
        employee.setContactNumber("0771234567");
        employee.setJob("Software Engineer");
        employee.setUsername("john123");
        employee.setEmail("john@gmail.com");
        employee.setPassword("1234");

        // Verify employee details
        assertEquals(
                "John Silva",
                employee.getName()
        );

        assertEquals(
                "Software Engineer",
                employee.getJob()
        );

        assertEquals(
                "john@gmail.com",
                employee.getEmail()
        );

        assertNotNull(employee);

        System.out.println(
                "HR ADD EMPLOYEE JUNIT TEST PASSED"
        );
    }
}