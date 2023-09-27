package com.grievance.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grievance.indto.ChangePasswordDto;
import com.grievance.indto.LoginDto;
import com.grievance.indto.MemberDto;
import com.grievance.outdto.MemberOutDto;
import com.grievance.service.MemberService;

/**
 * MemberController class.
 */
@RestController
@RequestMapping("/api/")
@CrossOrigin("*")
public class MemberController {

    /**
	 * Loggers.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MemberController.class);

	/**
	 * Service to handle user related operations.
	 */
	@Autowired
	private MemberService memberService;

	/**
	 * Post mapping when user logging in.
	 *
	 * @param loginDto
	 * @return response entity outDto and https status.
	 */
	@PostMapping("member/login")
	public ResponseEntity<?> loginMember(
			@Valid @RequestBody final LoginDto loginDto) {
		LOGGER.info("Logging in member with email: {}",
				loginDto.getEmail());
		MemberOutDto memberDto1 = memberService
				.loginMember(loginDto);
		return new ResponseEntity<>(memberDto1,
				HttpStatus.ACCEPTED);
	}

	/**
	 * Get Mapping to fetch the list of all users.
	 *
	 * @param pageNo
	 * @return response entity having all members and http status.
	 */
	@GetMapping("member/getAll")
	public ResponseEntity<List<MemberOutDto>> getAllmembers(@RequestParam
			final Integer pageNo) {
		LOGGER.info("Fetching all members");
		return new ResponseEntity<>(memberService
				.getAllMember(pageNo), HttpStatus.ACCEPTED);
	}

	/**
	 * Put Mapping while new user is being created.
	 *
	 * @param memberDto
	 * @return response entity created user and https status.
	 */
	@PostMapping("member/create")
	public ResponseEntity<?> createMemberAuth(@Valid
			@RequestBody final MemberDto memberDto) {
		LOGGER.info("Authenticating and creating member"
				+ "with details: {}", memberDto);
		MemberOutDto createdMember = memberService
				.createMemberAuth(memberDto);
		return new ResponseEntity<>(
				createdMember, HttpStatus.CREATED);

	}

	/**
	 * Put mapping to perform update in password.
	 *
	 * @param changePasswordDto
	 * @param email
	 * @param password
	 * @return response entity having updated user and http status.
	 */
	@PutMapping("member/changePassword")
	public ResponseEntity<MemberOutDto> changePassword(@Valid
			@RequestBody final ChangePasswordDto changePasswordDto,
			@RequestHeader final String email,
			@RequestHeader final String password) {
		 LOGGER.info("Received request to change"
		 		+ " password for email: {}", email);
		        MemberOutDto updated = memberService
		        		.changePassword(changePasswordDto,
		        				email, password);
		        LOGGER.info("Password successfully "
		        		+ "updated for email: {}", email);
		 return new ResponseEntity<>(updated, HttpStatus.OK);
	}


	/**
	 * To delete a member by its email.
	 *
	 * @param email - email of member to be deleted.
	 * @return ResponseEntity indicating deletion and HTTP status.
	 */
	@DeleteMapping("member/delete")
	public ResponseEntity<?> deleteMember(
			@RequestParam final String email) {
		LOGGER.info("Request to delete member with emial: {}",
				email);
		memberService.deleteMember(email);
		LOGGER.info("Member with email: {} deleted successfully.",
				email);
		return new ResponseEntity<>("Member Deleted",
				HttpStatus.OK);
	}

}
