package org.moduloFacturacion.bean;

import java.sql.Date;

public class FacturasBuscadas {
    
    private String facturaId;
    private double facturaTotalNeto;
    private double facturaTotalIva;
    private double facturaTotal;
    private Date facturaFecha;
    private String tipoFacturaDesc;

    public FacturasBuscadas() {
    }

    public FacturasBuscadas(String facturaId, double facturaTotalNeto, double facturaTotalIva, double facturaTotal, Date facturaFecha, String tipoFacturaDesc) {
        this.facturaId = facturaId;
        this.facturaTotalNeto = facturaTotalNeto;
        this.facturaTotalIva = facturaTotalIva;
        this.facturaTotal = facturaTotal;
        this.facturaFecha = facturaFecha;
        this.tipoFacturaDesc = tipoFacturaDesc;
    }

    public String getTipoFacturaDesc() {
        return tipoFacturaDesc;
    }

    public void setTipoFacturaDesc(String tipoFacturaDesc) {
        this.tipoFacturaDesc = tipoFacturaDesc;
    }
    

    public String getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(String facturaId) {
        this.facturaId = facturaId;
    }

    public double getFacturaTotalNeto() {
        return facturaTotalNeto;
    }

    public void setFacturaTotalNeto(double facturaTotalNeto) {
        this.facturaTotalNeto = facturaTotalNeto;
    }

    public double getFacturaTotalIva() {
        return facturaTotalIva;
    }

    public void setFacturaTotalIva(double facturaTotalIva) {
        this.facturaTotalIva = facturaTotalIva;
    }

    public double getFacturaTotal() {
        return facturaTotal;
    }

    public void setFacturaTotal(double facturaTotal) {
        this.facturaTotal = facturaTotal;
    }

    public Date getFacturaFecha() {
        return facturaFecha;
    }

    public void setFacturaFecha(Date facturaFecha) {
        this.facturaFecha = facturaFecha;
    }

    

    
}
