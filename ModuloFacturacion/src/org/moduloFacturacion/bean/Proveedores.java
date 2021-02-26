package org.moduloFacturacion.bean;


public class Proveedores {
        private String proveedorId;
        private String proveedorNombre;
        private String proveedorTelefono;

    public Proveedores() {
    }

    public Proveedores(String proveedorId, String proveedorNombre, String proveedorTelefono) {
        this.proveedorId = proveedorId;
        this.proveedorNombre = proveedorNombre;
        this.proveedorTelefono = proveedorTelefono;
    }

    public String getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(String proveedorId) {
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
