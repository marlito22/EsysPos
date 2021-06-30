package esys.soluciones.esyspos.configuracion;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.sql.Connection;
import java.text.NumberFormat;

import esys.soluciones.esyspos.MySQL.MySQLConexion;
import esys.soluciones.esyspos.R;
import esys.soluciones.esyspos.sqlite.ConexionSQL;


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
    private static final int CODIGO_PARA_AUTORIZAR = 200;
    public static ConexionSQL BD_SQLite;


    //public static Context activity_actual;

    private static ProgressDialog progressDialog = null;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void Permisos(Activity activity){
        int PERMISO_CAMARA = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        int PERMISO_INTERNET = ContextCompat.checkSelfPermission(activity, Manifest.permission.INTERNET);
        int PERMISO_ACCESS_NETWORK_STATE = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_NETWORK_STATE);
        int PERMISO_WRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int PERMISO_READ_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);


        if (PERMISO_CAMARA != PackageManager.PERMISSION_GRANTED
                || PERMISO_INTERNET != PackageManager.PERMISSION_GRANTED
                || PERMISO_ACCESS_NETWORK_STATE != PackageManager.PERMISSION_GRANTED
                || PERMISO_WRITE_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED
                || PERMISO_READ_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions( new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE},CODIGO_PARA_AUTORIZAR);
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
    public static AlertDialog.Builder MensajeConfirmacion(Context context, String Mensaje, String Titulo){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
        dialogo1.setTitle(Titulo);
        dialogo1.setMessage(Mensaje);
        dialogo1.setCancelable(false);
        return dialogo1;
    }


}
