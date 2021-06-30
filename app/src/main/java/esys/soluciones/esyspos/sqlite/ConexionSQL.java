package esys.soluciones.esyspos.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import static esys.soluciones.esyspos.sqlite.Tablas_SQLite_Clientes.*;

public class ConexionSQL extends SQLiteOpenHelper {
    private Context context;

    public ConexionSQL(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(TBL_REFERENCIAS);
            db.execSQL(TBL_CLIENTES);
        }catch (Exception e){
            Toast.makeText(context,"Configuracion Guardada", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
