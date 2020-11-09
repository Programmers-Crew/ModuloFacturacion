
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
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.moduloFacturacion.bean.AutoCompleteComboBoxListener;
import org.moduloFacturacion.bean.CambioScene;
import org.moduloFacturacion.bean.Proveedores;
import org.moduloFacturacion.bean.ValidarStyle;
import org.moduloFacturacion.db.Conexion;


public class ProveedoresViewController implements Initializable {
    CambioScene cambioScene = new CambioScene();
    ObservableList<Proveedores> listaProveedores;
    ObservableList<String> listaCodigoProveedores;
    Image imgError = new Image("org/moduloFacturacion/img/error.png");
    Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");
    String codigo;

    
     MenuPrincipalContoller menu = new MenuPrincipalContoller();
    ValidarStyle validar = new ValidarStyle();
    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO};
    public Operacion tipoOperacionProveedores= Operacion.NINGUNO;
    public Operacion cancelar = Operacion.NINGUNO;
    
    
    @FXML
    private AnchorPane anchor;
    @FXML
    private JFXTextField txtCodigoProveedores;
    @FXML
    private JFXTextField txtNombreProveedores;
    @FXML
    private JFXTextField txtTelefonoProveedores;
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private TableView<Proveedores> tblProveedores;
    @FXML
    private TableColumn<Proveedores, String> colCodigoProveedores;
    @FXML
    private TableColumn<Proveedores, String> colNombreProveedores;
    @FXML
    private TableColumn<Proveedores, String> colTellefonoProveedores;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private ComboBox<String> cmbCodigoProveedores;

    
     //EVENTOS DE LA VISTA DE PROVEEDORES
    public void limpiarTextProveedores(){
          txtCodigoProveedores.setText("");
          txtNombreProveedores.setText("");
          txtTelefonoProveedores.setText("");
    }
    
    public void desactivarControlesProveedores(){    
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
    }
    
    public void activarControlesProveedores(){        
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
    }
    
    public void desactivarTextProveedores(){
        txtNombreProveedores.setEditable(false);
        
    }
    
    public void activarTextProveedores(){
        txtNombreProveedores.setEditable(true);
    }
    
    
        public ObservableList<Proveedores> getProveedores(){
        ArrayList<Proveedores> lista = new ArrayList();
        ArrayList<String> listaCodigo = new ArrayList();
        String sql= "{call SpListarProveedores()}";
            int x =0;
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new Proveedores(
                              rs.getString("proveedorId"),
                              rs.getString("proveedorNombre"),
                              rs.getString("proveedorTelefono")
                ));
                
                listaCodigo.add(x, rs.getString("proveedorId"));
                x++;
            }
        }catch(SQLException ex){
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
        listaCodigoProveedores = FXCollections.observableList(listaCodigo);
        cmbCodigoProveedores.setItems(listaCodigoProveedores);
        return listaProveedores = FXCollections.observableList(lista);
    }
    
        
    public void cargarDatosProveedores(){
       tblProveedores.setItems(getProveedores());
       colCodigoProveedores.setCellValueFactory(new PropertyValueFactory("proveedorId"));
       colNombreProveedores.setCellValueFactory(new PropertyValueFactory("proveedorNombre"));
       colTellefonoProveedores.setCellValueFactory(new PropertyValueFactory("proveedorTelefono"));
       limpiarTextProveedores();
        new AutoCompleteComboBoxListener(cmbCodigoProveedores);
        desactivarControlesProveedores();
    }
    
    
    @FXML
    private void seleccionarElementos(MouseEvent event) {
        int index = tblProveedores.getSelectionModel().getSelectedIndex();
        try{
            txtCodigoProveedores.setText(colCodigoProveedores.getCellData(index).toString());
            txtNombreProveedores.setText(colNombreProveedores.getCellData(index));
            txtTelefonoProveedores.setText(colTellefonoProveedores.getCellData(index));
            btnEliminar.setDisable(false);
            btnEditar.setDisable(false);
            
            codigo = colCodigoProveedores.getCellData(index);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void accionProveedores(){
        switch(tipoOperacionProveedores){
            case AGREGAR:
                tipoOperacionProveedores = Operacion.GUARDAR;
                cancelar = Operacion.CANCELAR;
                desactivarControlesProveedores();
                btnAgregar.setText("GUARDAR");
                btnEliminar.setText("CANCELAR");
                btnEliminar.setDisable(false);
                activarTextProveedores();
                cmbCodigoProveedores.setDisable(true);
                btnBuscar.setDisable(true);
                limpiarTextProveedores();
                break;
            case CANCELAR:
                tipoOperacionProveedores = Operacion.NINGUNO;
                desactivarControlesProveedores();
                desactivarTextProveedores();
                btnAgregar.setText("AGREGAR");
                btnEliminar.setText("ELIMINAR");
                limpiarTextProveedores();
                cmbCodigoProveedores.setDisable(false);
                btnBuscar.setDisable(false);
                cancelar = Operacion.NINGUNO;
                break;
        }
    }
    
    
    public void accionProveedores(String sql){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        PreparedStatement ps;
        ResultSet rs;
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        switch(tipoOperacionProveedores){
            
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
                        tipoOperacionProveedores = Operacion.CANCELAR;
                        accionProveedores();
                        cargarDatosProveedores();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL AGREGAR");
                        noti.text("HA OCURRIDO UN ERROR AL GUARDAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionProveedores = Operacion.CANCELAR;
                        accionProveedores();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA AGREGADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionProveedores = Operacion.CANCELAR;
                    accionProveedores();
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
                        cargarDatosProveedores();
                        tipoOperacionProveedores = Operacion.CANCELAR;
                        accionProveedores();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        
                        
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ELIMINAR");
                        noti.text("HA OCURRIDO UN ERROR AL ELIMINAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionProveedores = Operacion.CANCELAR;
                        accionProveedores();
                    }
                }else{
                     noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ELIMINADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionProveedores = Operacion.CANCELAR;
                    accionProveedores();
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
                        tipoOperacionProveedores = Operacion.CANCELAR;
                        accionProveedores();
                        cargarDatosProveedores();
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ACTUALIZAR");
                        noti.text("HA OCURRIDO UN ERROR AL ACTUALIZAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionProveedores = Operacion.CANCELAR;
                        accionProveedores();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ACTUALIZAR EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionProveedores = Operacion.CANCELAR;
                    accionProveedores();
                }
                break;
            case BUSCAR:
                 try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    
                    while(rs.next()){
                        txtCodigoProveedores.setText(rs.getString("proveedorId"));
                        txtNombreProveedores.setText(rs.getString("proveedorNombre"));
                        txtTelefonoProveedores.setText(rs.getString("proveedorTelefono"));
                        codigo = rs.getString("proveedorId");
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tblProveedores.getItems().size(); i++){
                            if(colCodigoProveedores.getCellData(i) == codigo){
                                tblProveedores.getSelectionModel().select(i);
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
                        btnEditar.setDisable(false);
                        btnEliminar.setDisable(false);
                        activarTextProveedores();
                    }else{
                         
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL BUSCAR");
                        noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionProveedores = Operacion.CANCELAR;
                        accionProveedores();
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
                    tipoOperacionProveedores = Operacion.CANCELAR;
                    accionProveedores();
                }
                break;
        }
    }
    
    
    @FXML
    private void btnAgregar(MouseEvent event) {
        if(tipoOperacionProveedores == Operacion.GUARDAR){
            if(txtCodigoProveedores.getText().isEmpty() || txtNombreProveedores.getText().isEmpty() || txtTelefonoProveedores.getText().isEmpty()){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("HAY CAMPOS VACÍOS");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();   
                noti.show();
            }else{
                if(txtNombreProveedores.getText().length()<50){
                    Proveedores nuevoProveedor = new Proveedores();
                    nuevoProveedor.setProveedorId(txtCodigoProveedores.getText());
                    nuevoProveedor.setProveedorNombre(txtNombreProveedores.getText());
                    nuevoProveedor.setProveedorTelefono(txtTelefonoProveedores.getText());                    
                    String sql = "{call SpAgregarProveedores('"+nuevoProveedor.getProveedorId()+"','"+nuevoProveedor.getProveedorNombre()+"','"+nuevoProveedor.getProveedorTelefono()+"')}";
                    accionProveedores(sql);
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
             tipoOperacionProveedores = Operacion.AGREGAR;
                accionProveedores();
        }
    }
    
    
    @FXML
    private void btnEditar(MouseEvent event) {
        Proveedores nuevoProveedor = new Proveedores();
        nuevoProveedor.setProveedorId(txtCodigoProveedores.getText());
        nuevoProveedor.setProveedorNombre(txtNombreProveedores.getText());
        nuevoProveedor.setProveedorTelefono(txtTelefonoProveedores.getText());
        
        tipoOperacionProveedores = Operacion.ACTUALIZAR;
        String sql = "{call SpActualizarProveedor('"+codigo+"','"+nuevoProveedor.getProveedorId()+"','"+nuevoProveedor.getProveedorNombre()+"','"+nuevoProveedor.getProveedorTelefono()+"')}";
        accionProveedores(sql);
    }

    
    @FXML
    private void btnEliminar(MouseEvent event) {
        if(tipoOperacionProveedores == Operacion.GUARDAR){
            tipoOperacionProveedores = Operacion.CANCELAR;
            accionProveedores();
        }else{
            String sql = "{call SpEliminarProveedor('"+codigo+"')}";
            tipoOperacionProveedores = Operacion.ELIMINAR;
            accionProveedores(sql);
        }
    }

    
     public void buscar(){
        if(cmbCodigoProveedores.getValue().equals("")){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("El CAMPO DE CÓDIGO ESTA VACÍO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }else{
            tipoOperacionProveedores = Operacion.BUSCAR;
            String sql = "{ call SpBuscarProveedores('"+cmbCodigoProveedores.getValue()+"')}";
            accionProveedores(sql);
        }
    }
    
    @FXML
    private void atajosProveedores(KeyEvent event) {
        if(cmbCodigoProveedores.isFocused()){
            if(event.getCode() == KeyCode.ENTER){
                buscar();
            }
        }
    }
     
    @FXML
    private void btnBuscar(MouseEvent event) {
        buscar();
    }
    
    
    @FXML
    private void codigoBuscadoProveedores(MouseEvent event) {
        limpiarTextProveedores();
        desactivarControlesProveedores();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatosProveedores();
        validar.validarView(menu.prefs.get("dark", "root"), anchor);
    }    

    @FXML
    private void regresar(MouseEvent event) throws IOException {
         String menu = "org/moduloFacturacion/view/menuPrincipal.fxml";
        cambioScene.Cambio(menu,(Stage) anchor.getScene().getWindow());
    }
    @FXML

    private void validarCodigo(KeyEvent event) {
        if(tipoOperacionProveedores == Operacion.GUARDAR){
            
                if(txtCodigoProveedores.getText().matches(".*[a-z].*")||txtCodigoProveedores.getText().matches(".*[A-Z].*")){
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
                    btnAgregar.setDisable(false);
                }
              
        }else{
            
                if(txtCodigoProveedores.getText().matches(".*[a-z].*") || txtCodigoProveedores.getText().matches(".*[A-Z].*")){
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

    @FXML
    private void validarTelefono(KeyEvent event) {
        if(tipoOperacionProveedores == Operacion.GUARDAR){
            
                if(txtTelefonoProveedores.getText().matches(".*[a-z].*") || txtTelefonoProveedores.getText().matches(".*[A-Z].*")){
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
                    if(txtTelefonoProveedores.getText().length() >8){
                        btnAgregar.setDisable(true);
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR");
                        noti.text("EL CAMPO DE TELÉFONO NO DEBE SER MAYOR A 8 DÍGITOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();   
                        noti.show();
                    }else{
                        btnAgregar.setDisable(false);
                    }
                    
                }
              
        }else{
            
                if(txtTelefonoProveedores.getText().matches(".*[a-z].*") || txtTelefonoProveedores.getText().matches(".*[A-Z].*")){
                    btnEditar.setDisable(true);
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE TELÉFONO NO PUEDE LLEVAR LETRAS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
                }else{
                    if(txtTelefonoProveedores.getText().length() >8){
                        btnEditar.setDisable(true);
                        Notifications noti = Notifications.create();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR");
                        noti.text("EL CAMPO DE TELÉFONO NO DEBE SER MAYOR A 8 DÍGITOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();   
                        noti.show();
                    }else{
                        btnEditar.setDisable(false);
                    }
                    
                    
                }
            
        }
    }

    
}
