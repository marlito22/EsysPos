package esys.soluciones.esyspos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import esys.soluciones.esyspos.adaptadores_rv.Adaptador_GV_Principal;

public class mdi extends AppCompatActivity {

    GridView gridView_principal;
    String[] nombres_menus = {"Cuadre","Pedidos","Clientes","Consultar"};
    int[] numero_imagen = {R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo};
    Intent intent;
    Query_MySQL.llenar_AutoCompleteTextView lista_predictiva;
    private AutoCompleteTextView completeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdi);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gridView_principal = findViewById(R.id.GV_principal);
        Adaptador_GV_Principal AGV = new Adaptador_GV_Principal(this,nombres_menus,numero_imagen);
        gridView_principal.setAdapter(AGV);
        gridView_principal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch(position) {
                    case 0:
                        intent = new Intent(mdi.this, cuadre_caja.class);
                        startActivity(intent);
                        break;
                    case 1:
                        CrearPedidoCliente();
                        break;
                    case 2:
                        intent = new Intent(mdi.this, cliente_mdi.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(mdi.this, consultar_referencia.class);
                        startActivity(intent);
                        break;
                    case 4:
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"Le diste click a: " +nombres_menus[+position],Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void CrearPedidoCliente(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(mdi.this);
        View view1 = getLayoutInflater().inflate(R.layout.popup_cliente_pedido, null);
        completeTextView = view1.findViewById(R.id.popup_txtcliente);
        Button crear_pedido = view1.findViewById(R.id.btnCrearPedidoCliente);
        Button cancelar_pedido = view1.findViewById(R.id.btnCancelarPedidoCliente);
        builder.setView(view1);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        lista_predictiva = new Query_MySQL.llenar_AutoCompleteTextView(mdi.this);

        cancelar_pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        crear_pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                intent = new Intent(mdi.this, pedidos_mdi.class);
                intent.putExtra("cliente",completeTextView.getText().toString());
                startActivity(intent);
            }
        });


        lista_predictiva.setTextView(completeTextView);
        lista_predictiva.setDialog(alertDialog);
        lista_predictiva.execute();



    }

}
