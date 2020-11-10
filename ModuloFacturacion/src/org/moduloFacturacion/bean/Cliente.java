package org.moduloFacturacion.bean;

public class Cliente {
    String clienteId;
    String clienteNit;
    String clienteNombre;

    public Cliente() {
    }

    public Cliente(String clienteId, String clienteNit, String clienteNombre) {
        this.clienteId = clienteId;
        this.clienteNit = clienteNit;
        this.clienteNombre = clienteNombre;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNit() {
        return clienteNit;
    }

    public void setClienteNit(String clienteNit) {
        this.clienteNit = clienteNit;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    @Override
    public String toString() {
        return "Cliente{" + "clienteId=" + clienteId + ", clienteNit=" + clienteNit + ", clienteNombre=" + clienteNombre + '}';
    }
    
}
