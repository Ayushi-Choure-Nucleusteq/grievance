package com.grievance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grievance.entity.Department;

/**
 * Repository interface for the Department entity.
 */
@Repository
public interface DepartmentRepo extends JpaRepository<Department, Integer> {

     /**
     * Finds a department by its name.
     *
     * @param deptName the department name
     * @return the department
     */
    Department findByDeptName(String deptName);
}
