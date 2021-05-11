package com.example.employeeDetails.controller;

import com.example.employeeDetails.model.Department;
import com.example.employeeDetails.resource.DepartmentConverter;
import com.example.employeeDetails.resource.DepartmentResource;
import com.example.employeeDetails.services.DepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/departments")
@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentConverter departmentConverter;

    @GetMapping("/")
    @ApiOperation(value = "Get all departments", notes = "Get details of all departments")
    public ResponseEntity<List<DepartmentResource>> getDepartments() {
        List<Department> departments = departmentService.getDepartments();
        return ResponseEntity.ok(departmentConverter.entityToDto(departments));
    }

    @GetMapping("/{departmentId}")
    @ApiOperation(value = "Get department", notes = "Get details of department using department Id")
    public ResponseEntity<Optional<DepartmentResource>> getOne(@PathVariable("departmentId") Long departmentId) {
        Optional<Department> departmentOptional = departmentService.getDepartmentId(departmentId);
        Department department = departmentOptional.get();
        return ResponseEntity.ok(Optional.ofNullable(departmentConverter.entityToDto(department)));
    }

    @PostMapping("/")
    @ApiOperation(value = "Add employee", notes = "Add record to the department")
    public ResponseEntity<DepartmentResource> addDepartment(@RequestBody DepartmentResource departmentDto) {

        Department departmentEntity = departmentConverter.dtoToEntity(departmentDto);
        Department department = departmentService.addDepartment(departmentEntity);
        return ResponseEntity.ok(departmentConverter.entityToDto(department));
    }

    @PutMapping("/{departmentId}")
    @ApiOperation(value = "Update department", notes = "Update the details of department")
    public ResponseEntity<DepartmentResource> updateDepartment(@RequestBody DepartmentResource departmentDto,
                                                               @PathVariable(value = "departmentId") long departmentId) throws Exception {
        Department departmentEntity = departmentConverter.dtoToEntity(departmentDto);
        Department department = departmentService.updateDepartment(departmentEntity, departmentId);
        return ResponseEntity.ok(departmentConverter.entityToDto(department));
    }

    @DeleteMapping("/{departmentId}")
    @ApiOperation(value = "Delete department", notes = "Delete employee record by using department Id")
    public void deleteDepartment(@PathVariable("departmentId") long departmentId) {
        departmentService.deleteDepartment(departmentId);
    }
}
