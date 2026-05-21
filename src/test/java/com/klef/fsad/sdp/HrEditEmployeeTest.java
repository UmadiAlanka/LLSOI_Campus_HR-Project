package com.klef.fsad.sdp;

import com.klef.fsad.sdp.model.Employee;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
// Junit Testing 06-HR Edit Employee
public class HrEditEmployeeTest {

    @Test
    void editEmployeeTest() {

        // Existing employee
        Employee employee =
                new Employee();

        employee.setName("John Silva");
        employee.setJob("Assistant");
        employee.setEmail("john@gmail.com");

        // HR edits employee details
        employee.setName("John Updated");
        employee.setJob("Senior Lecturer");
        employee.setEmail("johnupdated@gmail.com");

        // Verify updated details
        assertEquals(
                "John Updated",
                employee.getName()
        );

        assertEquals(
                "Senior Lecturer",
                employee.getJob()
        );

        assertEquals(
                "johnupdated@gmail.com",
                employee.getEmail()
        );

        // Verify old values changed
        assertNotEquals(
                "Assistant",
                employee.getJob()
        );

        System.out.println(
                "HR EDIT EMPLOYEE JUNIT TEST PASSED"
        );

    }
}