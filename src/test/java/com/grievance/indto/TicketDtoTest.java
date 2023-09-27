package com.grievance.indto;

import com.grievance.enums.TicketStatus;
import com.grievance.enums.TicketType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TicketDtoTest {

    private TicketDto ticketDto;

    @BeforeEach
    void setUp() {
        ticketDto = new TicketDto();
    }

    @Test
    void testTicketName() {
        ticketDto.setTicketName("Test Ticket");
        assertEquals("Test Ticket", ticketDto.getTicketName());
    }

    @Test
    void testDescription() {
        ticketDto.setDescription("Test Ticket Test Ticket");
        assertEquals("Test Ticket Test Ticket", ticketDto.getDescription());
    }

    @Test
    void testStatus() {
        ticketDto.setStatus(TicketStatus.OPEN);
        assertEquals(TicketStatus.OPEN, ticketDto.getStatus());
    }

    @Test
    void testCreationDate() {
        ticketDto.setCreationDate("2023-09-22");
        assertEquals("2023-09-22", ticketDto.getCreationDate());
    }

    @Test
    void testLastUpdateDate() {
        ticketDto.setLastUpdateDate("2023-09-23");
        assertEquals("2023-09-23", ticketDto.getLastUpdateDate());
    }

    @Test
    void testComments() {
        ticketDto.setComments("Need to fix this ASAP");
        assertEquals("Need to fix this ASAP", ticketDto.getComments());
    }

    @Test
    void testTicketType() {
        ticketDto.setTicketType(TicketType.FEEDBACK);
        assertEquals(TicketType.FEEDBACK, ticketDto.getTicketType());
    }

    @Test
    void testDepartment() {
        DepartmentDto departmentDto = new DepartmentDto();
        
        ticketDto.setDepartment(departmentDto);
        assertNotNull(ticketDto.getDepartment());
    }

    @Test
    void testMember() {
        LoginDto loginDto = new LoginDto();
        ticketDto.setMember(loginDto);
        assertNotNull(ticketDto.getMember());
    }

    @Test
    void testToString() {
        ticketDto.setTicketName("Bug in UI");
        ticketDto.setDescription("There's a glitch in the main page");
        ticketDto.setStatus(TicketStatus.OPEN);
        ticketDto.setCreationDate("2023-09-22");
        ticketDto.setLastUpdateDate("2023-09-23");
        ticketDto.setComments("Need to fix this ASAP");
        ticketDto.setTicketType(TicketType.FEEDBACK);
        DepartmentDto departmentDto = new DepartmentDto();
        ticketDto.setDepartment(departmentDto);
        LoginDto loginDto = new LoginDto();
        ticketDto.setMember(loginDto);

        String expectedString = "TicketDto [ticketName=Bug in UI, description=There's a glitch in the main page, status=OPEN, creationDate=2023-09-22, lastUpdateDate=2023-09-23, ticketType=FEEDBACK, department=" + departmentDto.toString() + ", member=" + loginDto.toString() + "]";
        assertEquals(expectedString, ticketDto.toString());
    }
}
