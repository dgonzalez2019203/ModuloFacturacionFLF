package org.moduloFacturacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class ActivationViewController implements Initializable {

    @FXML
    private Pane panelTransicion;
    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private JFXTextField txtClave;
    Image imgError = new Image("org/moduloFacturacion/img/error.png");
    Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");
    public Preferences prefsValidacion = Preferences.userRoot().node(this.getClass().getName());;
    
    @FXML
    private AnchorPane anchor;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefsValidacion.put("act", "771495f765ad06279c51dbbade9400ff");
        
        prefsValidacion.put("program", "false");

    }    


    @FXML
    private void btnCerrar(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void btnRegistrar(MouseEvent event) throws IOException {
       
        if(!txtClave.getText().isEmpty()){
            
            
                if(prefsValidacion.get("act", "root").equals(txtClave.getText())){

                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgCorrecto));
                    noti.title("ACTIVACIÓN EXITOSA");
                    noti.text("SE HA REGISTRADO CORRECTAMENTE");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();


                    Stage primaryStage = new Stage();
                    Parent  root = FXMLLoader.load(getClass().getClassLoader().getResource("org/moduloFacturacion/view/LoginView.fxml"));
                    Scene scene = new Scene(root);
                    primaryStage.setTitle("LOGIN PROGRAMMERS BILLING");
                    primaryStage.getIcons().add(new Image(getClass().getResource("/org/moduloFacturacion/img/LogoGrande.png").toExternalForm()));
                    primaryStage.setWidth(668);
                    primaryStage.setHeight(520);
                    primaryStage.setScene(scene);     
                    primaryStage.initStyle(StageStyle.UNDECORATED);
                    primaryStage.show();
                    prefsValidacion.put("program", "true");
                    Stage stage = (Stage) anchor.getScene().getWindow();
                    stage.close();

                }else{
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("ESTA CLAVE NO ESTA REGISTRADA");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                }
            }     
    }

    @FXML
    private void txtActivacion(KeyEvent event) {
        char letra = event.getCharacter().charAt(0);
        if(!Character.isLetterOrDigit(letra)){
            event.consume();
        }
        
    }
    
}
