package com.grievance.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.grievance.enums.TicketStatus;
import com.grievance.enums.TicketType;

public class TicketTest {

    private Ticket ticket;

    @BeforeEach
    public void setUp() {
        ticket = new Ticket();
    }

    @Test
    public void testTicketId() {
        ticket.setTicketId(1);
        assertEquals(1, ticket.getTicketId());
    }

    @Test
    public void testTicketName() {
        ticket.setTicketName("Test Ticket");
        assertEquals("Test Ticket", ticket.getTicketName());
    }

    @Test
    public void testDescription() {
        ticket.setDescription("Test Description");
        assertEquals("Test Description", ticket.getDescription());
    }

    @Test
    public void testTicketStatus() {
        ticket.setStatus(TicketStatus.OPEN);
        assertEquals(TicketStatus.OPEN, ticket.getStatus());
    }

    @Test
    public void testCreationDate() {
        ticket.setCreationDate("2023-09-22 12:00:00");
        assertEquals("2023-09-22 12:00:00", ticket.getCreationDate());
    }

    @Test
    public void testLastUpdateDate() {
        ticket.setLastUpdateDate("2023-09-23 12:00:00");
        assertEquals("2023-09-23 12:00:00", ticket.getLastUpdateDate());
    }

    @Test
    public void testTicketType() {
        ticket.setTicketType(TicketType.FEEDBACK);
        assertEquals(TicketType.FEEDBACK, ticket.getTicketType());
    }

    @Test
    public void testDepartment() {
        Department department = new Department();
        ticket.setDepartment(department);
        assertNotNull(ticket.getDepartment());
    }

    @Test
    public void testMember() {
        Member member = new Member();
        ticket.setMember(member);
        assertNotNull(ticket.getMember());
    }

    @Test
    public void testComments() {
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        ticket.setComments(Arrays.asList(comment1, comment2));
        assertNotNull(ticket.getComments());
        assertEquals(2, ticket.getComments().size());
    }

    @Test
    public void testToString() {
        ticket.setTicketId(1);
        ticket.setTicketName("Test Ticket");
        ticket.setDescription("Test Description");
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreationDate("2023-09-22 12:00:00");
        ticket.setLastUpdateDate("2023-09-23 12:00:00");
        ticket.setTicketType(TicketType.FEEDBACK);

        String expectedString = "Ticket [ticketId=1, ticketName=Test Ticket, description=Test Description, status=OPEN, creationDate=2023-09-22 12:00:00, lastUpdateDate=2023-09-23 12:00:00, ticketType=FEEDBACK]";

        assertEquals(expectedString, ticket.toString());
    }
    
    @Test
    public void testEqualsAndHashCode() {
        Ticket ticket1 = new Ticket();
        ticket1.setTicketId(1);
        ticket1.setTicketName("TestTicket");
        ticket1.setDescription("Test Description");
        ticket1.setStatus(TicketStatus.OPEN);
        ticket1.setCreationDate("2023-09-22 12:00:00");
        ticket1.setLastUpdateDate("2023-09-23 12:00:00");
        ticket1.setTicketType(TicketType.FEEDBACK);
        Department department = new Department();
        ticket1.setDepartment(department);
        Member member = new Member();
        ticket1.setMember(member);
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        ticket1.setComments(Arrays.asList(comment1, comment2));

        Ticket ticket2 = new Ticket();
        ticket2.setTicketId(1);
        ticket2.setTicketName("TestTicket");
        ticket2.setDescription("Test Description");
        ticket2.setStatus(TicketStatus.OPEN);
        ticket2.setCreationDate("2023-09-22 12:00:00");
        ticket2.setLastUpdateDate("2023-09-23 12:00:00");
        ticket2.setTicketType(TicketType.FEEDBACK);
        Department department2 = new Department();
        ticket2.setDepartment(department2);
        Member member2 = new Member();
        ticket2.setMember(member2);
        Comment comment3 = new Comment();
        Comment comment4 = new Comment();
        ticket2.setComments(Arrays.asList(comment3, comment4));
        
        Ticket ticket3 = new Ticket();
        ticket3.setTicketId(1);

        assertEquals(ticket1, ticket2);
        assertNotEquals(ticket1, ticket3);

        assertEquals(ticket1.hashCode(), ticket2.hashCode());
        assertNotEquals(ticket1.hashCode(), ticket3.hashCode());
    }
}
