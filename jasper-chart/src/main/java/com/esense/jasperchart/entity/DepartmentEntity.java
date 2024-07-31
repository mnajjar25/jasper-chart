package com.esense.jasperchart.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@DynamicUpdate
@Table(name = "departments")
public class DepartmentEntity {

    @Id
    @Column(name = "DEPT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    @Column(name = "DEPT_NAME",unique = true)
    private String departmentName;

    @Column(name = "DEPT_LOCATION",nullable = false)
    private String location;

    @OneToMany(mappedBy = "dept",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<EmployeeEntity> employees;
}
