package com.example.employeeDetails.repository;

import com.example.employeeDetails.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
