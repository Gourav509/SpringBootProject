package com.example.employeeDetails.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"employees"}, ignoreUnknown = true)
public class DepartmentResource {

    private long departmentId;
    private String name;
    private List<EmployeeResource> employees;

    public DepartmentResource(long departmentId, String name) {
        this.departmentId = departmentId;
        this.name = name;
    }
}
