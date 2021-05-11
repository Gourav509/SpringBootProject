package com.example.employeeDetails.services;

import com.example.employeeDetails.model.Department;
import com.example.employeeDetails.model.Employee;
import com.example.employeeDetails.repository.DepartmentRepository;
import com.example.employeeDetails.resource.AuditDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImplementation implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<Department> getDepartments() {
        List<Department> getAllDepartments = departmentRepository.findAll();
        return getAllDepartments.stream().filter(Predicate.not(Department::isDeleted)).collect(Collectors.toList());
    }

    @Override
    public List<Employee> getEmployeeByDepartment(long departmentId) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        List<Employee> employees = department.get().getEmployees();
        return employees;
    }

    @Override
    public Optional<Department> getDepartmentId(long departmentId) {
        if (!departmentRepository.getOne(departmentId).isDeleted())
            return departmentRepository.findById(departmentId);
        else
            return Optional.empty();
    }

    @Override
    public Department addDepartment(Department department) {
        departmentRepository.save(department);
        return department;
    }

    @Override
    public Department updateDepartment(Department department, long departmentId) throws Exception {
        Department departmentDetails = departmentRepository.findById(departmentId).orElseThrow(() ->
                new Exception("Department not found for this id:: " + departmentId));
        departmentDetails.setDepartmentId(department.getDepartmentId());
        departmentDetails.setName(department.getName());
        departmentDetails.setDeleted(department.isDeleted());
        departmentRepository.save(departmentDetails);
        return departmentDetails;
    }

    @Override
    public void deleteDepartment(long departmentId) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        Department updateDepartment = new Department();
        updateDepartment.setDepartmentId(department.get().getDepartmentId());
        updateDepartment.setName(department.get().getName());
        updateDepartment.setEmployees(department.get().getEmployees());
        updateDepartment.setDeleted(true);
        departmentRepository.save(updateDepartment);
    }

    @Override
    public AuditDto<Department> getAudit(long departmentId) {
        Optional<Department> departmentOptional = departmentRepository.findById(departmentId);
        AuditDto auditDto = new AuditDto();
        auditDto.setCreatedBy(departmentOptional.get().getCreatedBy());
        auditDto.setCreatedOn(departmentOptional.get().getCreatedOn());
        auditDto.setModifiedBy(departmentOptional.get().getModifiedBy());
        auditDto.setModifiedOn(departmentOptional.get().getModifiedOn());
        return auditDto;
    }
}
