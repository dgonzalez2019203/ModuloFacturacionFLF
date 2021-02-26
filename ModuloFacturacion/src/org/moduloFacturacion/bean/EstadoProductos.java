package org.moduloFacturacion.bean;

public class EstadoProductos {
    private String estadoProductoId;
    private String estadoProductoDesc;

    public EstadoProductos() {
    }

    public EstadoProductos(String estadoProductoId, String estadoProductoDesc) {
        this.estadoProductoId = estadoProductoId;
        this.estadoProductoDesc = estadoProductoDesc;
    }

    public String getEstadoProductoId() {
        return estadoProductoId;
    }

    public void setEstadoProductoId(String estadoProductoId) {
        this.estadoProductoId = estadoProductoId;
    }

    public String getEstadoProductoDesc() {
        return estadoProductoDesc;
    }

    public void setEstadoProductoDesc(String estadoProductoDesc) {
        this.estadoProductoDesc = estadoProductoDesc;
    }
    
    
}
