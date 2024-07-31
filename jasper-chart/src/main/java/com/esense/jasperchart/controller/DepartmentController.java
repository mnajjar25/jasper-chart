package com.esense.jasperchart.controller;

import com.esense.jasperchart.dto.Department;
import com.esense.jasperchart.service.DepartmentService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/dept")
@AllArgsConstructor
public class DepartmentController {

    private DepartmentService departmentService;
    private HttpServletResponse response;

    @GetMapping("/getDepartments")
    public ResponseEntity<List<Department>> getDepartments(){
        return new ResponseEntity<>(departmentService.getAllDepartments(), HttpStatus.OK);
    }
    @PostMapping("/createDepartment")
    public ResponseEntity<String> createDepartment(@Valid @RequestBody Department department){
        return new ResponseEntity<>(departmentService.createDepartment(department), HttpStatus.CREATED);
    }
    @GetMapping("/exportDepartmentsPdf")
    public void exportDepartmentsPdf() throws JRException, IOException {
        departmentService.exportDepartmentsPdf(response);
    }

}
