package com.example.esyspos;

import androidx.appcompat.app.AppCompatActivity;
import static com.example.esyspos.General.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class configuracion extends AppCompatActivity {

    private EditText servidor,puerto,basedatos,usuario,contraseña;
    private Button guardar,cancelar;
    private Activity activity;
    private Spinner terminal;
    private String[] array = {"1","2","3","4","6","7","8","9"};
    private String DatoSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        //instanciamos la actividad del formulario
        activity = this;
        //TextBox's
        servidor = findViewById(R.id.txtServidor);
        puerto = findViewById(R.id.txtPuerto);
        basedatos = findViewById(R.id.txtBD);
        usuario = findViewById(R.id.txtUsuario);
        contraseña = findViewById(R.id.txtContraseña);
        //Botones
        guardar = findViewById(R.id.btnGuardar);
        cancelar = findViewById(R.id.btnAtras);
        //Iniciamos variables globales
        IniciarVariables();
        //cargar datos de spinner
        terminal = findViewById(R.id.spinner_terminal);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,R.layout.spinner_item_formato,array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        terminal.setAdapter(adapter);
        //cargamos archivo de configuracion
        CargarDatos(servidor,puerto,basedatos,usuario,contraseña,terminal);
        //eventos de los botones
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuardarDatos(servidor,puerto,basedatos,usuario,contraseña,DatoSpinner,getApplicationContext());
                IniciarConexionMysql(configuracion.this);
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });

        terminal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DatoSpinner = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
