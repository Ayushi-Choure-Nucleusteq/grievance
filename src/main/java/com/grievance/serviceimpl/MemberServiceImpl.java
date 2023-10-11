package com.grievance.serviceimpl;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.grievance.entity.Department;
import com.grievance.entity.Member;
import com.grievance.exception.RecordAlreadyExistException;
import com.grievance.exception.ResourceNotFoundException;
import com.grievance.exception.UnauthorizedException;
import com.grievance.indto.ChangePasswordDto;
import com.grievance.indto.LoginDto;
import com.grievance.indto.MemberDto;
import com.grievance.outdto.MemberOutDto;
import com.grievance.repository.DepartmentRepo;
import com.grievance.repository.MemberRepo;
import com.grievance.service.MemberService;

/**
 * MemberServiceImpl Service Implementation Class. Implements the business
 * operations defined in MemberService.
 */
@Service
public class MemberServiceImpl implements MemberService {
    
    /**
     * Loggers.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MemberServiceImpl.class);

     /**
	 * Repository for member related operations.
	 */
	@Autowired
	private MemberRepo memberRepo;

	/**
	 * Repository for department related operations.
	 */
	@Autowired
	private DepartmentRepo deptRepo;

	/**
	 * Conversion for conversion from indto to entity to outdto.
	 */
	@Autowired
	private Conversion conversion;

	/**
	 * Decode password.
	 */
	@Autowired
	private DecodePassword decode;

	/**
	 * Creates a new member in the system.
	 *
	 * @param memberDto Member details.
	 * @return Details of the created member.
	 */
	@Override
	public final MemberOutDto createMemberAuth(
			final MemberDto memberDto) {

	    LOGGER.info("Attempting to create a new "
	            + "member with email: {}",
	            memberDto.getEmail());
		Member existingMember = memberRepo.findByEmail(
				memberDto.getEmail());
		if (Objects.nonNull(existingMember)) {
		    LOGGER.error("A member with email {} already"
		            + "exists.", memberDto.getEmail());
			throw new RecordAlreadyExistException(
			  "A member with this email already exists.");
		}

		Department existingDept = deptRepo.findByDeptName(
				memberDto.getDepartment().getDeptName());

		if (Objects.isNull(existingDept)) {
		    LOGGER.error("Department with name {} does not"
		      + " exist.", memberDto.getDepartment().getDeptName());
			throw new ResourceNotFoundException(
			  "Department with this name does not exist.");
		}
		final String passPattern = "^(?=.*[a-z])(?=.*[A-Z])"
		       + "(?=.*\\d)(?=.*[@$!%*?&])"
		       + "[A-Za-z\\d@$!%*?&]{8,}$";
		if (Objects.isNull(memberDto.getPassword())
				|| !decode.decodePassword(memberDto.
					getPassword()).matches(passPattern)) {
            LOGGER.warn("Provided password for member {} "
                    + "doesn't match the required "
                    + "pattern.", memberDto.getEmail());
			throw new IllegalArgumentException(
					"Password not acceptable");
		}
		Member member = conversion.memberdtoToEntity(memberDto);
		member.setIsLoggedIn(false);
		member.setDepartment(existingDept);
		Member savedMember = memberRepo.save(member);
		 LOGGER.info("Successfully created member with "
		         + "email: {}", memberDto.getEmail());

		MemberOutDto memberOutDto = conversion.memberToOutDto(
				savedMember);
		return memberOutDto;
	}

	
	/**
	 * Fetches all members in the system.
	 *
	 *@param pageNo
	 * @return List of members.
	 */
	@Override
	public final List<MemberOutDto> getAllMember(final Integer pageNo) {
	    LOGGER.info("Fetching members, page number: {}", pageNo);
		final Integer pageSize = 5;
		Page<Member> members = memberRepo.findAll(
		        PageRequest.of(pageNo, pageSize));
		if (members.isEmpty()) {
		    LOGGER.warn("No members found for page number: {}", pageNo);
			throw new ResourceNotFoundException("No User exists.");
		}
		List<MemberOutDto> memberDtos = new ArrayList<>();
		for (Member member2 : members) {
			MemberOutDto memberDto = conversion
					.memberToOutDto(member2);
			memberDtos.add(memberDto);
		}
		LOGGER.info("Successfully fetched {} members for page "
		        + "number: {}", memberDtos.size(), pageNo);
		return memberDtos;
	}

	/**
	 * Logs a member in after checking their credentials.
	 *
	 * @param loginDto Login details.
	 * @return Details of the logged-in member.
	 * @throws UnauthorizedException If credentials are invalid.
	 */
	@Override
	public final MemberOutDto loginMember(final LoginDto loginDto)
			throws UnauthorizedException {
	    LOGGER.info("Initiating login request for "
	            + "email: {}", loginDto.getEmail());
		Member member = memberRepo.findByEmail(loginDto.getEmail());
		if (Objects.isNull(member)) {
		    LOGGER.error("Login failed. No member found "
		            + "with email: {}", loginDto.getEmail());
			throw new ResourceNotFoundException(
					"User with email does not exist.");
		}
		String decodedInPass = decode.decodePassword(
				loginDto.getPassword());
		String decodedDbPass = decode.decodePassword(
				member.getPassword());

		if (!decodedInPass.equals(decodedDbPass)) {
		    LOGGER.warn("Login attempt with invalid credentials "
		            + "for email: {}", loginDto.getEmail());
			throw new UnauthorizedException(
					"Invalid Password!");
		}
		MemberOutDto memberDto = conversion.memberToOutDto(member);
		if (!member.getIsLoggedIn()) {
			memberDto.setIsLoggedIn(true);
			member.setIsLoggedIn(true);
			memberRepo.save(member);
			LOGGER.info("First Login successful"
			        + " for email: {}.", loginDto.getEmail());
		} else {
			memberDto.setIsLoggedIn(false);
			LOGGER.info("Nth Login successful "
			        + "for email: {}.", loginDto.getEmail());
		}
		return memberDto;
	}

	/**
	 * Changes the password of a member.
	 *
	 * @param changePasswordDto New password details.
	 * @param email             Member's email.
	 * @param password          Member's old password.
	 * @return Updated member details.
	 * @throws UnauthorizedException If old password is invalid.
	 */
	@Override
	public final MemberOutDto changePassword(
			final ChangePasswordDto changePasswordDto,
			final String email,
			final String password) throws UnauthorizedException {
	    LOGGER.info("Initiating password change request "
	            + "for email: {}", email);
		Member member = memberRepo.findByEmail(email);
		if (!member.getPassword().equals(
			  changePasswordDto.getOldPassword())) {
		    LOGGER.error("Change password request failed", email);
			throw new UnauthorizedException(
				"Invalid Credentials for Old Password");
		}
		final String passPattern = "^(?=.*[a-z])(?=.*[A-Z])"
		    + "(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
		if (Objects.isNull(changePasswordDto.getNewPassword())
				|| !decode.decodePassword(
					changePasswordDto.getNewPassword()).
						matches(passPattern)) {
		    LOGGER.error("Change password request failed", email);
			throw new IllegalArgumentException(
					"Password not acceptable");
		}
		member.setPassword(changePasswordDto.getNewPassword());
		member = memberRepo.save(member);
		LOGGER.info("Password successfully changed"
		        + " for email: {}", email);
		MemberOutDto memberDto = conversion.memberToOutDto(member);
		return memberDto;
	}


	/**
	 * Business logic to delete a Member.
	 *
	 * @param email  the department data transfer object
	 */
	@Override
	public final void deleteMember(final String email) {
	    LOGGER.info("Initiating delete request for "
	            + "member with email: {}", email);

		Member member = memberRepo.findByEmail(
				email);
		if (Objects.isNull(member)) {
		    LOGGER.error("Delete request failed."
		            + "No member found with email: {}", email);
			throw new ResourceNotFoundException(
				"Member with this email does not exists");
		}
		memberRepo.delete(member);
		LOGGER.info("Successfully deleted member "
		        + "with email: {}", email);
	}
}
