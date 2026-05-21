package com.klef.fsad.sdp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeLeaveRequestTest {
    // Junit Testing 08-Employee Leave Request
    @Test
    void requestLeaveTest() {

        // Leave request details
        String employeeName = "John Silva";
        String leaveType = "Medical Leave";
        String startDate = "2026-05-20";
        String endDate = "2026-05-22";
        String reason = "Fever";

        // Validate leave request
        assertEquals(
                "John Silva",
                employeeName
        );

        assertEquals(
                "Medical Leave",
                leaveType
        );

        assertEquals(
                "2026-05-20",
                startDate
        );

        assertEquals(
                "2026-05-22",
                endDate
        );

        assertEquals(
                "Fever",
                reason
        );

        assertNotNull(leaveType);

        System.out.println(
                "EMPLOYEE REQUEST JUNIT TEST PASSED"
        );
    }
}