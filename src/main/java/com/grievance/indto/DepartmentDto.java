package com.grievance.indto;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
/**
 * Represents a DTO for the Department entity.
 */
public final class DepartmentDto {

     /**
	 * Department Name.
	 */
    @NotEmpty(message = "Department is required")
    @Column(unique = true)
    private String deptName;


    /**
     * Retrieves the department name.
     *
     * @return the department name.
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * Sets the department name.
     *
     * @param aname the department name to set.
     */
    public void setDeptName(final String aname) {
        this.deptName = aname;
    }

    /**
     * Default constructor.
     */
    public DepartmentDto() {
        super();
    }

    /**
     * Constructor that accepts the department name.
     *
     * @param aname the department name.
     */
    public DepartmentDto(final String aname) {
        this.deptName = aname;
    }

	/**
	 *To string deptDo object.
	 */
	@Override
	public String toString() {
		return "DepartmentDto [deptName=" + deptName + "]";
	}


}
