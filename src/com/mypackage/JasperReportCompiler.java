

import net.sf.jasperreports.engine.JasperCompileManager;

public class JasperReportCompiler {

    public static void main(String[] args) {
        try {
            // Compile the JRXML file
            JasperCompileManager.compileReportToFile("template.jrxml", "ItemReport.jasper");

            System.out.println("Report compiled successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

