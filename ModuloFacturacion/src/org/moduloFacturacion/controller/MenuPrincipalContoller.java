package org.moduloFacturacion.controller;

import VO.ArchivosVO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import org.moduloFacturacion.report.GenerarReporte;


import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.moduloFacturacion.bean.CuadroImagen;
import org.moduloFacturacion.bean.MiVisorPDF;

import org.moduloFacturacion.bean.AutoCompleteComboBoxListener;
import org.moduloFacturacion.bean.CambioScene;
import org.moduloFacturacion.bean.Usuario;
import org.moduloFacturacion.bean.ValidarStyle;
import org.moduloFacturacion.db.Conexion;


public class MenuPrincipalContoller implements Initializable {
    double xOffset = 0;
    double yOffset = 0;
    LoginViewController login = new LoginViewController();
    CambioScene cambioScene = new CambioScene();
    @FXML
    private Button off;
    @FXML
    private Button on;
    @FXML
    private CheckBox checkBox;
    @FXML
    private AnchorPane cajaConsulta;
    @FXML
    private ScrollPane scroll;
    @FXML
    private JFXButton btnAtras;
    @FXML
    private JFXButton btnSiguiente;

    

    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO};
    public Operacion tipoOperacion= Operacion.NINGUNO;
    public Operacion cancelar = Operacion.NINGUNO;
    Image imgError= new Image("org/moduloFacturacion/img/error.png");
    Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");
    Image warning= new Image("org/moduloFacturacion/img/warning.png");
    private ObservableList<Usuario>listaUsuario;
    private ObservableList<String>listaCombo;
    private ObservableList<String>listaComboCodigo;
    
    @FXML
    private Label labelUsuario;
    @FXML
    private AnchorPane cajaInventario;
    @FXML
    private Tab tabAjustes;
    @FXML
    private Tab tabInformacion;
    @FXML
    private Pane paneBienvenida;
    @FXML
    private AnchorPane cajaFactura;
    @FXML
    private AnchorPane paneUsuario;
    @FXML
    private AnchorPane paneTabla;
    @FXML
    private MenuItem itemInventario;
    @FXML
    private AnchorPane anchor;
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private TableView<Usuario> tableUsuario;
    @FXML
    private TableColumn<Usuario, String> colCodigoUsuario;
    
    @FXML
    private TableColumn<Usuario, String> colPasswordUsuario;
    @FXML
    private TableColumn<Usuario, String> colTipoUsuario;
    @FXML
    private TableColumn<Usuario, String> colNombreUsuario;
    @FXML
    private ComboBox<String> cmbCodigoUsuario;
    @FXML
    private ComboBox<String> cmbTipoUsuario;
    
    public Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
    public Preferences prefsUsuario1 = Preferences.userRoot().node(this.getClass().getName());
    public Preferences prefsRegresar = Preferences.userRoot().node(this.getClass().getName());
    public Preferences prefsRegresarProductos = Preferences.userRoot().node(this.getClass().getName());
     ValidarStyle validar = new ValidarStyle();
    
    
    @FXML
    private void recordarContraseña(ActionEvent event) {
        if(checkBox.isSelected()){
            prefsUsuario1.put("validar", "recordar");
        }else{
            prefsUsuario1.put("validar", "no recordar");
        }
    }
    
    public void limpiarText(){
        txtUsuario.setText("");
        txtPassword.setText("");
        cmbTipoUsuario.setValue("");
        cmbCodigoUsuario.setValue("");
        cmbTipoUsuario.setPromptText("Seleccione un tipo de Usuario");
    }
    public void desactivarText(){
        txtUsuario.setEditable(false);
        txtPassword.setEditable(false);
        cmbTipoUsuario.setDisable(true); 
    }
    public void activarText(){
        txtUsuario.setEditable(true);
        txtPassword.setEditable(true);
        cmbTipoUsuario.setDisable(false); 
    }
    public void desactivarControles(){
        btnEliminar.setDisable(true);
        btnEditar.setDisable(true);
        
    }
    public void activarControles(){
        btnEliminar.setDisable(false);
        btnEditar.setDisable(false);
        
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
    private void validarcontraseña(KeyEvent event) {
        char letra = event.getCharacter().charAt(0);
        
        if(!Character.isLetterOrDigit(letra)){
            event.consume();
        }else{
        
        }
    }


    public ObservableList<Usuario> getUsuario(){
        ArrayList<Usuario> lista = new ArrayList();
        ArrayList<String> listaCodigo = new ArrayList();
        String sql = "{call spListarUsuario()}";
        int x=0;
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Usuario(
                        rs.getString("usuarioId"),
                         rs.getString("usuarioNombre"),
                        rs.getString("usuarioPassword"),
                        rs.getString("tipoUsuario")
                ));
              listaCodigo.add(x,rs.getString("usuarioId"));
              x++;
            }
            listaComboCodigo = FXCollections.observableList(listaCodigo);
            cmbCodigoUsuario.setItems(listaComboCodigo);
        } catch (SQLException ex) {
            ex.printStackTrace();
            Image imgError = new Image("org/moduloFacturacion/img/error.png");
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL CARGAR DATOS");
            noti.text("Error al cargar la base de datos");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
        
        return listaUsuario = FXCollections.observableList(lista);
    }
    
    
    public void cargarDatos(){
        
        tableUsuario.setItems(getUsuario());
        
        colCodigoUsuario.setCellValueFactory(new PropertyValueFactory("usuarioId"));
        colTipoUsuario.setCellValueFactory(new PropertyValueFactory("tipoUsuario"));
        colNombreUsuario.setCellValueFactory(new PropertyValueFactory("usuarioNombre"));
        colPasswordUsuario.setCellValueFactory(new PropertyValueFactory("usuarioPassword"));
        new AutoCompleteComboBoxListener<>(cmbCodigoUsuario);
        desactivarControles();
        desactivarText();
        llenarComboBox();
        limpiarText();   
    }
    
    
    public void llenarComboBox(){
        String sql = "{call spListarTipoUsuario()}";
        int x = 0;
        ArrayList<String>lista= new ArrayList<>();
        try {
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x,rs.getString("tipoUsuario"));
                x++;
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        listaCombo = FXCollections.observableList(lista);
        cmbTipoUsuario.setItems(listaCombo);
        
    }
    
    public void anuncio(){
        LocalDate fechaActual = LocalDate.now();
        String sql="{call SpRestarDias('"+fechaActual+"')}";
        String sql2 = "{call SpValidarCredito()}";
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ps.execute();
            
            PreparedStatement ps2= Conexion.getIntance().getConexion().prepareCall(sql2);
            ResultSet rs =ps2.executeQuery();
            while(rs.next()){
                
            }
            
            if(rs.first()){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(warning));
                noti.title("CREDITOS");
                noti.text("Tiene creditos Pendientes");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        prefsRegresar.put("regresar", "menu");
        prefsRegresarProductos.put("regresarProducto", "menu");
         validar.validarMenu(prefs.get("dark", "root"), anchor);
        labelUsuario.setText("¡BIENVENIDO "+login.prefsUsuario.get("usuario","root").toUpperCase()+"!");
        cmbCodigoUsuario.setValue("");
        if(login.prefsLogin.get("tipo","root").equals("empleado")){
            cajaInventario.setDisable(true);
            tabAjustes.setDisable(true);
            itemInventario.setDisable(true);
        }
        
        if(prefsUsuario1.get("validar", "root").equals("recordar")){
            checkBox.setSelected(true);
        }else{
                checkBox.setSelected(false);
        }
       
        // caja de bienvenida
         FadeTransition ft = new FadeTransition();
       ft.setFromValue(0);
       ft.setToValue(1);
       ft.setDuration(Duration.seconds(1));
       ft.setNode(paneBienvenida);
       ft.setCycleCount(1);
       ft.play();
       
       
       TranslateTransition tt = new TranslateTransition();
       tt.setFromY(-20);
       tt.setToY(1);
       tt.setDuration(Duration.seconds(1.5));
       tt.setNode(paneBienvenida);
       tt.setCycleCount(1);
       tt.play();
       
       
       // caja de inventario
       
        FadeTransition ftInventario = new FadeTransition();
       ftInventario.setFromValue(0);
       ftInventario.setToValue(1);
       ftInventario.setDuration(Duration.seconds(2));
       ftInventario.setNode(cajaInventario);
       ftInventario.setCycleCount(1);
       ftInventario.play();
       
       
       TranslateTransition tInventario = new TranslateTransition();
       tInventario.setFromY(-60);
       tInventario.setToY(0);
       tInventario.setDuration(Duration.seconds(2.5));
       tInventario.setNode(cajaInventario);
       tInventario.setCycleCount(1);
       tInventario.play();
       
       //CAJA DE PRECIOS
          FadeTransition ftPrecios = new FadeTransition();
       ftPrecios.setFromValue(0);
       ftPrecios.setToValue(1);
       ftPrecios.setDuration(Duration.seconds(2.5));
       ftPrecios.setNode(cajaConsulta);
       ftPrecios.setCycleCount(1);
       ftPrecios.play();
       
       
       TranslateTransition ttPrecios = new TranslateTransition();
       ttPrecios.setFromY(-80);
       ttPrecios.setToY(1);
       ttPrecios.setDuration(Duration.seconds(3));
       ttPrecios.setNode(cajaConsulta);
       ttPrecios.setCycleCount(1);
       ttPrecios.play();
       
       
       // caja de facturas
       FadeTransition ftFacturas = new FadeTransition();
       ftFacturas.setFromValue(0);
       ftFacturas.setToValue(1);
       ftFacturas.setDuration(Duration.seconds(3));
       ftFacturas.setNode(cajaFactura);
       ftFacturas.setCycleCount(1);
       ftFacturas.play();
       
       
       TranslateTransition ttFacturas = new TranslateTransition();
       ttFacturas.setFromY(-80);
       ttFacturas.setToY(0);
       ttFacturas.setDuration(Duration.seconds(3.5));
       ttFacturas.setNode(cajaFactura);
       ttFacturas.setCycleCount(1);
       ttFacturas.play();
       
    }    

        //pestaña de ajustes
    @FXML
    private void tabAjustesEvent(Event event) {
        // caja de usuario
       FadeTransition ftUsuario = new FadeTransition();
       ftUsuario.setFromValue(0);
       ftUsuario.setToValue(1);
       ftUsuario.setDuration(Duration.seconds(2));
       ftUsuario.setNode(paneUsuario);
       ftUsuario.setCycleCount(1);
       ftUsuario.play();
       
       
       TranslateTransition ttUsuario = new TranslateTransition();
       ttUsuario.setFromY(-80);
       ttUsuario.setToY(0);
       ttUsuario.setDuration(Duration.seconds(1.0));
       ttUsuario.setNode(paneUsuario);
       ttUsuario.setCycleCount(1);
       ttUsuario.play();
        
       //caja de tabla
       FadeTransition ftTablaUsuario = new FadeTransition();
       ftTablaUsuario.setFromValue(0);
       ftTablaUsuario.setToValue(1);
       ftTablaUsuario.setDuration(Duration.seconds(2));
       ftTablaUsuario.setNode(paneTabla);
       ftTablaUsuario.setCycleCount(1);
       ftTablaUsuario.play();
       
       
       TranslateTransition ttTabla = new TranslateTransition();
       ttUsuario.setFromY(-80);
       ttUsuario.setToY(0);
       ttUsuario.setDuration(Duration.seconds(2.0));
       ttUsuario.setNode(paneTabla);
       ttUsuario.setCycleCount(1);
       ttUsuario.play();
       cargarDatos();
    }
    

    @FXML
    private void tabBienvenida(Event event) {
         // caja de bienvenida
         FadeTransition ft = new FadeTransition();
       ft.setFromValue(0);
       ft.setToValue(1);
       ft.setDuration(Duration.seconds(1));
       ft.setNode(paneBienvenida);
       ft.setCycleCount(1);
       ft.play();
       
       
       TranslateTransition tt = new TranslateTransition();
       tt.setFromY(-20);
       tt.setToY(1);
       tt.setDuration(Duration.seconds(1.5));
       tt.setNode(paneBienvenida);
       tt.setCycleCount(1);
       tt.play();

       
       // caja de inventario
       
        FadeTransition ftInventario = new FadeTransition();
       ftInventario.setFromValue(0);
       ftInventario.setToValue(1);
       ftInventario.setDuration(Duration.seconds(2));
       ftInventario.setNode(cajaInventario);
       ftInventario.setCycleCount(1);
       ftInventario.play();
       
       
       TranslateTransition tInventario = new TranslateTransition();
       tInventario.setFromY(-60);
       tInventario.setToY(0);
       tInventario.setDuration(Duration.seconds(2.5));
       tInventario.setNode(cajaInventario);
       tInventario.setCycleCount(1);
       tInventario.play();
       
         
       //CAJA DE PRECIOS
          FadeTransition ftPrecios = new FadeTransition();
       ftPrecios.setFromValue(0);
       ftPrecios.setToValue(1);
       ftPrecios.setDuration(Duration.seconds(2.5));
       ftPrecios.setNode(cajaConsulta);
       ftPrecios.setCycleCount(1);
       ftPrecios.play();
       
       
       TranslateTransition ttPrecios = new TranslateTransition();
       ttPrecios.setFromY(-80);
       ttPrecios.setToY(1);
       ttPrecios.setDuration(Duration.seconds(3));
       ttPrecios.setNode(cajaConsulta);
       ttPrecios.setCycleCount(1);
       ttPrecios.play();
       
       // caja de facturas
       FadeTransition ftFacturas = new FadeTransition();
       ftFacturas.setFromValue(0);
       ftFacturas.setToValue(1);
       ftFacturas.setDuration(Duration.seconds(2));
       ftFacturas.setNode(cajaFactura);
       ftFacturas.setCycleCount(1);
       ftFacturas.play();
       
       
       TranslateTransition ttFacturas = new TranslateTransition();
       ttFacturas.setFromY(-80);
       ttFacturas.setToY(0);
       ttFacturas.setDuration(Duration.seconds(3.0));
       ttFacturas.setNode(cajaFactura);
       ttFacturas.setCycleCount(1);
       ttFacturas.play();
    }
    


    
    /* INVENTARIO*/
        @FXML
    private void inventarioView(ActionEvent event) throws IOException {
        inventario();
    }
    
    
    public void inventario() throws IOException{
        String inventarioUrl = "org/moduloFacturacion/view/InventarioView.fxml";
        cambioScene.Cambio(inventarioUrl,(Stage) anchor.getScene().getWindow());
    }
    
    
     @FXML
    private void inventarioAtajo(MouseEvent event) throws IOException {
        inventario();
    }
    
   
    /*FACTURA */
    public void factura() throws IOException{
        String facturaUrl = "org/moduloFacturacion/view/FacturacionView.fxml";
        cambioScene.Cambio(facturaUrl,(Stage) anchor.getScene().getWindow());
    }
    
    @FXML
    private void facturasView(ActionEvent event) throws IOException {
        factura();
    }
    @FXML
    private void facturaAtajo(MouseEvent event) throws IOException {
        factura();
    }
      
     //atajos de menu de bienvenida
    @FXML
    private void AtajosInicio(KeyEvent event) {
    }
    
    //atajos de configuracion
    @FXML
    private void AtajosConfiguracion(KeyEvent event) {
        if(cmbCodigoUsuario.isFocused()){
                if(event.getCode() == KeyCode.ENTER){
                    buscar();
                }
        }
    }

    //atajos de vista en general
    @FXML
    private void AtajosVista(KeyEvent event) throws IOException {
        //modulos
       if(event.getCode() == KeyCode.F1){
           factura();
       }else{
           if(event.getCode() == KeyCode.F2){
               inventario();
           }
       }
    }

    public void accion(){
        switch(tipoOperacion){
            case AGREGAR:
                tipoOperacion = Operacion.GUARDAR;
                cancelar = Operacion.CANCELAR;
                desactivarControles();
                btnAgregar.setText("GUARDAR");
                btnEliminar.setText("CANCELAR");
                btnEliminar.setDisable(false);
                activarText();
                cmbCodigoUsuario.setDisable(true);
                btnBuscar.setDisable(true);
                limpiarText();
                break;
            case CANCELAR:
                tipoOperacion = Operacion.NINGUNO;
                desactivarControles();
                desactivarText();
                btnAgregar.setText("AGREGAR");
                btnEliminar.setText("ELIMINAR");
                limpiarText();
                cmbCodigoUsuario.setDisable(false);
                btnBuscar.setDisable(false);
                cancelar = Operacion.NINGUNO;
                break;
        }
    }
    
    public void accion(String sql){
         Alert alert = new Alert(AlertType.CONFIRMATION);
        PreparedStatement ps;
        ResultSet rs;
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        switch(tipoOperacion){
            case GUARDAR:        
                alert.setTitle("AGREGAR REGISTRO");
                alert.setHeaderText("AGREGAR REGISTRO");
                alert.setContentText("¿Está seguro que desea guardar este registro?");
                
                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
                
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == buttonTypeSi ){
                    try {
                        ps = Conexion.getIntance().getConexion().prepareCall(sql);
                        ps.execute();
                        
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SE HA AGREGADO EXITOSAMENTE EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = Operacion.CANCELAR;
                        accion();
                        cargarDatos();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL AGREGAR");
                        noti.text("HA OCURRIDO UN ERROR AL GUARDAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = Operacion.CANCELAR;
                        accion();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA AGREGADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacion = Operacion.CANCELAR;
                    accion();
                }
                
            
                break;

            case ELIMINAR:
                 alert.setTitle("ELIMINAR REGISTRO");
                alert.setHeaderText("ELIMINAR REGISTRO");
                alert.setContentText("¿Está seguro que desea Eliminar este registro?");
               
                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
                
                Optional<ButtonType> resultEliminar = alert.showAndWait();
                
                if(resultEliminar.get() == buttonTypeSi){
                    try {
                        ps = Conexion.getIntance().getConexion().prepareCall(sql);
                        ps.execute();
                        
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SE HA ELIMINADO EXITOSAMENTE EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        cargarDatos();
                        tipoOperacion = Operacion.CANCELAR;
                        accion();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        
                        
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ELIMINAR");
                        noti.text("HA OCURRIDO UN ERROR AL ELIMINAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = Operacion.CANCELAR;
                        accion();
                    }
                }else{
                     noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ELIMINADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacion = Operacion.CANCELAR;
                    accion();
                }
                
                break;
                
            case ACTUALIZAR:
                
                
                alert.setTitle("ACTUALIZAR REGISTRO");
                alert.setHeaderText("ACTUALIZAR REGISTRO");
                alert.setContentText("¿Está seguro que desea Actualizar este registro?");
               
                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
                
                Optional<ButtonType> resultactualizar = alert.showAndWait();
                if(resultactualizar.get() == buttonTypeSi ){
                    try {
                        ps = Conexion.getIntance().getConexion().prepareCall(sql);
                        ps.execute();
                        
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SE HA ACTUALIZADO EXITOSAMENTE EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = Operacion.CANCELAR;
                        accion();
                        cargarDatos();
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ACTUALIZAR");
                        noti.text("HA OCURRIDO UN ERROR AL ACTUALIZAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = Operacion.CANCELAR;
                        accion();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ACTUALIZAR EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacion = Operacion.CANCELAR;
                    accion();
                }
                
                break;
            case BUSCAR:
                try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    String codigo="";
                    while(rs.next()){
                        cmbCodigoUsuario.setValue(rs.getString("usuarioId"));
                        txtUsuario.setText(rs.getString("usuarioNombre"));
                        txtPassword.setText(rs.getString("usuarioPassword"));
                        cmbTipoUsuario.setValue(rs.getString("tipoUsuario"));
                        codigo = rs.getString("usuarioId");
                        
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tableUsuario.getItems().size(); i++){
                            if(colCodigoUsuario.getCellData(i) == codigo){
                                tableUsuario.getSelectionModel().select(i);
                                break;
                            }
                        }
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SU OPERACIÓN SE HA REALIZADO CON EXITO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                    }else{
                         
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL BUSCAR");
                        noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacion = Operacion.CANCELAR;
                        accion();
                    }
                }catch(SQLException ex){
                    ex.printStackTrace();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR AL BUSCAR");
                    noti.text("HA OCURRIDO UN ERROR EN LA BASE DE DATOS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacion = Operacion.CANCELAR;
                    accion();
                }
                break;
                 
        }
    
    }
    
    
    
    @FXML
    private void btnAgregar(MouseEvent event) {
             if(tipoOperacion == Operacion.GUARDAR){
                if(txtUsuario.getText().isEmpty() || txtPassword.getText().isEmpty() || cmbTipoUsuario.getValue() == ""){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("HAY CAMPOS VACÍOS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                    
                }else{
                    if((txtUsuario.getText().length() >= 9 && txtUsuario.getText().length() < 30 ) &&(txtPassword.getText().length() >= 9 && txtPassword.getText().length() < 30)){
                        int tipoUsuario;
                        Usuario nuevoUsuario = new Usuario();
                        nuevoUsuario.setUsuarioNombre(txtUsuario.getText());
                        nuevoUsuario.setUsuarioPassword(txtPassword.getText());
                        if(cmbTipoUsuario.getValue().equals("Administrador")){
                               tipoUsuario = 1;
                        }else{
                            tipoUsuario = 2;
                        }

                        String sql = "{call spAgregarUsuario('"+nuevoUsuario.getUsuarioNombre()+"','"+nuevoUsuario.getUsuarioPassword()+"','"+tipoUsuario+"')}";
                        accion(sql);
                    }else{
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR");
                        noti.text("USUARIO Y/O CONTRASEÑA NO TIENEN UNA LONGITUD ADECUADA (DEBEN ESTAR ENTRE 9 Y 30 CARACTERES)");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();   
                        noti.show();
                    }
                    
                }
            }else{
                tipoOperacion = Operacion.AGREGAR;
                accion();
            }
    }

    @FXML
    private void btnEditar(MouseEvent event) {
        int tipoUsuario;
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsuarioId(cmbCodigoUsuario.getValue());
        nuevoUsuario.setUsuarioNombre(txtUsuario.getText());
        nuevoUsuario.setUsuarioPassword(txtPassword.getText());
        if(cmbTipoUsuario.getValue().equals("Administrador")){
               tipoUsuario = 1;
        }else{
            tipoUsuario = 2;
        }
        tipoOperacion = Operacion.ACTUALIZAR;
        String sql = "{call SpActualizarUsuario('"+nuevoUsuario.getUsuarioId()+"','"+nuevoUsuario.getUsuarioNombre()+"','"+nuevoUsuario.getUsuarioPassword()+"','"+tipoUsuario+"')}";
        accion(sql);
}
    
    
    @FXML
    private void btnEliminar(MouseEvent event) {
        
        if(tipoOperacion == Operacion.GUARDAR){
            tipoOperacion = Operacion.CANCELAR;
            accion();
        }else{
            String sql = "{call SpEliminarUsuarios('"+cmbCodigoUsuario.getValue()+"')}";
            tipoOperacion = Operacion.ELIMINAR;
            accion(sql);
        }
    }

       public void buscar(){
            if(cmbCodigoUsuario.getValue().equals("")){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE CÓDIGO ESTA VACÍO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
        }else{
            tipoOperacion = Operacion.BUSCAR;
            String sql = "{call spBuscarUsuario('"+cmbCodigoUsuario.getValue()+"')}";
            accion(sql);
        }
       }
    @FXML
    private void btnBuscar(MouseEvent event) {
       
      buscar();
    }

    @FXML
    private void seleccionarElementos(MouseEvent event) {
        int index = tableUsuario.getSelectionModel().getSelectedIndex();
        try{
            activarText();
            cmbCodigoUsuario.setValue(colCodigoUsuario.getCellData(index).toString());
            txtUsuario.setText(colNombreUsuario.getCellData(index));
            txtPassword.setText(colPasswordUsuario.getCellData(index));
            cmbTipoUsuario.setValue(colTipoUsuario.getCellData(index));
            btnEditar.setDisable(false);
            btnEliminar.setDisable(false);
            cmbTipoUsuario.setDisable(false);
        }catch(Exception e){
            
        }
        
    }
    
     @FXML
    private void off(MouseEvent event) {
         prefs.put("dark", "oscuro");
        validar.validarMenu(prefs.get("dark", "root"), anchor);
    }

    @FXML
    private void on(MouseEvent event) {
        prefs.put("dark", "claro");
        validar.validarMenu(prefs.get("dark", "root"), anchor);
    }
        @FXML
    private void consultaPrecios(MouseEvent event) throws IOException {
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("org/moduloFacturacion/view/ConsultaPrecios.fxml"));
        Scene scene = new Scene(root);
         
        
       root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        stage.setWidth(599);
        stage.setHeight(510);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("CONSULTA DE PRECIOS");
        stage.setScene(scene);
        stage.show();
    }
    // tab de informacion donde irá el manual de usuario
    
    
    
   

  
    
    int numImg;
    private ArrayList<ArchivosVO> ListaComponente;
    MiVisorPDF pn = new MiVisorPDF();
    ArchivosVO pl = new ArchivosVO();
    
    CuadroImagen img= new CuadroImagen();
    int paginas = 0;
    private int totalp = -1;

    @FXML
    private void tabInformacion(Event event) {
        //Es considerado pagina 1
        numImg = 0;
        //Lee la pagina 1
        
        ListaComponente = pn.leerPDF();
        
        //Guardamos todas las paginas en el ArrayList
        
        
            pl = ListaComponente.get(paginas);
            InputStream targetStream = new ByteArrayInputStream(pl.getArchivos());

            img.setImagen(targetStream);
            
        
       
        
        totalp = ListaComponente.size();
        //Mostramos la primera pagina
        ArchivosVO pi = new ArchivosVO();
        pi = ListaComponente.get(0);
        ImageView img1 = new ImageView();
        img1.setImage(img.imagen);
        
        img1.fitWidthProperty().bind(scroll.widthProperty());
        scroll.setCenterShape(true);
        scroll.setContent(img1);
        
    }
    
     @FXML
    private void btnAtras(ActionEvent event) {
         try{
                paginas--;
                pl = ListaComponente.get(paginas);
                InputStream targetStream = new ByteArrayInputStream(pl.getArchivos());

                img.setImagen(targetStream);
                
                


                totalp = ListaComponente.size();
                //Mostramos la primera pagina
                ArchivosVO pi = new ArchivosVO();
                pi = ListaComponente.get(0);
                ImageView img1 = new ImageView();
                img1.setImage(img.imagen);

                img1.fitWidthProperty().bind(scroll.widthProperty());
                scroll.setCenterShape(true);
                scroll.setContent(img1);
             }catch(Exception ex){
                  Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("YA NO SE ECONTRARON  MÁS PÁGINAS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                    paginas = -1;
             }
    }
    
      @FXML
    private void btnSiguiente(ActionEvent event) {
             try{
                paginas++;
                 pl = ListaComponente.get(paginas);
                InputStream targetStream = new ByteArrayInputStream(pl.getArchivos());

                img.setImagen(targetStream);
                
                 


                totalp = ListaComponente.size();
                //Mostramos la primera pagina
                ArchivosVO pi = new ArchivosVO();
                pi = ListaComponente.get(0);
                ImageView img1 = new ImageView();
                img1.setImage(img.imagen);

                img1.fitWidthProperty().bind(scroll.widthProperty());
                scroll.setCenterShape(true);
                scroll.setContent(img1);
             }catch(Exception ex){
                  Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("YA NO SE ECONTRARON  MÁS PÁGINAS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                    paginas = -1;
             }
    }
    
    private void cierreCaja(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("CIERRE DE CAJA");
        dialog.setHeaderText("Ingrese la fecha: ");
        dialog.setContentText("año/mes/día");
        Optional<String>  result = dialog.showAndWait();
        
        if(result.isPresent()){
            try{
            
                Map parametros = new HashMap();

                 String FechaCorte = result.get();
                String repuesta = "'"+FechaCorte+"'";
                
                parametros.put("FechaCorte", "'"+FechaCorte+"'");
                System.out.println(repuesta);
                 GenerarReporte.mostrarReporte("CierreDeCaja.jasper", "CIERRE DE CAJAS", parametros);
                 
                }catch(Exception e){
                    e.printStackTrace();
                }
        }
        
    }

    private void reporteVentas(ActionEvent event) {
         TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("REPORTE DE VENTAS");
        dialog.setHeaderText("Ingrese la fecha: ");
        dialog.setContentText("año/mes/día");
        Optional<String>  result = dialog.showAndWait();
        if(result.isPresent()){
            try{
                Map parametros = new HashMap();

                String FechaCorte = result.get();
                 
                String repuesta = "'"+FechaCorte+"'";
                
                parametros.put("FechaCorte", "'"+FechaCorte+"'");
                 GenerarReporte.mostrarReporte("CorteDeCaja.jasper", "REPORTE DE VENTAS", parametros);
                
                
                }catch(Exception e){
                    e.printStackTrace();
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("DEBE SELECCIONAR FECHA DE INICIO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                }
        }
    }

    
 }

