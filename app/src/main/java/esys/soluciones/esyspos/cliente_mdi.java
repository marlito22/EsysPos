package esys.soluciones.esyspos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class cliente_mdi extends AppCompatActivity {
    Button atras, guardar;
    Activity activity;
    TextView Identificacion, PrimerNombre, SegundoNombre, PrimerAperllido,SegundoApellido, Direccion, Correo, Telefono;
    Query_MySQL.insertar_cliente_mysql clienteMysql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_mdi);
        activity = this;

        guardar = findViewById(R.id.btnGuardar);
        atras = findViewById(R.id.btnAtras);
        Identificacion = findViewById(R.id.txtNitcli);
        PrimerNombre = findViewById(R.id.txtPrimerNombre);
        SegundoNombre = findViewById(R.id.txtSegundoNombre);
        PrimerAperllido = findViewById(R.id.txtPrimerApellido);
        SegundoApellido = findViewById(R.id.txtSegundoApellido);
        Direccion = findViewById(R.id.txtDireccion);
        Correo = findViewById(R.id.txtCorreo);
        Telefono = findViewById(R.id.txtTelefono);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = PrimerNombre.getText().toString() + " " + SegundoNombre.getText().toString()+ " " + PrimerAperllido.getText().toString()+ " " + SegundoApellido.getText().toString();
                clienteMysql = new Query_MySQL.insertar_cliente_mysql(activity);
                clienteMysql.execute(
                        Identificacion.getText().toString(),
                        nombre.toUpperCase(),
                        Direccion.getText().toString(),
                        Telefono.getText().toString(),
                        Correo.getText().toString());
            }
        });


        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               activity.finish();
            }
        });


    }
}
