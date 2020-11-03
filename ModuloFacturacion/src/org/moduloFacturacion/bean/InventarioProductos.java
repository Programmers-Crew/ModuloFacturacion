package org.moduloFacturacion.bean;

public class InventarioProductos {
    private int inventarioProductoId;
    private int inventarioProductoCant;
    private String productoDesc;
    private String estadoProductoDesc;

    public InventarioProductos() {
    }

    public InventarioProductos(int inventarioProductoId, int inventarioProductoCant, String productoDesc, String estadoProductoDesc) {
        this.inventarioProductoId = inventarioProductoId;
        this.inventarioProductoCant = inventarioProductoCant;
        this.productoDesc = productoDesc;
        this.estadoProductoDesc = estadoProductoDesc;
    }

    public int getInventarioProductoId() {
        return inventarioProductoId;
    }

    public void setInventarioProductoId(int inventarioProductoId) {
        this.inventarioProductoId = inventarioProductoId;
    }

    public int getInventarioProductoCant() {
        return inventarioProductoCant;
    }

    public void setInventarioProductoCant(int inventarioProductoCant) {
        this.inventarioProductoCant = inventarioProductoCant;
    }

    public String getProductoDesc() {
        return productoDesc;
    }

    public void setProductoDesc(String productoDesc) {
        this.productoDesc = productoDesc;
    }

    public String getEstadoProductoDesc() {
        return estadoProductoDesc;
    }

    public void setEstadoProductoDesc(String estadoProductoDesc) {
        this.estadoProductoDesc = estadoProductoDesc;
    }



    
}
