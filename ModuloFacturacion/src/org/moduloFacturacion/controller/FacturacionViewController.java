package org.moduloFacturacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
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
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXTextField txtEfectivo;
    @FXML
    private JFXTextField txtCambio;
    @FXML
    private JFXButton btnVender;
    @FXML
    private TableColumn<FacturacionDetalleBackup, Integer> colCodigoFactura;


  
    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO, VENDER};
    public Operacion cancelar = Operacion.NINGUNO;
    
    // ==================== VARIABLES DE FACTURACION
    public Operacion tipoOperacionFacturacion = Operacion.NINGUNO; 

    ObservableList<String> listaComboCliente;
    ObservableList<String> listaComboProductos;
    ObservableList<FacturacionDetalleBackup> listaBackUp;

    boolean comprobarCliente = false;
    Notifications noti = Notifications.create();
    int codigoProducto;
    
    
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
        btnEditar.setDisable(true);
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
    
    public void limpiarTextCliente(){
        txtFacturaId.setText("");
        txtNitCliente.setValue("");
        txtNombreCliente.setText("");
    }
    public void limpiarTextEfectivo(){
        txtNombreCliente.setText("");
        txtEfectivo.setText("");
        txtCambio.setText("");
        txtTotalFactura.setText("");
    }
    
        @FXML
    private void buscarCliente(ActionEvent event) {
        buscarClienteMetodo();
    }


    
    @FXML
    private void AtajoCliente(KeyEvent event) {
        if(txtNitCliente.isFocused()){
            if(event.getCode() == KeyCode.ENTER){
                buscarClienteMetodo();
            }
        }
    }
    @FXML
    private void atajoProducto(KeyEvent event) {
        if(cmbNombreProducto.isFocused()){
            if(event.getCode() == KeyCode.ENTER){
                buscarPrecioMetodo();
                System.out.println("hola");
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
        if(txtNitCliente.getValue().equals("")){
            
        }else{
            
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
       
        
    }
         @FXML
    private void buscarPrecio(ActionEvent event) {
        
        buscarPrecioMetodo();
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
                        if(resultado.first()){
                            txtPrecioProducto.setEditable(false);
                            if(tipoOperacionFacturacion == Operacion.ACTUALIZAR){
                                btnVender.setDisable(true);
                            }else{
                                 btnVender.setDisable(false);
                            }
                           
                        }else{
                            txtPrecioProducto.setText("");
                            btnVender.setDisable(true);
                            noti.graphic(new ImageView(imgError));
                            noti.title("ESTE PRODUCTO NO EXISTE");
                            noti.text("DEBERÁ AGREGARLO EN EL MODULO DE PRODUCTOS");
                            noti.position(Pos.BOTTOM_RIGHT);
                            noti.hideAfter(Duration.seconds(4));
                            noti.darkStyle();
                            noti.show();
                        }
                }catch(Exception e){
                    e.printStackTrace();
                    Notifications noti = Notifications.create();
                    noti.graphic(new ImageView(imgError));
                    noti.title("ERROR AL BUSCAR EL PRODUCTO");
                    noti.text("HA OCURRIDO UN ERROR EN LA BASE DE DATOS"+e);
                    noti.position(Pos.BOTTOM_RIGHT);
                    noti.hideAfter(Duration.seconds(4));
                    noti.darkStyle();
                    noti.show();

                }
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
                            rs.getInt("facturaDetalleIdBackup"),
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
        colCodigoFactura.setCellValueFactory(new PropertyValueFactory("facturaDetalleIdBackup"));
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
    
    
    public void guardarFactura(){
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
        @FXML
    private void btnImprimir(MouseEvent event) {
       
       comprobarClienteExistente();
       
       guardarFactura();
       
       limpiarTextCliente();
       
      limpiarTextEfectivo();
    }
    
      @FXML
    private void btnEditar(MouseEvent event) {
        if(cmbNombreProducto.getValue().equals("") || txtPrecioProducto.getText().isEmpty() || txtCantidadProducto.getText().isEmpty()){
            Notifications noti = Notifications.create();
            noti.graphic(new ImageView(imgError));
            noti.title("ERROR");
            noti.text("HAY UN CAMPO VACÍO");
            noti.position(Pos.BOTTOM_RIGHT);
            noti.hideAfter(Duration.seconds(4));
            noti.darkStyle();
            noti.show();
        }else{
            int index = tblBackUp.getSelectionModel().getSelectedIndex();
            FacturacionDetalleBackup nuevaFactura = new FacturacionDetalleBackup();
            nuevaFactura.setFacturaDetalleIdBackup(colCodigoFactura.getCellData(index));
            nuevaFactura.setProductoDesc(cmbNombreProducto.getValue());
            
            nuevaFactura.setCantidadBackup(Integer.parseInt(txtCantidadProducto.getText()));
            nuevaFactura.setTotalParcialBackup(Double.parseDouble(txtPrecioProducto.getText())*Integer.parseInt(txtCantidadProducto.getText()));
            
            
           String sql = "{call spEditarBackup('"+nuevaFactura.getFacturaDetalleIdBackup()+"','"+buscarCodigoProducto(nuevaFactura.getProductoDesc())+"','"+nuevaFactura.getCantidadBackup()+"','"+nuevaFactura.getTotalParcialBackup()+"')}";
           try{
               PreparedStatement ps = Conexion.getIntance().getConexion().prepareCall(sql);
               ps.execute();
               
                noti.graphic(new ImageView(imgCorrecto));
                noti.title("OPERACIÓN EXITOSA");
                noti.text("SE HA EDITADO EXITOSAMENTE EL REGISTRO");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
                tipoOperacionFacturacion = Operacion.NINGUNO;
                cargarDatos();
                btnEditar.setDisable(true);
                valorTotalFactura();
           }catch(SQLException ex){
               ex.printStackTrace();
                 Notifications noti = Notifications.create();
                noti.graphic(new ImageView(imgError));
                noti.title("ERROR");
                noti.text("NO SE HA PODIDO ACTUALIZAR EL CAMPO");
                noti.position(Pos.BOTTOM_RIGHT);
                noti.hideAfter(Duration.seconds(4));
                noti.darkStyle();
                noti.show();
           }
        }
    }
    
    
    @FXML
    private void seleccionarElementos(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType buttonTypeSi = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");
        
        alert.setTitle("WARNING");
        alert.setHeaderText("EDITAR REGISTRO DE FACTURA");
        alert.setContentText("¿Está seguro que desea editar este registro?");

        alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == buttonTypeSi){
            try{
                int index  = tblBackUp.getSelectionModel().getSelectedIndex();
                cmbNombreProducto.setValue(colDesProductoBackUp.getCellData(index));
                txtCantidadProducto.setText(colCantidadProductoBackUp.getCellData(index).toString());
                
                btnEditar.setDisable(false);
                btnVender.setDisable(true);
                btnImprimir.setDisable(true);
                tipoOperacionFacturacion = Operacion.ACTUALIZAR;
            }catch(Exception e){
            
            }
        }else{
            tblBackUp.getSelectionModel().clearSelection();
        }
    }
    
    
    
    
    @FXML
    private void validarLetras(KeyEvent event) {
           char letra = event.getCharacter().charAt(0);
        
        if(!Character.isDigit(letra)){
            event.consume();
        }else{
        
        }
        
    }
    
    @FXML
    private void validarEfectivo(KeyEvent event) {
        
          char letra = event.getCharacter().charAt(0);
          
        if(!Character.isDigit(letra)){
            if(letra != '.'){
                event.consume();
            }
            
            
        }else{
        
        }
        
        
        
            
    }
  

    @FXML
    private void cambio(KeyEvent event) {
        double  totalFactura = Double.parseDouble(txtTotalFactura.getText());
        double efectivo = Double.parseDouble(txtEfectivo.getText());
        
        double total = efectivo -totalFactura;
        if(total<0){
            btnImprimir.setDisable(true);
        }else{
            btnImprimir.setDisable(false);
        }
        txtCambio.setText(String.valueOf(total));
    }
    
}

