package org.moduloFacturacion.bean;

public class CategoriaProducto {
    String categoriaId;
    String categoriaNombre;

    public CategoriaProducto(String categoriaId, String categoriaNombre) {
        this.categoriaId = categoriaId;
        this.categoriaNombre = categoriaNombre;
    }

    public CategoriaProducto() {
    }

    public String getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(String categoriaId) {
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
