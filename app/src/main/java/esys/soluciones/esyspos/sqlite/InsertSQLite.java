package esys.soluciones.esyspos.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.*;
import android.widget.Toast;

import static esys.soluciones.esyspos.configuracion.General.*;

public class InsertSQLite {

    public static void Clientes(double IDENTIFICACION, String NOMBRE, int LISTA_PRECIO, Context context){
        try {
            SQLiteDatabase DB = BD_SQLite.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("IDENTIFICACION",IDENTIFICACION);
            values.put("NOMBRE",NOMBRE);
            values.put("LISTA_PRECIO",LISTA_PRECIO);

            Long ID_RESULTANTE = DB.insert(Tablas_SQLite_Clientes.CREAR_TBL_CLIENTES,Tablas_SQLite_Clientes.ID,values);

        }catch (Exception e){
            Toast.makeText(context,"Problema al Insertar Cliente: " + e.getMessage(), Toast.LENGTH_LONG);
        }
    }



}
