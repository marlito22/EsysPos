package esys.soluciones.esyspos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import esys.soluciones.esyspos.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorBuscarReferencia
        extends RecyclerView.Adapter<AdaptadorBuscarReferencia.ConsultarReferenciasHolder>
        implements View.OnClickListener{
    private View.OnClickListener listener;
    Context context;
    List<DatosConsultarReferencias> datosConsultarReferencias;

    public AdaptadorBuscarReferencia(Context context, List<DatosConsultarReferencias> datosConsultarReferencias) {
        this.context = context;
        this.datosConsultarReferencias = datosConsultarReferencias;
    }

    public void filtrar(ArrayList<DatosConsultarReferencias> filtroReferencias){
        this.datosConsultarReferencias = filtroReferencias;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ConsultarReferenciasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buscar_referencia_pedido, parent, false );
        v.setOnClickListener(this);
        return new ConsultarReferenciasHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultarReferenciasHolder holder, int position) {
        holder.txtNomref.setText(datosConsultarReferencias.get(position).getNomref());
        holder.txtcodigoref.setText("Codigo: "+datosConsultarReferencias.get(position).getCodigoref());
        holder.txtpreven.setText(datosConsultarReferencias.get(position).getPreven());
    }

    @Override
    public int getItemCount() {
        return datosConsultarReferencias.size();
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


    public class ConsultarReferenciasHolder extends RecyclerView.ViewHolder {
        TextView txtNomref, txtcodigoref,txtpreven;


        public ConsultarReferenciasHolder(@NonNull View itemView) {
            super(itemView);
            txtNomref = itemView.findViewById(R.id.textview_nomref);
            txtcodigoref = itemView.findViewById(R.id.textview_codigoref);
            txtpreven = itemView.findViewById(R.id.textview_preven);
        }
    }
}
