package org.moduloFacturacion.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class MenuPrincipalContoller implements Initializable {
    
    LoginViewController login = new LoginViewController();
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
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelUsuario.setText("¡Bienvenido "+login.prefsUsuario.get("usuario","root")+"!");
        
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
    }

    @FXML
    private void atajosMenuPrincipal(KeyEvent event) {
    }
    
    
    @FXML
    private void inventarioView(ActionEvent event) {
    }
   

    @FXML
    private void facturasView(ActionEvent event) {
    }

    

   
    @FXML
    private void Clientesview(ActionEvent event) {
    }
    
  

    
    
    @FXML
    private void productosView(ActionEvent event) {
    }

    @FXML
    private void proveedoresView(ActionEvent event) {
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

    @FXML
    private void ClientesAtajo(MouseEvent event) {
    }

    @FXML
    private void inventarioAtajo(MouseEvent event) {
    }

    @FXML
    private void facturaAtajo(MouseEvent event) {
    }

    


 }

