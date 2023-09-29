package com.grievance.serviceimpl;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

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

		Member existingMember = memberRepo.findByEmail(
				memberDto.getEmail());
		if (Objects.nonNull(existingMember)) {
			throw new RecordAlreadyExistException(
			  "A member with this email already exists.");
		}

		Department existingDept = deptRepo.findByDeptName(
				memberDto.getDepartment().getDeptName());

		if (Objects.isNull(existingDept)) {
			throw new ResourceNotFoundException(
			  "Department with this name does not exist.");
		}
		final String passPattern = "^(?=.*[a-z])(?=.*[A-Z])"
		       + "(?=.*\\d)(?=.*[@$!%*?&])"
		       + "[A-Za-z\\d@$!%*?&]{8,}$";
		if (Objects.isNull(memberDto.getPassword())
				|| (!decode.decodePassword(memberDto.
					getPassword()).matches(passPattern))) {
			throw new IllegalArgumentException(
					"Password not acceptable");
		}
		Member member = conversion.memberdtoToEntity(memberDto);
		member.setIsLoggedIn(false);
		member.setDepartment(existingDept);
		Member savedMember = memberRepo.save(member);

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
		final Integer pageSize = 8;
		Page<Member> members = memberRepo.findAll(
		        PageRequest.of(pageNo, pageSize));
		if (members.isEmpty()) {
			throw new ResourceNotFoundException("No User exists.");
		}
		List<MemberOutDto> memberDtos = new ArrayList<>();
		for (Member member2 : members) {
			MemberOutDto memberDto = conversion
					.memberToOutDto(member2);
			memberDtos.add(memberDto);
		}
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
		Member member = memberRepo.findByEmail(loginDto.getEmail());
		if (Objects.isNull(member)) {
			throw new ResourceNotFoundException(
					"User with email does not exist.");
		}
		String decodedInPass = decode.decodePassword(
				loginDto.getPassword());
		String decodedDbPass = decode.decodePassword(
				member.getPassword());

		if (!decodedInPass.equals(decodedDbPass)) {
			throw new UnauthorizedException(
					"Invalid Credentials!");
		}
		MemberOutDto memberDto = conversion.memberToOutDto(member);
		if (!member.getIsLoggedIn()) {
			memberDto.setIsLoggedIn(true);
			member.setIsLoggedIn(true);
			memberRepo.save(member);
		} else {
			memberDto.setIsLoggedIn(false);
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
		Member member = memberRepo.findByEmail(email);
		if (!member.getPassword().equals(
			  changePasswordDto.getOldPassword())) {
			throw new UnauthorizedException(
				"Invalid Credentials for Old Password");
		}
		final String passPattern = "^(?=.*[a-z])(?=.*[A-Z])"
		    + "(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
		if (Objects.isNull(changePasswordDto.getNewPassword())
				|| (!decode.decodePassword(
					changePasswordDto.getNewPassword()).
						matches(passPattern))) {
			throw new IllegalArgumentException(
					"Password not acceptable");
		}
		member.setPassword(changePasswordDto.getNewPassword());
		member = memberRepo.save(member);
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
		Member member = memberRepo.findByEmail(
				email);
		if (Objects.isNull(member)) {
			throw new ResourceNotFoundException(
				"Member with this email does not exists");
		}
		memberRepo.delete(member);
	}
}
