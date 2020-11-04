package org.moduloFacturacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
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
import javafx.scene.control.ComboBox;
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
import org.moduloFacturacion.bean.Productos;
import org.moduloFacturacion.db.Conexion;


public class FacturacionViewController implements Initializable {
    CambioScene cambioScene = new CambioScene();    
     Image imgError = new Image("org/moduloFacturacion/img/error.png");
    Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");
    Image imgWarning = new Image("org/moduloFacturacion/img/warning.png");
    
    ObservableList<String> listaComboCliente;
    ObservableList<String> listaComboProductos;

    boolean comprobarCliente = false;
    Notifications noti = Notifications.create();
    int codigoProducto;
    
    
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

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarComboNit();
        llenarComboProdcutos();
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
    
    
    @FXML
    private void regresar(MouseEvent event) throws IOException {
         String menu = "org/moduloFacturacion/view/menuPrincipal.fxml";
        cambioScene.Cambio(menu,(Stage) anchor.getScene().getWindow());
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
    
    
    @FXML
    private void btnImprimir(MouseEvent event) {
       comprobarClienteExistente();
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
    
    
    @FXML
    private void buscarPrecio(ActionEvent event) {
        buscarPrecioMetodo();
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
    
  
    
}
