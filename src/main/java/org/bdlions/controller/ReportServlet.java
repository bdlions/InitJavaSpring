/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bdlions.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.bdlions.dto.User;
import org.bdlions.manager.UserManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author alamgir
 */
@CrossOrigin
@RestController
public class ReportServlet {

    @RequestMapping("/report")
    public void getReport(HttpServletResponse response) {

        response.setContentType("application/pdf");

        String sourceFileName = getClass().getClassLoader().getResource("reports/customers.jasper").getFile();
        UserManager userManager = new UserManager();
        User user = userManager.getUserByIdentity("user1@gmail.com");
        List<User> dataList = new ArrayList<>();
        dataList.add(user);

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);

        Map parameters = new HashMap();
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(sourceFileName, parameters, beanColDataSource);
            OutputStream os = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, os);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
