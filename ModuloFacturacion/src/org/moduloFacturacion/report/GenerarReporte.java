
package org.moduloFacturacion.report;

import net.sf.jasperreports.engine.util.JRLoader;   // leer el reporte
import net.sf.jasperreports.engine.JasperReport;    // Obtener el archiivo seleccionado
import net.sf.jasperreports.engine.JasperPrint;     // Ver el archivo impreso
import net.sf.jasperreports.engine.JasperPrintManager; // Controlador
import net.sf.jasperreports.engine.JasperFillManager; // Saber que archivo quiero imprimir
import net.sf.jasperreports.view.JasperViewer; // Ver el archivo
import java.util.Map;
import java.io.InputStream;
import org.moduloFacturacion.db.Conexion;

public class GenerarReporte {
    public static void mostrarReporte(String nombreReporte, String titulo, Map parametros){
        InputStream reporte = GenerarReporte.class.getResourceAsStream(nombreReporte);
        try{
            JasperReport reporteMaestro = (JasperReport)JRLoader.loadObject(reporte);
            JasperPrint reporteImpreso = JasperFillManager.fillReport(reporteMaestro, parametros,Conexion.getIntance().getConexion());
            JasperViewer visor = new JasperViewer(reporteImpreso, false);
            visor.setTitle(titulo);
            visor.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
