package esys.soluciones.esyspos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static esys.soluciones.esyspos.General.*;

public class AdaptadorPedidosReferencias extends RecyclerView.Adapter<AdaptadorPedidosReferencias.PedidosReferenciasHolder>
        implements View.OnClickListener{
    private View.OnClickListener listener;
    Context context;
    List<DatosReferenciasPedidos> datosReferenciasPedidos;

    public AdaptadorPedidosReferencias(Context context, List<DatosReferenciasPedidos> datosReferenciasPedidos) {
        this.context = context;
        this.datosReferenciasPedidos = datosReferenciasPedidos;
    }

    @NonNull
    @Override
    public PedidosReferenciasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_referencias_pedidos, parent, false );
        v.setOnClickListener(this);
        return new PedidosReferenciasHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosReferenciasHolder holder, int position) {
        holder.txtNomref.setText("►"+datosReferenciasPedidos.get(position).getNomref());
        holder.txtcodigoref.setText(""+datosReferenciasPedidos.get(position).getCodigoref());
        holder.txtpreven.setText( FormatoNumero.format(FormatoNumero(datosReferenciasPedidos.get(position).getValor())));
        holder.txtcabtidad.setText(""+datosReferenciasPedidos.get(position).getCantidad());
        holder.txttotal.setText(datosReferenciasPedidos.get(position).getTotal());
    }

    @Override
    public int getItemCount() {
        return datosReferenciasPedidos.size();
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }


    public class PedidosReferenciasHolder extends RecyclerView.ViewHolder {
        TextView txtNomref, txtcodigoref,txtpreven,txtcabtidad,txttotal;

        public PedidosReferenciasHolder(@NonNull View itemView) {
            super(itemView);
            txtNomref = itemView.findViewById(R.id.txt_nombre_referencia_pedidos);
            txtcodigoref = itemView.findViewById(R.id.txt_codigo_referencia_pedidos);
            txtcabtidad = itemView.findViewById(R.id.txt_cantidad_referencia_pedidos);
            txtpreven = itemView.findViewById(R.id.txt_precio_referencia_pedidos);
            txttotal = itemView.findViewById(R.id.txt_total_referencia_pedidos);
        }
    }
}
