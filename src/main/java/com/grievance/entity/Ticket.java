package com.grievance.entity;

import java.util.List;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.grievance.enums.TicketStatus;
import com.grievance.enums.TicketType;

/**
 * Entity class representing a Ticket in the system.
 */
@Entity
public final class Ticket {

    /** Id of the ticket. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ticketId;

	/** Name of the ticket. */
	@Column(name = "ticket_name")
	private String ticketName;

	/** Description of the ticket. */
	@Column(name = "description")
	private String description;

	/** Status of the ticket. */
	private TicketStatus status;

	/** Creation date of the ticket. */
	private String creationDate;

	/** Last update date of the ticket. */
	@Column(name = "last_update")
	private String lastUpdateDate;

	/** Type of the ticket. */
	@Column(name = "ticket_type")
	private TicketType ticketType;

	/** Department of the ticket. */
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	/** Member associated with the ticket. */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "member_id")
	private Member member;

	/** Comments associated with the ticket. */
	@OneToMany(cascade = CascadeType.ALL,
			mappedBy = "ticket", fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Comment> comments;

	/**
	 * Gets the ticket ID.
	 *
	 * @return the ticket ID
	 */
	public Integer getTicketId() {
		return ticketId;
	}

	/**
	 * Sets the ticket ID.
	 *
	 * @param aticketId the ticket ID
	 */
	public void setTicketId(final Integer aticketId) {
		this.ticketId = aticketId;
	}

	/**
	 * Gets the ticket name.
	 *
	 * @return the ticket name
	 */
	public String getTicketName() {
		return ticketName;
	}

	/**
	 * Sets the ticket name.
	 *
	 * @param aticketName the ticket name
	 */
	public void setTicketName(final String aticketName) {
		this.ticketName = aticketName;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param adescription the description
	 */
	public void setDescription(final String adescription) {
		this.description = adescription;
	}

	/**
	 * Gets the ticket status.
	 *
	 * @return the status
	 */
	public TicketStatus getStatus() {
		return status;
	}

	/**
	 * Sets the ticket status.
	 *
	 * @param astatus the ticket status
	 */
	public void setStatus(final TicketStatus astatus) {
		this.status = astatus;
	}

	/**
	 * Gets the creation date.
	 *
	 * @return the creation date
	 */
	public String getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 *
	 * @param acreationDate the creation date
	 */
	public void setCreationDate(final String acreationDate) {
		this.creationDate = acreationDate;
	}

	/**
	 * Gets the last update date.
	 *
	 * @return the last update date
	 */
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * Sets the last update date.
	 *
	 * @param alastUpdateDate the last update date
	 */
	public void setLastUpdateDate(final String alastUpdateDate) {
		this.lastUpdateDate = alastUpdateDate;
	}

	/**
	 * Gets the ticket type.
	 *
	 * @return the ticket type
	 */
	public TicketType getTicketType() {
		return ticketType;
	}

	/**
	 * Sets the ticket type.
	 *
	 * @param aticketType the ticket type
	 */
	public void setTicketType(final TicketType aticketType) {
		this.ticketType = aticketType;
	}

	/**
	 * Gets the associated department.
	 *
	 * @return the department
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * Sets the associated department.
	 *
	 * @param adepartment the department
	 */
	public void setDepartment(final Department adepartment) {
		this.department = adepartment;
	}

	/**
	 * Gets the member associated with the ticket.
	 *
	 * @return the member
	 */
	public Member getMember() {
		return member;
	}

	/**
	 * Sets the member associated with the ticket.
	 *
	 * @param amember the member
	 */
	public void setMember(final Member amember) {
		this.member = amember;
	}

	/**
	 * Gets the list of comments for the ticket.
	 *
	 * @return the list of comments
	 */
	public List<Comment> getComments() {
		return comments;
	}

	/**
	 * Sets the list of comments for the ticket.
	 *
	 * @param acomments the list of comments
	 */
	public void setComments(final List<Comment> acomments) {
		this.comments = acomments;
	}

	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", ticketName="
				+ ticketName + ", description=" + description
				+ ", status=" + status + ", creationDate="
				+ creationDate + ", lastUpdateDate="
				+ lastUpdateDate + ", ticketType="
				+ ticketType + "]";
	}

	/**
	 * Hash Code method.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(creationDate,
				description, lastUpdateDate,
				member, status, ticketId,
				ticketName, ticketType);
	}

	/**
	 * Equals Method.
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
		Ticket other = (Ticket) obj;
		return Objects.equals(creationDate,
						other.creationDate)
				&& Objects.equals(description,
						other.description)
				&& Objects.equals(lastUpdateDate,
						other.lastUpdateDate)
				&& Objects.equals(member,
						other.member)
				&& status == other.status
				&& Objects.equals(ticketId,
						other.ticketId)
				&& Objects.equals(ticketName,
						other.ticketName)
				&& ticketType == other.ticketType;
	}


}
