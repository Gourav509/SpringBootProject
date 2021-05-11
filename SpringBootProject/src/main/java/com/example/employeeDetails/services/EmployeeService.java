package com.example.employeeDetails.services;

import com.example.employeeDetails.model.Employee;
import com.example.employeeDetails.resource.AuditDto;
import com.example.employeeDetails.resource.EmployeeResource;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    public void demoAop();
    List<EmployeeResource> getEmployees();

    EmployeeResource getEmployeeId(long employeeId);

    EmployeeResource addEmployee(EmployeeResource employeeDto);

    EmployeeResource updateEmployee(EmployeeResource employeeResource, long employeeId) throws Exception;

    Page<Employee> getAllPaginated(int pageNumber, int pageSize, String sortBy, String sortDirection);

    String deleteEmployee(long parseLong);

    List<Employee> findBySalary();

    List<EmployeeResource> findByFirstName(String firstName);

    List<Employee> findAll();

    AuditDto<Employee> getAudit(long employeeId);

    Page<EmployeeResource> findPaginated(int pageNo, int pageSize, String sortBy);

    EmployeeResource getPageEmployee(long employeeId);
}
