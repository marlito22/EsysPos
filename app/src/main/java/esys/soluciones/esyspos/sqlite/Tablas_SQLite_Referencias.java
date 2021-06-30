package esys.soluciones.esyspos.sqlite;

public class Tablas_SQLite_Referencias {

    public static final String CODIGO = "";
    public static final String NOMBRE = "NOMBRE";
    public static final String COSTO = "COSTO";
    public static final String VENTA = "VENTA";
    public static final String VENTA1= "VENTA1";
    public static final String VENTA2= "VENTA2";
    public static final String VENTA3= "VENTA3";
    public static final String VENTA4= "VENTA4";
    public static final String VENTA5= "VENTA5";
    public static final String VENTA6= "VENTA6";
    public static final String VENTA7= "VENTA7";
    public static final String VENTA8= "VENTA8";
    public static final String TBL_REFERENCIAS = "referencias";



    public static final String CREAR_TBL_REFERENCIAS = "CREATE TABLE " + TBL_REFERENCIAS + "(" +
            CODIGO +" INTEGER, " +
            NOMBRE +" TEXT NOT NULL DEFAULT 'SIN NOMBRE', " +
            COSTO +" REAL NOT NULL DEFAULT 0, " +
            VENTA +" REAL NOT NULL DEFAULT 0, " +
            VENTA1 +" REAL NOT NULL DEFAULT 0, " +
            VENTA2 +" REAL NOT NULL DEFAULT 0, " +
            VENTA3 +" REAL NOT NULL DEFAULT 0, " +
            VENTA4 +" REAL NOT NULL DEFAULT 0, " +
            VENTA5 +" REAL NOT NULL DEFAULT 0, " +
            VENTA6 +" REAL NOT NULL DEFAULT 0, " +
            VENTA7 +" REAL NOT NULL DEFAULT 0, " +
            VENTA8 +" REAL NOT NULL DEFAULT 0, " +
            " PRIMARY KEY(" + CODIGO + "));";
}
