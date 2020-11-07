package org.moduloFacturacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

import org.moduloFacturacion.bean.FacturacionDetalleBackup;
import org.moduloFacturacion.bean.FacturasBuscadas;
import org.moduloFacturacion.bean.ProductoBuscado;

import org.moduloFacturacion.bean.ValidarStyle;

import org.moduloFacturacion.db.Conexion;


public class FacturacionViewController implements Initializable {
    CambioScene cambioScene = new CambioScene();    
     Image imgError = new Image("org/moduloFacturacion/img/error.png");
    Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");
    Image imgWarning = new Image("org/moduloFacturacion/img/warning.png");
    @FXML
    private JFXTextField txtFacturaId;
    @FXML
    private JFXTextField txtTotalFactura;


    
    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO, VENDER,FILTRAR,CARGAR};
    public Operacion cancelar = Operacion.NINGUNO;
    

    
    // ==================== VARIABLES DE FACTURACION
    public Operacion tipoOperacionFacturacion = Operacion.NINGUNO; 

    ObservableList<String> listaComboCliente;
    ObservableList<String> listaComboProductos;
    ObservableList<FacturacionDetalleBackup> listaBackUp;

    boolean comprobarCliente = false;
    Notifications noti = Notifications.create();
    int codigoProducto;
    
    // ================= VARIABLES PRA BUSCAR FACTURAS
        public Operacion tipoOperacionBusquedaFacturas = Operacion.NINGUNO; 

        ObservableList<String> listaNumeroFactura;
        ObservableList<FacturasBuscadas> listaFacturasBuscadas;
        ObservableList<ProductoBuscado> listaProductoBuscado;

    int codigoFacturas;


    
    
    // ======================= PROPIEDADES VISTA FACTURACION 
    @FXML
    private AnchorPane anchor;
    @FXML
    private JFXTextField txtNombreCliente;
    @FXML
    private ComboBox<String> txtNitCliente;
    @FXML
    private JFXButton btnImprimir;
    @FXML
    private JFXTextField txtPrecioProducto;
    @FXML
    private ComboBox<String> cmbNombreProducto;
    @FXML
    private JFXTextField txtCantidadProducto;
    
    java.util.Date d = new java.util.Date();  
    SimpleDateFormat plantilla = new SimpleDateFormat("dd/MM/yyyy H:mm");
    String tiempo = plantilla.format(d);
    java.sql.Date date2 = new java.sql.Date(d.getTime());
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    LoginViewController login = new LoginViewController();
    
    ValidarStyle validar = new ValidarStyle();

    @FXML
    private TableColumn<FacturacionDetalleBackup, String> colDesProductoBackUp;
    @FXML
    private TableColumn<FacturacionDetalleBackup, Integer> colCantidadProductoBackUp;
    @FXML
    private TableColumn<FacturacionDetalleBackup, Double> colPrecioProductoBackUp;
    @FXML
    private TableColumn<FacturacionDetalleBackup, Double> colTotalParcialBackUp;
    @FXML
    private TableView<FacturacionDetalleBackup> tblBackUp;

    double totalFactura=0;
    
    // ============================ PROPIEDADES DE BUSQUEDA DE FACTURAS 
    @FXML
    private TableView<FacturasBuscadas> tblResultadoFactura;
    @FXML
    private TableColumn<FacturasBuscadas,Integer> colNumeroFacBuscado;
    @FXML
    private TableColumn<FacturasBuscadas, Double> colTotlalNeto;
    @FXML
    private TableColumn<FacturasBuscadas, Double> colTotalIva;
    @FXML
    private TableColumn<FacturasBuscadas, Double> colTotalBuscado;
    @FXML
    private TableColumn<FacturasBuscadas, Date> colFechaBuscada;
    @FXML
    private JFXComboBox<String> txtBusquedaCodigoFac;
    @FXML
    private JFXButton btnBuscarFactura;
    @FXML
    private JFXButton btnFiltrarFactura;
    @FXML
    private JFXButton btnCargarFacturas;
    @FXML
    private JFXDatePicker txtFechaInicio;
    @FXML
    private JFXDatePicker txtFechaFinal;
    @FXML
    private JFXButton btnCorteDeCaja;
    @FXML
    private TableView<ProductoBuscado> tblResultadoProducto;
    @FXML
    private TableColumn<ProductoBuscado, String> colProductoBuscado;
    @FXML
    private TableColumn<ProductoBuscado, Integer> colCantidadBuscada;
    @FXML
    private TableColumn<ProductoBuscado, Double> colPrecioUnitBuscado;
    @FXML
    private JFXTextField txtResultadoNit;
    @FXML
    private JFXTextField txtResultadoNombre;
    
    
    
    public void valorTotalFactura(){
        String sql = "{call SpSumarBackup()}";
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                totalFactura =  rs.getDouble("sum(totalParcialBackup)");
            }
            
            txtTotalFactura.setText(String.valueOf(totalFactura));
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         validar.validarView(menu.prefs.get("dark", "root"), anchor);
        llenarComboNit();
        llenarComboProdcutos();
        cargarDatos();
        valorTotalFactura();
    }    

    @FXML
    private void regresar(MouseEvent event) throws IOException {
         String menu = "org/moduloFacturacion/view/menuPrincipal.fxml";
        cambioScene.Cambio(menu,(Stage) anchor.getScene().getWindow());
    }
    
    // =================== CODIGO FACTURACION
    public void limpiarTextFacturacion(){
        cmbNombreProducto.setValue("");
        txtPrecioProducto.setText("");
        txtCantidadProducto.setText("");
    }
    
    
        @FXML
    private void buscarCliente(ActionEvent event) {
        buscarClienteMetodo();
    }

    @FXML
    private void buscarPrecio(ActionEvent event) {
        buscarPrecioMetodo();
    }

    
    @FXML
    private void AtajoCliente(KeyEvent event) {
        if(txtNitCliente.isFocused()){
            if(event.getCode() == KeyCode.ENTER){
                buscarClienteMetodo();
            }
        }
    }
    

    public void llenarComboNit(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call SpListarClientes()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("clienteNit"));
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

        listaComboCliente = FXCollections.observableList(lista);
        txtNitCliente.setItems(listaComboCliente);
        new AutoCompleteComboBoxListener(txtNitCliente);
    }

    
    
    public void buscarClienteMetodo(){
         String sql = "{call SpBuscarClientesNit('"+txtNitCliente.getValue()+"')}";
         
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                    txtNombreCliente.setText(rs.getString("clienteNombre"));
                    
                }
            if(rs.first()){
                txtNombreCliente.setEditable(false);
                  
            }else{
                comprobarCliente=true;
                txtNombreCliente.setText("");
                txtNombreCliente.setEditable(true);

                noti.graphic(new ImageView(imgWarning));
                noti.title("USUARIO NO EXISTE");
                noti.text("DEBERÁ INGRESAR EL CAMPO NOMBRE");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
                
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
        
    }
    
    
    public void comprobarClienteExistente(){
        if(comprobarCliente == false){
            System.out.println("hay cliente");
        }else{
            if(txtNitCliente.getValue().equals("") || txtNombreCliente.getText().isEmpty()){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR AL AGREGAR");
                noti.text("CAMPOS VACÍOS EN EL AREA DE CLIENTES");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
                
            }else{
                
                String sql ="{call SpAgregarClientes('"+txtNitCliente.getValue()+"','"+txtNombreCliente.getText()+"')}";
                try{
                    PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    ps.execute();
                    llenarComboNit();
                }catch(SQLException ex){
                    ex.printStackTrace();
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR AL AGREGAR");
                    noti.text("HA OCURRIDO UN ERROR AL GUARDAR EL REGISTRO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                }
            }
        }
    }
    
    


public int buscarCodigoProducto(String precioProductos){    
        try{
            PreparedStatement sp = Conexion.getIntance().getConexion().prepareCall("{call SpBuscarcodigoProducto(?)}");
            sp.setString(1, precioProductos);
            ResultSet resultado = sp.executeQuery(); 
            
            while(resultado.next()){
            codigoProducto = resultado.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return codigoProducto;
    }
    
    
    public void buscarPrecioMetodo(){
        if(cmbNombreProducto.getValue()!= ""){
                try{
                     PreparedStatement sp = Conexion.getIntance().getConexion().prepareCall("{call SpBuscarProductos(?)}");
                    sp.setInt(1, buscarCodigoProducto(cmbNombreProducto.getValue()));
                     ResultSet resultado = sp.executeQuery(); 
                        while(resultado.next()){
                            txtPrecioProducto.setText(resultado.getString("productoPrecio"));
                        }  
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
    }
    
    
    public void llenarComboProdcutos(){
        ArrayList<String> lista = new ArrayList();
        String sql= "{call SpListarProductos()}";
            int x =0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(x, rs.getString("productoDesc"));
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

        listaComboProductos = FXCollections.observableList(lista);
        cmbNombreProducto.setItems(listaComboProductos);
        new AutoCompleteComboBoxListener(cmbNombreProducto);
    }
    
    
     public ObservableList<FacturacionDetalleBackup> getBackUp(){
        ArrayList<FacturacionDetalleBackup> lista = new ArrayList();
        String sql = "{call SpListarBackup()}";
        int x=0;
        double totalParcial=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new FacturacionDetalleBackup(
                            rs.getString("productoDesc"),
                            rs.getInt("cantidadBackup"),
                            rs.getDouble("productoPrecio"),
                            rs.getDouble("totalParcialBackup")
                ));
                totalParcial = rs.getDouble("totalParcialBackup");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        } 
            totalFactura = totalFactura+totalParcial;
            txtTotalFactura.setText(String.valueOf(totalFactura));
                return listaBackUp = FXCollections.observableList(lista);
    }
     
    public void cargarDatos(){
        tblBackUp.setItems(getBackUp());
        colDesProductoBackUp.setCellValueFactory(new PropertyValueFactory("productoDesc"));
        colCantidadProductoBackUp.setCellValueFactory(new PropertyValueFactory("cantidadBackup"));  
        colPrecioProductoBackUp.setCellValueFactory(new PropertyValueFactory("productoPrecio"));
        colTotalParcialBackUp.setCellValueFactory(new PropertyValueFactory("totalParcialBackup"));

        
        cmbNombreProducto.setValue("");
        limpiarTextFacturacion();
    }  
    
  public void accionEstado(String sql){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        PreparedStatement ps;
        ResultSet rs;
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        
        
        switch(tipoOperacionFacturacion){
            case AGREGAR:
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
                        tipoOperacionFacturacion = Operacion.CANCELAR;
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
                        tipoOperacionFacturacion = Operacion.CANCELAR;
                    }                
                break;
        }
    }
    
  
  @FXML
    private void btnAgregarFacturaBackUp(MouseEvent event) {
            if(cmbNombreProducto.getValue().equals("")|| txtPrecioProducto.getText().isEmpty() || txtCantidadProducto.getText().isEmpty() || txtNombreCliente.getText().isEmpty() || txtNombreCliente.getText().isEmpty() || txtFacturaId.getText().isEmpty() ){
                Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("HAY CAMPOS VACÍOS");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();   
                noti.show();
            
            }else{
                    
                    
                   FacturacionDetalleBackup nuevoBackUp = new FacturacionDetalleBackup();
                   nuevoBackUp.setProductoDesc(cmbNombreProducto.getValue());
                   nuevoBackUp.setCantidadBackup(Integer.parseInt(txtCantidadProducto.getText()));
                   nuevoBackUp.setTotalParcialBackup(Double.parseDouble(txtPrecioProducto.getText())*Integer.parseInt(txtCantidadProducto.getText()));

                   String sql = "{call SpAgregarBackup('"+buscarCodigoProducto(nuevoBackUp.getProductoDesc())+"','"+ nuevoBackUp.getCantidadBackup()+"','"+nuevoBackUp.getTotalParcialBackup()+"')}";
                   tipoOperacionFacturacion = Operacion.AGREGAR;
                   accionEstado(sql);  
            }
    }
    
    
    public int getClienteId(){
        int codigoCliente=0;
            
        String sql = "{call SpBuscarClientesNit('"+txtNitCliente.getValue()+"')}";
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                codigoCliente = rs.getInt("clienteId");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
            
            
        return codigoCliente;
    }
    
    public int getUsuarioId(){
        int codigoUsuario=0;
        System.out.println(login.prefsUsuario.get("usuario", "root"));
        String sql = "{call SpBuscarUsuarioId('"+login.prefsUsuario.get("usuario", "root")+"')}";
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                codigoUsuario = rs.getInt("usuarioId");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        System.out.println(codigoUsuario);
        return codigoUsuario;
    }
    
        @FXML
    private void btnImprimir(MouseEvent event) {
       
       comprobarClienteExistente();
       
       double totalNeto = Double.parseDouble(txtTotalFactura.getText())/1.12;
       double totalIva = totalNeto*0.12;
       
       String sql = "{call SpTransferirBackup()}";
       String sqlEliminar = "{call SpEliminarBackup()}";
       String sqlFactura = "{call SpAgregarFactura('"+txtFacturaId.getText()+"','"+getClienteId()+"','"+date2+"','"+getUsuarioId()+"','"+totalNeto+"','"+totalIva+"','"+txtTotalFactura.getText()+"')}";
       try{
           PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
           ps.execute();
           
           PreparedStatement psFactura = Conexion.getIntance().getConexion().prepareCall(sqlFactura);
               psFactura.execute();
           
           PreparedStatement psEliminar = Conexion.getIntance().getConexion().prepareCall(sqlEliminar);
           psEliminar.execute();
           
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgCorrecto));
            noti.title("OPERACIÓN EXITOSA");
            noti.text("SE HA IMPRESO Y REGISTRADO CON ÉXITO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
           cargarDatos();
       }catch(SQLException ex){
           ex.printStackTrace();
           Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("HUBO UN ERROR AL REGISTRAR EN LA BASE DE DATOS");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
       }
       
    }
    
// ================================ CODIGO BUSQUEDA FACTURAS
    
    public ObservableList<FacturasBuscadas> getFacturasBuscadas(){
        ArrayList<FacturasBuscadas> lista = new ArrayList();
        ArrayList<String> comboNumeroFacturas = new ArrayList();
        String sql = "{call SpListarBusquedasFacturas()}";
        int x=0;
        
        try{
            PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new FacturasBuscadas(
                            rs.getInt("facturaId"),
                            rs.getDouble("facturaTotalNeto"),
                            rs.getDouble("facturaTotalIva"),
                            rs.getDouble("facturaTotal"),
                            rs.getDate("facturaFecha")
                ));
                comboNumeroFacturas.add(x, rs.getString("facturaId"));
                x++;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        listaNumeroFactura = FXCollections.observableList(comboNumeroFacturas);
        txtBusquedaCodigoFac.setItems(listaNumeroFactura);
        return listaFacturasBuscadas = FXCollections.observableList(lista);
    }
 
    
    public void cargarFacturasBuscadas(){
        tblResultadoFactura.setItems(getFacturasBuscadas());
        
        colNumeroFacBuscado.setCellValueFactory(new PropertyValueFactory("facturaId"));
        colTotlalNeto.setCellValueFactory(new PropertyValueFactory("facturaTotalNeto"));  
        colTotalIva.setCellValueFactory(new PropertyValueFactory("facturaTotalIva"));
        colTotalBuscado.setCellValueFactory(new PropertyValueFactory("facturaTotal"));
        colFechaBuscada.setCellValueFactory(new PropertyValueFactory("facturaFecha"));
        
        txtBusquedaCodigoFac.setValue("");
    }  
    
    
    @FXML
    private void cargarFacturasBuscadas(Event event) {
        cargarFacturasBuscadas();
    }
    
    public void accion(String sql){
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        PreparedStatement ps;
        ResultSet rs;
        Notifications noti = Notifications.create();
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        switch(tipoOperacionBusquedaFacturas){
            case FILTRAR:
                 try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    int numero=0;
                    while(rs.next()){
                        colNumeroFacBuscado.setText(rs.getString("facturaId"));
                        colTotlalNeto.setText(rs.getString("facturaTotalNeto"));
                        colTotalIva.setText(rs.getString("facturaTotalIva"));
                        colTotalBuscado.setText(rs.getString("facturaTotal"));
                        colFechaBuscada.setText(rs.getString("facturaFecha"));
                        
                        codigoFacturas = rs.getInt("facturaId");
                        
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tblResultadoFactura.getItems().size(); i++){
                            if(colNumeroFacBuscado.getCellData(i) == codigoFacturas){
                                tblResultadoFactura.getSelectionModel().select(i);
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
                        tipoOperacionBusquedaFacturas = Operacion.CANCELAR;
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
                    tipoOperacionBusquedaFacturas = Operacion.CANCELAR;
                }
                break;
            case CARGAR:
                 alert.setTitle("VOLVER A CARGAR DATOS");
                alert.setHeaderText("VOLVER A CARGAR DATOS");
                alert.setContentText("¿Está seguro que desea cargar todos los datos de nuevo?");
               
                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
                
                Optional<ButtonType> resultactualizar = alert.showAndWait();
                if(resultactualizar.get() == buttonTypeSi ){
                    try {
                        ps = Conexion.getIntance().getConexion().prepareCall(sql);
                        ps.execute();
                        
                        noti.graphic(new ImageView(imgCorrecto));
                        noti.title("OPERACIÓN EXITOSA");
                        noti.text("SE HAN CARGADO EXITOSAMENTE SUS REGISTROS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionBusquedaFacturas = Operacion.CANCELAR;
                        cargarFacturasBuscadas();
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        noti.graphic(new ImageView(imgError));
                        noti.title("ERROR AL CARGAR SUS REGISTROS");
                        noti.text("HA OCURRIDO UN ERROR AL MOMENTO DE CARGAR TODOS LOS REGISTROS");
                        noti.position(Pos.BOTTOM_RIGHT);
                        noti.hideAfter(Duration.seconds(4));
                        noti.darkStyle();
                        noti.show();
                        tipoOperacionBusquedaFacturas = Operacion.CANCELAR;
                    }
                }else{  
                    noti.graphic(new ImageView(imgError));
                    noti.title("OPERACIÓN CANCELADA");
                    noti.text("NO SE HAN PODIDO CARGAR TODOS LOS REGISTROS");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();
                    tipoOperacionBusquedaFacturas = Operacion.CANCELAR;
                }
                break;
            case BUSCAR:
                 try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    int numero=0;
                    while(rs.next()){
                        colNumeroFacBuscado.setText(rs.getString("facturaId"));
                        colTotlalNeto.setText(rs.getString("facturaTotalNeto"));
                        colTotalIva.setText(rs.getString("facturaTotalIva"));
                        colTotalBuscado.setText(rs.getString("facturaTotal"));
                        colFechaBuscada.setText(rs.getString("facturaFecha"));
                        
                        codigoFacturas = rs.getInt("facturaId");
                        
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tblResultadoFactura.getItems().size(); i++){
                            if(colNumeroFacBuscado.getCellData(i) == codigoFacturas){
                                tblResultadoFactura.getSelectionModel().select(i);
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
                        tipoOperacionBusquedaFacturas = Operacion.CANCELAR;
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
                    tipoOperacionBusquedaFacturas = Operacion.CANCELAR;
                }
                break;
        }
    }
    
     public void buscarFactura(){
      if(txtBusquedaCodigoFac.getValue().equals("")){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
        }else{
            tipoOperacionBusquedaFacturas = Operacion.BUSCAR;

            String sql = "{call SpListarBusquedasFacturasPorId('"+txtBusquedaCodigoFac.getValue()+"')}";
            accion(sql);
        }  
    }
     
    public void buscarPorFechas(){
      if(txtFechaInicio.getValue().equals("") || txtFechaFinal.getValue().equals("")){
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR");
                    noti.text("El CAMPO DE BUSQUEDA ESTA VACÍO");
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();   
                    noti.show();
        }else{
            tipoOperacionBusquedaFacturas = Operacion.FILTRAR;

            String sql = "{call SpBuscarDetalleFacturasFecha('"+txtFechaInicio.getValue()+"','"+txtFechaFinal.getValue()+"')}";
            accion(sql);
        }  
    }
    
    public void buscarProducto(){

            String sql = "{call SpBuscarClienteFacturaFecha('"+colNumeroFacBuscado.getText()+"')}";
            accion(sql);
            
            PreparedStatement ps;
            ResultSet rs;
            
            try{
                    ps = Conexion.getIntance().getConexion().prepareCall(sql);
                    rs = ps.executeQuery();
                    int numero=0;
                    
                    while(rs.next()){
                        colNumeroFacBuscado.setText(rs.getString("facturaId"));
                        colTotlalNeto.setText(rs.getString("facturaTotalNeto"));
                        colTotalIva.setText(rs.getString("facturaTotalIva"));
                        colTotalBuscado.setText(rs.getString("facturaTotal"));
                        colFechaBuscada.setText(rs.getString("facturaFecha"));
                        
                        codigoFacturas = rs.getInt("facturaId");
                        
                    }                    
                    if(rs.first()){
                        for(int i=0; i<tblResultadoFactura.getItems().size(); i++){
                            if(colNumeroFacBuscado.getCellData(i) == codigoFacturas){
                                tblResultadoFactura.getSelectionModel().select(i);
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
                        tipoOperacionBusquedaFacturas = Operacion.CANCELAR;
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
                    tipoOperacionBusquedaFacturas = Operacion.CANCELAR;
                }
        }  
    
}



