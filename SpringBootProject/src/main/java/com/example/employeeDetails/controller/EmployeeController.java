package com.example.employeeDetails.controller;

import com.example.employeeDetails.exception.ResourceNotFoundException;
import com.example.employeeDetails.model.Employee;
import com.example.employeeDetails.repository.EmployeeRepository;
import com.example.employeeDetails.resource.AuditDto;
import com.example.employeeDetails.resource.EmployeeConverter;
import com.example.employeeDetails.resource.EmployeeResource;
import com.example.employeeDetails.services.EmployeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeConverter employeeConverter;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/")
    @ApiOperation(value = "Get all employees", notes = "Get details of all employees")
    public ResponseEntity<List<EmployeeResource>> getEmployees() {

        List<EmployeeResource> employees = employeeService.getEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{employeeId}")
    @ApiOperation(value = "Get employee", notes = "Get details of employee using employee Id")
    public ResponseEntity<EmployeeResource> getOne(@PathVariable("employeeId") Long employeeId) throws ResourceNotFoundException {

        EmployeeResource employee = employeeService.getEmployeeId(employeeId);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/")
    @ApiOperation(value = "Add employee", notes = "Add details of an employee")
    public ResponseEntity<EmployeeResource> addEmployee(@RequestBody EmployeeResource employeeResource) {

        EmployeeResource employeeResource1 = employeeService.addEmployee(employeeResource);
        return ResponseEntity.ok(employeeResource1);
    }

    @PutMapping("/{employeeId}")
    @ApiOperation(value = "Update employee", notes = "Update the details of an employee")
    public ResponseEntity<EmployeeResource> updateEmployee(@RequestBody EmployeeResource employeeResource,
                                                           @PathVariable(value = "employeeId") long employeeId)
            throws Exception {
        EmployeeResource employeeResource1 = employeeService.updateEmployee(employeeResource, employeeId);
        return ResponseEntity.ok(employeeResource1);
    }

    @DeleteMapping("/{employeeId}")
    @ApiOperation(value = "Delete employees", notes = "Delete employee record by using employee Id")
    public ResponseEntity<String> deleteEmployee(@PathVariable("employeeId") long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Success");
    }


    @GetMapping("/audit/{employeeId}")
    public ResponseEntity<AuditDto<Employee>> getAudit(@PathVariable("employeeId") long employeeId) {
        return ResponseEntity.ok(employeeService.getAudit(employeeId));
    }


    @GetMapping("/getFirstName")
    ResponseEntity<List<EmployeeResource>> findByFirstName(@RequestParam String firstName) {
        List<EmployeeResource> employeeResources = employeeService.findByFirstName(firstName);
        return ResponseEntity.ok(employeeResources);
    }

    @GetMapping("/salary")
    ResponseEntity<List<EmployeeResource>> findBySalary() {
        return ResponseEntity.ok(employeeConverter.entityToDto(employeeService.findBySalary()));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<EmployeeResource>> getAllEmployee(@RequestParam(defaultValue = "1") int pageNo,
                                                                 @RequestParam(defaultValue = "3") int pageSize,
                                                                 @RequestParam(defaultValue = "firstName") String sortBy) {

        Page<EmployeeResource> paginated = employeeService.findPaginated(pageNo, pageSize, sortBy);
        return ResponseEntity.ok(paginated);
    }

    @GetMapping("/getPage/{employeeId}")
    public ResponseEntity<EmployeeResource> getPage(@PathVariable(value = "employeeId") long employeeId) {

        EmployeeResource employeeResource = employeeService.getPageEmployee(employeeId);
        return ResponseEntity.ok(employeeResource);
    }


}