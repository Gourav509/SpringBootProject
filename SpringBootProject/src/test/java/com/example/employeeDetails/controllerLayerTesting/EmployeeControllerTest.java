package com.example.employeeDetails.controllerLayerTesting;

import com.example.employeeDetails.controller.EmployeeController;
import com.example.employeeDetails.model.Employee;
import com.example.employeeDetails.repository.EmployeeRepository;
import com.example.employeeDetails.resource.DepartmentConverter;
import com.example.employeeDetails.resource.DepartmentResource;
import com.example.employeeDetails.resource.EmployeeConverter;
import com.example.employeeDetails.resource.EmployeeResource;
import com.example.employeeDetails.services.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    UserDetailsService userDetailsService;
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private EmployeeConverter employeeConverter;
    @MockBean
    private DepartmentConverter departmentConverter;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "spring")
    public void testGetAllEmployees() throws Exception {

        List<EmployeeResource> employeeResourceList = new ArrayList<>();
        employeeResourceList.add(new EmployeeResource(1l, "am", "Ry", 2500, 26,
                "Male", "am.ry@gmail.com"));
        employeeResourceList.add(new EmployeeResource(1l, "am", "Ry", 2500, 26,
                "Male", "am.ry@gmail.com"));
        Mockito.when(employeeService.getEmployees()).thenReturn(employeeResourceList);
        String URI = "/api/employees/";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String expected = mapToJson(employeeResourceList);
        String actual = response.getContentAsString();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "spring")
    public void testGetEmployeeById() throws Exception {

        EmployeeResource employeeResource = new EmployeeResource(1l, "am", "Ry", 2500,
                26, "Male", "am.ry@gmail.com");
        Employee employee = new Employee(1l, "am", "Ry", 2500, 26, "Male",
                "am.ry@gmail.com", false);
        Mockito.when(employeeService.getEmployeeId(Mockito.anyLong())).thenReturn(employeeResource);
        String URI = "/api/employees/2";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String expected = mapToJson(employeeResource);
        String actual = response.getContentAsString();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "spring")
    public void testCreateEmployee() throws Exception {

        EmployeeResource employeeResource = new EmployeeResource(1l, "am", "Ry", 2500,
                26, "Male", "am.ry@gmail.com");
        Employee employee = new Employee(1l, "am", "Ry", 2500, 26, "Male",
                "am.ry@gmail.com", false);
        Mockito.when(employeeService.addEmployee(Mockito.any(EmployeeResource.class))).thenReturn(employeeResource);
        String URI = "/api/employees/";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(employeeResource)).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String expected = mapToJson(employeeResource);
        String actual = response.getContentAsString();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "spring")
    public void testUpdateEmployee() throws Exception {
        EmployeeResource employeeResource = new EmployeeResource(1l, "am", "Ry", 2500,
                26, "Male", "am.ry@gmail.com");
        Employee employee = new Employee(1l, "am", "Ry", 2500, 26, "Male",
                "am.ry@gmail.com", false);
        String URI = "/api/employees/2";
        Mockito.when(employeeService.updateEmployee(Mockito.any(EmployeeResource.class), Mockito.anyLong()))
                .thenReturn(employeeResource);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(employeeResource)).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String expected = mapToJson(employeeResource);
        String actual = response.getContentAsString();
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "spring")
    public void deleteEmployee() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/employees/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "spring")
    public void testGetPaginatedEmployees() throws Exception {

        List<DepartmentResource> departmentResourceList = new ArrayList<>();
        List<Employee> employeeResourceList = new ArrayList<>();
        Employee employee = new Employee(1l, "am", "Ry", 2500,
                26, "Male", "am.ry@gmail.com",false);
        employeeResourceList.add(employee);
        Page<Employee> page = new PageImpl<>(employeeResourceList);
        Mockito.when(employeeService.getAllPaginated(1,1,"firstName","ASC")).thenReturn(page);
        String URI = "/api/employees?pageNo=1&pageSize=1&sortBy=firstName";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        String expected = mapToJson(page);
        String actual = result.getResponse().getContentAsString();
        System.out.println("Expected:- " + expected);
        System.out.println("Actual:- " + actual);
        assertEquals(expected, actual);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    // Maps an Object into Json String
    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}