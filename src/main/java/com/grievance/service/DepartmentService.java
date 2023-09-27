package com.grievance.service;

import java.util.List;

import com.grievance.indto.DepartmentDto;
import com.grievance.outdto.DepartmentOutDto;

/**
 * DepartmentService Interface.
 * <p>
 * Defines the business operations related to departments within the system.
 * </p>
 */
public interface DepartmentService {

	/**
	 * Creates a new department in the system.
	 *
	 * @param deptDto Data Transfer Object representing the department.
	 * @return DepartmentOutDto.
	 */
	DepartmentOutDto createDepartment(DepartmentDto deptDto);

	/**
	 * Fetches a list of all departments present in the system.
	 *
	 * @return List of DepartmentOutDto, each representing a department.
	 */
	List<DepartmentOutDto> getAllDepts();

	/**
	 * Fetches a list of all departments present in the system.
	 *
	 *@param pageNo
	 * @return List of DepartmentOutDto, each representing a department.
	 */
	List<DepartmentOutDto> getAllDeptsPage(Integer pageNo);

	/**
	 * Deletes a specific department from the system.
	 *
	 * @param deptdto  The DTO representing the department to be deleted.
	 */
	void deleteDept(DepartmentDto deptdto);

}
