package esys.soluciones.esyspos;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConexion extends AsyncTask<Void,Integer,Boolean> {

    private Connection cn;
    private String error_mysql;
    private Activity activity;
    boolean procedimiento;
    private Query_MySQL.procedimientos_mysql procedimientosMysql;
    private int terminal;

    public void setProcedimiento(boolean procedimiento) {
        this.procedimiento = procedimiento;
    }

    public MySQLConexion(Activity activity) {
        this.activity = activity;
    }

    public void setTerminal(int terminal) {
        this.terminal = terminal;
    }

    @Override
    protected void onPreExecute() {
        General.cargando(activity,true);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            String cadenaConexion = "jdbc:mysql://"+ General.servidor+":"+ General.puerto+"/"+ General.bd;
            Class.forName( "com.mysql.jdbc.Driver").newInstance();
            cn = DriverManager.getConnection(cadenaConexion, General.user, General.pass);
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
            General.connection = cn;
            if (procedimiento){
                procedimientosMysql = new Query_MySQL.procedimientos_mysql(activity);
                procedimientosMysql.setTerminal(terminal);
                procedimientosMysql.execute();
            }else{
                Toast.makeText(activity,"Conexion Estable" , Toast.LENGTH_SHORT).show();
                General.cargando(activity,false);
            }
        }else {
            General.connection = null;
            Toast.makeText(activity,"Error de Conexion: \r\n\r\n " + error_mysql, Toast.LENGTH_SHORT).show();
            General.cargando(activity,false);
        }
    }

}