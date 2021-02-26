
package org.moduloFacturacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.moduloFacturacion.bean.CambioScene;
import org.moduloFacturacion.db.Conexion;


public class LoginViewController implements Initializable {
    
    private Label label;
    @FXML
    private Pane panelTransicion;
    @FXML
    private AnchorPane anchor;
    
    CambioScene cambioScene = new CambioScene();
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXPasswordField txtContraseña;
    @FXML
    private JFXButton btnIngresar;
    public Preferences prefsLogin = Preferences.userRoot().node(this.getClass().getName());
    public Preferences prefsUsuario = Preferences.userRoot().node(this.getClass().getName());
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       txtUsuario.requestFocus();
       FadeTransition ft = new FadeTransition();
       ft.setFromValue(0);
       ft.setToValue(1);
       ft.setDuration(Duration.seconds(2));
       ft.setNode(panelTransicion);
       ft.setCycleCount(1);
       ft.play();
       
       TranslateTransition tt = new TranslateTransition();
       tt.setFromY(-100);
       tt.setToY(0);
       tt.setDuration(Duration.seconds(2.5));
       tt.setNode(panelTransicion);
       tt.setCycleCount(1);
       tt.play();
       
       
       
    }    
    
    
    public boolean validacion(String usuario){
        boolean validacion=false;
        String sql = "{call spBuscarUsuairo('"+usuario+"')}";
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            if(rs.first()){
                System.out.println("hay");
                validacion = true;
            }else{
                System.out.println("no hay");
                validacion = false;
            }
        }catch(SQLException ex){
            
        }
        
        return validacion;
    }
    
    public void validarLogin(){
        String sql = "{call SpLoginValidar('"+txtUsuario.getText()+"')}";
        PreparedStatement ps;
        int tipo=0;
        try {
            ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                tipo = rs.getInt("tipoUsuarioId");
            }
            if(tipo==1){
                prefsLogin.put("tipo","administrador");
            }else{
                if(tipo==2){
                    prefsLogin.put("tipo","empleado");
                }
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
               
    }
    
    public void login() throws IOException{
       Image imgError= new Image("org/moduloFacturacion/img/error.png");
       Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");
       if(txtUsuario.getText().equals("") && txtContraseña.getText().equals("")){
           Notifications noti = Notifications.create();
           noti.graphic(new ImageView(imgError));
           noti.title("ERROR al iniciar Sesión");
           noti.text("Su contraseña y/o Usuario estan vacíos");
           noti.position(Pos.BOTTOM_RIGHT);
           noti.hideAfter(Duration.seconds(4));
           noti.darkStyle();
           
           noti.show();
       }else{
           String sql = "{call SpLogin('"+txtUsuario.getText()+"','"+txtContraseña.getText()+"')}";
           
           try {
               PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
               ResultSet rs = ps.executeQuery();
               
               if(rs.first()){
                    prefsUsuario.put("usuario",txtUsuario.getText());
                    validarLogin();
                    Stage stage1 = (Stage)anchor.getScene().getWindow();
                    Stage primaryStage= new Stage();
                    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("org/moduloFacturacion/view/menuPrincipal.fxml"));
                    Scene scene = new Scene(root);
                    primaryStage.setWidth(1100);
                    primaryStage.setHeight(590);
                    primaryStage.setScene(scene);
                    primaryStage.setTitle("PROGRAMMERS BILLING");
                    primaryStage.getIcons().add(new Image(getClass().getResource("/org/moduloFacturacion/img/LogoGrande.png").toExternalForm()));
                    primaryStage.show();
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgCorrecto));
                    noti.title("INICIO DE SESIÓN EXITOSO");
                    noti.text("Su contraseña y/o Usuario estan correctos");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    
                    stage1.close();
               }else{
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR al iniciar Sesión");
                    noti.text("Su contraseña y/o Usuario estan Incorrectos");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    txtUsuario.requestFocus();
                    txtUsuario.setText("");
                    txtContraseña.setText("");
               }
               
           } catch (SQLException ex) {
              ex.printStackTrace();
           }
        
       }
    }
   

    @FXML
    private void btnIngresar(MouseEvent event) throws IOException  {
       login();
    }

    @FXML
    private void btnCerrar(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void atajosLogin(KeyEvent event) throws IOException {
        if(event.getCode() == KeyCode.ENTER){
            login();
        }else{
            if(event.getCode() == KeyCode.ESCAPE){
                System.exit(0);
            }
        }
    }

    @FXML
    private void validarUsuario(KeyEvent event) {
        
        char letra = event.getCharacter().charAt(0);
        
        if(!Character.isLetterOrDigit(letra)){
            event.consume();
        }else{
        
        }
    }

    @FXML
    private void validarContraseña(KeyEvent event) {
        char letra = event.getCharacter().charAt(0);
        
        if(!Character.isLetterOrDigit(letra)){
            event.consume();
        }else{
        
        }
    }

 
    
}
