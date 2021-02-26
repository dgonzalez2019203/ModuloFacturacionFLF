package org.moduloFacturacion.system;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.moduloFacturacion.controller.ActivationViewController;
import org.moduloFacturacion.controller.LoginViewController;
import org.moduloFacturacion.controller.MenuPrincipalContoller;
import org.moduloFacturacion.db.Conexion;

public class MainApp extends Application {
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    LoginViewController login = new LoginViewController();
    ActivationViewController pc = new ActivationViewController();
   
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Conexion c = new Conexion();
        if(c.getConexion() != null){
            System.out.println("CONEXIO CORRECTA 1");
        }else{
            System.out.println("CONEXION INCORRECTA");
        }        
        Parent root;
       if(pc.prefsValidacion.get("program", "root").equals("true")){
           if(menu.prefsUsuario1.get("validar", "root").equals("recordar")){    
            
            root = FXMLLoader.load(getClass().getClassLoader().getResource("org/moduloFacturacion/view/menuPrincipal.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("PROGRAMMERS BILLING");
            stage.getIcons().add(new Image(getClass().getResource("/org/moduloFacturacion/img/LogoGrande.png").toExternalForm()));
            stage.setWidth(1100);
            stage.setHeight(597);
            stage.setMinWidth(1100);
            stage.setMinHeight(597);
            stage.setScene(scene);
            
            
            stage.show();
            
          
        }else{
            root = FXMLLoader.load(getClass().getClassLoader().getResource("org/moduloFacturacion/view/LoginView.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("LOGIN PROGRAMMERS BILLING");
            stage.getIcons().add(new Image(getClass().getResource("/org/moduloFacturacion/img/LogoGrande.png").toExternalForm()));
            stage.setWidth(668);
            stage.setHeight(520);
            stage.setScene(scene);     
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        }
       }else{
            root = FXMLLoader.load(getClass().getClassLoader().getResource("org/moduloFacturacion/view/ActivationView.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("ACTIVACIÓN PROGRAMMERS BILLING");
            stage.getIcons().add(new Image(getClass().getResource("/org/moduloFacturacion/img/LogoGrande.png").toExternalForm()));
            stage.setWidth(600);
            stage.setHeight(420);
            stage.setScene(scene);     
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
       }
       
    }

     public static void main(String[] args) {
        launch(args);
       

    }
    
}
