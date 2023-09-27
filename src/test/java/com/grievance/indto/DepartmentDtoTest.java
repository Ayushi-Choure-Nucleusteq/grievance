package com.grievance.indto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepartmentDtoTest {

    private DepartmentDto departmentDto;

    @BeforeEach
    public void setUp() {
        departmentDto = new DepartmentDto();
    }

    @Test
    public void testGetDeptName() {
        departmentDto.setDeptName("Finance");
        assertEquals("Finance", departmentDto.getDeptName());
    }

    @Test
    public void testConstructor() {
        DepartmentDto dto = new DepartmentDto("HR");
        assertEquals("HR", dto.getDeptName());
    }

    @Test
    public void testToString() {
        departmentDto.setDeptName("Operations");
        String expected = "DepartmentDto [deptName=Operations]";
        assertEquals(expected, departmentDto.toString());
    }
}
