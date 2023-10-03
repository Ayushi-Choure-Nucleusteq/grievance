package com.grievance.indto;

import com.grievance.enums.MemberRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemberDtoTest {

    private MemberDto memberDto;

    @BeforeEach
    void setUp() {
        memberDto = new MemberDto();
    }

    @Test
    void testName() {
        memberDto.setName("Ayushi");
        assertEquals("Ayushi", memberDto.getName());
    }

    @Test
    void testEmail() {
        memberDto.setEmail("ayushi@nucleusteq.com");
        assertEquals("ayushi@nucleusteq.com", memberDto.getEmail());
    }

    @Test
    void testPassword() {
        memberDto.setPassword("Password");
        assertEquals("Password", memberDto.getPassword());
    }

    @Test
    void testRole() {
        memberDto.setRole(MemberRole.ADMIN);
        assertEquals(MemberRole.ADMIN, memberDto.getRole());
    }

    @Test
    void testDepartment() {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDeptName("HR");
        memberDto.setDepartment(departmentDto);
        assertEquals(departmentDto, memberDto.getDepartment());
    }

    @Test
    void testToString() {
        memberDto.setName("Ayushi");
        memberDto.setEmail("ayushi@nucleusteq.com");
        memberDto.setPassword("Password");
        memberDto.setRole(MemberRole.ADMIN);
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDeptName("HR");
        memberDto.setDepartment(departmentDto);

        String expectedString = "MemberDto [name=Ayushi, email=ayushi@nucleusteq.com, password=Password, role=ADMIN, department=" + departmentDto.toString() + "]";
        assertEquals(expectedString, memberDto.toString());
    }
}
