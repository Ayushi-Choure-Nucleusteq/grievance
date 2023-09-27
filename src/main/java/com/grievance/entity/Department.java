package com.grievance.entity;

import java.util.ArrayList;


import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Represents a department in the system.
 */
@Entity
@Table(name = "department")
public final class Department {

    /**
     * Unique identifier for department.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deptId;

    /**
     * Department Name which must be unique.
     */
    @Column(unique = true)
    private String deptName;

    /**
     * department having one to many relation with member.
     */
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "department", fetch = FetchType.LAZY)
    private List<Member> members = new ArrayList<>();

    /**
     * department having one to many relation with ticket.
     */
    @OneToMany(cascade = CascadeType.ALL,
    		mappedBy = "department", fetch = FetchType.LAZY)
    private List<Ticket> ticket;

    /**
     * Gets the department ID.
     * @return the department ID.
     */
    public Integer getDeptId() {
        return deptId;
    }

    /**
     * Sets the department ID.
     * @param adeptId the department ID to set.
     */
    public void setDeptId(final Integer adeptId) {
        this.deptId = adeptId;
    }

    /**
     * Gets the department name.
     * @return the department name.
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * Sets the department name.
     * @param adeptName the department name to set.
     */
    public void setDeptName(final String adeptName) {
        this.deptName = adeptName;
    }

    /**
     * Gets the list of members.
     * @return the list of members.
     */
    public List<Member> getMembers() {
        return members;
    }

    /**
     * Sets the list of members.
     * @param amembers the list of members to set.
     */
    public void setMembers(final List<Member> amembers) {
        this.members = amembers;
    }

    /**
     * Gets the list of tickets.
     * @return the list of tickets.
     */
    public List<Ticket> getTicket() {
        return ticket;
    }

    /**
     * Sets the list of tickets.
     * @param aticket the list of tickets to set.
     */
    public void setTicket(final List<Ticket> aticket) {
        this.ticket = aticket;
    }

    /**
     * Default constructor.
     */
    public Department() {
        super();
    }

    /**
     * to string of object.
     */
    @Override
    public String toString() {
        return "Department [deptId=" + deptId
        	+ ", deptName=" + deptName + "]";
    }

	@Override
	public int hashCode() {
		return Objects.hash(deptId, deptName);
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
		Department other = (Department) obj;
		return Objects.equals(deptId, other.deptId)
			&& Objects.equals(deptName, other.deptName);
	}


}
