package org.moduloFacturacion.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.moduloFacturacion.bean.ValidarStyle;
import org.moduloFacturacion.controller.MenuPrincipalContoller;
import org.moduloFacturacion.db.Conexion;


public class MainApp extends Application {
    MenuPrincipalContoller menu = new MenuPrincipalContoller();
    

    @Override
    public void start(Stage stage) throws Exception {
        Parent root;
        if(menu.prefsUsuario.get("validar", "root").equals("recordar")){
            root = FXMLLoader.load(getClass().getClassLoader().getResource("org/moduloFacturacion/view/menuPrincipal.fxml"));
            Scene scene = new Scene(root);
            stage.setWidth(1100);
            stage.setHeight(590);
            stage.setScene(scene);
            stage.show();
        }else{
            root = FXMLLoader.load(getClass().getClassLoader().getResource("org/moduloFacturacion/view/LoginView.fxml"));
            Scene scene = new Scene(root);
            stage.setWidth(668);
            stage.setHeight(520);
            stage.setScene(scene);     
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        }
        
         
        
       Conexion c = new Conexion();
        if(c.getConexion() != null){
            System.out.println("CONEXIO CORRECTA 1");
        }else{
            System.out.println("CONEXION INCORRECTA");
        }
         
       
    }

    public static void main(String[] args) {
        launch(args);
       

    }
    
}
