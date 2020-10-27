
package org.moduloFacturacion.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class LoginViewController implements Initializable {
    
    private Label label;
    @FXML
    private Pane panelTransicion;
    
    
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
    private void cerrar(MouseEvent event) {   
        System.exit(0);
    
    }
    
}
