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
}
