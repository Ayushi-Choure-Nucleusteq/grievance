package com.grievance.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Represents a comment in the system.
 */
@Entity
public final class Comment {

    /**
     * Unique identifier for the comment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    /**
     * Content of the comment.
     */
    @Column(name = "comments")
    private String content;

    /**
     * The creation time of the comment.
     */
    @Column(name = "creation_time")
    private String creationTime;

    /**
     * Name of the employee.
     */
    @Column(name = "member_name")
    private String memberEmail;

    /**
     * The associated ticket of the comment.
     */
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    @JsonBackReference
    private Ticket ticket;

    /**
     * Gets the comment ID.
     *
     * @return the comment ID
     */
    public Integer getCommentId() {
        return commentId;
    }

    /**
     * Sets the comment ID.
     *
     * @param aCommentId the comment ID
     */
    public void setCommentId(final Integer aCommentId) {
        this.commentId = aCommentId;
    }

    /**
     * Gets the content of the comment.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the comment.
     *
     * @param aContent the content
     */
    public void setContent(final String aContent) {
        this.content = aContent;
    }

    /**
     * Gets the name of the employee.
     *
     * @return the employee name
     */
    public String getMemberEmail() {
        return memberEmail;
    }

    /**
     * Gets the creation time.
     *
     * @return the employee name
     */
    public String getCreationTime() {
        return creationTime;
	}

    /**
     * sets the name of the employee.
     *
     * @param acreationTime
     */
	public void setCreationTime(final String acreationTime) {
		this.creationTime = acreationTime;
	}

	/**
	 * get ticket.
	 * @return ticket.
	 */
	public Ticket getTicket() {
		return ticket;
	}

	/**
	 * set ticket.
	 *
	 * @param aticket
	 */
	public void setTicket(final Ticket aticket) {
		this.ticket = aticket;
	}

	/**
     * Sets the name of the employee.
     *
     * @param aMemberEmail the employee name
     */
    public void setMemberEmail(final String aMemberEmail) {
        this.memberEmail = aMemberEmail;
    }

    /**
     * Default constructor.
     */
    public Comment() {
        super();
    }

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId
				+ ", content=" + content
				+ ", creationTime=" + creationTime
				+ ", memberEmail=" + memberEmail
				+ ", ticket=" + ticket + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(commentId, content,
				creationTime, memberEmail);
	}

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
		Comment other = (Comment) obj;
		return Objects.equals(commentId, other.commentId)
			&& Objects.equals(content, other.content)
			&& Objects.equals(creationTime, other.creationTime)
			&& Objects.equals(memberEmail, other.memberEmail);
	}




}
