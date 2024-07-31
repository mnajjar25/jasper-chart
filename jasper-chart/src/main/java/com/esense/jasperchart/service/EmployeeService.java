package com.esense.jasperchart.service;

import com.esense.jasperchart.dto.Employee;
import com.esense.jasperchart.entity.DepartmentEntity;
import com.esense.jasperchart.entity.EmployeeEntity;
import com.esense.jasperchart.exception.EmployeeAPIException;
import com.esense.jasperchart.exception.ResourceNotFoundException;
import com.esense.jasperchart.repository.DepartmentRepository;
import com.esense.jasperchart.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;
    private ModelMapper mapper;


    public Employee addEmployee(Long deptId,Employee employee){

        EmployeeEntity employeeEntity = mapToEntity(employee);
        DepartmentEntity departmentEntity = departmentRepository.findById(deptId).orElseThrow(
                () -> new ResourceNotFoundException("Department","id",deptId)
        );
        employeeEntity.setDept(departmentEntity);
        EmployeeEntity newEmployee = employeeRepository.save(employeeEntity);
        return mapToDTO(newEmployee);
    }

    public List<Employee> getEmployeesByDeptId(Long deptId){
        List<EmployeeEntity> employees = employeeRepository.findByDept_DepartmentId(deptId);
        return employees.stream().map(this::mapToDTO).toList();
    }

    public Employee getEmployeeById(Long deptId,Long empId){
        EmployeeEntity employee = getEmployeeIfValid(deptId,empId);
        return mapToDTO(employee);
    }

    public Employee updateEmployee(Long deptId,Long empId,Employee employee){
        EmployeeEntity employeeEntity = getEmployeeIfValid(deptId,empId);
        employeeEntity.setFirstName(employee.getFirstName());
        employeeEntity.setLastName(employee.getLastName());
        employeeEntity.setEmail(employee.getEmail());
        EmployeeEntity updatedEmployee = employeeRepository.save(employeeEntity);
        return mapToDTO(updatedEmployee);
    }

    public void deleteEmployee(Long deptId,Long empId){
        EmployeeEntity employee = getEmployeeIfValid(deptId,empId);
        employeeRepository.delete(employee);
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public Employee getEmployeeByEmpId(Long empId) {
        EmployeeEntity employee = employeeRepository.findById(empId).orElseThrow(
                () -> new ResourceNotFoundException("Employee","id",empId));

        return mapToDTO(employee);
    }

    private EmployeeEntity getEmployeeIfValid(Long deptId,Long empId){
        DepartmentEntity department = departmentRepository.findById(deptId).
                orElseThrow(() -> new ResourceNotFoundException("Department","id",deptId));

        EmployeeEntity employee = employeeRepository.findById(empId).
                orElseThrow(() -> new ResourceNotFoundException("Employee","id",empId));

        if(!employee.getDept().getDepartmentId().equals(department.getDepartmentId())){
            throw new EmployeeAPIException(HttpStatus.BAD_REQUEST,"Employee does not belong to Department");
        }
        return employee;
    }


    private Employee mapToDTO(EmployeeEntity employeeEntity){
        Employee employee = mapper.map(employeeEntity,Employee.class);
        return employee;
    }

    private EmployeeEntity mapToEntity(Employee employee){
        EmployeeEntity employeeEntity = mapper.map(employee,EmployeeEntity.class);
        return employeeEntity;
    }
}
