package com.grievance.service;

import java.util.List;
import java.util.Optional;

import com.grievance.enums.TicketStatus;
import com.grievance.indto.TicketDto;
import com.grievance.outdto.TicketOutDto;

/**
 * Interface defining the operations for managing tickets.
 */
public interface TicketService {

     /**
	 * Creates a new ticket in the system.
	 *
	 * @param ticketDto DTO representing the ticket details.
	 * @param email
	 * @param password
	 * @return TicketOutDto representing the saved ticket.
	 */
	TicketOutDto createTicket(TicketDto ticketDto,
			String email, String password);

	/**
	 * Retrieves all tickets based on the given credentials
	 *  and whether they are personal tickets.
	 *
	 * @param email    User's email.
	 * @param password User's password.
	 * @param myTicket Whether the tickets are personal to the user.
	 * @param pageNo page number.
	 * @param status status for filter.
	 * @return List of TicketOutDto representing the retrieved tickets.
	 */
	List<TicketOutDto> getAllTicketsFilter(String email, String password,
			boolean myTicket,
			Integer pageNo,
			Optional<TicketStatus> status);

	/**
	 * Updates the ticket with the given ID.
	 *
	 * @param ticketDto DTO representing the updated ticket details.
	 * @param id        Unique ID of the ticket to be updated.
	 * @param email
	 * @param password
	 * @return TicketOutDto the updated ticket.
	 */
	TicketOutDto updateTicket(TicketDto ticketDto,
		Integer id, String email, String password);

	/**
	 * Retrieves the ticket with the given ID.
	 *
	 * @param ticketId Unique ID of the ticket.
	 * @param email
	 * @param password
	 * @return TicketOutDto representing the retrieved ticket.
	 */
	TicketOutDto getById(Integer ticketId,
			String email, String password);

	/**
	 * Fetch tickets based on the provided email and password.
	 *
	 * @param email
	 * @param password
	 * @param myTicket
	 * @param pageNo
	 * @return the output DTO of the created ticket
	 */
	List<TicketOutDto> getAllTicketsAuth(
		String email, String password,
		boolean myTicket, Integer pageNo);

}
