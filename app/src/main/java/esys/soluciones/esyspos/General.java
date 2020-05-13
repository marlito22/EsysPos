package esys.soluciones.esyspos;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.esyspos.R;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by PROGRAMADOR2 on 01/11/2017.
 */


public class General extends Application {

    //VARIABLES GOBALES
    public static SharedPreferences sharpref;
    public static String servidor;
    public static String puerto ;
    public static String bd ;
    public static String user ;
    public static String pass;
    public static String Terminal;
    public static int CONSECUTIVO_PEDIDO;
    public static Connection connection;


    //public static Context activity_actual;

    private static ProgressDialog progressDialog = null;
    private static DialogFragment dialogFragment = null;

    public static void cargando(Context context, boolean modo) {
        if(modo){
            progressDialog = new ProgressDialog(context);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setContentView(R.layout.popup_progressbar);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }else {
            progressDialog.dismiss();
        }
    }

    public static String FECHA_ACTUAL(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        String fecha_actual = formattedDate ;
        return fecha_actual;
    }
    public static void setConsecutivoPedido(int consecutivoPedido) {
        CONSECUTIVO_PEDIDO = consecutivoPedido;
    }
    public static void IniciarVariables(){

        try {
            servidor = sharpref.getString("Servidor", "");
            puerto = sharpref.getString("Puerto", "");
            bd = sharpref.getString("BD", "");
            user = sharpref.getString("User", "");
            pass = sharpref.getString("Pass", "");
            Terminal = sharpref.getString("Terminal", "");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void IniciarConexionMysql(Context context){
        IniciarVariables();
        new MySQLConexion(context).execute();
    }
    public static void CargarDatos(EditText txtServidor,EditText txtPuerto,EditText txtBD,EditText txtUser, EditText txtPass, Spinner terminal){
        txtServidor.setText(servidor);
        txtPuerto.setText(puerto);
        txtBD.setText(bd);
        txtUser.setText(user);
        txtPass.setText(pass);
        terminal.setSelection(obtenerPosicionItem(terminal,Terminal));
    }
    public static void GuardarDatos(EditText txtServidor, EditText txtPuerto, EditText txtBD, EditText txtUser, EditText txtPass, String Terminal, Context context){
        Toast ok = Toast.makeText(context,"Configuracion Guardada", Toast.LENGTH_SHORT);
        Toast error = Toast.makeText(context,"Problema al Guardar", Toast.LENGTH_SHORT);
        try {
            SharedPreferences.Editor editor = sharpref.edit();
            editor.putString("Servidor",txtServidor.getText().toString());
            editor.putString("Puerto",txtPuerto.getText().toString());
            editor.putString("BD",txtBD.getText().toString());
            editor.putString("User",txtUser.getText().toString());
            editor.putString("Pass",txtPass.getText().toString());
            editor.putString("Terminal",Terminal);
            editor.commit();
            ok.show();
        } catch (Exception E){
            error.show();
        }
    }
    public static boolean VerificarConexionDeRED(Context cxt){

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com.co");

            int val = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static String RegresarString(String str){
        String frase = "";
        for (int i = 0; i < str.length(); i++) {
            char current = str.charAt(i);

            if  ((current > 64 && current < 91) ||
                    (current == 32) ||
                    (current > 96 && current < 123) ||
                    (current > 47 && current < 63) ||
                    (current > 39 && current < 47))  {
                frase = frase + "" + current;
            }


        }

        return frase;
    }
    public static int obtenerPosicionItem(Spinner spinner, String terminal) {
        int posicion = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(terminal)) {
                posicion = i;
            }
        }
        return posicion;
    }

}
