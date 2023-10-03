package com.grievance.outdto;

import java.util.Objects;

/**
 * Data Transfer Object representing a department.
 */
public final class DepartmentOutDto {

    /** The Id of the department. */
    private Integer deptId;

    /** The name of the department. */
    private String deptName;

    /**
     * Default constructor.
     */
    public DepartmentOutDto() {
    }

    /**
     * Gets the department ID.
     *
     * @return the department ID
     */
    public Integer getDeptId() {
        return deptId;
    }

    /**
     * Sets the department ID.
     *
     * @param id the department ID to set
     */
    public void setDeptId(final Integer id) {
        this.deptId = id;
    }

    /**
     * Retrieves the name of the department.
     *
     * @return the department name
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * Sets the name of the department.
     *
     * @param aname the department name
     */
    public void setDeptName(final String aname) {
        this.deptName = aname;
    }

    /**
     *hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(deptId, deptName);
    }

    /**
     *equals.
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        DepartmentOutDto other = (DepartmentOutDto) object;
        return Objects.equals(deptId, other.deptId)
        		&& Objects.equals(deptName, other.deptName);
    }

	/**
	 *to string of departmentOutDto.
	 */
	@Override
	public String toString() {
		return "DepartmentOutDto [deptId="
	+ deptId + ", deptName="
				+ deptName + "]";
	}


}
