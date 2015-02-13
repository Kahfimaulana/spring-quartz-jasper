package com.test.jasper;

import com.test.util.GenerateReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.web.servlets.ReportServlet;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by Kahfi on 2/13/2015.
 * This class is a Job class that used by quartz
 * to generate a PDF File with Jasper Report
 *
 */
public class GenerateFromJsonJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            /*Get Json File
            * This example for generate Jasper Report from jsonDataSource
            * So I create a example json in some file.
            * You can get some json from web services , etc ..
            * */

            /*turn json into JsonDataSource*/
            JsonDataSource ds = new JsonDataSource(new FileInputStream("d:\\file.json"));

            /*Get jrxml file*/
            InputStream input = ReportServlet.class.getResourceAsStream("/report/spring-quartz.jrxml");
            JasperDesign jd  = JRXmlLoader.load(input);

            /*Set Destination Path and Filename*/
            String destination = "D:\\test";

            /*Print file into Pdf*/
            GenerateReport.printToFile("pdf", jd, ds, destination);

            System.out.println("Report has been generated");

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}