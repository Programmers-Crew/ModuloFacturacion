package org.moduloFacturacion.bean;

public class Productos {
    int productoId;
    String productoDesc;
    String proveedorNombre;
    String categoriaNombre;
    Double productoPrecio;

    public Productos(int productoId, String productoDesc, String proveedorNombre, String categoriaNombre, Double productoPrecio) {
        this.productoId = productoId;
        this.productoDesc = productoDesc;
        this.proveedorNombre = proveedorNombre;
        this.categoriaNombre = categoriaNombre;
        this.productoPrecio = productoPrecio;
    }

    public Productos() {
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getProductoDesc() {
        return productoDesc;
    }

    public void setProductoDesc(String productoDesc) {
        this.productoDesc = productoDesc;
    }

    public String getProveedorNombre() {
        return proveedorNombre;
    }

    public void setProveedorNombre(String proveedorNombre) {
        this.proveedorNombre = proveedorNombre;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public Double getProductoPrecio() {
        return productoPrecio;
    }

    public void setProductoPrecio(Double productoPrecio) {
        this.productoPrecio = productoPrecio;
    }

    @Override
    public String toString() {
        return "Productos{" + "productoId=" + productoId + ", productoDesc=" + productoDesc + ", proveedorNombre=" + proveedorNombre + ", categoriaNombre=" + categoriaNombre + ", productoPrecio=" + productoPrecio + '}';
    }
    
    
    
    
    
}
