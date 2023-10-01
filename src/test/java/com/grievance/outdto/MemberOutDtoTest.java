package com.grievance.outdto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.grievance.enums.MemberRole;

public class MemberOutDtoTest {
	
	@Test
    public void testEquals_hashCode() {
        MemberOutDto dto1 = new MemberOutDto();
        dto1.setId(1);
        dto1.setName("Ayushi");
        dto1.setEmail("ayushi@nucleusteq.com");
        dto1.setIsLoggedIn(true);
        dto1.setRole(MemberRole.ADMIN);

        MemberOutDto dto2 = new MemberOutDto();
        dto2.setId(1);
        dto2.setName("Ayushi");
        dto2.setEmail("ayushi@nucleusteq.com");
        dto2.setIsLoggedIn(true);
        dto2.setRole(MemberRole.ADMIN);

        assertTrue(dto1.equals(dto2));
        assertTrue(dto2.equals(dto1));
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

	
	@Test
    public void testToString() {
        MemberOutDto dto = new MemberOutDto();
        dto.setId(1);
        dto.setName("Ayushi");
        dto.setIsLoggedIn(true);
        dto.setEmail("ayushi@nucleusteq.com");
        dto.setRole(MemberRole.ADMIN);
        dto.setDeptName("HR");

        String expectedString = "MemberOutDto [id=1, name=Ayushi, isLoggedIn=true, email=ayushi@nucleusteq.com, role=ADMIN, deptName=HR]";
        assertEquals(expectedString, dto.toString());
    }
}
