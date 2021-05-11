package com.example.employeeDetails.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "department")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"employees"}, ignoreUnknown = true)
public class Department {

    @Id
    private long departmentId;
    @Column(name = "name")
    private String name;
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "departments")
    private List<Employee> employees;

}