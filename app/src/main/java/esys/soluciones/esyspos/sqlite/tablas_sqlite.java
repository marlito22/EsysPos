package esys.soluciones.esyspos.sqlite;

public class tablas_sqlite {

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

    public static final String TBL_CLIENTES = "CREATE TABLE \"clientes\" (\n" +
            "\t\"ID\"\tINTEGER,\n" +
            "\t\"IDENTIFICACION\"\tREAL NOT NULL DEFAULT 0,\n" +
            "\t\"NOMBRE\"\tTEXT NOT NULL DEFAULT 'SIN NOMBRE',\n" +
            "\t\"LISTA_PRECIO\"\tINTEGER NOT NULL DEFAULT 0,\n" +
            "\tPRIMARY KEY(\"ID\")\n" +
            ");";


}
