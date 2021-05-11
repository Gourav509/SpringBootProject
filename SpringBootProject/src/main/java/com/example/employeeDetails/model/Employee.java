package com.example.employeeDetails.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "employee")
public class Employee {
    @Id
    private long employeeId;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "salary")
    private double salary;
    @Column(name = "age")
    private int age;
    @Column(name = "gender")
    private String gender;
    @Column(name = "email")
    private String email;
    @Column(name = "isdeleted")
    private boolean isDeleted;

    @CreatedBy
    @Column(updatable = false, nullable = false)
    private String createdBy;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdOn;
    @LastModifiedBy
    @Column(nullable = false)
    private String modifiedBy;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedOn;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "employee_department",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private List<Department> departments;

    public Employee(long employeeId, String firstName, String lastName, double salary, int age, String gender, String email, boolean isDeleted) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.isDeleted = isDeleted;
    }

    public Employee(long employeeId, String firstName, String lastName, double salary, int age, String gender, String email, boolean isDeleted, List<Department> departments) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.isDeleted = isDeleted;
        this.departments = departments;
    }

    public Employee(double salary) {
        this.salary = salary;
    }
}
