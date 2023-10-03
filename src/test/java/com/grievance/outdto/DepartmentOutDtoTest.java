package com.grievance.outdto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DepartmentOutDtoTest {
	
	@Test
    public void testEquals_haschCode() {
        DepartmentOutDto dto1 = new DepartmentOutDto();
        dto1.setDeptId(1);
        dto1.setDeptName("HR");

        DepartmentOutDto dto2 = new DepartmentOutDto();
        dto2.setDeptId(1);
        dto2.setDeptName("HR");

        assertTrue(dto1.equals(dto2));
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

	
	@Test
    public void testToString() {
        DepartmentOutDto dto = new DepartmentOutDto();
        dto.setDeptId(1);
        dto.setDeptName("HR");

        String expected = "DepartmentOutDto [deptId=1, deptName=HR]";
        assertEquals(expected, dto.toString());
    }
}
