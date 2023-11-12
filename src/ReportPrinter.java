
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportPrinter {
    public static void main(String[] args) {
        try {
            // Prepare data
            List<Map<String, Object>> dataList = new ArrayList<>();
            Map<String, Object> data1 = new HashMap<>();
            data1.put("field1", "Value1A");
            data1.put("field2", "Value1B");
            dataList.add(data1);

            Map<String, Object> data2 = new HashMap<>();
            data2.put("field1", "Value2A");
            data2.put("field2", "Value2B");
            dataList.add(data2);

            // Compile the JasperReport template
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    ReportPrinter.class.getResourceAsStream("/template.jrxml")
            );

            // Fill the report with data
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);

            // Export the report to PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, "TemplateReport.pdf");

            System.out.println("Report generated successfully.");

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
