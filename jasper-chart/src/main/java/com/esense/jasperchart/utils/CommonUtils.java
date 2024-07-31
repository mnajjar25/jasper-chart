package com.esense.jasperchart.utils;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

public class CommonUtils {

    public static void generateJasperPdf(HttpServletResponse response, Map params, Connection conn, String fileName) throws JRException, IOException {
        InputStream jasperFile = CommonUtils.class.getResourceAsStream("/" + fileName);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperFile);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
        response.setContentType("application/pdf");
        try (ServletOutputStream outStream = response.getOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
            outStream.flush();
        }
    }//generateJasperPdf
}
