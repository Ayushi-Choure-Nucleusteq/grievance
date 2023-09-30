package com.grievance.service;

import static org.junit.Assert.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.grievance.entity.Comment;
import com.grievance.entity.Department;
import com.grievance.entity.Member;
import com.grievance.entity.Ticket;
import com.grievance.enums.MemberRole;
import com.grievance.enums.TicketStatus;
import com.grievance.enums.TicketType;
import com.grievance.exception.CannotEditTicketException;
import com.grievance.exception.ResourceNotFoundException;
import com.grievance.exception.UnauthorizedException;
import com.grievance.indto.DepartmentDto;
import com.grievance.indto.LoginDto;
import com.grievance.indto.TicketDto;
import com.grievance.outdto.TicketOutDto;
import com.grievance.repository.DepartmentRepo;
import com.grievance.repository.MemberRepo;
import com.grievance.repository.TicketRepo;
import com.grievance.serviceimpl.Conversion;
import com.grievance.serviceimpl.TicketServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

	@InjectMocks
	private TicketServiceImpl ticketService;

	@Mock
	private TicketRepo ticketRepo;

	@Mock
	private MemberRepo memberRepo;

	@Mock
	private DepartmentRepo deptRepo;
	
	@Spy
    Conversion conversion;

	private Member testMember;
	private LoginDto loginDto;
	private Ticket ticket;
	private TicketDto ticketDto;
	private DepartmentDto deptDto;
	private Department dept;
	private TicketOutDto expected;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		dept = new Department();
		dept.setDeptId(1);
		dept.setDeptName("HR");

		deptDto = new DepartmentDto();
		deptDto.setDeptName("HR");

		ticket = new Ticket();
		ticket.setComments(new ArrayList<>());
		ticket.setCreationDate(null);
		ticket.setDescription("Description");
		ticket.setLastUpdateDate(null);
		ticket.setDepartment(dept);

		loginDto = new LoginDto();
		loginDto.setEmail("ayushi@nucleusteq.com");
		loginDto.setPassword("QXl1c2hpQDEyMw==");

		testMember = new Member();
		testMember.setId(1);
		testMember.setName("Ayushi");
		testMember.setIsLoggedIn(false);
		testMember.setEmail("ayushi@nucleusteq.com");
		testMember.setRole(MemberRole.ADMIN);
		testMember.setDepartment(dept);
		testMember.setPassword("QXl1c2hpQDEyMw==");

		ticket.setMember(testMember);
		ticket.setStatus(TicketStatus.OPEN);
		ticket.setTicketId(1);
		ticket.setTicketName("Title");
		ticket.setTicketType(TicketType.GRIEVANCE);
		ticket.setDepartment(dept);

		ticketDto = new TicketDto();
		ticketDto.setCreationDate(null);
		ticketDto.setDepartment(deptDto);
		ticketDto.setDescription("This is description");
		ticketDto.setMember(loginDto);
		ticketDto.setStatus(TicketStatus.OPEN);
		ticketDto.setTicketName("This title");
		ticketDto.setTicketType(TicketType.GRIEVANCE);
//		ticketDto.setComments("");
		
		    expected = new TicketOutDto();
		    expected.setTicketId(ticket.getTicketId());
		    expected.setTicketName(ticket.getTicketName());
		    expected.setDescription(ticket.getDescription());
		    expected.setTicketType(ticket.getTicketType());
		    expected.setStatus(ticket.getStatus());
		    expected.setCreationDate(ticket.getCreationDate());
		    expected.setLastUpdateDate(ticket.getLastUpdateDate());
		    expected.setDepartment(ticket.getDepartment().getDeptName());
		    expected.setMember(ticket.getMember().getName());
		    expected.setComments(new ArrayList<>());
	}
	

	    @Test
	    public void testCreateTicket_MemberNotFound() {
	        when(memberRepo.findByEmail(ticketDto.getMember().getEmail())).thenReturn(null);

	        assertThrows(ResourceNotFoundException.class, () -> ticketService.createTicket(ticketDto, "ayushi@nucleusteq.com", "password"));
	    }

	    @Test
	    public void testCreateTicket_DepartmentNotFound() {
	    	
	        when(memberRepo.findByEmail(ticketDto.getMember().getEmail())).thenReturn(testMember);
	        when(deptRepo.findByDeptName(ticketDto.getDepartment().getDeptName())).thenReturn(null);

	        assertThrows(ResourceNotFoundException.class, () -> ticketService.createTicket(ticketDto, "ayushi@nucleusteq.com", "QXl1c2hpQDEyMw=="));
	    }

	    @Test
	    public void testCreateTicket_Success() {
	    	
	        when(memberRepo.findByEmail(ticketDto.getMember().getEmail())).thenReturn(testMember);
	        when(deptRepo.findByDeptName(ticketDto.getDepartment().getDeptName())).thenReturn(dept);
	        when(ticketRepo.save(any(Ticket.class))).thenReturn(ticket);

	        TicketOutDto result = ticketService.createTicket(ticketDto, "ayushi@nucleusteq.com", "QXl1c2hpQDEyMw==");

	        assertEquals(expected, result);
	    }
	    
		@Test
		public void testGetById_TicketExists() {
			int ticketId = 1;
			when(ticketRepo.findByticketId(ticketId)).thenReturn(ticket);
			TicketOutDto actualTicketOutDto = ticketService.getById(ticketId, "ayushi@nucleusteq.com", "QXl1c2hpQDEyMw==");

			assertNotNull(actualTicketOutDto);
			assertEquals(expected, actualTicketOutDto);
		}

		@Test
		public void testGetById_TicketNotFound() {
			int ticketId = 1;
			when(ticketRepo.findByticketId(ticketId)).thenReturn(null);

			assertThrows(ResourceNotFoundException.class,
					() -> ticketService.getById(ticketId, "ayushi@nucleusteq.com", "QXl1c2hpQDEyMw=="));

		}
		
		@Test
		public void testGetAllTicketsAuth_NoTicketsFound() {
		    when(memberRepo.findByEmail(ticketDto.getMember().getEmail())).thenReturn(testMember);
		    when(ticketRepo.findAll(any(Pageable.class))).thenReturn(Page.empty());

		    assertThrows(ResourceNotFoundException.class, () -> ticketService.getAllTicketsAuth("ayushi@nucleusteq.com", "QXl1c2hpQDEyMw==", false, 0));
		}

		
		@Test
	    public void testGetAllTickets_AdminSuccess() {
			List<Ticket> ticketList = Arrays.asList(ticket);
	        Page<Ticket> ticketPage = new PageImpl<>(ticketList);
	        
	        when(memberRepo.findByEmail(anyString())).thenReturn(testMember);
	        when(ticketRepo.findAllAndStatus(any(Pageable.class), any())).thenReturn(ticketPage);

	        List<TicketOutDto> result = ticketService.getAllTicketsFilter("ayushi@nucleusteq.com", "password", false, 0, Optional.empty());

	        assertEquals(1, result.size());
	    }
	
		@SuppressWarnings("unchecked")
		@Test
		public void testGetAllTicketsFilter_ForMemberWithStatusOpen() {
			testMember.setRole(MemberRole.MEMBER);
			ticket.setStatus(TicketStatus.OPEN);

			List<Ticket> ticketList = Arrays.asList(ticket);
	        Page<Ticket> ticketPage = new PageImpl<>(ticketList);
			when(memberRepo.findByEmail("ayushi@nucleusteq.com")).thenReturn(testMember);

			when(ticketRepo.findAllAndStatus(any(Pageable.class), any(Optional.class))).thenReturn(ticketPage);
			when(ticketRepo.findByMemberAndStatus(testMember.getId(), Optional.of(TicketStatus.OPEN),
					PageRequest.of(0, 5, Sort.by("status")))).thenReturn(ticketPage);

			Optional<TicketStatus> status = Optional.of(TicketStatus.OPEN);

			List<TicketOutDto> result = ticketService.getAllTicketsFilter("ayushi@nucleusteq.com", "QXl1c2hpQDEyMw==", true,
					0, status);
			assertEquals(1, result.size());
			assertEquals(TicketStatus.OPEN, result.get(0).getStatus());
		}

		@Test
		public void testGetAllTicketsFilter_NoTicketsForStatus() {
		    when(ticketRepo.findAllAndStatus(any(Pageable.class), eq(Optional.of(TicketStatus.OPEN)))).thenReturn(new PageImpl<>(new ArrayList<>()));

		    assertThrows(ResourceNotFoundException.class, () -> ticketService.getAllTicketsFilter("ayushi@nucleusteq.com", "password", false, 0, Optional.of(TicketStatus.OPEN)));
		}

		
		@SuppressWarnings("unchecked")
		@Test
		public void testGetAllTicketsFilter_ForMemberNotMyTicket() {
		    testMember.setRole(MemberRole.MEMBER);

		    List<Ticket> ticketList = Arrays.asList(ticket);
		    Page<Ticket> ticketPage = new PageImpl<>(ticketList);

		    when(memberRepo.findByEmail("ayushi@nucleusteq.com")).thenReturn(testMember);
		    when(ticketRepo.findAllAndStatus(any(Pageable.class), any())).thenReturn(ticketPage);
		    when(ticketRepo.findByDepartmentAndStatus(eq(testMember.getDepartment().getDeptId()), any(Optional.class), any(Pageable.class))).thenReturn(ticketPage);

		    List<TicketOutDto> result = ticketService.getAllTicketsFilter("ayushi@nucleusteq.com", "QXl1c2hpQDEyMw==", false, 0, Optional.empty());
		    assertEquals(1, result.size());
		}
		
		@Test
		public void testGetAllTicketsFilter_NoTickets() {
		    when(ticketRepo.findAllAndStatus(any(Pageable.class), any())).thenReturn(new PageImpl<>(new ArrayList<>()));

		    assertThrows(ResourceNotFoundException.class, () -> ticketService.getAllTicketsFilter("ayushi@nucleusteq.com", "password", false, 0, Optional.empty()));
		}

		@SuppressWarnings("unchecked")
		@Test
		public void testGetAllTicketsFilter_ForAdminWithStatusOpen() {
		    testMember.setRole(MemberRole.ADMIN);
		    ticket.setStatus(TicketStatus.OPEN);

		    List<Ticket> ticketList = Arrays.asList(ticket);
		    Page<Ticket> ticketPage = new PageImpl<>(ticketList);
		    when(memberRepo.findByEmail("ayushi@nucleusteq.com")).thenReturn(testMember);

		    when(ticketRepo.findAllAndStatus(any(Pageable.class), any(Optional.class))).thenReturn(ticketPage);

		    Optional<TicketStatus> status = Optional.of(TicketStatus.OPEN);

		    List<TicketOutDto> result = ticketService.getAllTicketsFilter("ayushi@nucleusteq.com", "QXl1c2hpQDEyMw==", false,
		            0, status);
		    assertEquals(1, result.size());
		    assertEquals(TicketStatus.OPEN, result.get(0).getStatus());
		}

		@Test
		public void testGetAllTicketsFilter_ForAdminWithNoMatchingStatus() {
		    testMember.setRole(MemberRole.ADMIN);

		    when(memberRepo.findByEmail("ayushi@nucleusteq.com")).thenReturn(testMember);
		    when(ticketRepo.findAllAndStatus(any(Pageable.class), eq(Optional.of(TicketStatus.BEING_ADDRESSED)))).thenReturn(new PageImpl<>(new ArrayList<>()));

		    assertThrows(ResourceNotFoundException.class, () -> ticketService.getAllTicketsFilter("ayushi@nucleusteq.com", "password", false, 0, Optional.of(TicketStatus.BEING_ADDRESSED)));
		}
		
		@Test
		public void testGetAllTicketsAuth_AdminAllTickets() {

			testMember.setRole(MemberRole.ADMIN);
			List<Ticket> ticketList = Arrays.asList(ticket);
	        Page<Ticket> ticketPage = new PageImpl<>(ticketList);
			when(memberRepo.findByEmail("ayushi@nucleusteq.com")).thenReturn(testMember);
			when(ticketRepo.findAll(any(Pageable.class))).thenReturn(ticketPage);

			List<TicketOutDto> result = ticketService.getAllTicketsAuth("ayushi@nucleusteq.com", "QXl1c2hpQDEyMw==", false,
					0);

			assertEquals(1, result.size());
		}
		
		@Test
        public void testGetAllTicketsAuth_MemberAllTickets() {

            testMember.setRole(MemberRole.MEMBER);
            List<Ticket> ticketList = Arrays.asList(ticket);
            Page<Ticket> ticketPage = new PageImpl<>(ticketList);
            when(memberRepo.findByEmail("ayushi@nucleusteq.com")).thenReturn(testMember);
            when(ticketRepo.findAll(any(Pageable.class))).thenReturn(ticketPage);
            when(ticketRepo.findByDepartment(any(Department.class), any(Pageable.class))).thenReturn(ticketPage);

            List<TicketOutDto> result = ticketService.getAllTicketsAuth("ayushi@nucleusteq.com", "QXl1c2hpQDEyMw==", false,
                    0);

            assertEquals(1, result.size());
        }
		
		@Test
        public void testGetAllTicketsAuth_MyAllTickets() {

            List<Ticket> ticketList = Arrays.asList(ticket);
            Page<Ticket> ticketPage = new PageImpl<>(ticketList);
            when(memberRepo.findByEmail("ayushi@nucleusteq.com")).thenReturn(testMember);
            when(ticketRepo.findAll(any(Pageable.class))).thenReturn(ticketPage);
            when(ticketRepo.findByMember(any(Member.class), any(Pageable.class))).thenReturn(ticketPage);

            List<TicketOutDto> result = ticketService.getAllTicketsAuth("ayushi@nucleusteq.com", "QXl1c2hpQDEyMw==", true,
                    0);

            assertEquals(1, result.size());
        }
		


		@Test
		public void testUpdateTicket_TicketNotFound() {
		    when(ticketRepo.findById(anyInt())).thenReturn(Optional.empty());
		    assertThrows(ResourceNotFoundException.class, () -> ticketService.updateTicket(ticketDto, 1, "email@example.com", "QXl1c2hpQDEyMw=="));
		}

		
		@Test
		public void testUpdateTicket_Unauthorized() {
		    when(ticketRepo.findById(anyInt())).thenReturn(Optional.of(ticket));
		    assertThrows(UnauthorizedException.class, () -> ticketService.updateTicket(ticketDto, 1, "wrongEmail@example.com", "QXl1c2hpQDEyMw=="));
		}

		@Test
		public void testUpdateTicket_UserNotExist() {
		    when(ticketRepo.findById(anyInt())).thenReturn(Optional.of(ticket));
		    when(memberRepo.findByEmail("ayushi@nucleusteq.com")).thenReturn(null);
		    assertThrows(ResourceNotFoundException.class, () -> ticketService.updateTicket(ticketDto, 1, "ayushi@nucleusteq.com", "QXl1c2hpQDEyMw=="));
		}

		
//		@Test
//		public void testUpdateTicket_DepartmentEmailMismatch() {
//		    Department differentDept = new Department();
//		    differentDept.setDeptId(2);
//		    differentDept.setDeptName("IT");
//		    testMember.setDepartment(differentDept);
//		    
//		    // Ensure that the ticketDto's member email is different from the testMember's email.
//		    ticketDto.getMember().setEmail("differentEmail@example.com");
//
//		    when(ticketRepo.findById(anyInt())).thenReturn(Optional.of(ticket));
//		    when(memberRepo.findByEmail("ayushi@nucleusteq.com")).thenReturn(testMember);
//		    
//		    assertThrows(CannotEditTicketException.class, () -> ticketService.updateTicket(ticketDto, 1, "ayushi@nucleusteq.com", "QXl1c2hpQDEyMw=="));
//		}

		
		@Test
		public void testUpdateTicket_NoCommentsForResolved() {
		    ticketDto.setStatus(TicketStatus.RESOLVED);
		    when(ticketRepo.findById(anyInt())).thenReturn(Optional.of(ticket));
		    when(memberRepo.findByEmail("ayushi@nucleusteq.com")).thenReturn(testMember);
		    assertThrows(CannotEditTicketException.class, () -> ticketService.updateTicket(ticketDto, 1, "ayushi@nucleusteq.com", "QXl1c2hpQDEyMw=="));
		}

		
		@Test
		public void testUpdateTicket_Success() {
		    when(ticketRepo.findById(anyInt())).thenReturn(Optional.of(ticket));
		    when(memberRepo.findByEmail("ayushi@nucleusteq.com")).thenReturn(testMember);
		    when(ticketRepo.save(any(Ticket.class))).thenReturn(ticket);
		    TicketOutDto result = ticketService.updateTicket(ticketDto, 1, "ayushi@nucleusteq.com", "QXl1c2hpQDEyMw==");
//		    assertEquals(expected, result);
		    assertEquals(expected.getTicketId(), result.getTicketId());
		    assertEquals(expected.getTicketName(), result.getTicketName());
		}
		
		@Test
		public void testUpdateTicket_CannotResolveWithoutComments() {
		    ticketDto.setStatus(TicketStatus.RESOLVED);
		    ticket.setComments(new ArrayList<>());
		    
		    when(ticketRepo.findById(anyInt())).thenReturn(Optional.of(ticket));
		    when(memberRepo.findByEmail("ayushi@nucleusteq.com")).thenReturn(testMember);
		    
		    assertThrows(CannotEditTicketException.class, () -> ticketService.updateTicket(ticketDto, 1, "ayushi@nucleusteq.com", "QXl1c2hpQDEyMw=="));
		}
		
	      @Test
	        public void testUpdateTicket_WihtCommentSuccess() {
	            List<Comment> comments = new ArrayList<>();
	            comments.add(new Comment());
	            ticket.setComments(comments);
	            when(ticketRepo.findById(anyInt())).thenReturn(Optional.of(ticket));
	            when(memberRepo.findByEmail("ayushi@nucleusteq.com")).thenReturn(testMember);
	            when(ticketRepo.save(any(Ticket.class))).thenReturn(ticket);
	            TicketOutDto result = ticketService.updateTicket(ticketDto, 1, "ayushi@nucleusteq.com", "QXl1c2hpQDEyMw==");
//	            assertEquals(expected, result);
	            assertEquals(expected.getTicketId(), result.getTicketId());
	            assertEquals(expected.getTicketName(), result.getTicketName());
	        }

}
