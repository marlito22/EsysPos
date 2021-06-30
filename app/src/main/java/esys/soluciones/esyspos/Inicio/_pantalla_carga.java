package esys.soluciones.esyspos.Inicio;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import static esys.soluciones.esyspos.configuracion.General.*;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import esys.soluciones.esyspos.configuracion.General;
import esys.soluciones.esyspos.MySQL.Query_MySQL;
import esys.soluciones.esyspos.R;
import esys.soluciones.esyspos.sqlite.ConexionSQL;

public class _pantalla_carga extends AppCompatActivity {

    private Button iniciar_sesion;
    private ImageButton configuracion;
    private EditText usuario, contraseña;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__pantalla_carga);

        Permisos(this);

        BD_SQLite = new ConexionSQL(this,"eSys",null,1);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //iniciamos variables de la conexion
        General.sharpref = getSharedPreferences("Conta",MODE_PRIVATE);
        General.IniciarVariables();
        General.IniciarConexionMysql(this,false,0);
        if(General.servidor.equals("")){
            Toast.makeText(getApplicationContext(),"Configure su Dispositivo > Configuracion",Toast.LENGTH_LONG).show();
        }
        //Botones
        iniciar_sesion = findViewById(R.id.btnEnviarPedido);
        configuracion = findViewById(R.id.bntConta);
        //textBox
        usuario = findViewById(R.id.txtNombreUsuario);
        contraseña = findViewById(R.id.txtContraseña);
        //eventos de los botones
        iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Query_MySQL.login_mysql(_pantalla_carga.this).execute(""+usuario.getText(),""+contraseña.getText());
            }
        });
        configuracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_pantalla_carga.this, esys.soluciones.esyspos.configuracion.configuracion.class);
                startActivity(intent);
            }
        });


    }
}
