/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moduloFacturacion.bean;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;



public class CambioScene {    
    //método donde cambia de una escena a otra.
    public void Cambio(String url, Stage primaryStage, int ancho, int largo) throws IOException{
        
      
       
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(url));
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setWidth(ancho);
        primaryStage.setHeight(largo);
        
        
        primaryStage.show();
        
        
    }
}
