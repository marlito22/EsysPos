package esys.soluciones.esyspos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorPedidosReferencias
        extends RecyclerView.Adapter<AdaptadorPedidosReferencias.PedidosReferenciasHolder>
        implements View.OnClickListener, View.OnLongClickListener{
    private View.OnClickListener listener;
    private  View.OnLongClickListener longClickListener;
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
        v.setOnLongClickListener(this);
        return new PedidosReferenciasHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosReferenciasHolder holder, int position) {
        holder.txtNomref.setText("►"+datosReferenciasPedidos.get(position).getNomref());
        holder.txtcodigoref.setText(""+datosReferenciasPedidos.get(position).getCodigoref());
        holder.txtpreven.setText(datosReferenciasPedidos.get(position).getValor());
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

    public void setLong(View.OnLongClickListener aLong){
        this.longClickListener = aLong;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (longClickListener!=null){
            longClickListener.onLongClick(view);
        }
        return false;
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
