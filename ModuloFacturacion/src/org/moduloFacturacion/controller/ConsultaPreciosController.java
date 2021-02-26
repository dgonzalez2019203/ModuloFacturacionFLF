package org.moduloFacturacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.moduloFacturacion.bean.AutoCompleteComboBoxListener;
import org.moduloFacturacion.bean.ValidarStyle;
import org.moduloFacturacion.db.Conexion;


public class ConsultaPreciosController implements Initializable {

    @FXML
    private JFXButton btnCerrar;
    @FXML
    private ComboBox<String> cmbComboFiltro;
    @FXML
    private ComboBox<String> cmbBuscar;
    @FXML
    private Button btnBuscarProducto;
    @FXML
    private JFXTextField txtCodigoProducto;
    @FXML
    private JFXTextField txtNombreProducto;
    @FXML
    private JFXTextField txtPrecioProducto;
    Image imgError = new Image("org/moduloFacturacion/img/error.png");
    Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");
    
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    ValidarStyle validar = new ValidarStyle();
    @FXML
    private AnchorPane anchor;
    ObservableList<String> listaFiltro;
    ObservableList<String> listaBuscar;
    
    public void cargarCombo(){
        ArrayList<String>lista = new ArrayList();
        
        lista.add(0,"CÓDIGO");
        lista.add(1,"NOMBRE");
        
        listaFiltro = FXCollections.observableList(lista);
        
        cmbComboFiltro.setItems(listaFiltro);
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validar.validarMenu(menu.prefs.get("dark", "root"), anchor);
        cargarCombo();
        btnBuscarProducto.setDisable(true);
        cmbBuscar.setDisable(false);
         new AutoCompleteComboBoxListener(cmbBuscar);
          new AutoCompleteComboBoxListener(cmbComboFiltro);
    }    

    @FXML
    private void btnCerrar(MouseEvent event) {
        Stage stage = (Stage) anchor.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void comboFiltro(ActionEvent event) {
        btnBuscarProducto.setDisable(false);
        ArrayList<String> lista = new ArrayList();
        cmbBuscar.setDisable(false);
        String sql ="{call SpListarProductos()}";
        int x=0;
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                 if(cmbComboFiltro.getValue().equals("CÓDIGO")){
                     lista.add(x, rs.getString("productoId"));
                     
                }else if(cmbComboFiltro.getValue().equals("NOMBRE")){
                    
                    lista.add(x, rs.getString("productoDesc"));
                    
                }
                 x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
       listaBuscar = FXCollections.observableList(lista);
       cmbBuscar.setItems(listaBuscar);
       
       new AutoCompleteComboBoxListener(cmbBuscar);
    }

    @FXML
    private void cmbBuscar(ActionEvent event) {
        buscar();
    }
    
    public void buscar(){
        String sql = "";
        if(cmbComboFiltro.getValue().equals("CÓDIGO")){
             sql = "{call SpBuscarProductos('"+cmbBuscar.getValue()+"')}";
            
        }else if(cmbComboFiltro.getValue().equals("NOMBRE")){
            sql = "{call SpBuscarProductosNombre('"+cmbBuscar.getValue()+"')}";
        }
        
        try{
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    txtCodigoProducto.setText(rs.getString("productoId"));
                    txtNombreProducto.setText(rs.getString("productoDesc"));
                    txtPrecioProducto.setText(rs.getString("productoPrecio"));
                }
                if(!rs.first()){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                }
        }catch(SQLException ex){
              Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR AL CARGAR DATOS CMB");
                noti.text("Error al cargar la base de datos");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
        }
    }
    
    @FXML
    private void btnBuscarProducto(MouseEvent event) {
        buscar();
    }
    
}
