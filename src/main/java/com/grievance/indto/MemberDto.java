package com.grievance.indto;

import javax.persistence.Column;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.grievance.enums.MemberRole;

/**
 * MemberDto Data Transfer Object Class. Used to encapsulate member data for
 * transfer between system layers.
 */
public final class MemberDto {

    /** Name of the member. */
	@NotEmpty(message = "Name is required")
	private String name;

	/**
	 * Email address of the member. Expected to be in a valid email format.
	 */
	@Email
	@NotEmpty(message = "Email is required")
	@Pattern(regexp = "^[a-z0-9_.]+@nucleusteq.com$",
					message = "email not valid")
	@Column(unique = true)
	private String email;

	/**
	 * Password for the member.
	 */
//	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "password not valid")
	private String password;

	/**
	 * Role associated with the member.
	 */
	@NotNull(message = "Role is required")
	private MemberRole role;

	/**
	 * Department of the member.
	 */
	@NotNull(message = "Department is required")
	private DepartmentDto department;

	/**
	 * Retrieve name of the user.
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the member.
	 *
	 * @param newName The name value to set.
	 */
	public void setName(final String newName) {
		this.name = newName;
	}

	/**
	 * Retrieves the mail of the user.
	 *
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email of the member.
	 *
	 * @param emailAddress The email value to set.
	 */
	public void setEmail(final String emailAddress) {
		this.email = emailAddress;
	}

	/**
	 * Retrieves the password of the user.
	 *
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of the member.
	 *
	 * @param pwd The password value to set.
	 */
	public void setPassword(final String pwd) {
		this.password = pwd;
	}

	/**
	 * Retrieves role for member.
	 *
	 * @return role
	 */
	public MemberRole getRole() {
		return role;
	}

	/**
	 * Sets the role of the member.
	 *
	 * @param amemberRole The role value to set.
	 */
	public void setRole(final MemberRole amemberRole) {
		this.role = amemberRole;
	}

	/**
	 * Retrieves the department of the use.
	 *
	 * @return department
	 */
	public DepartmentDto getDepartment() {
		return department;
	}

	/**
	 * Sets the department of the member.
	 *
	 * @param dept The department value to set.
	 */
	public void setDepartment(final DepartmentDto dept) {
		this.department = dept;
	}

	/**
	 * to return string representation of object.
	 */
	@Override
	public String toString() {
		return "MemberDto [name=" + name
				+ ", email=" + email
				+ ", password=" + password
				+ ", role=" + role
				+ ", department=" + department + "]";
	}
}
