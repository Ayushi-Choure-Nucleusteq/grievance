package com.grievance.service;

import java.util.List;


import com.grievance.indto.ChangePasswordDto;
import com.grievance.indto.LoginDto;
import com.grievance.indto.MemberDto;
import com.grievance.outdto.MemberOutDto;

/**
 * MemberService Interface.
 * <p>
 * Defines the business operations related to members within the grievance
 * system.
 * </p>
 */
public interface MemberService {

     /**
	 * Logs in a member into the system.
	 *
	 * @param loginDto Data Transfer Object with login credentials.
	 * @return MemberOutDto indicating success or failure.
	 */
	MemberOutDto loginMember(LoginDto loginDto);

	/**
	 * Fetches a list of all members present in the system.
	 *
	 *@param pageNo
	 * @return List of MemberOutDto, each representing a member.
	 */
	List<MemberOutDto> getAllMember(Integer pageNo);

	/**
	 * Creates a member in the system with authentication.
	 *
	 * @param memberDto Data Transfer Object representing the member.
	 * @return MemberOutDto.
	 */
	MemberOutDto createMemberAuth(MemberDto memberDto);

	/**
	 * Changes Password of authenticated member.
	 *
	 * @param changePasswordDto Data Transfer Object.
	 * @param email             Email of the member for authentication.
	 * @param password          Password of the member for authentication.
	 * @return MemberOutDto.
	 */
	MemberOutDto changePassword(ChangePasswordDto changePasswordDto,
			String email, String password);

	/**
	 * Deletes a specific user from the system.
	 *@param email
	 */
	void deleteMember(String email);

}
