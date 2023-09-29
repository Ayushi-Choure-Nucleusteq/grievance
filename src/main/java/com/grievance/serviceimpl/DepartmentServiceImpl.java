package com.grievance.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
		Department existingDept = deptRepo
			.findByDeptName(deptDto.getDeptName());
		if (Objects.nonNull(existingDept)) {
			throw new RecordAlreadyExistException(
			  "Department with this name already exists.");
		}
		Department dept = conversion.departmentDtoToEntity(deptDto);
		Department savedDept = deptRepo.save(dept);
		return conversion.departmentToOutDto(savedDept);
	}

	/**
	 * Business logic to fetch all existing departments.
	 *
	 * @return a list of department DTOs
	 */
	@Override
	public List<DepartmentOutDto> getAllDepts() {
		List<Department> dept = deptRepo.findAll();
		if (Objects.isNull(dept)) {
			throw new ResourceNotFoundException(
					"No department exists");
		}
		List<DepartmentOutDto> deptDtos = new ArrayList<>();
		for (Department department : dept) {
			DepartmentOutDto deptDto = conversion
					.departmentToOutDto(department);
			deptDtos.add(deptDto);
		}
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
		final Integer pageSize = 8;
		Page<Department> dept = deptRepo.findAll(
		        PageRequest.of(pageNo, pageSize));
		if (Objects.isNull(dept)) {
			throw new ResourceNotFoundException(
					"No department exists");
		}
		List<DepartmentOutDto> deptDtos = new ArrayList<>();
		for (Department department : dept) {
			DepartmentOutDto deptDto = conversion
					.departmentToOutDto(department);
			deptDtos.add(deptDto);
		}
		return deptDtos;
	}

	/**
	 * Business logic to delete a department.
	 *
	 * @param deptDto  the department data transfer object
	 */
	@Override
	public void deleteDept(final DepartmentDto deptDto) {
		Department dept = deptRepo.findByDeptName(
				deptDto.getDeptName());
		if (Objects.isNull(dept)) {
			throw new ResourceNotFoundException(
				"Department with this name does not exists");
		}
		deptRepo.delete(dept);
	}

}
