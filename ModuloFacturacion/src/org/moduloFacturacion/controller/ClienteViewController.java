
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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
import org.moduloFacturacion.bean.Cliente;
import org.moduloFacturacion.db.Conexion;

public class ClienteViewController implements Initializable {
    CambioScene cambioScene = new CambioScene();
    Image imgError = new Image("org/moduloFacturacion/img/error.png");
    Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");

  
   
    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO};
    public Operacion tipoOperacion= Operacion.NINGUNO;
    
    ObservableList<Cliente>listaCliente;
    ObservableList<String>listaFiltro;
    ObservableList<String>listaBuscar;
    int codigo=0;
    
    @FXML
    private AnchorPane anchor;
    @FXML
    private Pane paneInicio;
    @FXML
    private JFXTextField txtNitCliente;
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXTextField txtNombreCliente;
    @FXML
    private TableView<Cliente> tableCliente;
    @FXML
    private TableColumn<Cliente, Integer> colCodigoCliente;
    @FXML
    private TableColumn<Cliente, String> colNitCliente;
    @FXML
    private TableColumn<Cliente, String> colNombreCliente;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private ComboBox<String> cmbFiltroCombo;
    @FXML
    private ComboBox<String> cmbCodigoBuscar;
    
    
    public void limpiarText(){
        cmbCodigoBuscar.setValue("");
        txtNitCliente.setText("");
        txtNombreCliente.setText("");
        cmbFiltroCombo.setValue("");
        
    }
    public void desactivarControles(){
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
    }
    
    public void activarControles(){
        btnEditar.setDisable(false);
        btnEliminar.setDisable(false);
    }
    public void activarText(){
        txtNombreCliente.setEditable(true);
        txtNitCliente.setEditable(true);
        
    }
    
    public void desactivarText(){
        txtNombreCliente.setEditable(false);
        txtNitCliente.setEditable(false);
    }
    
    public ObservableList<Cliente> getCliente(){
        ArrayList<Cliente> lista = new ArrayList();
        String sql = "{call SpListarClientes()}";
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Cliente(
                        rs.getInt("clienteId"),
                        rs.getString("clienteNit"),
                        rs.getString("clienteNombre")
                ));
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return listaCliente = FXCollections.observableList(lista);
        
    }
    
    
    public void cargarDatos(){
       
        tableCliente.setItems(getCliente());
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory("clienteId"));
        colNitCliente.setCellValueFactory(new PropertyValueFactory("clienteNit"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory("clienteNombre"));
        limpiarText();
       
        
        desactivarControles();
        cmbCodigoBuscar.setDisable(true);
        btnBuscar.setDisable(true);
        new AutoCompleteComboBoxListener(cmbCodigoBuscar);
        cmbCodigoBuscar.setValue("");
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        ArrayList<String> lista = new ArrayList();
        cmbFiltroCombo.setValue("");
        lista.add(0,"CÓDIGO");
        lista.add(1,"NIT");
        listaFiltro = FXCollections.observableList(lista);
        cmbFiltroCombo.setItems(listaFiltro);
    }    

    @FXML
    private void regresar(MouseEvent event) throws IOException {
         String menu = "org/moduloFacturacion/view/menuPrincipal.fxml";
        cambioScene.Cambio(menu,(Stage) anchor.getScene().getWindow());
    }

    @FXML
    private void validarNit(KeyEvent event) {
        if(tipoOperacion == Operacion.GUARDAR){
            
                if(txtNitCliente.getText().matches(".*[a-z].*") || txtNitCliente.getText().matches(".*[A-Z].*")){
                    btnAgregar.setDisable(true);
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE CÓDIGO NO PUEDE LLEVAR LETRAS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }else{
                    if(txtNitCliente.getText().length() > 9){
                        btnAgregar.setDisable(true);
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR");
                        noti.text("EL CAMPO DE NIT NO DEBE SER MAYOR A 9 DÍGITOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();   
                        noti.show();
                    }else{
                        btnAgregar.setDisable(false);
                    }
                    
                }
              
        }else{
            
                if(txtNitCliente.getText().matches(".*[a-z].*") || txtNitCliente.getText().matches(".*[A-Z].*")){
                    btnEditar.setDisable(true);
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE CÓDIGO NO PUEDE LLEVAR LETRAS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }else{
                    btnEditar.setDisable(false);
                    
                }
            
        }
        
    }

    public void accion(){
        switch(tipoOperacion){
            case AGREGAR:
                tipoOperacion = Operacion.GUARDAR;
                
                desactivarControles();
                btnAgregar.setText("GUARDAR");
                btnEliminar.setText("CANCELAR");
                
                
                
                
                btnEliminar.setDisable(false);
                activarText();
                limpiarText();
                cmbCodigoBuscar.setDisable(true);
                btnBuscar.setDisable(true);
                break;
            case CANCELAR:
                tipoOperacion = Operacion.NINGUNO;
                desactivarControles();
                desactivarText();
                
                btnAgregar.setText("AGREGAR");
                btnEliminar.setText("ELIMINAR");
                limpiarText();
                cmbCodigoBuscar.setDisable(true);
                btnBuscar.setDisable(true);
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
                }
                break;
            case BUSCAR:
                 try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    
                    while(rs.next()){
                        txtNitCliente.setText(rs.getString("clienteNit"));
                        txtNombreCliente.setText(rs.getString("clienteNombre"));
                        
                        codigo = rs.getInt("clienteId");
                        
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tableCliente.getItems().size(); i++){
                            if(colCodigoCliente.getCellData(i) == codigo){
                                tableCliente.getSelectionModel().select(i);
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
                        activarText();
                        tipoOperacion = Operacion.NINGUNO;
                        
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
            if(txtNitCliente.getText().isEmpty() || txtNombreCliente.getText().isEmpty()){
                 Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("HAY CAMPOS VACÍOS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
            }else{
                if(txtNombreCliente.getText().length() > 25 || txtNitCliente.getText().length() > 9){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("CAMPOS FUERA DE RANGO, NOMBRE NO DEBE PASAR LOS 25 CARACTERES Y EL NIT NO DEBE PASAR LOS 9 CARACTERES");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }else{
                    Cliente nuevocliente = new Cliente();
                    nuevocliente.setClienteNit(txtNitCliente.getText());
                    nuevocliente.setClienteNombre(txtNombreCliente.getText());
                    String sql = "{call SpAgregarClientes('"+nuevocliente.getClienteNit()+"','"+nuevocliente.getClienteNombre()+"')}";
                    tipoOperacion = Operacion.GUARDAR;
                    accion(sql);
                }
            }
        }else{
            tipoOperacion = Operacion.AGREGAR;
            accion();
        }
    }

    @FXML
    private void btnEditar(MouseEvent event) {
        if(txtNitCliente.getText().isEmpty() || txtNombreCliente.getText().isEmpty()){
                 Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("HAY CAMPOS VACÍOS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
            }else{
                if(txtNombreCliente.getText().length() > 25 || txtNitCliente.getText().length() > 9){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("CAMPOS FUERA DE RANGO, NOMBRE NO DEBE PASAR LOS 25 CARACTERES Y EL NIT NO DEBE PASAR LOS 9 CARACTERES");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }else{
                    Cliente nuevocliente = new Cliente();
                    nuevocliente.setClienteNit(txtNitCliente.getText());
                    nuevocliente.setClienteNombre(txtNombreCliente.getText());
                    String sql = "{call SpActualizarClientes('"+codigo+"','"+nuevocliente.getClienteNit()+"','"+nuevocliente.getClienteNombre()+"')}";
                    tipoOperacion = Operacion.ACTUALIZAR;
                    accion(sql);
                }
            }
    }

    @FXML
    private void btnEliminar(MouseEvent event) {
        
        if(tipoOperacion == Operacion.GUARDAR){
            tipoOperacion = Operacion.CANCELAR;
            accion();
        }else{
             
            String sql = "{call SpEliminarClientes('"+codigo+"')}";
            tipoOperacion = Operacion.ELIMINAR;
            accion(sql);
        }
        
    }

    @FXML
    private void seleccionarElementos(MouseEvent event) {
        int index = tableCliente.getSelectionModel().getSelectedIndex();
        try{
            txtNitCliente.setText(colNitCliente.getCellData(index));
            txtNombreCliente.setText(colNombreCliente.getCellData(index));
            codigo = colCodigoCliente.getCellData(index);
            activarControles();
            activarText();
        }catch(Exception e){
        
        }
    }
    
     @FXML
    private void filtrobuscar(ActionEvent event) {
         System.out.println("hola esto es un filtro");
        cmbCodigoBuscar.setDisable(false);
        btnBuscar.setDisable(false);
        ArrayList<String> lista = new ArrayList();
        String sql = "{call SpListarClientes()}";
        if(cmbFiltroCombo.getValue().equals("CÓDIGO")){
            try{
                
                PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    lista.add(rs.getString("clienteId"));
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            if(cmbFiltroCombo.getValue().equals("NIT")){
                try{
                
                    PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    ResultSet rs = ps.executeQuery();

                    while(rs.next()){
                        lista.add(rs.getString("clienteNit"));
                    }
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
            
        }
        listaBuscar = FXCollections.observableList(lista);
        
        cmbCodigoBuscar.setItems(listaBuscar);
        new AutoCompleteComboBoxListener(cmbCodigoBuscar);
        
    }
    
    public void buscar(){
        String sql = "";
        tipoOperacion = Operacion.BUSCAR;
        if(cmbCodigoBuscar.getValue().equals("")){
            Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
        }else{
            if(cmbFiltroCombo.getValue().equals("CÓDIGO")){
                
                sql = "{call SpBuscarClientes('"+cmbCodigoBuscar.getValue()+"')}";

            }else{
                sql = "{call SpBuscarClientesNit('"+cmbCodigoBuscar.getValue()+"')}";
            }
            
            accion(sql);
        } 
    }
    
    @FXML
    private void btnBuscar(MouseEvent event) {
        
        
       buscar();
    }
      @FXML
    private void atajosCliente(KeyEvent event) {
        if(cmbCodigoBuscar.isFocused()){
            if(event.getCode() == KeyCode.ENTER){
                buscar();
            }
        }
    }

}
