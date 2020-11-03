package org.moduloFacturacion.bean;


public class Proveedores {
        private int proveedorId;
        private String proveedorNombre;
        private String proveedorTelefono;

    public Proveedores() {
    }

    public Proveedores(int proveedorId, String proveedorNombre, String proveedorTelefono) {
        this.proveedorId = proveedorId;
        this.proveedorNombre = proveedorNombre;
        this.proveedorTelefono = proveedorTelefono;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getProveedorNombre() {
        return proveedorNombre;
    }

    public void setProveedorNombre(String proveedorNombre) {
        this.proveedorNombre = proveedorNombre;
    }

    public String getProveedorTelefono() {
        return proveedorTelefono;
    }

    public void setProveedorTelefono(String proveedorTelefono) {
        this.proveedorTelefono = proveedorTelefono;
    }
    
    
    
}
