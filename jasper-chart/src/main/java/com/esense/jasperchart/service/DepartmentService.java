package com.esense.jasperchart.service;

import com.esense.jasperchart.dto.Department;
import com.esense.jasperchart.entity.DepartmentEntity;
import com.esense.jasperchart.repository.DepartmentRepository;
import com.esense.jasperchart.utils.CommonUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DepartmentService {
    private DepartmentRepository departmentRepository;
    private ModelMapper mapper;
    private DataSource dataSource;


    public List<Department> getAllDepartments(){
        return departmentRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public String createDepartment(Department department){
        departmentRepository.save(mapToEntity(department));
        return "created Successfully";
    }

    public void exportDepartmentsPdf(HttpServletResponse response) throws JRException, IOException {
        Map<String, Object> params = new HashMap<>();
        try {
            CommonUtils.generateJasperPdf(response,params,dataSource.getConnection(), "static/reports/chart_report.jasper");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Department mapToDTO(DepartmentEntity departmentEntity){
        Department department = mapper.map(departmentEntity,Department.class);
        return department;
    }

    private DepartmentEntity mapToEntity(Department department){
        DepartmentEntity departmentEntity = mapper.map(department,DepartmentEntity.class);
        return departmentEntity;
    }

}
