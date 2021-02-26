package org.moduloFacturacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
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
import org.moduloFacturacion.bean.Animations;
import org.moduloFacturacion.bean.AutoCompleteComboBoxListener;
import org.moduloFacturacion.bean.CambioScene;
import org.moduloFacturacion.bean.EstadoProductos;
import org.moduloFacturacion.bean.InventarioProductos;
import org.moduloFacturacion.bean.ValidarStyle;
import org.moduloFacturacion.db.Conexion;


public class InventarioViewController implements Initializable {
    
    CambioScene cambioScene = new CambioScene();
    Image imgError = new Image("org/moduloFacturacion/img/error.png");
    Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");
    @FXML
    private Pane btnProveedores;
    @FXML
    private Pane btnProductos;
    @FXML
    private Pane btnInicio;
    
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    ValidarStyle validar = new ValidarStyle();
    @FXML
    private AnchorPane anchor1;
    @FXML
    private AnchorPane anchor2;
    @FXML
    private AnchorPane anchor3;
    @FXML
    private AnchorPane anchor4;
    
    Animations animacion = new Animations();
    @FXML
    private JFXButton btnAgregarInventario1;



    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO, SUMAR};
    public Operacion cancelar = Operacion.NINGUNO;
    
    // Variables para Inventario
    public Operacion tipoOperacionInventario= Operacion.NINGUNO;

    ObservableList<InventarioProductos> listaInventarioProductos;
    ObservableList<String> listaEstadoInventario;
    ObservableList<String> listaCodigoProducto;
    ObservableList<String> listaCodigoFiltro;
    ObservableList<String> listaFiltro;
    ObservableList<String> listaBuscar;

    int buscarCodigoEstado = 0;
    String codigoProducto = "";

    //Variables para Estado
    public Operacion tipoOperacionEstado= Operacion.NINGUNO; 
   
    ObservableList<EstadoProductos> listaEstadoProductos;
    ObservableList<String> listaCodigoEstadoProductos;
    String codigoEstado = "";
    
    //Propiedades Inventario
    @FXML
    private AnchorPane anchor;
    @FXML
    private JFXButton btnAgregarInventario;
    @FXML
    private JFXButton btnEditarInventario;
    @FXML
    private JFXButton btnEliminarInventario;
    @FXML
    private ComboBox<String> cmbNombreEstado;
    @FXML
    private JFXTextField txtCantidadInventario;
    @FXML
    private JFXTextField txtProveedorInventario;
    @FXML
    private JFXTextField txtProductoInventario;
    @FXML
    private ComboBox<String> cmbCodigoProductoInventario;
    @FXML
    private TableView<InventarioProductos> tblInventario;
    @FXML
    private TableColumn<InventarioProductos, String> colCodigoProductoInventario;
    @FXML
    private TableColumn<InventarioProductos, Integer> colCantidadInventario;
    @FXML
    private TableColumn<InventarioProductos, String> colProveedorInventario;
    @FXML
    private TableColumn<InventarioProductos, String> colProductoInventario;
    @FXML
    private TableColumn<InventarioProductos, String> colEstadoInventario;
    @FXML
    private JFXButton btnBuscarInventario;
    @FXML
    private ComboBox<String> cmbFiltroCodigo;
    @FXML
    private ComboBox<String> cmbBuscar;

    //Propiedades Estado
    @FXML
    private JFXTextField txtCodigoEstadoProducto;
    @FXML
    private JFXTextField txtDescEstadoProducto;
    @FXML
    private JFXButton btnAgregarEstadoProductos;
    @FXML
    private JFXButton btnEditarEstadoProductos;
    @FXML
    private JFXButton btnEliminarEstadoProductos;
    @FXML
    private TableView<EstadoProductos> tblEstadoProductos;
    @FXML
    private TableColumn<EstadoProductos, String> colCodigoEstadoCodigo;
    @FXML
    private TableColumn<EstadoProductos, String> colDescEstadoProductos;
    @FXML
    private JFXButton btnBuscarEstadoProductos;
    @FXML
    private ComboBox<String> cmbCodigoEstadoProductos;
    
    
    
    //========================================== CODIGO PARA VISTA INVENTARIO =============================================================
        
    public void limpiarText(){
        cmbCodigoProductoInventario.setValue("");
        txtCantidadInventario.setText("");
        txtProveedorInventario.setText("");
        txtProductoInventario.setText("");
        cmbNombreEstado.setValue("");

    }
    
    public void desactivarControlesInventario(){    
        btnEditarInventario.setDisable(false);
        btnEliminarInventario.setDisable(false);
    }
    
    public void activarControles(){    
        btnEditarInventario.setDisable(false);
        btnEliminarInventario.setDisable(false);
    }
    
        public void desactivarTextInventario(){
        cmbCodigoProductoInventario.setDisable(true);
        txtProveedorInventario.setEditable(false);
        txtProductoInventario.setEditable(false);
        cmbNombreEstado.setDisable(true);
                btnEditarInventario.setDisable(false);
        btnEliminarInventario.setDisable(false);

    }
    
    public void activarTextInventario(){
        cmbCodigoProductoInventario.setDisable(false);
        txtCantidadInventario.setEditable(true);
        cmbNombreEstado.setDisable(false);
    }
    
    
    @FXML
    private void cargarProductos(Event event) {
        iniciarInventario();
        llenarComboProducto();
        activarControles();
        activarTextInventario();
        animacion.animacion(anchor1, anchor2);
    }
    
    
     public ObservableList<InventarioProductos> getInventario(){
        ArrayList<InventarioProductos> lista = new ArrayList();
        ArrayList<String> comboCodigoFiltro = new ArrayList();
        String sql = "{call SpListarInventarioProductos()}";
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new InventarioProductos(
                            rs.getString("productoId"),
                            rs.getInt("inventarioProductoCant"),
                            rs.getString("proveedorNombre"),
                            rs.getString("productoDesc"),
                            rs.getString("estadoProductoDesc"),
                            rs.getDouble("precioCosto")
                ));
                comboCodigoFiltro.add(x, rs.getString("productoId"));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        listaCodigoFiltro = FXCollections.observableList(comboCodigoFiltro);
        
        return listaInventarioProductos = FXCollections.observableList(lista);
    }
     
     public void llenarComboProducto(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call SpListarProductos()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("productoId"));
                x++;
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
            
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL CARGAR DATOS CMB");
            noti.text("Error al cargar la base de datos");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
        listaCodigoProducto = FXCollections.observableList(lista);
        cmbCodigoProductoInventario.setItems(listaCodigoProducto);
        
    }
     
     
     public void cargarDatos(){
        tblInventario.setItems(getInventario());
        activarControles();
        activarTextInventario();
        colCodigoProductoInventario.setCellValueFactory(new PropertyValueFactory("productoId"));
        colCantidadInventario.setCellValueFactory(new PropertyValueFactory("inventarioProductoCant"));
        colProductoInventario.setCellValueFactory(new PropertyValueFactory("productoDesc"));
        colProveedorInventario.setCellValueFactory(new PropertyValueFactory("proveedorNombre"));
        colEstadoInventario.setCellValueFactory(new PropertyValueFactory("estadoProductoDesc"));
        colEstadoInventario.setCellValueFactory(new PropertyValueFactory("estadoProductoDesc"));
        limpiarText();
       
        llenarComboEstado();
        cmbNombreEstado.setValue("");
        new AutoCompleteComboBoxListener(cmbFiltroCodigo);
        new AutoCompleteComboBoxListener(cmbNombreEstado);
        activarControles(); 
       activarTextInventario();
    }
     
     
     
    @FXML
    private void seleccionarElementosProductos(MouseEvent event) {
        int index = tblInventario.getSelectionModel().getSelectedIndex();
        try{
            cmbCodigoProductoInventario.setValue(colCodigoProductoInventario.getCellData(index));
            txtCantidadInventario.setText(colCantidadInventario.getCellData(index).toString());
            txtProveedorInventario.setText(colProveedorInventario.getCellData(index));
            txtProductoInventario.setText(colProductoInventario.getCellData(index));
            cmbNombreEstado.setValue(colEstadoInventario.getCellData(index));

            codigoProducto = colCodigoProductoInventario.getCellData(index);
            cmbNombreEstado.setDisable(false);
            
            activarControles();
            activarTextInventario();
        }catch(Exception ex){
             ex.printStackTrace();
        }
    }
    
    public void iniciarInventario(){
       animacion.animacion(anchor1, anchor2);
        Tooltip toolInicio = new Tooltip("Volver a Inicio");
        Tooltip.install(btnInicio, toolInicio);
        
        Tooltip toolProveedores = new Tooltip("Abrir Proveedores");
        Tooltip.install(btnProveedores, toolProveedores);
        
        Tooltip toolProductos = new Tooltip("Abrir Proveedores");
        Tooltip.install(btnProveedores, toolProductos);
        
        cargarDatos();
        
        tipoOperacionInventario = Operacion.CANCELAR;
        accionInventario();
    }
        
    public void llenarComboEstado(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call SpListarEstadoProductos()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("estadoProductoDesc"));
                x++;
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
            
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL CARGAR DATOS CMB");
            noti.text("Error al cargar la base de datos");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }
        listaEstadoInventario = FXCollections.observableList(lista);
        cmbNombreEstado.setItems(listaEstadoInventario);
    }
    
    
     public void accionInventario(){
        switch(tipoOperacionInventario){
            case AGREGAR:
                tipoOperacionInventario = Operacion.GUARDAR;
                cmbFiltroCodigo.setDisable(true);
                cancelar = Operacion.CANCELAR;
                desactivarControlesInventario();
                btnAgregarInventario.setText("GUARDAR");
                btnEliminarInventario.setText("CANCELAR");
                btnBuscarInventario.setDisable(true);
                btnEliminarInventario.setDisable(false);
                
                activarTextInventario();
                limpiarText();
                break;
            case CANCELAR:
                tipoOperacionInventario = Operacion.NINGUNO;
                desactivarControlesInventario();
                desactivarTextInventario();
                btnBuscarInventario.setDisable(false);
                btnAgregarInventario.setText("AGREGAR");
                btnEliminarInventario.setText("ELIMINAR");
                limpiarText();
                cmbFiltroCodigo.setDisable(false);
                break;
        }
    }
     
     
    public void accion(String sql){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        PreparedStatement ps;
        ResultSet rs;
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        switch(tipoOperacionInventario){
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
                        tipoOperacionInventario = Operacion.CANCELAR;
                        accionInventario();
                        
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
                        tipoOperacionInventario = Operacion.CANCELAR;
                        accionInventario();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA AGREGADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionInventario = Operacion.CANCELAR;
                    accionInventario();
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
                        tipoOperacionInventario = Operacion.CANCELAR;
                        accionInventario();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        
                        
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ELIMINAR");
                        noti.text("HA OCURRIDO UN ERROR AL ELIMINAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionInventario = Operacion.CANCELAR;
                        accionInventario();
                    }
                }else{
                     noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ELIMINADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionInventario = Operacion.CANCELAR;
                    accionInventario();
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
                        tipoOperacionInventario = Operacion.CANCELAR;
                        accionInventario();
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
                        tipoOperacionInventario = Operacion.CANCELAR;
                        accionInventario();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ACTUALIZAR EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionInventario = Operacion.CANCELAR;
                    accionInventario();
                }
                break;
            case BUSCAR:
                 try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    int codigo=0;
                    while(rs.next()){
                        cmbCodigoProductoInventario.setValue(rs.getString("productoId"));
                        txtCantidadInventario.setText(rs.getString("inventarioProductoCant"));
                        txtProveedorInventario.setText(rs.getString("proveedorNombre"));
                        txtProductoInventario.setText(rs.getString("productoDesc"));
                        cmbNombreEstado.setValue(rs.getString("estadoProductoDesc"));

                        codigoProducto = rs.getString("productoId");
                        
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tblInventario.getItems().size(); i++){
                            if(colCodigoProductoInventario.getCellData(i) == codigoProducto){
                                tblInventario.getSelectionModel().select(i);
                                break;
                            }
                        }
                        activarControles();
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SU OPERACIÓN SE HA REALIZADO CON EXITO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        cmbNombreEstado.setDisable(false);
                        activarTextInventario();
                        
                    }else{
                         
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL BUSCAR");
                        noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionInventario = Operacion.CANCELAR;
                        accionInventario();
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
                    tipoOperacionInventario = Operacion.CANCELAR;
                    accionInventario();
                }
                break;
                 case SUMAR:
                alert.setTitle("SUMAR PRODUCTO");
                alert.setHeaderText("SUMAR PRODUCTO");
                alert.setContentText("¿Está seguro que desea sumar esta cantidad?");
                
                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
                
                Optional<ButtonType> resultSuma = alert.showAndWait();
                if(resultSuma.get() == buttonTypeSi ){
                    try {
                        ps = Conexion.getIntance().getConexion().prepareCall(sql);
                        ps.execute();
                        
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SE HA ACTUALIZADO LA CANTIDAD EXITOSAMENTE");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionInventario = Operacion.CANCELAR;
                        accionInventario();
                        
                        cargarDatos();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL SUMAR");
                        noti.text("HA OCURRIDO UN ERROR AL SUMAR");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionInventario = Operacion.CANCELAR;
                        accionInventario();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA AGREGADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionInventario = Operacion.CANCELAR;
                    accionInventario();
                }
                break;
        }
    }
    
    
  
    
    public int buscarCodigoEstado(String descripcionEstado){    
        try{
            PreparedStatement sp = Conexion.getIntance().getConexion().prepareCall("{call SpBuscarCodigoEstado(?)}");
            sp.setString(1, descripcionEstado);
            ResultSet resultado = sp.executeQuery(); 
            
            while(resultado.next()){
            buscarCodigoEstado = resultado.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return buscarCodigoEstado;
    }

    
    @FXML
    public void buscarProducto(){
        if(cmbCodigoProductoInventario.getValue()!= ""){
                try{
                     PreparedStatement sp = Conexion.getIntance().getConexion().prepareCall("{call SpBuscarProductos(?)}");
                     sp.setString(1, cmbCodigoProductoInventario.getValue());
                     ResultSet resultado = sp.executeQuery(); 
                        while(resultado.next()){
                            txtProductoInventario.setText(resultado.getString("productoDesc"));
                            txtProveedorInventario.setText(resultado.getString("proveedorNombre"));
                            btnAgregarInventario.setDisable(false);
                        }  
                }catch(Exception e){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                    btnAgregarInventario.setDisable(true);
                }
            }
    }

    
    @FXML
    private void comboFiltro(ActionEvent event) {
        btnBuscarInventario.setDisable(false);
        ArrayList<String> lista = new ArrayList();
        String sql ="{call SpListarInventarioProductos()}";
        int x=0;
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                 if(cmbFiltroCodigo.getValue().equals("CÓDIGO")){
                     lista.add(x, rs.getString("productoId"));
                     
                }else if(cmbFiltroCodigo.getValue().equals("NOMBRE")){
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
    
    public void buscar(){
      if(cmbBuscar.getValue().equals("")){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
        }else{
            if(cmbFiltroCodigo.getValue().equals("CÓDIGO")){
                tipoOperacionInventario = Operacion.BUSCAR;
                    String sql = "{ call SpBuscarInventarioProductos('"+cmbBuscar.getValue()+"')}";
                    accion(sql);
            }else if(cmbFiltroCodigo.getValue().equals("NOMBRE")){
                    tipoOperacionInventario = Operacion.BUSCAR;
                    String sql = "{ call SpBuscarInventarioProductosNombre('"+cmbBuscar.getValue()+"')}";
                    accion(sql);
            }
        }  
    }
    
    @FXML
    private void cmbBuscar(ActionEvent event) {
        buscar();
    }
    
    @FXML
    private void btnBuscar(MouseEvent event) {
        buscar();
    }
    
        @FXML
    private void atajosInventario(KeyEvent event) {
         if(cmbBuscar.isFocused()){
            if(event.getCode() == KeyCode.ENTER){
                buscar();
            }
        }
    }
    
    public void cargarCombo(){
        ArrayList<String>lista = new ArrayList();
        
        lista.add(0,"CÓDIGO");
        lista.add(1,"NOMBRE");
        
        listaFiltro = FXCollections.observableList(lista);
        
        cmbFiltroCodigo.setItems(listaFiltro);
    }
    
    //=========================================================================================================================================
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         validar.validarView(menu.prefs.get("dark", "root"), anchor);
        iniciarInventario();
        cmbCodigoProductoInventario.setValue("");
        cmbNombreEstado.setValue("");
        cmbCodigoProductoInventario.setValue("");
        llenarComboProducto();
        cargarCombo();
        new AutoCompleteComboBoxListener(cmbBuscar);
        new AutoCompleteComboBoxListener(cmbFiltroCodigo);
        new AutoCompleteComboBoxListener(cmbCodigoProductoInventario);
    }    

    @FXML
    private void regresar(MouseEvent event) throws IOException {
         String menu = "org/moduloFacturacion/view/menuPrincipal.fxml";
        cambioScene.Cambio(menu,(Stage) anchor.getScene().getWindow());
    }
    
    
        @FXML
    private void validarCodigoInventario(KeyEvent event) {
            System.out.println("hola");
    }
      @FXML
    private void validarCantidadProducto(KeyEvent event) {
         char letra = event.getCharacter().charAt(0);
        
        if(!Character.isDigit(letra)){
            if(!Character.isWhitespace(letra)){
                event.consume();
            }else{
                if(Character.isSpaceChar(letra)){
                    event.consume();
                }
            }
           
        }
    }
    
       
    @FXML
    private void validarCodigoEstado(KeyEvent event) {
          char letra = event.getCharacter().charAt(0);
        
        if(!Character.isDigit(letra)){
            if(!Character.isWhitespace(letra)){
                event.consume();
            }else{
                if(Character.isSpaceChar(letra)){
                    event.consume();
                }
            }
           
        }
    }
    
}


