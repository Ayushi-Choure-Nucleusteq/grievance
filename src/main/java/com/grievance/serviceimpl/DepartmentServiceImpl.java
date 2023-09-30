package com.grievance.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.grievance.entity.Department;
import com.grievance.exception.RecordAlreadyExistException;
import com.grievance.exception.ResourceNotFoundException;
import com.grievance.indto.DepartmentDto;
import com.grievance.outdto.DepartmentOutDto;
import com.grievance.repository.DepartmentRepo;
import com.grievance.service.DepartmentService;

/**
 * Service implementation for department operations.
 */
@Service
public final class DepartmentServiceImpl implements DepartmentService {
    
    /**
     * Loggers.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

     /**
	 * Repository for Department related operations.
	 */
	@Autowired
	private DepartmentRepo deptRepo;

	/**
	 * Conversion for conversion from indto to entity to outdto.
	 */
	@Autowired
	private Conversion conversion;

	/**
	 * Business logic for creation of new department.
	 *
	 * @param deptDto the department data transfer object
	 * @return the output DTO of the created department
	 */
	@Override
	public DepartmentOutDto createDepartment(
			final DepartmentDto deptDto) {
	    LOGGER.info("Attempting to create a new "
	            + "department with name: {}", deptDto.getDeptName());

		Department existingDept = deptRepo
			.findByDeptName(deptDto.getDeptName());
		if (Objects.nonNull(existingDept)) {
		    LOGGER.error("Failed to create department. "
		            + "Department with name {} already exists."
		            , deptDto.getDeptName());
			throw new RecordAlreadyExistException(
			  "Department with this name already exists.");
		}
		Department dept = conversion.departmentDtoToEntity(deptDto);
		Department savedDept = deptRepo.save(dept);
		LOGGER.info("Successfully created department "
		        + "with name: {}", savedDept.getDeptName());
		return conversion.departmentToOutDto(savedDept);
	}

	/**
	 * Business logic to fetch all existing departments.
	 *
	 * @return a list of department DTOs
	 */
	@Override
	public List<DepartmentOutDto> getAllDepts() {
	    LOGGER.info("Fetching all departments from the database");
		List<Department> dept = deptRepo.findAll();
		if (Objects.isNull(dept) || dept.isEmpty()) {
		    LOGGER.info("No departments found in the database");
			throw new ResourceNotFoundException(
					"No department exists");
		}
		List<DepartmentOutDto> deptDtos = new ArrayList<>();
		for (Department department : dept) {
			DepartmentOutDto deptDto = conversion
					.departmentToOutDto(department);
			deptDtos.add(deptDto);
		}
		LOGGER.info("Successfully fetched {} "
		        + "departments", deptDtos.size());
		return deptDtos;
	}

	/**
	 * Business logic to fetch all existing departments.
	 *
	 *@param pageNo
	 * @return a list of department DTOs
	 */
	@Override
	public List<DepartmentOutDto> getAllDeptsPage(final Integer pageNo) {
	    LOGGER.info("Fetching all departments from the database pagination");
		final Integer pageSize = 8;
		Page<Department> dept = deptRepo.findAll(
		        PageRequest.of(pageNo, pageSize));
		if (Objects.isNull(dept) || dept.isEmpty()) {
		    LOGGER.info("No departments found in the database");
			throw new ResourceNotFoundException(
					"No department exists");
		}
		List<DepartmentOutDto> deptDtos = new ArrayList<>();
		for (Department department : dept) {
			DepartmentOutDto deptDto = conversion
					.departmentToOutDto(department);
			deptDtos.add(deptDto);
		}
		LOGGER.info("Successfully fetched {} "
                + "departments", deptDtos.size());
		return deptDtos;
	}

	/**
	 * Business logic to delete a department.
	 *
	 * @param deptDto  the department data transfer object
	 */
	@Override
	public void deleteDept(final DepartmentDto deptDto) {
	    LOGGER.info("Initiating request to delete department"
	            + " with name: {}", deptDto.getDeptName());
		Department dept = deptRepo.findByDeptName(
				deptDto.getDeptName());
		if (Objects.isNull(dept)) {
		    LOGGER.error("No department found with"
		            + " the name: {}", deptDto.getDeptName());
			throw new ResourceNotFoundException(
				"Department with this name does not exists");
		}
		LOGGER.info("Successfully deleted department "
		        + "with name: {}", deptDto.getDeptName());
		deptRepo.delete(dept);
	}

}
