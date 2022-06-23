package com.example.utnphones.service;

import com.example.utnphones.service.impl.JwtServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.userdetails.UserDetails;

import static com.example.utnphones.utils.MockModels.aUserDetails;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class JwtServiceTest {

    @InjectMocks
    private JwtServiceImpl jwtService;

    @Test
    void validateTokenSucceedTest() {
        UserDetails aUserDetails = aUserDetails();
        String token = jwtService.createToken(aUserDetails);

        Boolean validToken = jwtService.validateToken(token, aUserDetails);

        assertNotNull(validToken);
        assertTrue(validToken);
    }

    @Test
    void validateTokenFailTest() {
        UserDetails aUserDetails = aUserDetails();
        String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjbGllbnQiLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiQ0xJRU5UIn1dLCJleHAiOjE2NTU5MTc3NzB9.YXaomd18s0n25z2LbwrIpMuVMlGj227MWDpofE7ec0JLa4D-mqBOBwUChDyaA5-kuThWpsik2sYDYVEq7uaoAQ";

        assertThrows(ExpiredJwtException.class, () -> { jwtService.validateToken(expiredToken, aUserDetails); });
    }
}
