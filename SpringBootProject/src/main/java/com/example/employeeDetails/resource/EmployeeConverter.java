package com.example.employeeDetails.resource;

import com.example.employeeDetails.model.Employee;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Component
public class EmployeeConverter {

    @Autowired
    private final DepartmentConverter departmentConverter;

    public EmployeeResource entityToDto(Employee employee) {
        return EmployeeResource.builder().employeeId(employee.getEmployeeId()).firstName(employee.getFirstName())
                .lastName(employee.getLastName()).email(employee.getEmail()).age(employee.getAge()).salary(
                        employee.getSalary()).gender(employee.getGender())
                .departments(departmentConverter.entityToDto(employee.getDepartments())).build();
    }

    public List<EmployeeResource> entityToDto(List<Employee> employees) {
        return employees.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
    }

    public Employee dtoToEntity(EmployeeResource employeeDto) {
        return Employee.builder().employeeId(employeeDto.getEmployeeId()).firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName()).email(employeeDto.getEmail()).age(employeeDto.getAge()).salary(
                        employeeDto.getSalary()).gender(employeeDto.getGender()).departments(departmentConverter
                        .dtoToEntity(employeeDto.getDepartments())).build();

    }

    public List<Employee> dtoToEntity(List<EmployeeResource> employees) {
        return employees.stream().map(x -> dtoToEntity(x)).collect(Collectors.toList());
    }
}
