package com.grievance.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grievance.enums.TicketStatus;
import com.grievance.indto.TicketDto;
import com.grievance.outdto.TicketOutDto;
import com.grievance.service.TicketService;

/**
 * TicketController class to handle ticket related requests.
 */
@RestController
@RequestMapping("/api/")
@CrossOrigin("*")
public final class TicketController {

     /**
	 * logger.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TicketController.class);

	/**
	 * Service to handle ticket related operations.
	 */
	@Autowired
	private TicketService ticketService;

	/**
	 * To create a ticket.
	 *
	 * @param ticketDto containing ticket details
	 * @param email
	 * @param password
	 * @return ResponseEntity containing the created ticket and HTTP status
	 */
	@PostMapping("/ticket/create")
	public ResponseEntity<?> createTicket(@Valid
			@RequestBody final TicketDto ticketDto,
			@RequestHeader final String email,
			@RequestHeader final String password) {
		LOGGER.info("Creating ticket with details: {}", ticketDto);
		TicketOutDto createdTicket = ticketService.createTicket(
				ticketDto, email, password);
		LOGGER.info("Ticket created with ID: {}",
				createdTicket.getTicketId());
		return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
	}

	/**
	 * To get ticket by Id.
	 *
	 * @param ticketId
	 * @param email
	 * @param password
	 * @return ResponseEntity containing the ticket and HTTP status
	 */
	@GetMapping("ticket/viewById")
	public ResponseEntity<TicketOutDto> getById(
			@RequestParam final Integer ticketId,
			@RequestHeader final String email,
			@RequestHeader final String password) {
		LOGGER.info("Fetching ticket with ID: {}", ticketId);
		return new ResponseEntity<>(ticketService.getById(
			ticketId, email, password), HttpStatus.ACCEPTED);
	}

	/**
	 * Get all ticket.
	 *
	 * @param email
	 * @param password
	 * @param myTicket
	 * @param myDeptTicket
	 * @param pageNo
	 * @param status
	 * @return ResponseEntity containing the all ticket and HTTP status
	 */
	@GetMapping("ticket/getAll")
	public ResponseEntity<List<TicketOutDto>> getAllticketsAuth(
			@RequestHeader final String email,
			@RequestHeader final String password,
			@RequestParam final boolean myTicket,
			@RequestParam final boolean myDeptTicket,
			@RequestParam final Integer pageNo,
			@RequestParam(required = false)
			final Optional<TicketStatus> status) {
		LOGGER.info("Fetching all tickets for user: {}", email);
		if (status.isPresent()) {
		  return new ResponseEntity<>(ticketService.getAllTicketsFilter(
				email, password, myTicket, myDeptTicket, pageNo, status),
					HttpStatus.ACCEPTED);


		} else {
			return new ResponseEntity<>(
					ticketService.getAllTicketsAuth(
					email, password, myTicket, myDeptTicket, pageNo),
					HttpStatus.ACCEPTED);
		}
	}

	/**
	 * Put mapping to update ticket.
	 *
	 * @param ticketDto
	 * @param ticketId
	 * @param email
	 * @param password
	 * @return ResponseEntity containing the updated ticket and HTTP status
	 */
	@PutMapping("ticket/update")
	public ResponseEntity<TicketOutDto> updateTicket(@Valid
			@RequestBody final TicketDto ticketDto,
			@RequestParam final Integer ticketId,
			@RequestHeader final String email,
			@RequestHeader final String password) {
		LOGGER.info("Updating ticket with ID: {}", ticketId);
		TicketOutDto updated = ticketService.updateTicket(
				ticketDto, ticketId, email, password);
		LOGGER.info("Ticket with ID: {} updated", ticketId);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}
}
