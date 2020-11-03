package org.moduloFacturacion.bean;

public class EstadoProductos {
    private int estadoProductoId;
    private String estadoProductoDesc;

    public EstadoProductos() {
    }

    public EstadoProductos(int estadoProductoId, String estadoProductoDesc) {
        this.estadoProductoId = estadoProductoId;
        this.estadoProductoDesc = estadoProductoDesc;
    }

    public int getEstadoProductoId() {
        return estadoProductoId;
    }

    public void setEstadoProductoId(int estadoProductoId) {
        this.estadoProductoId = estadoProductoId;
    }

    public String getEstadoProductoDesc() {
        return estadoProductoDesc;
    }

    public void setEstadoProductoDesc(String estadoProductoDesc) {
        this.estadoProductoDesc = estadoProductoDesc;
    }
    
    
}
