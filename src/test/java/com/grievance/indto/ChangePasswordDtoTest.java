package com.grievance.indto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChangePasswordDtoTest {

    private ChangePasswordDto changePasswordDto;

    @BeforeEach
    public void setUp() {
        changePasswordDto = new ChangePasswordDto();
    }

    @Test
    public void testGetOldPassword() {
        changePasswordDto.setOldPassword("Password");
        assertEquals("Password", changePasswordDto.getOldPassword());
    }

    @Test
    public void testGetNewPassword() {
        changePasswordDto.setNewPassword("newPassword");
        assertEquals("newPassword", changePasswordDto.getNewPassword());
    }
}
