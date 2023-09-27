package com.grievance.indto;

import javax.validation.constraints.NotNull;

import com.grievance.enums.TicketStatus;
import com.grievance.enums.TicketType;

/**
 * Data Transfer Object representing a Ticket.
 */
public final class TicketDto {

    /** Name of the ticket. */
    private String ticketName;

    /** Description of the ticket. */
    private String description;

    /** Status of the ticket. */
    private TicketStatus status;

    /** Creation date of the ticket. */
    private String creationDate;

    /** Date of the last update for the ticket. */
    private String lastUpdateDate;

    /** Comments related to the ticket. */
    private String comments;

    /** Type of the ticket. */
    private TicketType ticketType;

    /** Department associated with the ticket. */
    private DepartmentDto department;

    /** Member associated with the ticket. */
    @NotNull(message = "Member is required.")
    private LoginDto member;

    /**
     * @return the ticket name.
     */
    public String getTicketName() {
        return ticketName;
    }

    /**
     * Sets the ticket name.
     *
     * @param newTicketName the name to set.
     */
    public void setTicketName(final String newTicketName) {
        this.ticketName = newTicketName;
    }

    /**
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param newDescription the description to set.
     */
    public void setDescription(final String newDescription) {
        this.description = newDescription;
    }

    /**
     * @return the status.
     */
    public TicketStatus getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param newStatus the status to set.
     */
    public void setStatus(final TicketStatus newStatus) {
        this.status = newStatus;
    }

    /**
     * @return the creation date.
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date.
     *
     * @param newCreationDate the creation date to set.
     */
    public void setCreationDate(final String newCreationDate) {
        this.creationDate = newCreationDate;
    }

    /**
     * @return the last update date.
     */
    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * Sets the last update date.
     *
     * @param newLastUpdateDate the last update date to set.
     */
    public void setLastUpdateDate(final String newLastUpdateDate) {
        this.lastUpdateDate = newLastUpdateDate;
    }

    /**
     * @return the ticket type.
     */
    public TicketType getTicketType() {
        return ticketType;
    }

    /**
     * Sets the ticket type.
     *
     * @param newTicketType the type to set.
     */
    public void setTicketType(final TicketType newTicketType) {
        this.ticketType = newTicketType;
    }

    /**
     * @return the department.
     */
    public DepartmentDto getDepartment() {
        return department;
    }

    /**
     * Sets the department.
     *
     * @param newDepartment the department to set.
     */
    public void setDepartment(final DepartmentDto newDepartment) {
        this.department = newDepartment;
    }

    /**
     * @return the member.
     */
    public LoginDto getMember() {
        return member;
    }

    /**
     * Sets the member.
     *
     * @param newMember the member to set.
     */
    public void setMember(final LoginDto newMember) {
        this.member = newMember;
    }

    /**
     * @return the comments.
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the comments.
     *
     * @param newComments the comments to set.
     */
    public void setComments(final String newComments) {
        this.comments = newComments;
    }

    /**
     * Default constructor.
     */
    public TicketDto() {
        super();
    }

    /**
     * String of ticketDto object.
     *
     * @return the string representation of the object.
     */
    @Override
    public String toString() {
        return "TicketDto [ticketName=" + ticketName + ", description="
    + description + ", status=" + status
        		+ ", creationDate=" + creationDate
        		+ ", lastUpdateDate=" + lastUpdateDate
        		+ ", ticketType=" + ticketType
        		+ ", department=" + department
        		+ ", member=" + member + "]";
    }
}
