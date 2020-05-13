package com.example.esyspos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorConsultarReferencias extends RecyclerView.Adapter<AdaptadorConsultarReferencias.ConsultarReferenciasHolder> {

    Context context;
    List<DatosConsultarReferencias> datosConsultarReferencias;

    public AdaptadorConsultarReferencias(Context context, List<DatosConsultarReferencias> datosConsultarReferencias) {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consultar_referencia, parent, false );
        return new ConsultarReferenciasHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultarReferenciasHolder holder, int position) {


        holder.txtNomref.setText(datosConsultarReferencias.get(position).getNomref());
        holder.txtcodigoref.setText("Codigo: "+datosConsultarReferencias.get(position).getCodigoref());
        holder.txtstock.setText("Stock: "+datosConsultarReferencias.get(position).getStock());
        holder.txtpreuco.setText("$"+datosConsultarReferencias.get(position).getPreuco());
        holder.txtpreven.setText("$"+datosConsultarReferencias.get(position).getPreven());
        holder.txtpreven1.setText("$"+datosConsultarReferencias.get(position).getPreven1());
        holder.txtpreven2.setText("$"+datosConsultarReferencias.get(position).getPreven2());
        holder.txtpreven3.setText("$"+datosConsultarReferencias.get(position).getPreven3());
        holder.txtpreven4.setText("$"+datosConsultarReferencias.get(position).getPreven4());
    }

    @Override
    public int getItemCount() {
        return datosConsultarReferencias.size();
    }

    public class ConsultarReferenciasHolder extends RecyclerView.ViewHolder {
        TextView txtNomref, txtcodigoref,txtpreuco,txtpreven,txtpreven1,txtpreven2,txtpreven3,txtpreven4, txtstock;


        public ConsultarReferenciasHolder(@NonNull View itemView) {
            super(itemView);
            txtNomref = itemView.findViewById(R.id.textview_nomref);
            txtcodigoref = itemView.findViewById(R.id.textview_codigoref);
            txtpreuco = itemView.findViewById(R.id.textview_preuco);
            txtpreven = itemView.findViewById(R.id.textview_preven);
            txtpreven1 = itemView.findViewById(R.id.textview_preven1);
            txtpreven2 = itemView.findViewById(R.id.textview_preven2);
            txtpreven3 = itemView.findViewById(R.id.textview_preven3);
            txtpreven4 = itemView.findViewById(R.id.textview_preven4);
            txtstock = itemView.findViewById(R.id.textview_stock);
        }
    }
}
