package org.moduloFacturacion.bean;

import java.sql.Date;

public class FacturasBuscadas {
    
    private int codigo;
    private double facturaTotalNeto;
    private double facturaTotalIva;
    private double facturaTotal;
    private Date facturaFecha;

    public FacturasBuscadas() {
    }

    public FacturasBuscadas(int codigo, double facturaTotalNeto, double facturaTotalIva, double facturaTotal, Date facturaFecha) {
        this.codigo = codigo;
        this.facturaTotalNeto = facturaTotalNeto;
        this.facturaTotalIva = facturaTotalIva;
        this.facturaTotal = facturaTotal;
        this.facturaFecha = facturaFecha;
    }

    public int getFacturaId() {
        return codigo;
    }

    public void setFacturaId(int facturaId) {
        this.codigo = facturaId;
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
