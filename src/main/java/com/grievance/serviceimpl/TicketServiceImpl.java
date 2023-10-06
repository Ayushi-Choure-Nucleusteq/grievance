package com.grievance.serviceimpl;

import java.time.LocalDateTime;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.grievance.entity.Comment;
import com.grievance.entity.Department;
import com.grievance.entity.Member;
import com.grievance.entity.Ticket;
import com.grievance.enums.MemberRole;
import com.grievance.enums.TicketStatus;
import com.grievance.exception.CannotEditTicketException;
import com.grievance.exception.ResourceNotFoundException;
import com.grievance.exception.UnauthorizedException;
import com.grievance.indto.TicketDto;
import com.grievance.outdto.TicketOutDto;
import com.grievance.repository.DepartmentRepo;
import com.grievance.repository.MemberRepo;
import com.grievance.repository.TicketRepo;
import com.grievance.service.TicketService;

/**
 * Service implementation for ticket operations.
 */
@Service
public final class TicketServiceImpl implements TicketService {
    
    /**
     * Loggers.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(TicketServiceImpl.class);

    /** Repository for ticket-related operations. */
    @Autowired
    private TicketRepo ticketRepo;

    /** Repository for member-related operations. */
    @Autowired
    private MemberRepo memberRepo;

    /** Repository for department-related operations. */
    @Autowired
    private DepartmentRepo deptRepo;

    /**
	 * Conversion for conversion from indto to entity to outdto.
	 */
	@Autowired
	private Conversion conversion;

	/**
	 * Create a ticket based on the provided ticket DTO.
	 *
	 * @param ticketDto the ticket data transfer object
	 * @return the output DTO of the created ticket
	 */
	@Override
	public TicketOutDto createTicket(final TicketDto ticketDto,
			final String email, final String password)
			throws ResourceNotFoundException {
	    LOGGER.info("Creating a new ticket for email: {}", email);
		if (!ticketDto.getMember().getEmail().equals(email)) {
		    LOGGER.error("Unauthorized attempt "
		            + "to create a ticket with mismatched email");
			throw new UnauthorizedException("Inavlid Details.");
		}
		Member existingMember = memberRepo.findByEmail(
				ticketDto.getMember().getEmail());

		if (Objects.isNull(existingMember)) {
		    LOGGER.error("Email not exists {}", email);
			throw new ResourceNotFoundException(
			  "The member associated does not exist.");
		}
		LOGGER.info("Found member details for email: {}", email);

		Department department = deptRepo.findByDeptName(
				ticketDto.getDepartment().getDeptName());

		if (Objects.isNull(department)) {
		    LOGGER.error("Department not exists {}",
		            ticketDto.getDepartment().getDeptName());
			throw new ResourceNotFoundException(
			  "The department does not exist.");
		}
		 LOGGER.info("Found department details for"
		         + " department name: {}",
		         ticketDto.getDepartment().getDeptName());
		ticketDto.setComments(null);
		Ticket ticket = conversion.ticketdtoToTicket(
				ticketDto);
		ticket.setMember(existingMember);
		ticket.setDepartment(department);
		String creationDate = LocalDateTime.now().format(
		DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
		ticket.setCreationDate(creationDate);
		ticket.setLastUpdateDate(creationDate);
		ticket.setStatus(TicketStatus.OPEN);
		Ticket savedTicket = ticketRepo.save(ticket);
		 LOGGER.info("Ticket created successfully with ID: {}",
		         savedTicket.getTicketId());

		return conversion.ticketToOutDto(savedTicket);
	}

	/**
	 *
	 * To fetch a ticket by its ID.
	 *
	 *@param ticketId
	 *@param email
	 *@param password
	 *@return ticketOutDto
	 *@throws ResourceNotFoundexception
	 */
	@Override
	public TicketOutDto getById(final Integer ticketId,
			final String email, final String password)
			throws ResourceNotFoundException {
	    LOGGER.info("Fetching ticket by ID: {}", ticketId);
		Ticket ticket = ticketRepo.findByticketId(ticketId);
		if (Objects.isNull(ticket)) {
		    LOGGER.error("No ticket found "
		            + "with Id {}", ticketId);
			throw new ResourceNotFoundException(
					"Ticket with this Id does not exists");
		}
		LOGGER.info("Successfully fetched ticket details "
		        + "for ID: {} for user: {}", ticketId, email);
		return conversion.ticketToOutDto(ticket);
	}

	/**
	 * Fetch tickets based on the provided email and password.
	 *
	 * @param email
	 * @param password
	 * @param myTicket
	 * @param pageNo
	 * @return the output DTO of the created ticket
	 */
	@Override
	public List<TicketOutDto> getAllTicketsAuth(final String email,
			final String password, final boolean myTicket,
			final boolean myDeptTicket, final Integer pageNo) {
	    LOGGER.info("Initiating request to fetch tickets"
	            + " for user: {}", email);
		final Integer pageSize = 5;
		Member member = memberRepo.findByEmail(email);
		LOGGER.info("Member with email {} has role: {}",
		        email, member.getRole());
		Page<Ticket> tickets = ticketRepo.findAll(
		  PageRequest.of(pageNo, pageSize, Sort.by("status")));
		if (tickets.isEmpty()) {
		    LOGGER.warn("No tickets found");
			throw new ResourceNotFoundException("No Ticket found.");
		}
		List<TicketOutDto> ticketDtos = new ArrayList<>();
		if (myTicket) {
			tickets = (Page<Ticket>) ticketRepo.findByMember(member,
			  PageRequest.of(pageNo, pageSize, Sort.by(
					  "status")));
			if (tickets.isEmpty()) {
			    LOGGER.warn("No Personal tickets found");
	            throw new ResourceNotFoundException("No Ticket found.");
			}
			for (Ticket ticket : tickets) {
			  ticketDtos.add(conversion.ticketToOutDto(ticket));
			}
			LOGGER.info("Fetching personal tickets for member"
			        + " with email: {}", email);
		} else if (member.getRole().equals(MemberRole.ADMIN)) {
		    if (myDeptTicket) {
                LOGGER.info("Fetching Department tickets for ADMIN");
              tickets = (Page<Ticket>) ticketRepo
                    .findByDepartment(member.getDepartment(),
                            PageRequest.of(
                         pageNo, pageSize, Sort.by("status")));
              if (tickets.isEmpty()) {
                  LOGGER.warn("No tickets found ADMIN Dept");
                  throw new ResourceNotFoundException("No Ticket found.");     
              }
                      for (Ticket ticket : tickets) {
                        ticketDtos.add(conversion.
                                  ticketToOutDto(ticket));
                      }
            } else {
			for (Ticket ticket : tickets) {
			  ticketDtos.add(conversion.ticketToOutDto(ticket));
			}
           }
			 LOGGER.info("Fetching all tickets for ADMIN");
		} else if (member.getRole().equals(MemberRole.MEMBER)) {
			tickets = (Page<Ticket>) ticketRepo.
				findByDepartment(member.getDepartment(),
				  PageRequest.of(pageNo, pageSize, Sort.by(
						  "status")));
			if (tickets.isEmpty()) {
			    throw new ResourceNotFoundException(
			            "No Ticket found."); 
			}
			for (Ticket ticket : tickets) {
		      ticketDtos.add(conversion.
						ticketToOutDto(ticket));
			}
			 LOGGER.info("Fetching all tickets for MEMBER");
		}


		return ticketDtos;
	}
	
	/**
     * Fetch tickets based on the provided email and password.
     *
     * @param email
     * @param password
     * @param myTicket
     * @param pageNo
     * @param status
     * @return the output DTO of the created ticket
     */

	@Override
	public List<TicketOutDto> getAllTicketsFilter(final String email,
			final String password, final boolean myTicket,
			final boolean myDeptTicket, final Integer pageNo,
			final Optional<TicketStatus> status) {
	    LOGGER.info("Initiating request to fetch filtered"
	            + " tickets for user: {}.", email);
		final Integer pageSize = 5;
		Member member = memberRepo.findByEmail(email);
		Page<Ticket> tickets = ticketRepo.findAllAndStatus(
				PageRequest.of(pageNo, pageSize, Sort.by(
						"status")), status);
		if (tickets.isEmpty()) {
		    LOGGER.warn("No tickets found");
			throw new ResourceNotFoundException("No Ticket found.");
		}

		List<TicketOutDto> ticketDtos = new ArrayList<>();
		if (myTicket) {
		    LOGGER.info("Fetching personal tickets");
			tickets = (Page<Ticket>) ticketRepo.
					findByMemberAndStatus(
			member.getId(), status, PageRequest.of(
				pageNo, pageSize, Sort.by("status")));
			if (tickets.isEmpty()) {
                LOGGER.warn("No Personal tickets found");
                throw new ResourceNotFoundException("No Ticket found.");
            }
			for (Ticket ticket : tickets) {
			  ticketDtos.add(conversion.ticketToOutDto(ticket));
			}
		} else if (member.getRole().equals(MemberRole.ADMIN)) {
		    if (myDeptTicket) {
		        LOGGER.info("Fetching Department tickets for ADMIN");
              tickets = (Page<Ticket>) ticketRepo
                    .findByDepartmentAndStatus(member.getDepartment()
                    .getDeptId(), status, PageRequest.of(
                         pageNo, pageSize, Sort.by("status")));
              if (tickets.isEmpty()) {
                  LOGGER.warn("No tickets found ADMIN Dept");
                  throw new ResourceNotFoundException("No Ticket found.");     
              } 
                      for (Ticket ticket : tickets) {
                        ticketDtos.add(conversion.
                                  ticketToOutDto(ticket));
                      
              }    
            } else {
		    LOGGER.info("Fetching all tickets for ADMIN");
		    for (Ticket ticket : tickets) {
			  ticketDtos.add(conversion.ticketToOutDto(ticket));
			}
            }
		} else if (member.getRole().equals(MemberRole.MEMBER)) {
		    LOGGER.info("Fetching all tickets for MEMBER");
		    tickets = (Page<Ticket>) ticketRepo
			  .findByDepartmentAndStatus(member.getDepartment()
				 .getDeptId(), status, PageRequest.of(
				     pageNo, pageSize, Sort.by("status")));
		    if (tickets.isEmpty()) {
		        LOGGER.warn("No tickets found MEMBER Dept");
                throw new ResourceNotFoundException("No Ticket found."); 
		    }
			for (Ticket ticket : tickets) {
		      ticketDtos.add(conversion.
						ticketToOutDto(ticket));
			}
		}

		return ticketDtos;
	}

	/**
	 * Update a ticket based on the provided ticket DTO.
	 *
	 * @param ticketDto the ticket data transfer object
	 * @param id   id of the ticket
	 * @param email		email of user
	 * @param password  password of user
	 * @return the output DTO of the created ticket
	 */
	@Override
	public TicketOutDto updateTicket(final TicketDto ticketDto,
			final Integer id, final String email,
			final String password) {
	    LOGGER.info("Initiating request to update "
	            + "ticket with ID: {}", id);
		Optional<Ticket> ticket = ticketRepo.findById(id);

		if (!ticket.isPresent()) {
		    LOGGER.error("No ticket found with ID: {}", id);
			throw new ResourceNotFoundException("No Ticket Found");
		}
		if (!ticketDto.getMember().getEmail().equals(email)) {
		    LOGGER.error("Mismatch in email for update request");
			throw new UnauthorizedException("Inavlid Details.");
		}
		Member member = memberRepo.findByEmail(
				ticketDto.getMember().getEmail());
		if (Objects.isNull(member)) {
		    LOGGER.error("No member associated with email:{}", email);
			throw new ResourceNotFoundException(
					"User doesn't exists");
		}
		boolean isSameDepartment = ticket.get()
		        .getDepartment().getDeptName()
		        .equals(member.getDepartment().getDeptName());
		boolean isSameEmail = member.getEmail()
		        .equals(ticket.get().getMember().getEmail());

		if (!isSameDepartment && !isSameEmail)  {
		    LOGGER.error("User trying to update ticket from another "
		            + "department or different member email.");
			throw new CannotEditTicketException(
					"You cannot update this ticket");
		}

		List<Comment> comments = ticket.get().getComments();

		if (ticketDto.getStatus().equals(TicketStatus.RESOLVED)
				&& Objects.isNull(ticketDto.getComments())) {
		    LOGGER.warn("Attempt to set ticket status to "
		            + "RESOLVED without adding comments.");
			throw new
			CannotEditTicketException(
			 "There must be a comment to resolve");
		}

		String updatedDate = LocalDateTime.now().format(
				DateTimeFormatter.ofPattern(
						"yyyy/MM/dd HH:mm:ss"));
		ticket.get().setLastUpdateDate(updatedDate);
		ticket.get().setStatus(ticketDto.getStatus());
		Comment newComment = new Comment();
		if (Objects.nonNull(ticketDto.getComments())) {
		LOGGER.info("Updated comment entity for ticket ID: {}", id);    
		newComment.setContent(ticketDto.getComments());
		newComment.setCreationTime(updatedDate);
		newComment.setMemberEmail(member.getEmail());
		newComment.setTicket(ticket.get());
		comments.add(newComment);
		ticket.get().setComments(comments);
		}
		Ticket updatedTicket = ticketRepo.save(ticket.get());
		LOGGER.info("Successfully updated ticket with ID: {}", id);

		return conversion.ticketToOutDto(updatedTicket);
	}

}
