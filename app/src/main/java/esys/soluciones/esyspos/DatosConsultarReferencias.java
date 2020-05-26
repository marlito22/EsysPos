package esys.soluciones.esyspos;

public class DatosConsultarReferencias {

    String Nomref;
    int codigoref;
    String preuco,preven,stock;


    public DatosConsultarReferencias(String nomref, int codigoref, String preuco, String preven, String stock) {
        Nomref = nomref;
        this.codigoref = codigoref;
        this.preuco = preuco;
        this.preven = preven;
        this.stock = stock;
    }

    public String getNomref() {
        return Nomref;
    }

    public int getCodigoref() {
        return codigoref;
    }

    public String getPreuco() {
        return preuco;
    }

    public String getPreven() {
        return preven;
    }

    public String getStock() {
        return stock;
    }
}
