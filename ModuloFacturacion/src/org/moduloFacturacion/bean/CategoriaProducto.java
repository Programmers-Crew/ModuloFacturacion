package org.moduloFacturacion.bean;

public class CategoriaProducto {
    int categoriaId;
    String categoriaNombre;

    public CategoriaProducto(int categoriaId, String categoriaNombre) {
        this.categoriaId = categoriaId;
        this.categoriaNombre = categoriaNombre;
    }

    public CategoriaProducto() {
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    @Override
    public String toString() {
        return "categoriaProducto{" + "categoriaId=" + categoriaId + ", categoriaNombre=" + categoriaNombre + '}';
    }
    
    
    
}
