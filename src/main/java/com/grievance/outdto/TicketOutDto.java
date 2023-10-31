package com.grievance.outdto;

import java.util.List;
import java.util.Objects;
import com.grievance.entity.Comment;
import com.grievance.enums.TicketStatus;
import com.grievance.enums.TicketType;

/**
 * Represents a ticket with its associated details.
 */
public final class TicketOutDto {

    /** Id of the ticket. */
    private Integer ticketId;

    /** Name of the ticket. */
    private String ticketName;

    /** Description of the ticket. */
    private String description;

    /** Status of the ticket. */
    private TicketStatus status;

    /** Creation date of the ticket. */
    private String creationDate;

    /** Last update date of the ticket. */
    private String lastUpdateDate;

    /** Type of the ticket. */
    private TicketType ticketType;

    /** Member associated with the ticket. */
    private String member;

    /** Department of the ticket. */
    private String department;

    /** Comments associated with the ticket. */
    private List<Comment> comments;

    /**
     * Default constructor.
     */
    public TicketOutDto() {
    }

    /**
     * Gets the ticket ID.
     * @return The ticket ID.
     */
    public Integer getTicketId() {
        return ticketId;
    }

    /**
     * Sets the ticket ID.
     * @param aticketId the ID to set.
     */
    public void setTicketId(final Integer aticketId) {
        this.ticketId = aticketId;
    }

    /**
     * Gets the ticket name.
     * @return The ticket name.
     */
    public String getTicketName() {
        return ticketName;
    }

    /**
     * Sets the ticket name.
     * @param aticketName Name of the ticket.
     */
    public void setTicketName(final String aticketName) {
        this.ticketName = aticketName;
    }

    /**
     * Gets the description of the ticket.
     * @return The ticket's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the ticket.
     * @param adescription The description to set.
     */
    public void setDescription(final String adescription) {
        this.description = adescription;
    }

    /**
     * Gets the status of the ticket.
     * @return The ticket's status.
     */
    public TicketStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the ticket.
     * @param astatus The status to set.
     */
    public void setStatus(final TicketStatus astatus) {
        this.status = astatus;
    }

    /**
     * Gets the creation date of the ticket.
     * @return The creation date.
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the ticket.
     * @param acreationDate The creation date to set.
     */
    public void setCreationDate(final String acreationDate) {
        this.creationDate = acreationDate;
    }

    /**
     * Gets the last update date of the ticket.
     * @return The last update date.
     */
    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * Sets the last update date of the ticket.
     * @param alastUpdateDate The last update date to set.
     */
    public void setLastUpdateDate(
    		final String alastUpdateDate) {
        this.lastUpdateDate = alastUpdateDate;
    }

    /**
     * Gets the type of the ticket.
     * @return The ticket type.
     */
    public TicketType getTicketType() {
        return ticketType;
    }

    /**
     * Sets the type of the ticket.
     * @param aticketType The ticket type to set.
     */
    public void setTicketType(final TicketType aticketType) {
        this.ticketType = aticketType;
    }

    /**
     * Gets the member associated with the ticket.
     * @return The member.
     */
    public String getMember() {
        return member;
    }

    /**
     * Sets the member associated with the ticket.
     * @param amember The member to set.
     */
    public void setMember(final String amember) {
        this.member = amember;
    }

    /**
     * Gets the department of the ticket.
     * @return The department.
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the department of the ticket.
     * @param adepartment The department to set.
     */
    public void setDepartment(final String adepartment) {
        this.department = adepartment;
    }

    /**
     * Gets the comments associated with the ticket.
     * @return The list of comments.
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * Sets the comments associated with the ticket.
     * @param acomments The list of comments to set.
     */
    public void setComments(final List<Comment> acomments) {
        this.comments = acomments;
    }

    @Override
    public int hashCode() {
        return Objects.hash(comments, creationDate,
        	department, description,
            lastUpdateDate, member,
            status, ticketId,
            ticketName, ticketType);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TicketOutDto other = (TicketOutDto) obj;
        return Objects.equals(comments, other.comments)
            && Objects.equals(creationDate, other.creationDate)
            && Objects.equals(department, other.department)
            && Objects.equals(description, other.description)
            && Objects.equals(lastUpdateDate, other.lastUpdateDate)
            && Objects.equals(member, other.member)
            && status == other.status && Objects.equals(
            		ticketId, other.ticketId)
            && Objects.equals(ticketName, other.ticketName)
            && ticketType == other.ticketType;
    }

    /**
     *String of TicketOutDto object.
     */
    @Override
    public String toString() {
        return "TicketOutDto [ticketId=" + ticketId + ", ticketName="
        	+ ticketName
            + ", description=" + description + ", status="
        	+ status
            + ", creationDate=" + creationDate
            + ", lastUpdateDate=" + lastUpdateDate
            + ", ticketType=" + ticketType
            + ", member=" + member + ", department="
            + department + ", comments="
            + comments + "]";
    }
}
