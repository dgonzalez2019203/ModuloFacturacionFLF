
package org.moduloFacturacion.bean;

import java.awt.Font;
import java.awt.print.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.time.LocalDate;
import javafx.collections.ObservableList;

public class ImprimirOrdenDeCompra implements Printable{
    PrinterJob printerJob;
    ObservableList<FacturacionDetalleBackup> mensaje;   
    String Nit, nombreCliente, direccionCliente, totalLetras,totalFactura;
    LocalDate fecha;
    public ImprimirOrdenDeCompra(){
        super();
        printerJob=PrinterJob.getPrinterJob();
        printerJob.setPrintable(this);       
    }
    
    public void imprima(ObservableList<FacturacionDetalleBackup> mensaje, String Nit, String nombreCliente, String direccionCliente,LocalDate fecha,String totalLetras, String totalFactura){
    this.mensaje=mensaje;
    this.Nit = Nit;
    this.nombreCliente = nombreCliente;
    this.direccionCliente = direccionCliente;
    this.fecha = fecha;
    this.totalLetras = totalLetras;
    this.totalFactura = totalFactura;
     if(printerJob.printDialog()){
        try{
            printerJob.print();
        }catch(Exception PrinterException){
            PrinterException.printStackTrace();
        }        
        } 
    }
    public void imprimir(Graphics2D g2d,PageFormat pf,int pagina){   
        Font font = new Font(null, Font.PLAIN, 0);    
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(-270),0, 0);
        Font rotatedFont = font.deriveFont(affineTransform);
        g2d.setFont(rotatedFont);
        g2d.drawString(String.valueOf(fecha.getDayOfMonth()), 320,28);
        g2d.drawString(String.valueOf(fecha.getMonthValue()),320,68);
        g2d.drawString(String.valueOf(fecha.getYear()),320,110);
        g2d.drawString(nombreCliente, 297, 68);
        g2d.drawString(direccionCliente,279, 68);
//        g2d.drawString(Nit,362 , 215);
            int ancho =242;
            int largo = 28;
            int anchoDesc = 68;
            int anchoValor = 237;
          for(int x=0; x< mensaje.size();x++){
              g2d.drawString(String.valueOf(mensaje.get(x).getCantidadBackup()),ancho, largo);
              g2d.drawString(mensaje.get(x).getProductoDesc()+"  "+String.valueOf(mensaje.get(x).getProductoPrecio()), ancho , anchoDesc);
              g2d.drawString(String.valueOf(mensaje.get(x).getTotalParcialBackup()), ancho, anchoValor);
              
              ancho = ancho-18;
          }
         g2d.drawString(totalFactura, 40, 240);
    }
    public int print(Graphics g,PageFormat pf,int pagina){
      Graphics2D g2d=(Graphics2D)g;
      g2d.translate(pf.getImageableX(),pf.getImageableY());     
            switch(pagina){
                case 0:
                imprimir(g2d,pf,pagina);
                return (PAGE_EXISTS);            
                default:
                return (NO_SUCH_PAGE);
            }   
    }          
}
