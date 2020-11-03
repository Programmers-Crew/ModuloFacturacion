package org.moduloFacturacion.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.moduloFacturacion.bean.AutoCompleteComboBoxListener;
import org.moduloFacturacion.bean.CambioScene;
import org.moduloFacturacion.bean.EstadoProductos;
import org.moduloFacturacion.bean.InventarioProductos;
import org.moduloFacturacion.db.Conexion;


public class InventarioViewController implements Initializable {
    
    CambioScene cambioScene = new CambioScene();
    Image imgError = new Image("org/moduloFacturacion/img/error.png");
    Image imgCorrecto= new Image("org/moduloFacturacion/img/correcto.png");

    
    public enum Operacion{AGREGAR,GUARDAR,ELIMINAR,BUSCAR,ACTUALIZAR,CANCELAR,NINGUNO};
    public Operacion tipoOperacionProveedores= Operacion.NINGUNO;
    public Operacion cancelar = Operacion.NINGUNO;
    
    // Variables para Inventario
    ObservableList<InventarioProductos> listaInventarioProductos;
    ObservableList<String> listaProveedorInventario;
    ObservableList<String> listaProductoInventario;
    ObservableList<String> listaEstadoInventario;
    ObservableList<String> listaFiltro;
    ObservableList<String> listaValorFiltroCodigo;
    ObservableList<String> listaValorFiltroNombre;
    ObservableList<String> listaCodigoInventario;

    int codigoInventario = 0;

    //Variables para Estado
    ObservableList<EstadoProductos> listaEstadoProductos;
    ObservableList<String> listaCodigoEstadoProductos;
    
    //Propiedades Inventario
    @FXML
    private AnchorPane anchor;
    @FXML
    private JFXTextField txtCantidadInventario;
    @FXML
    private JFXButton btnAgregarInventario;
    @FXML
    private JFXButton btnEditarInventario;
    @FXML
    private JFXButton btnEliminarInventario;
    @FXML
    private ComboBox<String> cmbNombreEstado;
    @FXML
    private JFXTextField txtProveedorInventario;
    @FXML
    private JFXTextField txtProductoInventario;
    @FXML
    private ComboBox<String> cmbCodigoProductoInventario;
    @FXML
    private TableView<InventarioProductos> tblInventario;
    @FXML
    private TableColumn<InventarioProductos, Integer> colCodigoProductoInventario;
    @FXML
    private TableColumn<InventarioProductos, Integer> colCantidadInventario;
    @FXML
    private TableColumn<InventarioProductos, String> colProveedorInventario;
    @FXML
    private TableColumn<InventarioProductos, String> colProductoInventario;
    @FXML
    private TableColumn<InventarioProductos, String> colEstadoInventario;
    @FXML
    private JFXButton btnBuscarInventario;
    @FXML
    private ComboBox<?> cmbFiltroCodigo;

    //Propiedades Estado
    @FXML
    private JFXTextField txtCodigoEstadoProducto;
    @FXML
    private JFXTextField txtDescEstadoProducto;
    @FXML
    private JFXButton btnAgregarEstadoProductos;
    @FXML
    private JFXButton btnEditarEstadoProductos;
    @FXML
    private JFXButton btnEliminarEstadoProductos;
    @FXML
    private TableView<EstadoProductos> tblEstadoProductos;
    @FXML
    private TableColumn<EstadoProductos, Integer> colCodigoEstadoCodigo;
    @FXML
    private TableColumn<EstadoProductos, String> colDescEstadoProductos;
    @FXML
    private JFXButton btnBuscarEstadoProductos;
    @FXML
    private JFXComboBox<String> cmbCodigoEstadoProductos;
    
    
    //========================================== CODIGO PARA VISTA INVENTARIO =============================================================
    
    //========================================== CODIGO PARA VISTA ESTADO PRODUCTO ========================================================

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void regresar(MouseEvent event) throws IOException {
         String menu = "org/moduloFacturacion/view/menuPrincipal.fxml";
        cambioScene.Cambio(menu,(Stage) anchor.getScene().getWindow());
    }
    
}
