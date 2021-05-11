package com.example.employeeDetails.resource;

import com.example.employeeDetails.model.Department;
import com.example.employeeDetails.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResource {

    private long employeeId;
    private String firstName;
    private String lastName;
    private double salary;
    private int age;
    private String gender;
    private String email;
    private List<DepartmentResource> departments;

    public EmployeeResource(long employeeId, String firstName, String lastName, double salary, int age, String gender,
                            String email) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.age = age;
        this.gender = gender;
        this.email = email;
    }

    public EmployeeResource(long employeeId, Employee employee) {
        this.employeeId = employeeId;
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.salary = employee.getSalary();
        this.age = employee.getAge();
        this.gender = employee.getGender();
        this.email = employee.getEmail();
        this.departments = entityToDto(employee.getDepartments());
    }


    public EmployeeResource(long employeeId, String firstName) {
        this.employeeId = employeeId;
        this.firstName = firstName;
    }

    public EmployeeResource(List<DepartmentResource> departments) {
        this.departments = departments;
    }

    public DepartmentResource entityToDto(Department department) {
        return DepartmentResource.builder().departmentId(department.getDepartmentId())
                .name(department.getName())
                .build();
    }

    public List<DepartmentResource> entityToDto(List<Department> department) {
        return department.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
    }
}