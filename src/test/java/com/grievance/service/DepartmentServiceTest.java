package com.grievance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.grievance.outdto.DepartmentOutDto;
import com.grievance.entity.Department;
import com.grievance.exception.RecordAlreadyExistException;
import com.grievance.exception.ResourceNotFoundException;
import com.grievance.indto.DepartmentDto;
import com.grievance.repository.DepartmentRepo;
import com.grievance.serviceimpl.Conversion;
import com.grievance.serviceimpl.DepartmentServiceImpl;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @InjectMocks
    DepartmentServiceImpl departmentService;

    @Mock
    DepartmentRepo deptRepo;

    @Spy
    Conversion conversion; 

    Department department;
    DepartmentDto departmentDto;
    DepartmentOutDto departmentOutDto;

    @BeforeEach
    void setUp() {
    	
        department = new Department();
        department.setDeptId(1);
        department.setDeptName("HR");

        departmentDto = new DepartmentDto();
        departmentDto.setDeptName("HR");

        departmentOutDto = new DepartmentOutDto();
        departmentOutDto.setDeptName("HR");
        departmentOutDto.setDeptId(1);
        
        
    }
    
    @Test
    public void testCreateDepartment_AlreadyExists() {
        when(deptRepo.findByDeptName(departmentDto.getDeptName())).thenReturn(department);

        assertThrows(RecordAlreadyExistException.class, () -> {
            departmentService.createDepartment(departmentDto);
        });
    }

    @Test
    public void testCreateDepartment_Success() {
        when(deptRepo.findByDeptName(departmentDto.getDeptName())).thenReturn(null);
        when(deptRepo.save(any())).thenReturn(department);

        DepartmentOutDto result = departmentService.createDepartment(departmentDto);

        assertEquals(departmentOutDto, result);
    }

    @Test
    public void testGetAllDepts_Empty() {
        when(deptRepo.findAll()).thenReturn(new ArrayList<>());

//        List<DepartmentOutDto> result = departmentService.getAllDepts();

        assertThrows(ResourceNotFoundException.class, () -> departmentService.getAllDepts());
    }

    @Test
    public void testGetAllDepts_Success() {
        List<Department> departments = new ArrayList<>();
        departments.add(department);

        when(deptRepo.findAll()).thenReturn(departments);

        List<DepartmentOutDto> result = departmentService.getAllDepts();

        assertEquals(1, result.size());
    }

    @Test
    public void testGetAllDeptsPage_Empty() {
        Page<Department> departmentPage = new PageImpl<>(new ArrayList<>());

        when(deptRepo.findAll(any(PageRequest.class))).thenReturn(departmentPage);

        assertThrows(ResourceNotFoundException.class, () -> departmentService.getAllDeptsPage(0));
    }

    @Test
    public void testGetAllDeptsPage_Success() {
        List<Department> departments = new ArrayList<>();
        departments.add(department);

        Page<Department> departmentPage = new PageImpl<>(departments);

        when(deptRepo.findAll(any(PageRequest.class))).thenReturn(departmentPage);

        List<DepartmentOutDto> result = departmentService.getAllDeptsPage(0);

        assertEquals(1, result.size());
    }

    @Test
    public void testDeleteDept_NotFound() {
        when(deptRepo.findByDeptName(departmentDto.getDeptName())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            departmentService.deleteDept(departmentDto);
        });
    }

    @Test
    public void testDeleteDept_Success() {
        when(deptRepo.findByDeptName(departmentDto.getDeptName())).thenReturn(department);

        departmentService.deleteDept(departmentDto);

        verify(deptRepo, times(1)).delete(department);
    }
    
    @Test
    void testDeleteDeptNotFound() {
    	when(deptRepo.findByDeptName("HR")).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> departmentService.deleteDept(departmentDto));
    } 

}
