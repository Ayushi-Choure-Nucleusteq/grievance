package com.grievance.entity;

import java.util.List;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.grievance.enums.MemberRole;

/**
 * Represents a member in the system.
 */
@Entity
public final class Member {

    /** Unique identifier for the member. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/** Name of the member. */
	private String name;

	/** Email of the member. */
	@Column(unique = true)
	private String email;

	/** Password of the member. */
	private String password;

	/** Role of the member: admin/member. */
	@Enumerated(EnumType.STRING)
	private MemberRole role;

	/**user is logged in or not. */
	private Boolean isLoggedIn;

	/** Department the member belongs to. */
	@ManyToOne
	private Department department;

	/** List of tickets that belong to member. */
	@OneToMany(cascade = CascadeType.ALL,
			mappedBy = "member", fetch = FetchType.LAZY)
	private List<Ticket> ticket;

	/**
	 * Returns the member's ID.
	 *
	 * @return Integer representing the member's ID.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the member's ID.
	 *
	 * @param memberId the member's ID to set.
	 */
	public void setId(final Integer memberId) {
		this.id = memberId;
	}

	/**
	 * Returns the member's name.
	 *
	 * @return String representing the member's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the member's name.
	 *
	 * @param memberName the member's name to set.
	 */
	public void setName(final String memberName) {
		this.name = memberName;
	}

	/**
	 * Returns the member's email.
	 *
	 * @return String representing the member's email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the member's email.
	 *
	 * @param memberEmail the member's email to set.
	 */
	public void setEmail(final String memberEmail) {
		this.email = memberEmail;
	}

	/**
	 * Returns the member's password.
	 *
	 * @return String representing the member's password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the member's password.
	 *
	 * @param memberPassword the member's password to set.
	 */
	public void setPassword(final String memberPassword) {
		this.password = memberPassword;
	}

	/**
	 * Returns the role of the member.
	 *
	 * @return MemberRole representing the member's role.
	 */
	public MemberRole getRole() {
		return role;
	}

	/**
	 * Sets the member's role.
	 *
	 * @param memberRole the role to set for the member.
	 */
	public void setRole(final MemberRole memberRole) {
		this.role = memberRole;
	}

	/**
	 * Returns the login status of the member.
	 *
	 * @return Boolean indicating if the member is logged in.
	 */
	public Boolean getIsLoggedIn() {
		return isLoggedIn;
	}

	/**
	 * Sets the login status of the member.
	 *
	 * @param aloggedIn Boolean value representing the login status.
	 */
	public void setIsLoggedIn(final Boolean aloggedIn) {
		this.isLoggedIn = aloggedIn;
	}

	/**
	 * Returns the department associated with the member.
	 *
	 * @return Department associated with the member.
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * Sets the department for the member.
	 *
	 * @param dept Department to associate with the member.
	 */
	public void setDepartment(final Department dept) {
		this.department = dept;
	}

	/**
	 * Returns the list of tickets associated with the member.
	 *
	 * @return List of Ticket objects.
	 */
	public List<Ticket> getTicket() {
		return ticket;
	}

	/**
	 * Sets the list of tickets for the member.
	 *
	 * @param atickets List of tickets to associate with the member.
	 */
	public void setTicket(final List<Ticket> atickets) {
		this.ticket = atickets;
	}

	/**
	 * A string of member object.
	 *
	 * @return String representation of the member.
	 */
	@Override
	public String toString() {
		return String.format("Member [id=%d, name=%s,"
				+ "email=%s, role=%s, isLoggedIn=%s]",
				id, name, email, role,
				isLoggedIn);
	}

	/**
	 * Default constructor for Member.
	 */
	public Member() {
		super();
	}

	/**
	 * Computes the hash code for the member.
	 *
	 * @return int representing the hash code.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(email, id, isLoggedIn,
				name, password, role);
	}

	/**
	 * Compares the current member object with another object for equality.
	 *
	 * @param obj Object to compare with.
	 * @return boolean indicating whether the two objects are equal.
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Member other = (Member) obj;
		return Objects.equals(email, other.email)
				&& Objects.equals(id, other.id)
				&& Objects.equals(isLoggedIn, other.isLoggedIn)
				&& Objects.equals(name, other.name)
				&& Objects.equals(password, other.password)
				&& role == other.role;
	}

}
