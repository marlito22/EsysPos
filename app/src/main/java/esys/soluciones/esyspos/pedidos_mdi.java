package esys.soluciones.esyspos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import esys.soluciones.esyspos.R;

import java.util.ArrayList;
import java.util.List;

public class pedidos_mdi extends AppCompatActivity {
    private TextView txtNombreCliente;
    private long nitcli;
    private ImageButton agregar;
    private Intent intent;
    private Button enviar,btnAceptarCantidadProducto, btnCancelarCantidadProducto;

    private Activity activity;

    private EditText txtBuscarReferencia, txtCantidadProducto;
    private RecyclerView rv_BuscarReferencia;
    private agregar_producto agregar_producto;
    private Query_MySQL.llenar_datos_buscar_referencia_pedidos_mysql referencia_pedidos_mysql;
    private Query_MySQL.insertar_pedido_mysql pedido_mysql;
    private Query_MySQL.consecutivo_pedido_mysql consecutivoPedidoMysql;
    private RecyclerView recyclerView;
    private List<DatosReferenciasPedidos> datosReferenciasPedidos;
    private List<DatosConsultarReferencias> datosConsultarReferencias;
    private AdaptadorBuscarReferencia BuscarReferencias;
    private AdaptadorPedidosReferencias adaptadorPedidosReferencias;
    private TextView total_pedido,idpedido;

    private AlertDialog alertDialog_cantidad;
    private AlertDialog.Builder builder_cantidad_producto;
    private View view_cantidad_producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_mdi);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final Bundle bundle = getIntent().getExtras();

        txtNombreCliente = findViewById(R.id.txtNombreClientePedido);
        agregar = findViewById(R.id.btnAregarProducto);
        recyclerView = findViewById(R.id.recyclerview_buscar_referencia_pedidos);
        enviar = findViewById(R.id.btnEnviarPedido);
        total_pedido = findViewById(R.id.textview_TotalPedido);
        idpedido = findViewById(R.id.textview_IdPedido);

        activity = this;

        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        datosReferenciasPedidos = new ArrayList<>();
        datosConsultarReferencias = new ArrayList<>();

        consecutivoPedidoMysql = new Query_MySQL.consecutivo_pedido_mysql(this);
        consecutivoPedidoMysql.setConsecutivo(idpedido);
        referencia_pedidos_mysql = new Query_MySQL.llenar_datos_buscar_referencia_pedidos_mysql(this);
        referencia_pedidos_mysql.setDatosConsultarReferencias(datosConsultarReferencias);
        referencia_pedidos_mysql.setConsecutivoPedidoMysql(consecutivoPedidoMysql);
        referencia_pedidos_mysql.execute();

        adaptadorPedidosReferencias = new AdaptadorPedidosReferencias(this,datosReferenciasPedidos);

        //consecutivoPedidoMysql.execute();

        nitcli = Long.parseLong(bundle.getString("cliente").substring(bundle.getString("cliente").indexOf("-") + 1));
        txtNombreCliente.setText(bundle.getString("cliente").substring(0,bundle.getString("cliente").indexOf("-")));

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BuscarProductos();
                }catch (Exception e){
                    Log.e("Error",e.getMessage());
                }

            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnviarPedido();
            }
        });

    }

    public void BuscarProductos(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(pedidos_mdi.this);
        View view1 = getLayoutInflater().inflate(R.layout.activity_buscar_referencia, null);
        builder_cantidad_producto = new AlertDialog.Builder(pedidos_mdi.this);
        view_cantidad_producto = getLayoutInflater().inflate(R.layout.popup_cantidad_producto_pedido, null);

        txtBuscarReferencia = view1.findViewById(R.id.buscar_referencia_pedidos);
        rv_BuscarReferencia = view1.findViewById(R.id.rv_buscar_referencia);

        builder.setView(view1);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        BuscarReferencias = new AdaptadorBuscarReferencia(this,datosConsultarReferencias);
        adaptadorPedidosReferencias = new AdaptadorPedidosReferencias(this,datosReferenciasPedidos);

        agregar_producto = new agregar_producto(this);
        agregar_producto.setDatosConsultarReferencias(datosConsultarReferencias);
        agregar_producto.setDatosReferenciasPedidos(datosReferenciasPedidos);
        agregar_producto.setRv_BuscarReferencia(rv_BuscarReferencia);
        agregar_producto.setTxtBuscarReferencia(txtBuscarReferencia);
        agregar_producto.setDialog_cantidad(alertDialog_cantidad);
        agregar_producto.setBuscarReferencias(BuscarReferencias);
        agregar_producto.setPedidosReferencias(adaptadorPedidosReferencias);
        agregar_producto.setTxtCantidadProducto(txtCantidadProducto);
        agregar_producto.setRv_pedidos(recyclerView);
        agregar_producto.setDialog(alertDialog);
        agregar_producto.setTotal(total_pedido);
        agregar_producto.CantidadProducto(builder_cantidad_producto,view_cantidad_producto);
        agregar_producto.setPedidos_mdi(pedidos_mdi.this);


        txtBuscarReferencia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                agregar_producto.filtrar(s.toString());
            }
        });

        agregar_producto.AgregarReferencia();

    }

    public void EnviarPedido(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(pedidos_mdi.this);
        View view1 = getLayoutInflater().inflate(R.layout.popup_comentario_pedidos, null);
        final TextView comentario;
        Button enviar, cancelar;

        adaptadorPedidosReferencias = new AdaptadorPedidosReferencias(this,datosReferenciasPedidos);

        comentario = view1.findViewById(R.id.popup_txtcomentario);
        enviar = view1.findViewById(R.id.btnEnviarPedidoCliente);
        cancelar = view1.findViewById(R.id.btnCancelarPedido);

        builder.setView(view1);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        pedido_mysql = new Query_MySQL.insertar_pedido_mysql(this);
        pedido_mysql.setNitcli_pedido(""+nitcli);
        pedido_mysql.setTotal_pedido(total_pedido.getText().toString());
        pedido_mysql.setId_pedido(Integer.parseInt(idpedido.getText().toString()));
        pedido_mysql.setReferencias(adaptadorPedidosReferencias);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedido_mysql.setComentario_pedido(comentario.getText().toString());
                pedido_mysql.setDialog(alertDialog);
                pedido_mysql.execute();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public void EventosReciclerPedidos() {
        recyclerView.setLayoutManager(new GridLayoutManager(activity,1));
            adaptadorPedidosReferencias.setLong(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    try {
                        Toast.makeText(activity,"Prueba de OnLongClickListener",Toast.LENGTH_LONG).show();
                    }catch (Exception e){
                        Log.e("",e.getMessage());
                    }
                    return false;
                }
            });

            adaptadorPedidosReferencias.setListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity,"Prueba de OnClickListener",Toast.LENGTH_LONG).show();
                }
            });


            recyclerView.setAdapter(adaptadorPedidosReferencias);
    }

}
