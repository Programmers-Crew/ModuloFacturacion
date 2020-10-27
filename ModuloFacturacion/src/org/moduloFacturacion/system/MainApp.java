package org.moduloFacturacion.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
         Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("org/moduloFacturacion/view/LoginView.fxml"));
        
         
         
        Scene scene = new Scene(root);
        stage.setWidth(668);
        stage.setHeight(520);
        stage.setScene(scene);     
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
