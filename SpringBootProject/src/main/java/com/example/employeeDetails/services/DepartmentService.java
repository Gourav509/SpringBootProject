package com.example.employeeDetails.services;

import com.example.employeeDetails.model.Department;
import com.example.employeeDetails.model.Employee;
import com.example.employeeDetails.resource.AuditDto;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    List<Department> getDepartments();

    Optional<Department> getDepartmentId(long departmentId);

    List<Employee> getEmployeeByDepartment(long departmentId);

    Department addDepartment(Department department);

    Department updateDepartment(Department department, long departmentId) throws Exception;

    void deleteDepartment(long departmentId);

    AuditDto<Department> getAudit(long departmentId);

}
