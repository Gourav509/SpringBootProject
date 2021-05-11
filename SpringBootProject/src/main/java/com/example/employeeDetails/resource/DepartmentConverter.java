package com.example.employeeDetails.resource;

import com.example.employeeDetails.model.Department;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Component
public class DepartmentConverter {

    public DepartmentResource entityToDto(Department department) {
        return DepartmentResource.builder().departmentId(department.getDepartmentId())
                .name(department.getName())
                .build();
    }

    public List<DepartmentResource> entityToDto(List<Department> department) {
        return department.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
    }

    public Department dtoToEntity(DepartmentResource departmentDto) {
        return Department.builder().departmentId(departmentDto.getDepartmentId())
                .name(departmentDto.getName())
                .build();
    }

    public List<Department> dtoToEntity(List<DepartmentResource> departmentDto) {
        return departmentDto.stream().map(x -> dtoToEntity(x)).collect(Collectors.toList());
    }

}
