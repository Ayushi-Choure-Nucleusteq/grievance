package com.grievance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;



import com.grievance.enums.MemberRole;
import com.grievance.indto.ChangePasswordDto;
import com.grievance.indto.DepartmentDto;
import com.grievance.indto.LoginDto;
import com.grievance.indto.MemberDto;
import com.grievance.outdto.MemberOutDto;
import com.grievance.repository.MemberRepo;
import com.grievance.service.MemberService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.List;

//@WebMvcTest(MemberController.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class MemberControllerTest {
	
	@InjectMocks
	private MemberController memberController;
	
	@Mock
    private MemberService memberService;
	
	@Mock
    private MemberRepo memberRepo; 
	
	@Autowired
    private MockMvc mockMvc;
	
	private MemberOutDto memberOutDto;
	private MemberDto memberDto;
	private LoginDto loginDto;
	private ChangePasswordDto changePasswordDto;
	
	@BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();

        memberOutDto = new MemberOutDto();
        memberOutDto.setName("Sneha");
        memberOutDto.setIsLoggedIn(false);
        memberOutDto.setEmail("sneha@nucleusteq.com");
        memberOutDto.setRole(MemberRole.ADMIN);
        memberOutDto.setDeptName("Technical");
        memberOutDto.setId(1);

        memberDto = new MemberDto();
        memberDto.setName("Sneha");
        memberDto.setEmail("sneha@nucleusteq.com");
        memberDto.setPassword("Sneha@123");
        memberDto.setRole(MemberRole.ADMIN);
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDeptName("Technical");
        memberDto.setDepartment(departmentDto);

        loginDto = new LoginDto();
        loginDto.setEmail("sneha@nucleusteq.com");
        loginDto.setPassword("Sneha@123");

        changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setNewPassword("Sneha123");
        changePasswordDto.setOldPassword("Sneha@123");
    }
	
	
    @Test
    public void testLoginMember() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("sneha@nucleusteq.com");
        loginDto.setPassword("Sneha@123");

        when(memberService.loginMember(loginDto)).thenReturn(memberOutDto);

        ObjectMapper objectMapper = new ObjectMapper();
		String inputJSON = objectMapper
				.writeValueAsString(loginDto);
      MvcResult result = mockMvc.perform(post("/api/member/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(inputJSON))
            .andReturn();
      assertEquals(202, result.getResponse().getStatus());
    }
  
    
    @Test
    public void testCreateMemberAuth() throws Exception {
        
        when(memberService.createMemberAuth(memberDto)).thenReturn(memberOutDto);

        ObjectMapper objectMapper = new ObjectMapper();
		String inputJSON = objectMapper
				.writeValueAsString(memberDto);
        MvcResult result = mockMvc.perform(post("/api/member/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJSON))
                .andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(201, status);
    }
    
    @Test
    public void testChangePassword() throws Exception {
        ChangePasswordDto inDto = new ChangePasswordDto();
        inDto.setNewPassword("Sneha123");
        inDto.setOldPassword("Sneha@123");
        when(memberService.changePassword(inDto, "sneha@nucleusteq.com", "Sneha@123")).thenReturn(memberOutDto);

        ObjectMapper objectMapper = new ObjectMapper();
		String inputJSON = objectMapper
				.writeValueAsString(inDto);
        MvcResult result = mockMvc.perform(put("/api/member/changePassword")
    		    .header("email", "sneha@nucleusteq.com")
    	        .header("password", "Sneha@123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJSON))
                .andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(200, status);
    }


    @Test
    public void testGetAllMembers() throws Exception {
        List<MemberOutDto> memberList = new ArrayList<>();
        memberList.add(memberOutDto);
        when(memberService.getAllMember(0)).thenReturn(memberList);

        MvcResult result = mockMvc.perform(get("/api/member/getAll")
                .param("pageNo", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(202, result.getResponse().getStatus());
    }

    
    @Test
    public void testDeleteMember() throws Exception {
        String email = "ayushi@nucleusteq.com";

        doNothing().when(memberService).deleteMember(email);

        MvcResult result = mockMvc.perform(delete("/api/member/delete")
                .param("email", email)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        assertEquals(200, result.getResponse().getStatus());
    }


}