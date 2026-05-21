package com.klef.fsad.sdp;

import com.klef.fsad.sdp.dto.LoginRequest;
import com.klef.fsad.sdp.dto.LoginResponse;
import com.klef.fsad.sdp.repository.AdminRepository;
import com.klef.fsad.sdp.repository.EmployeeRepository;
import com.klef.fsad.sdp.repository.HrRepository;
import com.klef.fsad.sdp.service.AuthService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

// Junit Testing 01-Invalid Login
@ExtendWith(MockitoExtension.class)
public class InvalidLoginTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private HrRepository hrRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void invalidLoginTest() {

        LoginRequest request = new LoginRequest();

        request.setUsername("wronguser");
        request.setPassword("wrongpass");

        LoginResponse response =
                authService.authenticate(request);

        assertFalse(response.isSuccess());

        assertEquals(
                "Invalid username or password",
                response.getMessage()
        );

        System.out.println(
                "INVALID LOGIN JUNIT TEST PASSED"
        );
    }
}