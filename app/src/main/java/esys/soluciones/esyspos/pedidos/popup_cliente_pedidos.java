package esys.soluciones.esyspos.pedidos;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import esys.soluciones.esyspos.R;

public class popup_cliente_pedidos extends AppCompatActivity {

    Button aceptar, cancelar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_cliente_pedido);

        aceptar = findViewById(R.id.btnCrearPedidoCliente);
        cancelar = findViewById(R.id.btnCancelarPedidoCliente);



    }
}
