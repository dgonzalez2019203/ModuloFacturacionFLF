package org.moduloFacturacion.bean;

public class ProductoBuscado {
    
    private String productoDesc;
    private int cantidad;
    private double productoPrecio;

    public ProductoBuscado() {
    }

    public ProductoBuscado(String productoDesc, int cantidad, double productoPrecio) {
        this.productoDesc = productoDesc;
        this.cantidad = cantidad;
        this.productoPrecio = productoPrecio;
    }

    public String getProductoDesc() {
        return productoDesc;
    }

    public void setProductoDesc(String productoDesc) {
        this.productoDesc = productoDesc;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getProductoPrecio() {
        return productoPrecio;
    }

    public void setProductoPrecio(double productoPrecio) {
        this.productoPrecio = productoPrecio;
    }
    
    
    
}
