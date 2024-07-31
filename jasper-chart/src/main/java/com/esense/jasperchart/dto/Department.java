package com.esense.jasperchart.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Department {

    private Long departmentId;

    @Size(max = 30, message = "Department name must be at most 30 characters long")
    private String departmentName;

    @NotBlank(message = "location is required")
    private String location;

    private List<Employee> employees;
}
