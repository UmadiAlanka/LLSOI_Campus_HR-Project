package com.klef.fsad.sdp;

import com.klef.fsad.sdp.model.Employee;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminEditUserTest {
    // Junit Testing 03-Admin Edit User

    @Test
    void editUserTest() {

        // Existing employee
        Employee employee =
                new Employee();

        employee.setName("Old User");
        employee.setEmail("old@gmail.com");
        employee.setJob("Assistant");

        // Admin edits user details
        employee.setName("Updated User");
        employee.setEmail("updated@gmail.com");
        employee.setJob("Lecturer");

        // Verify updated values
        assertEquals(
                "Updated User",
                employee.getName()
        );

        assertEquals(
                "updated@gmail.com",
                employee.getEmail()
        );

        assertEquals(
                "Lecturer",
                employee.getJob()
        );

        assertNotEquals(
                "Old User",
                employee.getName()
        );

        System.out.println(
                "ADMIN EDIT USER JUNIT TEST PASSED"
        );
    }
}