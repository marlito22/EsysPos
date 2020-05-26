package esys.soluciones.esyspos;

public class DatosReferenciasPedidos {
    private int codigoref, cantidad;
    private String nomref;
    private String valor;
    private String total;

    public DatosReferenciasPedidos(int codigoref, int cantidad, String nomref, String valor, String total) {
        this.codigoref = codigoref;
        this.cantidad = cantidad;
        this.nomref = nomref;
        this.valor = valor;
        this.total = total;
    }

    public String getTotal() {
        return total;
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

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNomref() {
        return nomref;
    }

    public String getValor() {
        return valor;
    }
}
