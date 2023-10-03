package com.grievance.outdto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.grievance.enums.TicketStatus;
import com.grievance.enums.TicketType;

public class TicketOutDtoTest {

	
	@Test
    public void testEqualsDifferentObjects() {
        TicketOutDto dto1 = new TicketOutDto();
        dto1.setTicketId(1);
        dto1.setTicketName("Sample Ticket");
        dto1.setDescription("Description");
        dto1.setLastUpdateDate(null);
        dto1.setLastUpdateDate(null);
        dto1.setMember("Ayushi");
        dto1.setStatus(TicketStatus.BEING_ADDRESSED);
        dto1.setTicketType(TicketType.FEEDBACK);
        dto1.setComments(null);
        dto1.setDepartment("HR");

        TicketOutDto dto2 = new TicketOutDto();
        dto2.setTicketId(1);
        dto2.setTicketName("Sample Ticket");
        dto2.setDescription("Description");
        dto2.setLastUpdateDate(null);
        dto2.setLastUpdateDate(null);
        dto2.setMember("Ayushi");
        dto2.setStatus(TicketStatus.BEING_ADDRESSED);
        dto2.setTicketType(TicketType.FEEDBACK);
        dto2.setComments(null);
        dto2.setDepartment("HR");

        assertTrue(dto1.equals(dto2));
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
	
	@Test
	public void testToString() {
        TicketOutDto dto = new TicketOutDto();
        dto.setTicketId(1);
        dto.setTicketName("Sample Ticket");
        dto.setDescription("Description");
        dto.setCreationDate(null);
        dto.setLastUpdateDate(null);
        dto.setMember("Ayushi");
        dto.setStatus(TicketStatus.BEING_ADDRESSED);
        dto.setTicketType(TicketType.FEEDBACK);
        dto.setComments(null);
        dto.setDepartment("HR");

        String expected = "TicketOutDto [ticketId=1, ticketName=Sample Ticket,"
            + " description=Description, status=BEING_ADDRESSED,"
            + " creationDate=null, lastUpdateDate=null, ticketType=FEEDBACK,"
            + " member=Ayushi, department=HR, comments=null]";
        assertEquals(expected, dto.toString());
    }
}
