package org.moduloFacturacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;



import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import org.moduloFacturacion.bean.AutoCompleteComboBoxListener;
import org.moduloFacturacion.bean.CambioScene;
import org.moduloFacturacion.bean.Usuario;
import org.moduloFacturacion.db.Conexion;


public class MenuPrincipalContoller implements Initializable {
    
    LoginViewController login = new LoginViewController();
    CambioScene cambioScene = new CambioScene();



    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO};
    public Operacion tipoOperacion= Operacion.NINGUNO;
    public Operacion cancelar = Operacion.NINGUNO;
    Image imgError= new Image("org/moduloFacturacion/img/error.png");
    Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");
    
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
    private AnchorPane cajaClientes;
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
    private TableColumn<Usuario, Integer> colCodigoUsuario;
    
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
                        rs.getInt("usuarioId"),
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelUsuario.setText("¡Bienvenido "+login.prefsUsuario.get("usuario","root")+"!");
        cmbCodigoUsuario.setValue("");
        if(login.prefsLogin.get("tipo","root").equals("empleado")){
            cajaInventario.setDisable(true);
            tabAjustes.setDisable(true);
            itemInventario.setDisable(true);
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
        
        
       //caja de clientes
       
       FadeTransition ftCliente = new FadeTransition();
       ftCliente.setFromValue(0);
       ftCliente.setToValue(1);
       ftCliente.setDuration(Duration.seconds(2));
       ftCliente.setNode(cajaClientes);
       ftCliente.setCycleCount(1);
       ftCliente.play();
       
       
       TranslateTransition ttCliente = new TranslateTransition();
       ttCliente.setFromY(-40);
       ttCliente.setToY(0);
       ttCliente.setDuration(Duration.seconds(2.0));
       ttCliente.setNode(cajaClientes);
       ttCliente.setCycleCount(1);
       ttCliente.play();
       
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
        
        
       //caja de clientes
       
         FadeTransition ftCliente = new FadeTransition();
       ftCliente.setFromValue(0);
       ftCliente.setToValue(1);
       ftCliente.setDuration(Duration.seconds(2));
       ftCliente.setNode(cajaClientes);
       ftCliente.setCycleCount(1);
       ftCliente.play();
       
       
       TranslateTransition ttCliente = new TranslateTransition();
       ttCliente.setFromY(-40);
       ttCliente.setToY(0);
       ttCliente.setDuration(Duration.seconds(2.0));
       ttCliente.setNode(cajaClientes);
       ttCliente.setCycleCount(1);
       ttCliente.play();
       
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
    
    public void inventario() throws IOException{
        String inventarioUrl = "org/moduloFacturacion/view/InventarioView.fxml";
        cambioScene.Cambio(inventarioUrl,(Stage) anchor.getScene().getWindow());
    }
        @FXML
    private void inventarioView(ActionEvent event) throws IOException {
        inventario();
    }
    
     @FXML
    private void inventarioAtajo(MouseEvent event) throws IOException {
        inventario();
    }
   
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
    
    public void clientes() throws IOException{
        String clienteUrl = "org/moduloFacturacion/view/ClienteView.fxml";
        cambioScene.Cambio(clienteUrl,(Stage) anchor.getScene().getWindow());
    }
    
    @FXML
    private void Clientesview(ActionEvent event) throws IOException {
        clientes();
    }
    
     @FXML
    private void ClientesAtajo(MouseEvent event) throws IOException {
        clientes();
    }
    
    public void productos() throws IOException{
        String inventarioUrl = "org/moduloFacturacion/view/ProductosView.fxml";
        cambioScene.Cambio(inventarioUrl,(Stage) anchor.getScene().getWindow());
    }
    
    @FXML
    private void productosView(ActionEvent event) throws IOException {
        productos();
    }

    public void proveedores() throws IOException{
        String inventarioUrl = "org/moduloFacturacion/view/ProveedoresView.fxml";
        cambioScene.Cambio(inventarioUrl,(Stage) anchor.getScene().getWindow());
    }
    
    @FXML
    private void proveedoresView(ActionEvent event) throws IOException {
        proveedores();
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
               productos();
           }else{
               if(event.getCode() == KeyCode.F3){
                   inventario();
               }else{
                   if(event.getCode() == KeyCode.F4){
                       clientes();
                   }else{
                       if(event.getCode() == KeyCode.F5){
                           proveedores();
                       }
                   }
               }
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
                    int codigo=0;
                    while(rs.next()){
                        cmbCodigoUsuario.setValue(rs.getString("usuarioId"));
                        txtUsuario.setText(rs.getString("usuarioNombre"));
                        txtPassword.setText(rs.getString("usuarioPassword"));
                        cmbTipoUsuario.setValue(rs.getString("tipoUsuario"));
                        codigo = rs.getInt("usuarioId");
                        
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
        nuevoUsuario.setUsuarioId(Integer.parseInt(cmbCodigoUsuario.getValue()));
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
    
    
    


    


 }

