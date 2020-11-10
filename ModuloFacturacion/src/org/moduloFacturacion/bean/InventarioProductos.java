package org.moduloFacturacion.bean;

public class InventarioProductos {
    private String productoId;
    private int inventarioProductoCant;
    private String productoDesc;
    private String estadoProductoDesc;
    private String proveedorNombre;
    
    public InventarioProductos() {
    }

    public InventarioProductos(String productoId, int inventarioProductoCant, String productoDesc, String estadoProductoDesc, String proveedorNombre) {
        this.productoId = productoId;
        this.inventarioProductoCant = inventarioProductoCant;
        this.productoDesc = productoDesc;
        this.estadoProductoDesc = estadoProductoDesc;
        this.proveedorNombre = proveedorNombre;
    }

    public String getProductoId() {
        return productoId;
    }

    public void setProductoId(String productoId) {
        this.productoId = productoId;
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

    public String getProveedorNombre() {
        return proveedorNombre;
    }

    public void setProveedorNombre(String proveedorNombre) {
        this.proveedorNombre = proveedorNombre;
    }





    
}
