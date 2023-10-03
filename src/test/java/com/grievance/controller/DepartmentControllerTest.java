package com.grievance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.grievance.indto.DepartmentDto;
import com.grievance.outdto.DepartmentOutDto;
import com.grievance.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import java.util.List;


@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class DepartmentControllerTest {
	
	@InjectMocks
    private DepartmentController departmentController;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private DepartmentService departmentService;

    private DepartmentDto departmentDto;
    private DepartmentOutDto departmentOutDto;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();

        departmentDto = new DepartmentDto();
        departmentDto.setDeptName("HR");

        departmentOutDto = new DepartmentOutDto();
        departmentOutDto.setDeptName("HR");
        departmentOutDto.setDeptId(1); 
    }

    @Test
    public void testGetAllDepts() throws Exception {
        List<DepartmentOutDto> departments = Arrays.asList(departmentOutDto);
        when(departmentService.getAllDepts()).thenReturn(departments);

        MvcResult mvcResult = mockMvc.perform(get("/api/department/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(202, status);
    }

    @Test
    public void testDeleteDepartment() throws Exception {
        doNothing().when(departmentService).deleteDept((departmentDto));

        ObjectMapper objectMapper = new ObjectMapper();
        String departmentDtoJson = objectMapper.writeValueAsString(departmentDto);

        MvcResult mvcResult = mockMvc.perform(delete("/api/department/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(departmentDtoJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void testCreateDepartment() throws Exception {
    	when(departmentService.createDepartment(any(DepartmentDto.class))).thenReturn(departmentOutDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String departmentDtoJson = objectMapper.writeValueAsString(departmentDto);
        	System.out.println(departmentOutDto);
        MvcResult mvcResult = mockMvc.perform(post("/api/department/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(departmentDtoJson))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }
    
    
    @Test
    public void testGetAllDepts_WithPagination() throws Exception {
        List<DepartmentOutDto> departments = Arrays.asList(departmentOutDto);
        
       when(departmentService.getAllDeptsPage(0)).thenReturn(departments);

        MvcResult mvcResult = mockMvc.perform(get("/api/department/getAll")
                .param("pageNo", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(202, status);
    }
}

