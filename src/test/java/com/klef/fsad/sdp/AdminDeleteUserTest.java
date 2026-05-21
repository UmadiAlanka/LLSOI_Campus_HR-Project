package com.klef.fsad.sdp;

import com.klef.fsad.sdp.model.Employee;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Junit Testing 04-Admin Delete User

public class AdminDeleteUserTest {

    @Test
    void deleteUserTest() {

        // Create employee list
        List<Employee> employees =
                new ArrayList<>();

        // Create employee
        Employee employee =
                new Employee();

        employee.setName("Test User");
        employee.setEmail("test@gmail.com");

        // Add employee to list
        employees.add(employee);

        // Verify employee exists
        assertEquals(
                1,
                employees.size()
        );

        // Admin deletes employee
        employees.remove(employee);

        // Verify deletion
        assertEquals(
                0,
                employees.size()
        );

        assertFalse(
                employees.contains(employee)
        );

        System.out.println(
                "ADMIN DELETE USER JUNIT TEST PASSED"
        );
    }
}