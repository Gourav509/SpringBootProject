package com.example.employeeDetails.serviceLayerTesting;

import com.example.employeeDetails.exception.ResourceNotFoundException;
import com.example.employeeDetails.model.Employee;
import com.example.employeeDetails.repository.EmployeeRepository;
import com.example.employeeDetails.resource.DepartmentConverter;
import com.example.employeeDetails.resource.DepartmentResource;
import com.example.employeeDetails.resource.EmployeeConverter;
import com.example.employeeDetails.resource.EmployeeResource;
import com.example.employeeDetails.services.EmployeeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeConverter employeeConverter;

    @MockBean
    private DepartmentConverter departmentConverter;

    @Test
    public void testGetMockEmployees() {
        Employee employee = new Employee(1l, "am", "Ry", 2500, 26, "Male", "am.ry@gmail.com", false);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        when(employeeRepository.findAll()).thenReturn(employeeList);
        Assert.assertEquals(1, employeeService.getEmployees().size());
    }

    @Test
    public void testGetMockEmployeeById() throws ResourceNotFoundException {
        EmployeeResource employeeResource = new EmployeeResource(1l, "Sha", "Ry", 2500, 26, "Male", "am.ry@gmail.com");
        Employee employee = employeeConverter.dtoToEntity(employeeResource);
        Mockito.when(employeeRepository.findById(1l)).thenReturn(Optional.of(employee));
        EmployeeResource result = employeeService.getEmployeeId(1l);
        Assert.assertEquals("Sha", result.getFirstName());
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        List<DepartmentResource> departments = new ArrayList<>();
        EmployeeResource employeeResource = new EmployeeResource(1l, "am", "Ry", 2500, 26, "Male", "am.ry@gmail.com");
        Employee employee = employeeConverter.dtoToEntity(employeeResource);
        when(employeeRepository.findById(1l)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);
        Optional<EmployeeResource> employeeResult = Optional.ofNullable(employeeService.updateEmployee(employeeResource, 1l));
        Assert.assertEquals(employeeResource, employeeResult.get());
    }

    @Test
    public void testPostEmployee() {
        List<DepartmentResource> departmentResourceList = new ArrayList<>();
        EmployeeResource employeeResource = new EmployeeResource(1l, "am", "Ry", 2500, 26, "Male", "am.ry@gmail.com");
        Employee employee = employeeConverter.dtoToEntity(employeeResource);
        Mockito.when(employeeRepository.save(Mockito.any())).thenReturn(employee);
        EmployeeResource result = employeeService.addEmployee(employeeResource);
        assertEquals(employeeResource, result);
    }

    @Test
    public void testDeleteEmployee() throws ResourceNotFoundException {
        EmployeeResource employeeResource = new EmployeeResource(1l, "am", "Ry", 2500, 26, "Male", "am.ry@gmail.com");
        Employee employee = new Employee(1l, "am", "Ry", 2500, 26, "Male", "am.ry@gmail.com", false);
        when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employee));
        assertEquals("Success", employeeService.deleteEmployee(1l));
    }
}