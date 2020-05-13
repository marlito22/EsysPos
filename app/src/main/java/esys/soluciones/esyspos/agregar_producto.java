package esys.soluciones.esyspos;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esyspos.R;

import java.util.ArrayList;
import java.util.List;

public class agregar_producto {

        Context activity;
        TextView total;
        double total_referencia;
        AdaptadorBuscarReferencia BuscarReferencias;
        AdaptadorPedidosReferencias pedidosReferencias;
        List<DatosConsultarReferencias> datosConsultarReferencias;
        List<DatosReferenciasPedidos> datosReferenciasPedidos;
        EditText txtBuscarReferencia,txtCantidadProducto;
        RecyclerView rv_BuscarReferencia,rv_pedidos;
        AlertDialog dialog,dialog_cantidad;
        View view_registro_producto;

        private Button btnAceptarCantidadProducto, btnCancelarCantidadProducto;

        public void setTxtCantidadProducto(EditText txtCantidadProducto) {
            this.txtCantidadProducto = txtCantidadProducto;
        }

        public void setDialog_cantidad(AlertDialog dialog_cantidad) {
            this.dialog_cantidad = dialog_cantidad;
        }

        public void setPedidosReferencias(AdaptadorPedidosReferencias pedidosReferencias) {
            this.pedidosReferencias = pedidosReferencias;
        }

        public void setTotal(TextView total) {
            this.total = total;
        }

        public agregar_producto(Context activity) {
            this.activity = activity;
        }

        public void setDatosReferenciasPedidos(List<DatosReferenciasPedidos> datosReferenciasPedidos) {
            this.datosReferenciasPedidos = datosReferenciasPedidos;
        }

        public void setDatosConsultarReferencias(List<DatosConsultarReferencias> datosConsultarReferencias) {
            this.datosConsultarReferencias = datosConsultarReferencias;
        }

        public void setBuscarReferencias(AdaptadorBuscarReferencia buscarReferencias) {
            BuscarReferencias = buscarReferencias;
        }

        public void setDialog(AlertDialog dialog) {
            this.dialog = dialog;
        }

        public void setRv_pedidos(RecyclerView rv_pedidos) {
            this.rv_pedidos = rv_pedidos;
        }

        public void setTxtBuscarReferencia(EditText txtBuscarReferencia) {
            this.txtBuscarReferencia = txtBuscarReferencia;
        }

        public void setRv_BuscarReferencia(RecyclerView rv_BuscarReferencia) {
            this.rv_BuscarReferencia = rv_BuscarReferencia;
        }

        public void filtrar(String texto){
            ArrayList<DatosConsultarReferencias> list = new ArrayList<>();

            for (DatosConsultarReferencias i : datosConsultarReferencias) {
                if (i.getNomref().toLowerCase().contains(texto.toLowerCase())) {
                    list.add(i);
                }
            }
            BuscarReferencias.filtrar(list);
        }

        public void CantidadProducto(AlertDialog.Builder builder, View view1){
        txtCantidadProducto = view1.findViewById(R.id.popup_txtcantidad);
        btnAceptarCantidadProducto = view1.findViewById(R.id.btnAceptarCantidad);
        btnCancelarCantidadProducto = view1.findViewById(R.id.btnCancelarCantidad);

        builder.setView(view1);
        dialog_cantidad = builder.create();
        dialog_cantidad.setCanceledOnTouchOutside(false);

        btnAceptarCantidadProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int cantidad = Integer.parseInt(txtCantidadProducto.getText().toString());
                    int codigo = datosConsultarReferencias.get(rv_BuscarReferencia.getChildAdapterPosition(view_registro_producto)).getCodigoref();
                    String precio = datosConsultarReferencias.get(rv_BuscarReferencia.getChildAdapterPosition(view_registro_producto)).getPreven().replace(",","");
                    String nombre = datosConsultarReferencias.get(rv_BuscarReferencia.getChildAdapterPosition(view_registro_producto)).getNomref();

                    if(pedidosReferencias.getItemCount() == 0){
                        datosReferenciasPedidos.add( new DatosReferenciasPedidos(codigo,cantidad,nombre,precio));
                    }else{
                        for (int i=0; i < pedidosReferencias.getItemCount();i++){
                            if(pedidosReferencias.datosReferenciasPedidos.get(i).getCodigoref() == codigo){
                                pedidosReferencias.datosReferenciasPedidos.get(i).setCantidad(cantidad);
                            }else{
                                datosReferenciasPedidos.add( new DatosReferenciasPedidos(codigo,cantidad,nombre,precio));
                            }
                        }
                    }
                    total_referencia = Double.parseDouble(total.getText().toString().replace(",","")) + (cantidad * Double.parseDouble(precio));
                    total.setText(""+total_referencia);
                    pedidosReferencias = new AdaptadorPedidosReferencias(activity,datosReferenciasPedidos);
                    rv_pedidos.setAdapter(pedidosReferencias);
                    dialog_cantidad.dismiss();
                    dialog.dismiss();
                }catch (Exception e){
                    Log.e("Er",e.getMessage());
                }
            }
        });

        btnCancelarCantidadProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_cantidad.dismiss();
            }
        });

    }

        public void AgregarReferencia() {
            rv_BuscarReferencia.setLayoutManager(new GridLayoutManager(activity,1));
            try{
                BuscarReferencias.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            view_registro_producto =v;
                            dialog_cantidad.show();
                        }catch (Exception e){
                            Log.e("",e.getMessage());
                        }

                    }
                });

            }catch (Exception e){
                Toast.makeText(activity,e.getMessage(),Toast.LENGTH_LONG).show();
            }
            rv_BuscarReferencia.setAdapter(BuscarReferencias);
            dialog.show();
        }
    }



