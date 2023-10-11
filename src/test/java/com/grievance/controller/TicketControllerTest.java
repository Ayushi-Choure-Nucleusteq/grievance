package com.grievance.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grievance.enums.TicketStatus;
import com.grievance.enums.TicketType;
import com.grievance.indto.DepartmentDto;
import com.grievance.indto.LoginDto;
import com.grievance.indto.TicketDto;
import com.grievance.outdto.TicketOutDto;
import com.grievance.service.TicketService;

@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {

    @InjectMocks
	private TicketController ticketController;
    
    @Mock
    private TicketService ticketService;
    
    @Autowired
    private MockMvc mockMvc;
   
    private TicketDto ticketDto;
    private TicketOutDto ticketOutDto;
    

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
        ticketDto = new TicketDto();
        ticketDto.setTicketName("Test");
        ticketDto.setDescription("Description Test");
        ticketDto.setTicketType(TicketType.GRIEVANCE);
        ticketDto.setStatus(TicketStatus.OPEN);
        DepartmentDto deptDto=new DepartmentDto();
        deptDto.setDeptName("HR");
        ticketDto.setDepartment(deptDto);
        LoginDto memberDto = new LoginDto();
        memberDto.setEmail("ayushi@nucleusteq.com");
        ticketDto.setMember(memberDto);
        
        ticketOutDto = new TicketOutDto();
        ticketOutDto.setTicketName("Test");
        ticketOutDto.setDescription("Description Test");
        ticketOutDto.setTicketType(TicketType.GRIEVANCE);
        ticketOutDto.setStatus(TicketStatus.OPEN);
        ticketOutDto.setComments(null);
        ticketOutDto.setCreationDate(null);
        ticketOutDto.setLastUpdateDate(null);
        ticketOutDto.setDepartment("HR");
        ticketOutDto.setMember("Ayushi");
    }

    @Test
    public void testCreateTicket() throws Exception {
        when(ticketService.createTicket(any(TicketDto.class), anyString(), anyString())).thenReturn(ticketOutDto);

        MvcResult mvcResult = mockMvc.perform(
            post("/api/ticket/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("email", "ayushi@nucleusteq.com")  
                .header("password", "Password")   
                .content(new ObjectMapper().writeValueAsString(ticketDto))
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    
    
    @Test
    public void testGetById() throws Exception {
         Integer testTicketId = 1;
        when(ticketService.getById(anyInt(), anyString(), anyString())).thenReturn(ticketOutDto);

        MvcResult mvcResult = mockMvc.perform(
            get("/api/ticket/viewById")
                .param("ticketId", testTicketId.toString()) 
                .header("email", "ayushi@nucleusteq.com")   
                .header("password", "Password")         
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(202, status); 
    }

    
    @Test
    public void testGetAllticketsAuth() throws Exception {
        String email = "ayushi@nucleusteq.com";
        String password = "QXl1c2hpQDEyMw==";
        boolean myTicket = true;
        boolean myDeptTicket = false;
        Integer pageNo = 0; 
        List<TicketOutDto> ticketOutDtos = new ArrayList<>();
        ticketOutDtos.add(ticketOutDto);

        when(ticketService.getAllTicketsAuth(email, password, myTicket, myDeptTicket, pageNo)).thenReturn(ticketOutDtos);

        MvcResult mvcResult = mockMvc.perform(
            get("/api/ticket/getAll")
                .header("email", email)
                .header("password", password)
                .param("myTicket", String.valueOf(myTicket))
                .param("myDeptTicket", String.valueOf(myDeptTicket))
                .param("pageNo", String.valueOf(pageNo))
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());

        int status = mvcResult.getResponse().getStatus();
        assertEquals(202, status); 
    }

    @Test
    public void testGetAllticketsAuth_WithStatus() throws Exception {
        String email = "ayushi@nucleusteq.com";
        String password = "Password";
        boolean myTicket = true;
        boolean myDeptTicket = false;
        Integer pageNo = 0;
        TicketStatus providedStatus = TicketStatus.OPEN; 
        List<TicketOutDto> ticketOutDtos = new ArrayList<>();
        ticketOutDtos.add(ticketOutDto);

        when(ticketService.getAllTicketsFilter(email, password, myTicket, myDeptTicket, pageNo, Optional.of(providedStatus)))
            .thenReturn(ticketOutDtos);

        MvcResult mvcResult = mockMvc.perform(
            get("/api/ticket/getAll")
                .header("email", email)
                .header("password", password)
                .param("myTicket", String.valueOf(myTicket))
                .param("myDeptTicket", String.valueOf(myDeptTicket))
                .param("pageNo", String.valueOf(pageNo))
                .param("status", providedStatus.toString())
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(202, status); 
    }

    
    
    @Test
    public void testUpdateTicket() throws Exception {
        String email = "ayushi@nucleusteq.com";
        String password = "Password";
        Integer ticketId = 1;

        when(ticketService.updateTicket(any(TicketDto.class), eq(ticketId), eq(email), eq(password)))
            .thenReturn(ticketOutDto);

        String ticketDtoJson = new ObjectMapper().writeValueAsString(ticketDto);  
        MvcResult mvcResult = mockMvc.perform(
            put("/api/ticket/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ticketDtoJson) 
                .header("email", email)
                .header("password", password)
                .param("ticketId", ticketId.toString())
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status); 
    }


  
}
