package com.example.esyspos;

public class DatosConsultarReferencias {

    String Nomref;
    int codigoref;
    String preuco,preven,preven1,preven2,preven3,preven4,stock;


    public DatosConsultarReferencias(String nomref, int codigoref, String preuco, String preven, String preven1, String preven2, String preven3, String preven4, String stock) {
        Nomref = nomref;
        this.codigoref = codigoref;
        this.preuco = preuco;
        this.preven = preven;
        this.preven1 = preven1;
        this.preven2 = preven2;
        this.preven3 = preven3;
        this.preven4 = preven4;
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

    public String getPreven1() {
        return preven1;
    }

    public String getPreven2() {
        return preven2;
    }

    public String getPreven3() {
        return preven3;
    }

    public String getPreven4() {
        return preven4;
    }

    public String getStock() {
        return stock;
    }
}
