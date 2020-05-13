package com.example.esyspos;
import static com.example.esyspos.General.*;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConexion extends AsyncTask<Void,Integer,Boolean> {

    private Connection cn;
    private String error_mysql;
    Context activity;

    public MySQLConexion(Context context) {
        activity = context;
    }

    @Override
    protected void onPreExecute() {
        cargando(activity,true);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            String cadenaConexion = "jdbc:mysql://"+servidor+":"+puerto+"/"+bd;
            Class.forName( "com.mysql.jdbc.Driver").newInstance();
            cn = DriverManager.getConnection(cadenaConexion,user,pass);
            if (cn == null){
                return false;
            }
        } catch (Exception e) {
            error_mysql = e.getMessage();
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean resultado) {
        if(resultado) {
            connection = cn;
            Toast.makeText(activity,"Conexion Estable" , Toast.LENGTH_SHORT).show();
        }else {
            connection = null;
            Toast.makeText(activity,"Error de Conexion: \r\n\r\n " + error_mysql, Toast.LENGTH_SHORT).show();
        }
        cargando(activity,false);
    }

}