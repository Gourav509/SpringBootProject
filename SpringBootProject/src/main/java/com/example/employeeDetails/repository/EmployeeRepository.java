package com.example.employeeDetails.repository;

import com.example.employeeDetails.model.Employee;
import com.example.employeeDetails.resource.EmployeeResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "Select * from Employee employee where isdeleted = false order by employee.employee_id", nativeQuery = true)
    List<Employee> findAll();

    @Query(value = "select * from Employee where firstname= :firstName and isdeleted = true order by salary desc " +
            "LIMIT 4", nativeQuery = true)
    List<Employee> findByFirstName(@Param("firstName") String firstname);

    @Query(value = "select e.salary from Employee e order by salary desc LIMIT 4", nativeQuery = true)
    List<Employee> findBySalary();

    @Query("SELECT new com.example.employeeDetails.resource.EmployeeResource(a.employeeId,a.firstName,a.lastName," +
            "a.salary,a.age,a.gender,a.email) FROM Employee a WHERE a.isDeleted=false")
    Page<EmployeeResource> getAllEmployees(Pageable pageable);

    @Query(value = "SELECT new com.example.employeeDetails.resource.EmployeeResource(a.employeeId,a) " +
            "FROM Employee a LEFT OUTER JOIN Department d on a.employeeId=d.departmentId WHERE a.employeeId=?1")
    EmployeeResource getPageEmployee(long employeeId);
}
