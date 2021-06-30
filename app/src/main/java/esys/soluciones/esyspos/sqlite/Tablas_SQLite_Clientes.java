package esys.soluciones.esyspos.sqlite;

public class Tablas_SQLite_Clientes {

    public static final String ID = "ID";
    public static final String IDENTIFICACION = "IDENTIFICACION";
    public static final String NOMBRE = "NOMBRE";
    public static final String LISTA_PRECIO = "LISTA_PRECIO";
    public static final String TBL_CLIENTES = "clientes";

    public static final String CREAR_TBL_CLIENTES = "CREATE TABLE "+ TBL_CLIENTES + " (" +
             ID + " INTEGER, " +
             IDENTIFICACION + " REAL NOT NULL DEFAULT 0, " +
             NOMBRE + " TEXT NOT NULL DEFAULT 'SIN NOMBRE', " +
             LISTA_PRECIO + " INTEGER NOT NULL DEFAULT 0, " +
            " PRIMARY KEY(" + ID + ") " +
            ");";


    public static final String TBL_REFERENCIAS = "CREATE TABLE \"referencias\" (\n" +
            "\t\"CODIGO\"\tINTEGER,\n" +
            "\t\"NOMBRE\"\tTEXT NOT NULL DEFAULT 'SIN NOMBRE',\n" +
            "\t\"COSTO\"\tREAL NOT NULL DEFAULT 0,\n" +
            "\t\"VENTA\"\tREAL NOT NULL DEFAULT 0,\n" +
            "\t\"VENTA1\"\tREAL NOT NULL DEFAULT 0,\n" +
            "\t\"VENTA2\"\tREAL NOT NULL DEFAULT 0,\n" +
            "\t\"VENTA3\"\tREAL NOT NULL DEFAULT 0,\n" +
            "\t\"VENTA4\"\tREAL NOT NULL DEFAULT 0,\n" +
            "\t\"VENTA5\"\tREAL NOT NULL DEFAULT 0,\n" +
            "\t\"VENTA6\"\tREAL NOT NULL DEFAULT 0,\n" +
            "\t\"VENTA7\"\tREAL NOT NULL DEFAULT 0,\n" +
            "\t\"VENTA8\"\tREAL NOT NULL DEFAULT 0,\n" +
            "\tPRIMARY KEY(\"CODIGO\")\n" +
            ");";



}
