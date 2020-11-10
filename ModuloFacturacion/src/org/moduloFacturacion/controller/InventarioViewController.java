package org.moduloFacturacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
    private void validarCantidadProducto(ActionEvent event) {
    }

    @FXML
    private void validarCodigoEstado(ActionEvent event) {
    }

    
    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO};
    public Operacion cancelar = Operacion.NINGUNO;
    
    // Variables para Inventario
    public Operacion tipoOperacionInventario= Operacion.NINGUNO;

    ObservableList<InventarioProductos> listaInventarioProductos;
    ObservableList<String> listaEstadoInventario;
    ObservableList<String> listaCodigoProducto;
    ObservableList<String> listaCodigoFiltro;

    int buscarCodigoEstado = 0;
    int codigoProducto = 0;

    //Variables para Estado
    public Operacion tipoOperacionEstado= Operacion.NINGUNO; 
   
    ObservableList<EstadoProductos> listaEstadoProductos;
    ObservableList<String> listaCodigoEstadoProductos;
    int codigoEstado = 0;
    
    //Propiedades Inventario
    @FXML
    private AnchorPane anchor;
    @FXML
    private JFXTextField txtCantidadInventario;
    @FXML
    private JFXButton btnAgregarInventario;
    @FXML
    private JFXButton btnEditarInventario;
    @FXML
    private JFXButton btnEliminarInventario;
    @FXML
    private ComboBox<String> cmbNombreEstado;
    @FXML
    private JFXTextField txtProveedorInventario;
    @FXML
    private JFXTextField txtProductoInventario;
    @FXML
    private ComboBox<String> cmbCodigoProductoInventario;
    @FXML
    private TableView<InventarioProductos> tblInventario;
    @FXML
    private TableColumn<InventarioProductos, Integer> colCodigoProductoInventario;
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
    private TableColumn<EstadoProductos, Integer> colCodigoEstadoCodigo;
    @FXML
    private TableColumn<EstadoProductos, String> colDescEstadoProductos;
    @FXML
    private JFXButton btnBuscarEstadoProductos;
    @FXML
    private JFXComboBox<String> cmbCodigoEstadoProductos;
    
    
    //========================================== CODIGO PARA VISTA INVENTARIO =============================================================
        
    public void limpiarText(){
        cmbCodigoProductoInventario.setValue("");
        txtCantidadInventario.setText("");
        txtProveedorInventario.setText("");
        txtProductoInventario.setText("");
        cmbNombreEstado.setValue("");
    }
    
    public void desactivarControlesInventario(){    
        btnEditarInventario.setDisable(true);
        btnEliminarInventario.setDisable(true);
    }
    
    public void activarControles(){    
        btnEditarInventario.setDisable(false);
        btnEliminarInventario.setDisable(false);
    }
    
        public void desactivarTextInventario(){
        cmbCodigoProductoInventario.setDisable(true);
        txtCantidadInventario.setEditable(false);
        txtProveedorInventario.setEditable(false);
        txtProductoInventario.setEditable(false);
        cmbNombreEstado.setDisable(true);

    }
    
    public void activarTextInventario(){
        cmbCodigoProductoInventario.setDisable(false);
        txtCantidadInventario.setEditable(true);
        txtProveedorInventario.setEditable(true);
        txtProductoInventario.setEditable(true);
        cmbNombreEstado.setDisable(false);
    }
    
    
    @FXML
    private void cargarProductos(Event event) {
        iniciarInventario();
        llenarComboProducto();
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
                            rs.getInt("productoId"),
                            rs.getInt("inventarioProductoCant"),
                            rs.getString("productoDesc"),
                            rs.getString("estadoProductoDesc"),
                            rs.getString("proveedorNombre")
                ));
                comboCodigoFiltro.add(x, rs.getString("productoId"));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        listaCodigoFiltro = FXCollections.observableList(comboCodigoFiltro);
        cmbFiltroCodigo.setItems(listaCodigoFiltro);
        
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
        colCodigoProductoInventario.setCellValueFactory(new PropertyValueFactory("productoId"));
        colCantidadInventario.setCellValueFactory(new PropertyValueFactory("inventarioProductoCant"));
        colProductoInventario.setCellValueFactory(new PropertyValueFactory("productoDesc"));
        colProveedorInventario.setCellValueFactory(new PropertyValueFactory("proveedorNombre"));
        colEstadoInventario.setCellValueFactory(new PropertyValueFactory("estadoProductoDesc"));
        limpiarText();
        desactivarControlesInventario();
        desactivarTextInventario();
        llenarComboEstado();
        cmbFiltroCodigo.setValue("");
        cmbNombreEstado.setValue("");
        new AutoCompleteComboBoxListener(cmbFiltroCodigo);
        new AutoCompleteComboBoxListener(cmbNombreEstado);
    }
     
     
     
    @FXML
    private void seleccionarElementosProductos(MouseEvent event) {
        int index = tblInventario.getSelectionModel().getSelectedIndex();
        try{
            cmbCodigoProductoInventario.setValue(colCodigoProductoInventario.getCellData(index).toString());
            txtCantidadInventario.setText(colCantidadInventario.getCellData(index).toString());
            txtProveedorInventario.setText(colProveedorInventario.getCellData(index));
            txtProductoInventario.setText(colProductoInventario.getCellData(index));
            cmbNombreEstado.setValue(colEstadoInventario.getCellData(index));

            codigoProducto = colCodigoProductoInventario.getCellData(index);
            activarControles();
            activarTextInventario();
            cmbNombreEstado.setDisable(false);
            
        }catch(Exception ex){
            ex.printStackTrace();
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR AL CARGAR DATOS");
            noti.text("Error al cargar la base de datos");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
           
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

                        codigoProducto = rs.getInt("productoId");
                        
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
                        activarTextInventario();
                        cmbNombreEstado.setDisable(true);
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
        }
    }
    
    
    private void validarCantidadProducto(KeyEvent event) {
        if(tipoOperacionInventario == Operacion.GUARDAR){
            
                if(txtCantidadInventario.getText().matches(".*[a-z].*") || txtCantidadInventario.getText().matches(".*[A-Z].*")){
                    btnAgregarInventario.setDisable(true);
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE CÓDIGO NO PUEDE LLEVAR LETRAS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }else{
                    btnAgregarInventario.setDisable(false);
                }
              
        }else{
            
                if(txtCantidadInventario.getText().matches(".*[a-z].*") || txtCantidadInventario.getText().matches(".*[A-Z].*")){
                    btnEditarInventario.setDisable(true);
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE CÓDIGO NO PUEDE LLEVAR LETRAS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }else{
                    btnEditarInventario.setDisable(false);
                    
                }
            
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
    private void btnAgregar(MouseEvent event) {
        if(tipoOperacionInventario == Operacion.GUARDAR){
           if(cmbCodigoProductoInventario.getValue().equals("") || txtCantidadInventario.getText().isEmpty() || cmbNombreEstado.getValue().equals("")){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("HAY CAMPOS VACÍOS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
           }else{
                   InventarioProductos nuevoInventario = new InventarioProductos();
                   nuevoInventario.setProductoId(Integer.parseInt(cmbCodigoProductoInventario.getValue()));
                   nuevoInventario.setInventarioProductoCant(Integer.parseInt(txtCantidadInventario.getText()));
                   nuevoInventario.setEstadoProductoDesc(cmbNombreEstado.getValue());

                   String sql = "{call SpAgregarInventarioProductos('"+nuevoInventario.getInventarioProductoCant()+"','"+ nuevoInventario.getProductoId()+"','"+ buscarCodigoEstado(nuevoInventario.getEstadoProductoDesc()) +"')}";
                   tipoOperacionInventario = Operacion.GUARDAR;
                   accion(sql);                   
               }
                 }else{
                    tipoOperacionInventario = Operacion.AGREGAR;;
                    accionInventario();
                            }
    }
    
    @FXML
    public void buscarProducto(){
        if(cmbCodigoProductoInventario.getValue()!= ""){
                try{
                     PreparedStatement sp = Conexion.getIntance().getConexion().prepareCall("{call SpBuscarProductos(?)}");
                     sp.setInt(1, Integer.parseInt(cmbCodigoProductoInventario.getValue()));
                     ResultSet resultado = sp.executeQuery(); 
                        while(resultado.next()){
                            txtProductoInventario.setText(resultado.getString("productoDesc"));
                            txtProveedorInventario.setText(resultado.getString("proveedorNombre"));
                        }  
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
    }
    
    @FXML
    private void btnEliminar(MouseEvent event) {
         if(tipoOperacionInventario == Operacion.GUARDAR){
            tipoOperacionInventario = Operacion.CANCELAR;
            accionInventario();
        }else{
             
            String sql = "{call SpEliminarInventarioProductos('"+codigoProducto+"')}";
            tipoOperacionInventario = Operacion.ELIMINAR;
            accion(sql);
        }
    }
    
    @FXML
    private void btnEditar(MouseEvent event) {
       InventarioProductos nuevoInventario = new InventarioProductos();
       nuevoInventario.setInventarioProductoCant(Integer.parseInt(txtCantidadInventario.getText()));
       nuevoInventario.setEstadoProductoDesc(cmbNombreEstado.getValue());

       String sql = "{call SpActualizarInventarioProductos('"+codigoProducto+"','"+nuevoInventario.getInventarioProductoCant()+"','"+buscarCodigoEstado(nuevoInventario.getEstadoProductoDesc())+"')}";
       tipoOperacionInventario = Operacion.ACTUALIZAR;
       accion(sql);                   
    }
    
    public void buscar(){
      if(cmbFiltroCodigo.getValue().equals("")){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
        }else{
            tipoOperacionInventario = Operacion.BUSCAR;

            String sql = "{call SpBuscarInventarioProductos('"+cmbFiltroCodigo.getValue()+"')}";
            tipoOperacionInventario = Operacion.BUSCAR;
            accion(sql);
        }  
    }
    
    @FXML
    private void btnBuscar(MouseEvent event) {
        buscar();
    }
    
        @FXML
    private void atajosInventario(KeyEvent event) {
         if(cmbFiltroCodigo.isFocused()){
            if(event.getCode() == KeyCode.ENTER){
                buscar();
            }
        }
    }
    
    //========================================== CODIGO PARA VISTA ESTADO PRODUCTO ========================================================

    public void limpiarTextEstado(){
        txtCodigoEstadoProducto.setText("");
        txtDescEstadoProducto.setText("");
    }
    
    public void desactivarControlesEstado(){    
        btnEditarEstadoProductos.setDisable(true);
        btnEliminarEstadoProductos.setDisable(true);
    }
    
    
    public void desactivarTextEstado(){
        txtCodigoEstadoProducto.setEditable(false);
        txtDescEstadoProducto.setEditable(false);
    }
    
    public void activarTextEstado(){
        txtCodigoEstadoProducto.setEditable(true);
        txtDescEstadoProducto.setEditable(true);
    }
    
    
     public ObservableList<EstadoProductos> getEstado(){
        ArrayList<EstadoProductos> lista = new ArrayList();
        ArrayList<String> comboCodigoFiltro = new ArrayList();
        String sql = "{call SpListarEstadoProductos()}";
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new EstadoProductos(
                            rs.getInt("estadoProductoId"),
                            rs.getString("estadoProductoDesc")
                ));
                comboCodigoFiltro.add(x, rs.getString("estadoProductoId"));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        listaCodigoEstadoProductos = FXCollections.observableList(comboCodigoFiltro);
        cmbCodigoEstadoProductos.setItems(listaCodigoEstadoProductos);
        
        return listaEstadoProductos = FXCollections.observableList(lista);
    }
    
   
    public void cargarDatosEstado(){
        tblEstadoProductos.setItems(getEstado());
        colCodigoEstadoCodigo.setCellValueFactory(new PropertyValueFactory("estadoProductoId"));
        colDescEstadoProductos.setCellValueFactory(new PropertyValueFactory("estadoProductoDesc"));
        limpiarTextEstado();
        new AutoCompleteComboBoxListener(cmbCodigoEstadoProductos);
        desactivarControlesEstado();
        desactivarTextEstado();
        tipoOperacionEstado = Operacion.CANCELAR;
        accionEstado();
    }
    
    
    @FXML
    private void seleccionarElementos(MouseEvent event) {
        int index = tblEstadoProductos.getSelectionModel().getSelectedIndex();
        try{
            txtCodigoEstadoProducto.setText(colCodigoEstadoCodigo.getCellData(index).toString());
            txtDescEstadoProducto.setText(colDescEstadoProductos.getCellData(index));
            btnEliminarEstadoProductos.setDisable(false);
            btnEditarEstadoProductos.setDisable(false);
            
            codigoEstado = colCodigoEstadoCodigo.getCellData(index);
            activarTextEstado();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void accionEstado(){
        switch(tipoOperacionEstado){
            case AGREGAR:
                tipoOperacionEstado = Operacion.GUARDAR;
                cancelar = Operacion.CANCELAR;
                desactivarControlesEstado();
                btnAgregarEstadoProductos.setText("GUARDAR");
                btnEliminarEstadoProductos.setText("CANCELAR");
                btnEliminarEstadoProductos.setDisable(false);
                activarTextEstado();
                cmbCodigoEstadoProductos.setDisable(true);
                btnBuscarEstadoProductos.setDisable(true);
                limpiarTextEstado();
                break;
            case CANCELAR:
                tipoOperacionEstado = Operacion.NINGUNO;
                desactivarControlesEstado();
                desactivarTextEstado();
                btnAgregarEstadoProductos.setText("AGREGAR");
                btnEliminarEstadoProductos.setText("ELIMINAR");
                limpiarTextEstado();
                cmbCodigoEstadoProductos.setDisable(false);
                btnBuscarEstadoProductos.setDisable(false);
                cancelar = Operacion.NINGUNO;
                break;
        }
    }
    
    
    public void accionEstado(String sql){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        PreparedStatement ps;
        ResultSet rs;
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        switch(tipoOperacionEstado){
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
                        tipoOperacionEstado = Operacion.CANCELAR;
                        accionEstado();
                        cargarDatosEstado();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL AGREGAR");
                        noti.text("HA OCURRIDO UN ERROR AL GUARDAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionEstado = Operacion.CANCELAR;
                        accionEstado();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA AGREGADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionEstado = Operacion.CANCELAR;
                    accionEstado();
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
                        cargarDatosEstado();
                        tipoOperacionEstado = Operacion.CANCELAR;
                        accionEstado();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        
                        
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ELIMINAR");
                        noti.text("HA OCURRIDO UN ERROR AL ELIMINAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionEstado = Operacion.CANCELAR;
                        accionEstado();
                    }
                }else{
                     noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ELIMINADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionEstado = Operacion.CANCELAR;
                    accionEstado();
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
                        tipoOperacionEstado = Operacion.CANCELAR;
                        accionEstado();
                        cargarDatosEstado();
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ACTUALIZAR");
                        noti.text("HA OCURRIDO UN ERROR AL ACTUALIZAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionEstado = Operacion.CANCELAR;
                        accionEstado();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ACTUALIZAR EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionEstado = Operacion.CANCELAR;
                    accionEstado();
                }
                break;
            case BUSCAR:
                 try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    
                    while(rs.next()){
                        txtCodigoEstadoProducto.setText(rs.getString("estadoProductoId"));
                        txtDescEstadoProducto.setText(rs.getString("estadoProductoDesc"));
                        codigoEstado = rs.getInt("estadoProductoId");
                        
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tblEstadoProductos.getItems().size(); i++){
                            if(colCodigoEstadoCodigo.getCellData(i) == codigoEstado){
                                tblEstadoProductos.getSelectionModel().select(i);
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
                        btnEditarEstadoProductos.setDisable(false);
                        btnEliminarEstadoProductos.setDisable(false);
                        activarTextEstado();
                    }else{
                         
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL BUSCAR");
                        noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionEstado = Operacion.CANCELAR;
                        accionEstado();
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
                    tipoOperacionEstado = Operacion.CANCELAR;
                    accionEstado();
                }
                break;
        }
    }
    
    
    @FXML
    private void btnAgregarEstado(MouseEvent event) {
        if(tipoOperacionEstado == Operacion.GUARDAR){
            if(txtCodigoEstadoProducto.getText().isEmpty() || txtDescEstadoProducto.getText().isEmpty()){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("HAY CAMPOS VACÍOS");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();   
                noti.show();
            
            }else{
                if(txtDescEstadoProducto.getText().length()<50){
                    EstadoProductos nuevaEstado = new EstadoProductos();
                    nuevaEstado.setEstadoProductoDesc(txtDescEstadoProducto.getText());
                    String sql = "{call SpAgregarEstadoProducto('"+nuevaEstado.getEstadoProductoDesc()+"')}";
                    accionEstado(sql);
                }else{
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("NOMBRE DE LA CATEGORÍA NO TIENEN UNA LONGITUD ADECUADA (DEBE SER MENOR DE 50 CARACTERES)");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }
            }
        
        }else{
             tipoOperacionEstado = Operacion.AGREGAR;
                accionEstado();
        }
    }
    
    
    @FXML
    private void btnEditarEstado(MouseEvent event) {
        EstadoProductos nuevaEstado = new EstadoProductos();
        nuevaEstado.setEstadoProductoId(Integer.parseInt(txtCodigoEstadoProducto.getText()));
        nuevaEstado.setEstadoProductoDesc(txtDescEstadoProducto.getText());
        
        tipoOperacionEstado = Operacion.ACTUALIZAR;
        String sql = "{call SpActualizarEstadoProducto('"+codigoEstado+"','"+nuevaEstado.getEstadoProductoDesc()+"')}";
        accionEstado(sql);
    }
    
    
     @FXML
    private void btnEliminarEstado(MouseEvent event) {
        if(tipoOperacionEstado == Operacion.GUARDAR){
            tipoOperacionEstado = Operacion.CANCELAR;
            accionEstado();
        }else{
            String sql = "{call SpEliminarEstadoProductos('"+codigoEstado+"')}";
            tipoOperacionEstado = Operacion.ELIMINAR;
            accionEstado(sql);
        }
    }
    
    
    public void buscarEstado(){
        if(cmbCodigoEstadoProductos.getValue().equals("")){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("El CAMPO DE CÓDIGO ESTA VACÍO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }else{
            tipoOperacionEstado = Operacion.BUSCAR;
            String sql = "{ call SpBuscarEstadoProductos('"+cmbCodigoEstadoProductos.getValue()+"')}";
            accionEstado(sql);
        }
    }
    
    
    @FXML
    private void btnBuscarEstado(MouseEvent event) {
        buscarEstado();
    }
    
    private void validarCodigoEstado(KeyEvent event) {
         if(tipoOperacionEstado == Operacion.GUARDAR){
            
                if(txtCodigoEstadoProducto.getText().matches(".*[a-z].*")){
                    btnAgregarEstadoProductos.setDisable(true);
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE CÓDIGO NO PUEDE LLEVAR LETRAS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }else{
                    btnAgregarEstadoProductos.setDisable(false);
                }
              
        }else{
            
                if(txtCodigoEstadoProducto.getText().matches(".*[a-z].*")){
                    btnEditarEstadoProductos.setDisable(true);
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE CÓDIGO NO PUEDE LLEVAR LETRAS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }else{
                    btnEditarEstadoProductos.setDisable(false);
                }
            
        }
    }
    
    
    @FXML
    private void cargarEstado(Event event) {
        cargarDatosEstado();
        animacion.animacion(anchor3, anchor4);
    }
    
    
    @FXML
    private void atajosEstado(KeyEvent event) {
        if(cmbCodigoEstadoProductos.isFocused()){
            if(event.getCode() == KeyCode.ENTER){
                buscarEstado();
            }
        }
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

                }    

    @FXML
    private void regresar(MouseEvent event) throws IOException {
         String menu = "org/moduloFacturacion/view/menuPrincipal.fxml";
        cambioScene.Cambio(menu,(Stage) anchor.getScene().getWindow());
    }
    
}


