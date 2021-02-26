package org.moduloFacturacion.bean;

public class InventarioProductos {
    private String productoId;
    private int inventarioProductoCant;
    private String proveedorNombre;
    private String productoDesc;
    private String estadoProductoDesc;
    private Double precioCosto;
    
    public InventarioProductos() {
    }

    public InventarioProductos(String productoId, int inventarioProductoCant, String proveedorNombre, String productoDesc, String estadoProductoDesc, Double precioCosto) {
        this.productoId = productoId;
        this.inventarioProductoCant = inventarioProductoCant;
        this.proveedorNombre = proveedorNombre;
        this.productoDesc = productoDesc;
        this.estadoProductoDesc = estadoProductoDesc;
        this.precioCosto = precioCosto;
    }

    public Double getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(Double precioCosto) {
        this.precioCosto = precioCosto;
    }

    public Double getProductoPrecio() {
        return precioCosto;
    }

    public void setProductoPrecio(Double productoPrecio) {
        this.precioCosto = productoPrecio;
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
