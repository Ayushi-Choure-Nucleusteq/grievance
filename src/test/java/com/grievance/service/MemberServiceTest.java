package com.grievance.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.grievance.outdto.MemberOutDto;
import com.grievance.entity.Department;
import com.grievance.entity.Member;
import com.grievance.entity.Ticket;
import com.grievance.enums.MemberRole;
import com.grievance.exception.RecordAlreadyExistException;
import com.grievance.exception.ResourceNotFoundException;
import com.grievance.exception.UnauthorizedException;
import com.grievance.indto.ChangePasswordDto;
import com.grievance.indto.DepartmentDto;
import com.grievance.indto.LoginDto;
import com.grievance.indto.MemberDto;
import com.grievance.repository.DepartmentRepo;
import com.grievance.repository.MemberRepo;
import com.grievance.serviceimpl.Conversion;
import com.grievance.serviceimpl.DecodePassword;
import com.grievance.serviceimpl.MemberServiceImpl;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    private MemberServiceImpl memberService;

    @Mock
    private MemberRepo memberRepo;

    @Mock
    private DepartmentRepo deptRepo;
    
    @Spy
    private DecodePassword decodePassword;
    
    @Spy
    Conversion conversion;

    
    private Member member;
    private Department department;
    private MemberDto memberDto;
    private MemberOutDto expected;
    private LoginDto loginDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        member = new Member();
        member.setId(1);
        member.setName("Twinkle");
        member.setEmail("twinkle@nucleusteq.com");
        member.setPassword("QXl1c2hpQDEyMw==");
        member.setRole(MemberRole.ADMIN);
        department = new Department();
        department.setDeptId(1);
        department.setDeptName("HR");
        member.setDepartment(department);
        List<Ticket> tickets = new ArrayList<>();
        member.setTicket(tickets);
        member.setIsLoggedIn(false);
        
        loginDto = new LoginDto();
        loginDto.setEmail("twinkle@nucleusteq.com");
        loginDto.setPassword("QXl1c2hpQDEyMw==");  
        
      memberDto = new MemberDto();
      memberDto.setName("Twinkle");
      memberDto.setEmail("twinkle@nucleusteq.com");
      memberDto.setPassword("QXl1c2hpQDEyMw==");
      memberDto.setRole(MemberRole.ADMIN);
      DepartmentDto deptDto = new DepartmentDto();
      deptDto.setDeptName("HR");
      memberDto.setDepartment(deptDto);
      
      expected = new MemberOutDto();
      expected.setId(member.getId());
      expected.setName(member.getName());
      expected.setEmail(member.getEmail());
      expected.setDeptName(member.getDepartment().getDeptName());
      expected.setIsLoggedIn(member.getIsLoggedIn());
      expected.setRole(member.getRole());
    }
    
    
    @Test
    public void testCreateMemberAuth_MemberAlreadyExists() {
     
        when(memberRepo.findByEmail(memberDto.getEmail())).thenReturn(member);
        
        assertThrows(RecordAlreadyExistException.class, () -> memberService.createMemberAuth(memberDto));
    }

    @Test
    public void testCreateMemberAuth_DepartmentDoesNotExist() {
       
        when(memberRepo.findByEmail(memberDto.getEmail())).thenReturn(null);
        when(deptRepo.findByDeptName(memberDto.getDepartment().getDeptName())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> memberService.createMemberAuth(memberDto));
    }

    @Test
    public void testCreateMemberAuth_PasswordIsNull() {

        memberDto.setPassword(null);
        when(memberRepo.findByEmail(memberDto.getEmail())).thenReturn(null);
        when(deptRepo.findByDeptName(memberDto.getDepartment().getDeptName())).thenReturn(department);

        assertThrows(IllegalArgumentException.class, () -> memberService.createMemberAuth(memberDto));
    }

    @Test
    public void testCreateMemberAuth_PasswordDoesNotMatchPattern() {
      
        memberDto.setPassword("InvalidPassword");
//        when(decodePassword.decodePassword(memberDto.getPassword())).thenReturn("InvalidPassword");
        when(memberRepo.findByEmail(memberDto.getEmail())).thenReturn(null);
        when(deptRepo.findByDeptName(memberDto.getDepartment().getDeptName())).thenReturn(department);

        assertThrows(IllegalArgumentException.class, () -> memberService.createMemberAuth(memberDto));
    }

    @Test
    public void testCreateMemberAuth_Success() {

        when(memberRepo.findByEmail(memberDto.getEmail())).thenReturn(null);
        when(deptRepo.findByDeptName(memberDto.getDepartment().getDeptName())).thenReturn(department);
        when(memberRepo.save(any(Member.class))).thenReturn(member);
//        when(decodePassword.decodePassword(memberDto.getPassword())).thenReturn("ValidPassword");
        
        MemberOutDto result = memberService.createMemberAuth(memberDto);

        assertEquals(expected, result);
    }

    @Test
    public void testGetAllMember_NoMembers() {
        when(memberRepo.findAll(any(Pageable.class))).thenReturn(Page.empty());

        assertThrows(ResourceNotFoundException.class, () -> memberService.getAllMember(0));
    }

    @Test
    public void testGetAllMember_Success() {
        List<Member> membersList = new ArrayList<>();
        membersList.add(member);
        Page<Member> membersPage = new PageImpl<>(membersList);
        
        when(memberRepo.findAll(any(Pageable.class))).thenReturn(membersPage);

        List<MemberOutDto> result = memberService.getAllMember(0);

        assertEquals(1, result.size());
        assertEquals(expected, result.get(0));
    }
   
    @Test
    public void testLoginMember_MemberNotFound() {
        when(memberRepo.findByEmail(loginDto.getEmail())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> memberService.loginMember(loginDto));
    }

    @Test
    public void testLoginMember_InvalidCredentials() {
    	loginDto.setPassword("NotValidPass");
        when(memberRepo.findByEmail(loginDto.getEmail())).thenReturn(member);
//        when(decodePassword.decodePassword(loginDto.getPassword())).thenReturn("IncorrectPassword");
//        when(decodePassword.decodePassword(member.getPassword())).thenReturn("EncodedPassword");

        assertThrows(UnauthorizedException.class, () -> memberService.loginMember(loginDto));
    }

    @Test
    public void testLoginMember_Success_NotLoggedIn() {
        when(memberRepo.findByEmail(loginDto.getEmail())).thenReturn(member);
//        when(decodePassword.decodePassword(loginDto.getPassword())).thenReturn("CorrectPassword");
//        when(decodePassword.decodePassword(member.getPassword())).thenReturn("CorrectPassword");
        when(memberRepo.save(any(Member.class))).thenReturn(member);

        MemberOutDto result = memberService.loginMember(loginDto);

        assertTrue(result.getIsLoggedIn());
    }

    @Test
    public void testLoginMember_Success_AlreadyLoggedIn() {
        member.setIsLoggedIn(true);
        when(memberRepo.findByEmail(loginDto.getEmail())).thenReturn(member);
//        when(decodePassword.decodePassword(loginDto.getPassword())).thenReturn("CorrectPassword");
//        when(decodePassword.decodePassword(member.getPassword())).thenReturn("CorrectPassword");

        MemberOutDto result = memberService.loginMember(loginDto);

        assertFalse(result.getIsLoggedIn());
    }

    
    @Test
    public void testChangePasswordNewPasswordDoesNotMatchPattern() {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setOldPassword("QXl1c2hpQDEyMw==");
        changePasswordDto.setNewPassword("invalid_password");

        when(memberRepo.findByEmail(anyString())).thenReturn(member);
        String pass="Password";

        assertThrows(IllegalArgumentException.class, () -> {
            memberService.changePassword(changePasswordDto, "twinkle@nucleusteq.com", pass);
        });
    }
    
    @Test
    public void testChangePasswordNewPasswordIsNull() {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setOldPassword("QXl1c2hpQDEyMw==");
        changePasswordDto.setNewPassword(null);

        when(memberRepo.findByEmail(anyString())).thenReturn(member);

        assertThrows(IllegalArgumentException.class, () -> {
            memberService.changePassword(changePasswordDto, "twinkle@nucleusteq.com", "Twinkle@123");
        });
    }
    
    @Test
    public void testChangePasswordWrongOldPassword() {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setOldPassword("Password");
        changePasswordDto.setNewPassword("QXl1c2hpQDEyMw==");

        when(memberRepo.findByEmail(anyString())).thenReturn(member);

        assertThrows(UnauthorizedException.class, () -> {
            memberService.changePassword(changePasswordDto, "twinkle@nucleusteq.com", "Password");
        });
    }
    
    
    @Test
    public void testChangePasswordSuccess() {
    	 ChangePasswordDto changePasswordDto = new ChangePasswordDto();
    	    changePasswordDto.setOldPassword("QXl1c2hpQDEyMw==");
    	    changePasswordDto.setNewPassword("QXl1c2hpQDEyMw==");

    	    when(memberRepo.findByEmail(anyString())).thenReturn(member);
    	    when(memberRepo.save(any(Member.class))).thenReturn(member);
    	    
    	    MemberOutDto result = memberService.changePassword(changePasswordDto, "twinkle@nucleusteq.com", "QXl1c2hpQDEyMw==");
//    	    MemberOutDto expected = conversion.memberToOutDto(member);

    	    assertEquals(expected, result);
    }
    
    @Test
    public void testDeleteMember_Success() {
        Member mockMember = new Member();
        mockMember.setEmail("ayushi@nucleusteq.com");

        when(memberRepo.findByEmail(anyString())).thenReturn(mockMember);

        memberService.deleteMember(mockMember.getEmail());

        verify(memberRepo, times(1)).delete(mockMember);
    }

    @Test
    public void testDeleteMember_MemberNotFound() {
        when(memberRepo.findByEmail(anyString())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> memberService.deleteMember("ayushi@nucleustq.com"));

        verify(memberRepo, never()).delete(any());
    }

}
