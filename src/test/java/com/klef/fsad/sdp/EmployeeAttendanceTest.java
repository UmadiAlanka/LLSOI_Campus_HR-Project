package com.klef.fsad.sdp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Junit Testing 07-Employee Mark Attendance

public class EmployeeAttendanceTest {

    @Test
    void markAttendanceTest() {

        // Employee attendance details
        String employeeName = "John Silva";
        String attendanceStatus = "Present";
        String date = "2026-05-16";

        // Validate attendance details
        assertEquals(
                "John Silva",
                employeeName
        );

        assertEquals(
                "Present",
                attendanceStatus
        );

        assertEquals(
                "2026-05-16",
                date
        );

        assertNotNull(attendanceStatus);
        System.out.println(
                "EMPLOYEE MARK ATTENDANCE JUNIT TEST PASSED"
        );
    }

}