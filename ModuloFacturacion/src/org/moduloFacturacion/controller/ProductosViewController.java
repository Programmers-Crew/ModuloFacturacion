
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
import org.moduloFacturacion.bean.AutoCompleteComboBoxListener;
import org.moduloFacturacion.bean.CambioScene;
import org.moduloFacturacion.bean.CategoriaProducto;
import org.moduloFacturacion.db.Conexion;


public class ProductosViewController implements Initializable {
    CambioScene cambioScene = new CambioScene();
    ObservableList<CategoriaProducto> listaCategoria;
    ObservableList<String> listaCodigoCategoria;
    Image imgError = new Image("org/moduloFacturacion/img/error.png");
    Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");

    
    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO};
    public Operacion tipoOperacionCategoria= Operacion.NINGUNO;
    public Operacion cancelar = Operacion.NINGUNO;
    int codigo;
    
    @FXML
    private AnchorPane anchor;
    @FXML
    private Pane buttonInicio;
    @FXML
    private Pane buttonProveedor;
    @FXML
    private JFXTextField txtNombreCategoria;
    @FXML
    private TableView<CategoriaProducto> tableCategoria;
    @FXML
    private TableColumn<CategoriaProducto, Integer> colCodigoCategoria;
    @FXML
    private TableColumn<CategoriaProducto, String> colNombreCategoria;
    @FXML
    private ComboBox<String> cmbCodigoCategoria;
    @FXML
    private JFXButton btnAgregarCategoria;
    @FXML
    private JFXButton btnEditarCategoria;
    @FXML
    private JFXButton btnEliminarCategoria;
    @FXML
    private JFXButton btnBuscarCategoria;
    @FXML
    private JFXTextField txtCodigoCategoria;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Tooltip toolInicio = new Tooltip("Volver a Inicio");
        Tooltip.install(buttonInicio, toolInicio);
        
        Tooltip toolProveedores = new Tooltip("Abrir Proveedores");
        Tooltip.install(buttonProveedor, toolProveedores);
        
       
    }    
    
    //EVENTOS DE LA VISTA PRODUCTOS
    @FXML
    private void cargarProductos(Event event) {
    }

    
    
    @FXML
    private void regresar(MouseEvent event) throws IOException {
         String menu = "org/moduloFacturacion/view/menuPrincipal.fxml";
        cambioScene.Cambio(menu,(Stage) anchor.getScene().getWindow());
    }

    

    @FXML
    private void buttonProveedor(MouseEvent event) throws IOException {
        String proveedores = "org/moduloFacturacion/view/ProveedoresView.fxml";
        cambioScene.Cambio(proveedores,(Stage) anchor.getScene().getWindow());
    }
    
    //EVENTOS DE LA VISTA DE CATEGORIA
    public void limpiarTextCategoria(){
          txtCodigoCategoria.setText("");
          txtNombreCategoria.setText("");
          
    }
    
    public void desactivarControlesCategoria(){
        
        btnEditarCategoria.setDisable(true);
        btnEliminarCategoria.setDisable(true);
    }
    
    public void activarControlesCategoria(){
        
        btnEditarCategoria.setDisable(false);
        btnEliminarCategoria.setDisable(false);
    }
    
    public void desactivarTextCategoria(){
        txtNombreCategoria.setEditable(false);
        
    }
    
    public void activarTextCategoria(){
        txtNombreCategoria.setEditable(true);
        
    }
    
    
    
    public ObservableList<CategoriaProducto> getCategoria(){
        ArrayList<CategoriaProducto> lista = new ArrayList();
        ArrayList<String> listaCodigo = new ArrayList();
        String sql= "{call SpListarCategoriaProductos()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                lista.add(new CategoriaProducto(
                              rs.getInt("categoriaId"),
                              rs.getString("categoriaNombre")
                ));
                
                listaCodigo.add(x, rs.getString("categoriaId"));
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
        
        listaCodigoCategoria = FXCollections.observableList(listaCodigo);
        cmbCodigoCategoria.setItems(listaCodigoCategoria);
        return listaCategoria = FXCollections.observableList(lista);
    
    }
    
    public void cargarDatosCategoria(){
        tableCategoria.setItems(getCategoria());
        colCodigoCategoria.setCellValueFactory(new PropertyValueFactory("categoriaId"));
        colNombreCategoria.setCellValueFactory(new PropertyValueFactory("categoriaNombre"));
        limpiarTextCategoria();
         new AutoCompleteComboBoxListener(cmbCodigoCategoria);
        desactivarControlesCategoria();
    }
    
    @FXML
    private void cargarCategoria(Event event) {
        cargarDatosCategoria();
    }
    
    @FXML
    private void validarNombreCategoria(KeyEvent event) {
    }

    @FXML
    private void seleccionarElementos(MouseEvent event) {
        int index = tableCategoria.getSelectionModel().getSelectedIndex();
        try{
            txtCodigoCategoria.setText(colCodigoCategoria.getCellData(index).toString());
            txtNombreCategoria.setText(colNombreCategoria.getCellData(index));
            btnEliminarCategoria.setDisable(false);
            btnEditarCategoria.setDisable(false);
            
            codigo = colCodigoCategoria.getCellData(index);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    private void atajosCategoria(KeyEvent event) {
        if(cmbCodigoCategoria.isFocused()){
            if(event.getCode() == KeyCode.ENTER){
                buscar();
            }
        }
    }

    public void accionCategoria(){
        switch(tipoOperacionCategoria){
            case AGREGAR:
                tipoOperacionCategoria = Operacion.GUARDAR;
                cancelar = Operacion.CANCELAR;
                desactivarControlesCategoria();
                btnAgregarCategoria.setText("GUARDAR");
                btnEliminarCategoria.setText("CANCELAR");
                btnEliminarCategoria.setDisable(false);
                activarTextCategoria();
                cmbCodigoCategoria.setDisable(true);
                btnBuscarCategoria.setDisable(true);
                limpiarTextCategoria();
                break;
            case CANCELAR:
                tipoOperacionCategoria = Operacion.NINGUNO;
                desactivarControlesCategoria();
                desactivarTextCategoria();
                btnAgregarCategoria.setText("AGREGAR");
                btnEliminarCategoria.setText("ELIMINAR");
                limpiarTextCategoria();
                cmbCodigoCategoria.setDisable(false);
                btnBuscarCategoria.setDisable(false);
                cancelar = Operacion.NINGUNO;
                break;
        }
        
    
    }
    
    public void accionCategoria(String sql){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        PreparedStatement ps;
        ResultSet rs;
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        switch(tipoOperacionCategoria){
            
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
                        tipoOperacionCategoria = Operacion.CANCELAR;
                        accionCategoria();
                        cargarDatosCategoria();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL AGREGAR");
                        noti.text("HA OCURRIDO UN ERROR AL GUARDAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionCategoria = Operacion.CANCELAR;
                        accionCategoria();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA AGREGADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionCategoria = Operacion.CANCELAR;
                    accionCategoria();
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
                        cargarDatosCategoria();
                        tipoOperacionCategoria = Operacion.CANCELAR;
                        accionCategoria();
                        
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        
                        
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ELIMINAR");
                        noti.text("HA OCURRIDO UN ERROR AL ELIMINAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionCategoria = Operacion.CANCELAR;
                        accionCategoria();
                    }
                }else{
                     noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ELIMINADO EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionCategoria = Operacion.CANCELAR;
                    accionCategoria();
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
                        tipoOperacionCategoria = Operacion.CANCELAR;
                        accionCategoria();
                        cargarDatosCategoria();
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL ACTUALIZAR");
                        noti.text("HA OCURRIDO UN ERROR AL ACTUALIZAR EL REGISTRO");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionCategoria = Operacion.CANCELAR;
                        accionCategoria();
                    }
                }else{
                    
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HA ACTUALIZAR EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionCategoria = Operacion.CANCELAR;
                    accionCategoria();
                }
                break;
            case BUSCAR:
                 try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    
                    while(rs.next()){
                        txtCodigoCategoria.setText(rs.getString("categoriaId"));
                        txtNombreCategoria.setText(rs.getString("categoriaNombre"));
                        codigo = rs.getInt("categoriaId");
                        
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tableCategoria.getItems().size(); i++){
                            if(colCodigoCategoria.getCellData(i) == codigo){
                                tableCategoria.getSelectionModel().select(i);
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
                        btnEditarCategoria.setDisable(false);
                        btnEliminarCategoria.setDisable(false);
                        activarTextCategoria();
                    }else{
                         
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL BUSCAR");
                        noti.text("NO SE HA ENCONTRADO EN LA BASE DE DATOS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionCategoria = Operacion.CANCELAR;
                        accionCategoria();
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
                    tipoOperacionCategoria = Operacion.CANCELAR;
                    accionCategoria();
                }
                break;
            
        }
        
    }
    
    
    
    @FXML
    private void btnAgregarCategoria(MouseEvent event) {
        if(tipoOperacionCategoria == Operacion.GUARDAR){
            if(txtCodigoCategoria.getText().isEmpty() || txtNombreCategoria.getText().isEmpty()){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("HAY CAMPOS VACÍOS");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();   
                noti.show();
            
            }else{
                if(txtNombreCategoria.getText().length()<50){
                    CategoriaProducto nuevaCategoria = new CategoriaProducto();
                    nuevaCategoria.setCategoriaId(Integer.parseInt(txtCodigoCategoria.getText()));
                    nuevaCategoria.setCategoriaNombre(txtNombreCategoria.getText());
                    String sql = "{call SpAgregarCategoriaProductos('"+nuevaCategoria.getCategoriaId()+"','"+nuevaCategoria.getCategoriaNombre()+"')}";
                    accionCategoria(sql);
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
             tipoOperacionCategoria = Operacion.AGREGAR;
                accionCategoria();
        }
    }

    @FXML
    private void btnEditarCategoria(MouseEvent event) {
        CategoriaProducto nuevaCategoria = new CategoriaProducto();
        nuevaCategoria.setCategoriaId(Integer.parseInt(txtCodigoCategoria.getText()));
        nuevaCategoria.setCategoriaNombre(txtNombreCategoria.getText());
        
        tipoOperacionCategoria = Operacion.ACTUALIZAR;
        String sql = "{call SpActualizarCategoriaProductos('"+codigo+"','"+nuevaCategoria.getCategoriaId()+"','"+nuevaCategoria.getCategoriaNombre()+"')}";
        accionCategoria(sql);
    }

    @FXML
    private void btnEliminarCategoria(MouseEvent event) {
        if(tipoOperacionCategoria == Operacion.GUARDAR){
            tipoOperacionCategoria = Operacion.CANCELAR;
            accionCategoria();
        }else{
            String sql = "{call SpEliminarCategoriaProductos('"+codigo+"')}";
            tipoOperacionCategoria = Operacion.ELIMINAR;
            accionCategoria(sql);
        }
    }
    
    public void buscar(){
        if(cmbCodigoCategoria.getValue().equals("")){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("El CAMPO DE CÓDIGO ESTA VACÍO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();   
            noti.show();
        }else{
            tipoOperacionCategoria = Operacion.BUSCAR;
            String sql = "{ call SpBuscarCategoriaProductos('"+cmbCodigoCategoria.getValue()+"')}";
            accionCategoria(sql);
        }
    }
    
    @FXML
    private void btnBuscarCategoria(MouseEvent event) {
        buscar();
    }

    @FXML
    private void codigoBuscadoCategoria(MouseEvent event) {
        limpiarTextCategoria();
        desactivarControlesCategoria();
    }
    
}
