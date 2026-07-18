package ATMTuto;

import java.awt.Component;
import java.io.File;
import java.sql.Connection;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Loads iReport-wizard style .jrxml reports (same pattern as javaAssignment).
 */
public final class JasperReportUtil {

    private JasperReportUtil() {
    }

    /**
     * Compile .jrxml from disk, fill with DB connection, show JasperViewer.
     * Example path: "src/main/resources/reports/AccountsReport.jrxml"
     */
    public static void showReport(Component parent, String jrxmlFilePath) {
        Connection con = null;
        try {
            String resolved = resolveJrxmlPath(jrxmlFilePath);
            if (resolved == null) {
                JOptionPane.showMessageDialog(parent, "Report file not found:\n" + jrxmlFilePath);
                return;
            }

            con = DBConnection.getConnection();
            if (con == null) {
                return;
            }

            JasperReport report = JasperCompileManager.compileReport(resolved);
            JasperPrint print = JasperFillManager.fillReport(report, null, con);
            JasperViewer.viewReport(print, false);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent, e.getMessage());
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    /** Backwards-compatible overload used by older buttons. */
    public static void showReport(Component parent, String jrxmlResource, String title, String adminName) {
        // Prefer wizard-style file paths under resources/reports
        String fileName = jrxmlResource;
        int slash = jrxmlResource.lastIndexOf('/');
        if (slash >= 0) {
            fileName = jrxmlResource.substring(slash + 1);
        }
        // Map old Admin* names to new wizard names
        if (fileName.startsWith("Admin")) {
            fileName = fileName.replace("Admin", "");
        }
        showReport(parent, "src/main/resources/reports/" + fileName);
    }

    public static void exportPdf(Component parent, String jrxmlResource, String title, String adminName) {
        // Keep simple: open viewer (user can save/print from JasperViewer)
        showReport(parent, jrxmlResource, title, adminName);
    }

    private static String resolveJrxmlPath(String jrxmlFilePath) {
        File direct = new File(jrxmlFilePath);
        if (direct.isFile()) {
            return direct.getAbsolutePath();
        }

        // NetBeans may run with ATM as cwd or parent as cwd
        String[] candidates = new String[]{
            jrxmlFilePath,
            "ATM/" + jrxmlFilePath,
            "src/main/resources/reports/" + new File(jrxmlFilePath).getName(),
            "ATM/src/main/resources/reports/" + new File(jrxmlFilePath).getName(),
            "target/classes/reports/" + new File(jrxmlFilePath).getName(),
            "ATM/target/classes/reports/" + new File(jrxmlFilePath).getName()
        };
        for (String path : candidates) {
            File f = new File(path);
            if (f.isFile()) {
                return f.getAbsolutePath();
            }
        }
        return null;
    }
}
