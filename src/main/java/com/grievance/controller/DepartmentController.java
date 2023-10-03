package com.grievance.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grievance.indto.DepartmentDto;
import com.grievance.outdto.DepartmentOutDto;
import com.grievance.service.DepartmentService;

/**
 * Controller class for handling department related requests.
 */
@RestController
@RequestMapping("/api/")
@CrossOrigin("*")
public class DepartmentController {

    /**
	 * Logger.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DepartmentController.class);


	/**
	 * Service to handle department related operations.
	 */
	@Autowired
	private DepartmentService deptService;

	/**
	 * To fetch all existing departments.
	 *
	 *@param pageNo
	 * @return ResponseEntity.
	 */
	@GetMapping("department/getAll")
	public ResponseEntity<List<DepartmentOutDto>> getAllDepts(
			@RequestParam(required = false)
			final Integer pageNo) {
		LOGGER.info("Fetching all departments...");
		if (Objects.nonNull(pageNo)) {
			List<DepartmentOutDto> departments =
					deptService.getAllDeptsPage(pageNo);
			LOGGER.info("Fetched {} departments page.",
					departments.size());
			return new ResponseEntity<>(departments,
					HttpStatus.ACCEPTED);
		}
		List<DepartmentOutDto> departments = deptService.getAllDepts();
		LOGGER.info("Fetched {} departments.", departments.size());
		return new ResponseEntity<>(departments, HttpStatus.ACCEPTED);
	}

	/**
	 * To delete a department by its ID.
	 *
	 * @param deptDto - Department data to be deleted.
	 * @return ResponseEntity indicating deletion and HTTP status.
	 */
	@DeleteMapping("department/delete")
	public ResponseEntity<?> deleteDepartment(@Valid
			@RequestBody final DepartmentDto deptDto) {
		LOGGER.info("Request to delete department with ID: {}",
				deptDto.getDeptName());
		deptService.deleteDept(deptDto);
		LOGGER.info("Department with ID: {} deleted successfully.",
				deptDto.getDeptName());
		return new ResponseEntity<>("Department Deleted",
				HttpStatus.OK);
	}

	/**
	 * To create a department with authentication.
	 *
	 * @param deptDto  - Department data to be created.
	 * @return ResponseEntity having the department and HTTP status.
	 */
	@PostMapping("department/create")
	public ResponseEntity<DepartmentOutDto> createDeptAuth(@Valid
			@RequestBody final DepartmentDto deptDto) {
		LOGGER.info(
				"Request to create new department : {}",
				deptDto.getDeptName());
		DepartmentOutDto createdDept = deptService
				.createDepartment(deptDto);
		LOGGER.info("Department with name: {} created successfully.",
				createdDept.getDeptName());
		return new ResponseEntity<>(createdDept,
				HttpStatus.CREATED);
	}

}
