package com.example.employeeDetails.services;

import com.example.employeeDetails.exception.ResourceNotFoundException;
import com.example.employeeDetails.model.Employee;
import com.example.employeeDetails.repository.EmployeeRepository;
import com.example.employeeDetails.resource.AuditDto;
import com.example.employeeDetails.resource.EmployeeConverter;
import com.example.employeeDetails.resource.EmployeeResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImplementation implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeConverter employeeConverter;

    @Override
    public List<EmployeeResource> getEmployees() {
        return employeeConverter.entityToDto(employeeRepository.findAll().stream().filter(Predicate.not(
                Employee::isDeleted)).collect(Collectors.toList()));
    }

    @Override
    @Cacheable(value="employeeCache",key="#employeeId",unless="#result==null")
    public EmployeeResource getEmployeeId(long employeeId) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
                new ResourceNotFoundException("Employee not found for ID: " +
                        employeeId));
        if (employee.isDeleted()) {
            return null;
        }
        return employeeConverter.entityToDto(employee);
    }
    
    @Override
    public Page<Employee> getAllPaginated(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        Page<Employee> pagedResult = employeeRepository.findAll(pageable);
        return pagedResult;
    }

    @Override
    public EmployeeResource addEmployee(EmployeeResource employeeDto) {
        employeeRepository.save(employeeConverter.dtoToEntity(employeeDto));
        return employeeDto;
    }

    @Override
    @CachePut(value="employeeCache",key="#employeeId")
    public EmployeeResource updateEmployee(EmployeeResource employeeResource, long employeeId) throws Exception {
        Employee employeeDetails = employeeRepository.findById(employeeId).orElseThrow(() ->
                new Exception("Employee not found for this id:: " + employeeId));
        employeeDetails.setEmployeeId(employeeResource.getEmployeeId());
        employeeDetails.setFirstName(employeeResource.getFirstName());
        employeeDetails.setLastName(employeeResource.getLastName());
        employeeDetails.setSalary(employeeResource.getSalary());
        employeeDetails.setEmail(employeeResource.getEmail());
        employeeDetails.setAge(employeeResource.getAge());
        employeeDetails.setDeleted(false);
        employeeDetails.setDepartments(employeeDetails.getDepartments());
        employeeRepository.save(employeeConverter.dtoToEntity(employeeResource));
        return employeeResource;
    }

    @Override
    @CacheEvict(value="employeeCache",key="#employeeId")
    public String deleteEmployee(long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        Employee updateEmployee = new Employee();
        updateEmployee.setEmployeeId(employee.get().getEmployeeId());
        updateEmployee.setFirstName(employee.get().getFirstName());
        updateEmployee.setLastName(employee.get().getLastName());
        updateEmployee.setSalary(employee.get().getSalary());
        updateEmployee.setEmail(employee.get().getEmail());
        updateEmployee.setAge(employee.get().getAge());
        updateEmployee.setGender(employee.get().getGender());
        updateEmployee.setDeleted(true);
        updateEmployee.setDepartments(employee.get().getDepartments());
        employeeRepository.save(updateEmployee);
        return "Success";
    }

    @Override
    public List<EmployeeResource> findByFirstName(String firstName) {
        return employeeConverter.entityToDto(employeeRepository.findByFirstName(firstName));
    }

    @Override
    public List<Employee> findBySalary() {

        return employeeRepository.findBySalary();
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public AuditDto<Employee> getAudit(long employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        AuditDto auditDto = new AuditDto();
        auditDto.setCreatedBy(employeeOptional.get().getCreatedBy());
        auditDto.setCreatedOn(employeeOptional.get().getCreatedOn());
        auditDto.setModifiedBy(employeeOptional.get().getModifiedBy());
        auditDto.setModifiedOn(employeeOptional.get().getModifiedOn());
        return auditDto;
    }

    @Override
    public Page<EmployeeResource> findPaginated(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy).ascending());
        return this.employeeRepository.getAllEmployees(pageable);
    }

    @Override
    public EmployeeResource getPageEmployee(long employeeId) {
        return this.employeeRepository.getPageEmployee(employeeId);
    }

    @Override
    public void demoAop() {
        System.out.println("DEMO AOP using before AOP");
    }
}
