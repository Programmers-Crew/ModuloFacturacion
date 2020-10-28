package org.moduloFacturacion.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.moduloFacturacion.db.Conexion;


public class MainApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
         Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("org/moduloFacturacion/view/LoginView.fxml"));
        
       Conexion c = new Conexion();
        if(c.getConexion() != null){
            System.out.println("CONEXIO CORRECTA 1");
        }else{
            System.out.println("CONEXION INCORRECTA");
        }
         
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
