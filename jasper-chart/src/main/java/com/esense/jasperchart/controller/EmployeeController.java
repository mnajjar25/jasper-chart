package com.esense.jasperchart.controller;

import com.esense.jasperchart.dto.Employee;
import com.esense.jasperchart.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class EmployeeController {
    private EmployeeService employeeService;


    @PostMapping("/departments/{deptId}/employees")
    public ResponseEntity<Employee> addEmployee(@PathVariable Long deptId, @Valid @RequestBody Employee employee){
        return new ResponseEntity<>(employeeService.addEmployee(deptId,employee), HttpStatus.CREATED);
    }

    @GetMapping("/departments/{deptId}/employees")
    public ResponseEntity<List<Employee>> getEmployeesByDeptId(@PathVariable Long deptId){
        return ResponseEntity.ok(employeeService.getEmployeesByDeptId(deptId));
    }

    @GetMapping("/departments/{deptId}/employees/{empId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long deptId,@PathVariable Long empId){
        return ResponseEntity.ok(employeeService.getEmployeeById(deptId,empId));
    }

    @PutMapping("/departments/{deptId}/employees/{empId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long deptId,@PathVariable Long empId,@Valid @RequestBody Employee employee){
        return ResponseEntity.ok(employeeService.updateEmployee(deptId,empId,employee));
    }

    @DeleteMapping("/departments/{deptId}/employees/{empId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long deptId,@PathVariable Long empId){
        employeeService.deleteEmployee(deptId,empId);
        return new ResponseEntity<>("Employee deleted successfully.",HttpStatus.OK);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllTasks(){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/employees/{empId}")
    public ResponseEntity<Employee> getEmployeeByEmpId(@PathVariable Long empId){
        return ResponseEntity.ok(employeeService.getEmployeeByEmpId(empId));
    }
}
