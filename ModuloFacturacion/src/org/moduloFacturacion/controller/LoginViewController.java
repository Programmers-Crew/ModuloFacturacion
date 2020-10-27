
package org.moduloFacturacion.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.moduloFacturacion.bean.CambioScene;


public class LoginViewController implements Initializable {
    
    private Label label;
    private Pane panelTransicion;
    @FXML
    private AnchorPane anchor;
    
    CambioScene cambioScene = new CambioScene();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       FadeTransition ft = new FadeTransition();
       ft.setFromValue(0);
       ft.setToValue(1);
       ft.setDuration(Duration.seconds(1));
       ft.setNode(panelTransicion);
       ft.setCycleCount(1);
       ft.play();
       
       TranslateTransition tt = new TranslateTransition();
       tt.setFromY(110);
       tt.setToY(-10);
       tt.setDuration(Duration.seconds(1.5));
       tt.setNode(panelTransicion);
       tt.setCycleCount(1);
       tt.play();
    }    

   

    @FXML
    private void btnIngresar(MouseEvent event) throws IOException {
        Stage stage1 = (Stage)anchor.getScene().getWindow();
        
        Stage primaryStage= new Stage();
        
        
        
        
        stage1.close();

    }

    @FXML
    private void btnCerrar(MouseEvent event) {
        System.exit(0);
    }
    
}
