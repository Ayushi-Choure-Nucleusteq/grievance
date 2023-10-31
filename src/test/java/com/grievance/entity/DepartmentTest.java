package com.grievance.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department();
    }

    
    @Test
    void testDeptId() {
        department.setDeptId(1);
        assertEquals(1, department.getDeptId());
    }

    @Test
    void testDeptName() {
        department.setDeptName("Finance");
        assertEquals("Finance", department.getDeptName());
    }

    @Test
    void testMembers() {
        Member member1 = new Member();
         member1.setName("Ayushi Choure");
        List<Member> members = new ArrayList<>();
        members.add(member1);
        department.setMembers(members);
        assertEquals(1, department.getMembers().size());
    }

    @Test
    void testTickets() {
        Ticket ticket1 = new Ticket();
         ticket1.setTicketName("Issue with clockin");
         List<Ticket> tickets = new ArrayList<>();
         tickets.add(ticket1);
        department.setTicket(tickets);
        assertEquals(1, department.getTicket().size());
    }

    @Test
    void testToString() {
        department.setDeptId(1);
        department.setDeptName("Finance");
        String expected = "Department [deptId=1, deptName=Finance]";
        assertEquals(expected, department.toString());
    }
    
    
    @Test
    public void testEqualsAndHashCode() {
        Department department1 = new Department();
        department1.setDeptId(1);
        department1.setDeptName("HR");

        Department department2 = new Department();
        department2.setDeptId(1);
        department2.setDeptName("HR");

        Department department3 = new Department();
        department3.setDeptId(2);
        department3.setDeptName("Finance");

        assertEquals(department1, department2);
        assertNotEquals(department1, department3);

        assertEquals(department1.hashCode(), department2.hashCode());
        assertNotEquals(department1.hashCode(), department3.hashCode());
    }
}
