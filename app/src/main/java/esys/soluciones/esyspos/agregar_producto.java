package esys.soluciones.esyspos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import static esys.soluciones.esyspos.General.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import esys.soluciones.esyspos.adaptadores_rv.AdaptadorBuscarReferencia;
import esys.soluciones.esyspos.adaptadores_rv.AdaptadorPedidosReferencias;

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
        AlertDialog dialog;

        View view_registro_producto;
        pedidos_mdi pedidos_mdi;
        AlertDialog.Builder builder_cantidad_producto;
        View view_cantidad_producto;

        private Button btnAceptarCantidadProducto, btnCancelarCantidadProducto;
        private ImageView imgImagenProducto;

        public void setView_registro_producto(View view_registro_producto) {
            this.view_registro_producto = view_registro_producto;
        }

    public void setBuilder_cantidad_producto(AlertDialog.Builder builder_cantidad_producto) {
        this.builder_cantidad_producto = builder_cantidad_producto;
    }

    public void setView_cantidad_producto(View view_cantidad_producto) {
        this.view_cantidad_producto = view_cantidad_producto;
    }

    public void setPedidos_mdi(esys.soluciones.esyspos.pedidos_mdi pedidos_mdi) {
            this.pedidos_mdi = pedidos_mdi;
        }

        public void setTxtCantidadProducto(EditText txtCantidadProducto) {
            this.txtCantidadProducto = txtCantidadProducto;
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

        public void CantidadProducto(AlertDialog.Builder builder, View view1 ){
        txtCantidadProducto = view1.findViewById(R.id.popup_txtcantidad);
        btnAceptarCantidadProducto = view1.findViewById(R.id.btnAceptarCantidad);
        btnCancelarCantidadProducto = view1.findViewById(R.id.btnCancelarCantidad);
        imgImagenProducto = view1.findViewById(R.id.imageView_foto_articulo);

        final AlertDialog dialog_cantidad;


        builder.setView(view1);
        dialog_cantidad = builder.create();
        dialog_cantidad.setCanceledOnTouchOutside(false);

            /*Cargar nuestra imagen*/
            File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "POS/Referencias/Prueba.jpg");
            String fname=new File(Environment.getExternalStorageDirectory(), "Prueba.jpg").getAbsolutePath();
            /*Opcional. En caso de que estemos cargando muchas imagenes, es importante carga0rlas en un tamaño suficiente para que se vean bien pero que
            no consuman mucha memoria. En este caso, al usar inSampleSize = 4 lo que hará Android es cargar la imagen a 1/4 de su tamaño original*/
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            /*Si no hubieramos creado la imagen de la manera correcta, el método de decodeFile siempre nos regresaría nulo*/
            Bitmap bmp = BitmapFactory.decodeFile(fname,options);
            if (file.exists()) {
                if (bmp == null) {
                    imgImagenProducto.setImageResource(R.drawable.logo); //poner imagen genérica
                } else {
                    imgImagenProducto.setImageBitmap(bmp);
                    bmp = null; //importante cerrar las referencias para que no se queden en memoria
                }
                file = null;//importante cerrar las referencias para que no se queden en memoria
            }




        btnAceptarCantidadProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    int cantidad = Integer.parseInt(txtCantidadProducto.getText().toString());
                    int codigo =  BuscarReferencias.datosConsultarReferencias.get(rv_BuscarReferencia.getChildAdapterPosition(view_registro_producto)).getCodigoref();
                    String precio = FormatoMoneda( BuscarReferencias.datosConsultarReferencias.get(rv_BuscarReferencia.getChildAdapterPosition(view_registro_producto)).getPreven());
                    String nombre =  BuscarReferencias.datosConsultarReferencias.get(rv_BuscarReferencia.getChildAdapterPosition(view_registro_producto)).getNomref();
                    String total_precio = FormatoMoneda(""+(FormatoNumero(precio) * cantidad)) ;
                    boolean NuevoProducto = true;

                    if(pedidosReferencias.getItemCount() == 0){
                        datosReferenciasPedidos.add( new DatosReferenciasPedidos(codigo,cantidad,nombre,precio,total_precio));
                    }else{
                        for (int i=0; i < pedidosReferencias.getItemCount();i++){
                            if(pedidosReferencias.datosReferenciasPedidos.get(i).getCodigoref() == codigo){
                                pedidosReferencias.datosReferenciasPedidos.get(i).setCantidad(cantidad);
                                //total_precio = FormatoMoneda((FormatoNumero(pedidosReferencias.datosReferenciasPedidos.get(i).getTotal()) + FormatoNumero(total_precio)));
                                pedidosReferencias.datosReferenciasPedidos.get(i).setTotal(FormatoMoneda((FormatoNumero(pedidosReferencias.datosReferenciasPedidos.get(i).getTotal()) + FormatoNumero(total_precio))));
                                NuevoProducto = false;
                                break;
                            }
                        }
                        if (NuevoProducto){
                            datosReferenciasPedidos.add( new DatosReferenciasPedidos(codigo,cantidad,nombre,precio,total_precio));
                        }
                    }
                    total_referencia = FormatoNumero(total.getText().toString()) + FormatoNumero(total_precio);
                    total.setText(FormatoMoneda(total_referencia));
                    pedidosReferencias = new AdaptadorPedidosReferencias(activity,datosReferenciasPedidos);
                    rv_pedidos.setAdapter(pedidosReferencias);
                    pedidos_mdi.EventosReciclerPedidos();
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
                dialog.dismiss();
            }
        });

            dialog_cantidad.show();


    }

        public void AgregarReferencia() {
            rv_BuscarReferencia.setLayoutManager(new GridLayoutManager(activity,1));
            try{
                BuscarReferencias.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            CantidadProducto(builder_cantidad_producto,view_cantidad_producto);
                            view_registro_producto =v;
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



