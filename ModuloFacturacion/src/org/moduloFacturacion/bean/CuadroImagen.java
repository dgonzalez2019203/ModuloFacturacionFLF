package org.moduloFacturacion.bean;



import java.io.InputStream;
import javafx.scene.image.Image;
import javax.swing.JPanel;

public  class CuadroImagen extends JPanel {

    private float zoom;
    public static double ancho;
    public static double alto;
    
    public Image imagen;
    private Image imagenAux;
    private boolean hayFoto = false;

    public CuadroImagen() {
        this.zoom = 0.0F;
        setBounds(0, 0, 595, 842);
        setVisible(true);
    }

    public void setImagen(InputStream vi) {
        this.zoom = 0.0F;
        try {
          
            imagen = new Image(vi);
            
            this.imagenAux = this.imagen;
            this.hayFoto = true;

            ancho = imagen.getWidth();
            alto = imagen.getHeight();
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  

   
  


  
}
