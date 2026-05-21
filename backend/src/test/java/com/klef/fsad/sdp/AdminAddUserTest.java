package com.klef.fsad.sdp;

import com.klef.fsad.sdp.model.Employee;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminAddUserTest {
    // Junit Testing 02-Add User
    @Test
    void addUserTest() {

        Employee employee =
                new Employee();

        employee.setName("Test User");
        employee.setAddress("Colombo");
        employee.setContactNumber("0712345678");
        employee.setJob("Lecturer");
        employee.setUsername("testuser");
        employee.setEmail("test@gmail.com");
        employee.setPassword("1234");

        assertEquals(
                "Test User",
                employee.getName()
        );

        assertEquals(
                "test@gmail.com",
                employee.getEmail()
        );

        assertNotNull(employee);

        System.out.println(
                "ADD USER JUNIT TEST PASSED"
        );
    }
}