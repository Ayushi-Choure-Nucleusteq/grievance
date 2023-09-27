package com.grievance.indto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginDtoTest {

    private LoginDto loginDto;

    @BeforeEach
    void setUp() {
        loginDto = new LoginDto();
    }

    @Test
    void testEmail() {
        loginDto.setEmail("ayushi@nucleusteqs.com");
        assertEquals("ayushi@nucleusteqs.com", loginDto.getEmail());
    }

    @Test
    void testPassword() {
        loginDto.setPassword("password123");
        assertEquals("password123", loginDto.getPassword());
    }

    @Test
    void testToString() {
        loginDto.setEmail("ayushi@nucleusteqs.com");
        loginDto.setPassword("password123");
        String expected = "Login [email=ayushi@nucleusteqs.com, password=password123]";
        assertEquals(expected, loginDto.toString());
    }
}
