package com.grievance.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.grievance.enums.MemberRole;

public class MemberTest {

    private Member member;

    @BeforeEach
    public void setUp() {
        member = new Member();
    }

    
    @Test
    public void testMemberId() {
        member.setId(1);
        assertEquals(1, member.getId());
    }

    @Test
    public void testMemberName() {
        member.setName("Ayushi Choure");
        assertEquals("Ayushi Choure", member.getName());
    }

    @Test
    public void testMemberEmail() {
        member.setEmail("ayushi@nucleusteq.com");
        assertEquals("ayushi@nucleusteq.com", member.getEmail());
    }

    @Test
    public void testMemberPassword() {
        member.setPassword("Password");
        assertEquals("Password", member.getPassword());
    }

    @Test
    public void testMemberRole() {
        member.setRole(MemberRole.ADMIN);
        assertEquals(MemberRole.ADMIN, member.getRole());
    }

    @Test
    public void testIsLoggedIn() {
        member.setIsLoggedIn(true);
        assertEquals(true, member.getIsLoggedIn());
    }

    @Test
    public void testDepartment() {
        Department department = new Department();
        member.setDepartment(department);
        assertNotNull(member.getDepartment());
    }

    @Test
    public void testTickets() {
        member.setTicket(new ArrayList<>());
        assertNotNull(member.getTicket());
    }

    @Test
    public void testToString() {
        Member member = new Member();
        member.setId(1);
        member.setName("Ayushi");
        member.setEmail("ayushi@nucleusteq.com");
        member.setPassword("password123");
        member.setRole(MemberRole.ADMIN);
        member.setIsLoggedIn(true);
        String expected = "Member [id=1, name=Ayushi,email=ayushi@nucleusteq.com, role=ADMIN, isLoggedIn=true]";
        assertEquals(expected, member.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        Member member1 = new Member();
        member1.setId(1);
        member1.setName("Ayushi");
        member1.setEmail("ayushi@nucleusteq.com");
        member1.setPassword("password123");
        member1.setRole(MemberRole.ADMIN);
        member1.setIsLoggedIn(true);

        Member member2 = new Member();
        member2.setId(1);
        member2.setName("Ayushi");
        member2.setEmail("ayushi@nucleusteq.com");
        member2.setPassword("password123");
        member2.setRole(MemberRole.ADMIN);
        member2.setIsLoggedIn(true);

        assertEquals(member1, member1);
        assertEquals(member1, member2);
        assertEquals(member1.hashCode(), member2.hashCode());
        assertEquals(member1.hashCode(), member2.hashCode());

        member1.setId(1);
        member2.setId(2);

        assertNotEquals(member1, member2);
        assertNotEquals(member1.hashCode(), member2.hashCode());
    }
}
