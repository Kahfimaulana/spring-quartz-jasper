package com.test.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kahfi on 2/13/2015.
 */
public class GenerateReport {

    /**
     * Use to print PDF file
     * @param fileType .pdf
     * @param jasperDesign .jrxml file
     * @param dataSource json / db data source
     * @param destination path and filename to save report
     */
    public static void printToFile(String fileType, JasperDesign jasperDesign, JRDataSource dataSource, String destination){
        JasperReport jasperReport = null;
        JasperPrint jasperPrint = null;
        try {
            jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map<String, Object> params = new HashMap<String, Object>();

            jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            if("pdf".equals(fileType)){
                JasperExportManager.exportReportToPdfFile(jasperPrint, destination + ".pdf");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
