
package org.moduloFacturacion.bean;

public class FacturacionDetalleBackup {
    
    private int facturaDetalleIdBackup;
    private String productoDesc;
    private int cantidadBackup;
    private double productoPrecio;
    private double totalParcialBackup;

    public FacturacionDetalleBackup() {
    }

    public FacturacionDetalleBackup(int facturaDetalleIdBackup, String productoDesc, int cantidadBackup, double productoPrecio, double totalParcialBackup) {
        this.facturaDetalleIdBackup = facturaDetalleIdBackup;
        this.productoDesc = productoDesc;
        this.cantidadBackup = cantidadBackup;
        this.productoPrecio = productoPrecio;
        this.totalParcialBackup = totalParcialBackup;
    }

    public int getFacturaDetalleIdBackup() {
        return facturaDetalleIdBackup;
    }

    public void setFacturaDetalleIdBackup(int facturaDetalleIdBackup) {
        this.facturaDetalleIdBackup = facturaDetalleIdBackup;
    }

    public String getProductoDesc() {
        return productoDesc;
    }

    public void setProductoDesc(String productoDesc) {
        this.productoDesc = productoDesc;
    }

    public int getCantidadBackup() {
        return cantidadBackup;
    }

    public void setCantidadBackup(int cantidadBackup) {
        this.cantidadBackup = cantidadBackup;
    }

    public double getProductoPrecio() {
        return productoPrecio;
    }

    public void setProductoPrecio(double productoPrecio) {
        this.productoPrecio = productoPrecio;
    }

    public double getTotalParcialBackup() {
        return totalParcialBackup;
    }

    public void setTotalParcialBackup(double totalParcialBackup) {
        this.totalParcialBackup = totalParcialBackup;
    }

    @Override
    public String toString() {
        return "FacturacionDetalleBackup{" + "facturaDetalleIdBackup=" + facturaDetalleIdBackup + ", productoDesc=" + productoDesc + ", cantidadBackup=" + cantidadBackup + ", productoPrecio=" + productoPrecio + ", totalParcialBackup=" + totalParcialBackup + '}';
    }

    
    
    
}
