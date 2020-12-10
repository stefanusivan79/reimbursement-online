package com.example.reimbursementonlinebackend.util;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
@PropertySource("classpath:data.properties")
public class PDFUtil {

    @Value("${file.path}")
    private String filePath;

    @Value("${template.jasper.path}")
    private String templateJasperPath;

    public String generatePdfFileJasper(HashMap<String, Object> params, List<Object> detail, String fileName,
                                        String templateName) {

        String dest = filePath + fileName;
        String template = templateJasperPath + templateName;

        try {
            if (params == null) {
                params = new HashMap<>();
            }

            JRDataSource ds;
            if (null == detail) {
                ds = new JREmptyDataSource();
            } else {
                ds = new JRBeanCollectionDataSource(detail);
                params.put("CollectionBeanParam", ds);
            }

            JasperPrint jp = JasperFillManager.fillReport(template, params, ds);
            JasperExportManager.exportReportToPdfFile(jp, dest);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return dest;
    }
}
