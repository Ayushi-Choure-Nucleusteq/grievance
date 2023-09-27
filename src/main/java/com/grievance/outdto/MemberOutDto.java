package com.grievance.outdto;

import java.util.Objects;

import com.grievance.enums.MemberRole;

/**
 * Represents a member with its associated details.
 */
public final class MemberOutDto {

    /** The ID of the member. */
	private Integer id;

	/** Name of the member. */
	private String name;

	/** Indicates if the member is logged in. */
	private Boolean isLoggedIn;

	/** Email address of the member. */
	private String email;

	/** Role of the member. */
	private MemberRole role;

	/** Department name associated with the member. */
	private String deptName;

	/**
	 * Default constructor.
	 */
	public MemberOutDto() {
	}

	/**
	 * Gets the ID of the member.
	 *
	 * @return the ID of the member.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the ID of the member.
	 *
	 * @param aid the ID to set.
	 */
	public void setId(final Integer aid) {
		this.id = aid;
	}

	/**
	 * Retrieves the name of the member.
	 *
	 * @return Name of the member.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the member.
	 *
	 * @param aname Name to set.
	 */
	public void setName(final String aname) {
		this.name = aname;
	}

	/**
	 * Checks if the member is logged in.
	 *
	 * @return True if the member is logged in; false otherwise.
	 */
	public Boolean getIsLoggedIn() {
		return isLoggedIn;
	}

	/**
	 * Sets the logged in status for the member.
	 *
	 * @param aisLoggedIn Logged in status.
	 */
	public void setIsLoggedIn(final Boolean aisLoggedIn) {
		this.isLoggedIn = aisLoggedIn;
	}

	/**
	 * Retrieves the email address of the member.
	 *
	 * @return Email of the member.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email for the member.
	 *
	 * @param aemail Email to set.
	 */
	public void setEmail(final String aemail) {
		this.email = aemail;
	}

	/**
	 * Retrieves the role of the member.
	 *
	 * @return Role of the member.
	 */
	public MemberRole getRole() {
		return role;
	}

	/**
	 * Sets the role for the member.
	 *
	 * @param arole Role to set.
	 */
	public void setRole(final MemberRole arole) {
		this.role = arole;
	}

	/**
	 * Retrieves the department name of the member.
	 *
	 * @return Department name.
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * Sets the department name for the member.
	 *
	 * @param adeptName Department name to set.
	 */
	public void setDeptName(final String adeptName) {
		this.deptName = adeptName;
	}

	/**
	 *hashcode method.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id, deptName,
				email, isLoggedIn,
				name, role);
	}

	/**
	 *equals method.
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		MemberOutDto other = (MemberOutDto) obj;
		return Objects.equals(id, other.id)
				&& Objects.equals(deptName, other.deptName)
				&& Objects.equals(email, other.email)
				&& Objects.equals(isLoggedIn, other.isLoggedIn)
				&& Objects.equals(name, other.name)
				&& role == other.role;
	}

	/**
	 * to return string representation of object.
	 */
	@Override
	public String toString() {
		return "MemberOutDto [id=" + id + ","
				+ " name=" + name + ","
				+ " isLoggedIn=" + isLoggedIn
				+ ", email=" + email
				+ ", role=" + role
				+ "," + " deptName=" + deptName + "]";
	}

}
