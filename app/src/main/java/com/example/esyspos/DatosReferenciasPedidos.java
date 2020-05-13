package com.example.esyspos;

public class DatosReferenciasPedidos {
    private int codigoref, cantidad;
    private String nomref;
    private String valor;

    public DatosReferenciasPedidos(int codigoref, int cantidad, String nomref, String valor) {
        this.codigoref = codigoref;
        this.cantidad = cantidad;
        this.nomref = nomref;
        this.valor = valor;
    }

    public int getCodigoref() {
        return codigoref;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = this.cantidad + cantidad;
    }

    public String getNomref() {
        return nomref;
    }

    public String getValor() {
        return valor;
    }
}
