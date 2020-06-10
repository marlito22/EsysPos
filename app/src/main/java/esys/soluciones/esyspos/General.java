package esys.soluciones.esyspos;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.sql.Connection;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by PROGRAMADOR2 on 01/11/2017.
 */


public class General extends Application {

    //VARIABLES GOBALES
    public static final NumberFormat _FormatoMoneda = NumberFormat.getCurrencyInstance();
    public static SharedPreferences sharpref;
    public static String servidor;
    public static String puerto ;
    public static String bd ;
    public static String user ;
    public static String pass;
    public static String Terminal;
    public static Connection connection;
    private static final int CODIGO_PARA_AUTORIZAR_CAMARA = 1;


    //public static Context activity_actual;

    private static ProgressDialog progressDialog = null;
    public static void Permisos(Activity activity){
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},CODIGO_PARA_AUTORIZAR_CAMARA);
        }

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET},CODIGO_PARA_AUTORIZAR_CAMARA);
        }

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_NETWORK_STATE},CODIGO_PARA_AUTORIZAR_CAMARA);
        }

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},CODIGO_PARA_AUTORIZAR_CAMARA);
        }

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},CODIGO_PARA_AUTORIZAR_CAMARA);
        }
    }
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
    public static void IniciarConexionMysql(Activity context, boolean procedimientos, int terminal){
        IniciarVariables();
        MySQLConexion conexion = new MySQLConexion(context);
        conexion.setProcedimiento(procedimientos);
        conexion.setTerminal(terminal);
        conexion.execute();
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
            editor.apply();
            ok.show();
        } catch (Exception E){
            error.show();
        }
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
    public static double FormatoNumero(String numero){
        Double Resultado = 0.0;

        if(numero == null) {
            Resultado = 0.0;
        } else {
            Resultado = Double.parseDouble(numero.replaceAll("[(az) | (AZ) | ($ ,)]", ""));
        }
        return Resultado ;
    }

    public static String FormatoMoneda(Object numero){
        if(numero == null) {
            numero = "$0";
        } else {
            if (numero.toString().matches("[0-9]*")){
                numero = _FormatoMoneda.format(numero).substring(1,numero.toString().indexOf("."));
            }else{
                if (numero.toString().contains(".")){
                    numero = _FormatoMoneda.format(FormatoNumero(numero.toString()));
                    numero = numero.toString().substring(0,numero.toString().indexOf("."));
                }else {
                    numero = _FormatoMoneda.format(FormatoNumero(numero.toString()));
                }

            }
        }
        return numero.toString() ;
    }

}
