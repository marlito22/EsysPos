package com.example.esyspos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adaptador_GV_Principal extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private String[] nombres_menus;
    private int[] numero_imagen;

    public Adaptador_GV_Principal(Context context, String[] nombres_menus, int[] numero_imagen) {
        this.context = context;
        this.nombres_menus = nombres_menus;
        this.numero_imagen = numero_imagen;
    }

    @Override
    public int getCount() {
        return numero_imagen.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(layoutInflater == null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.row_item,null);
        }
        ImageView imageView =convertView.findViewById(R.id.imagen_view_menu_GV_principal);
        TextView textView =convertView.findViewById(R.id.text_view_menu_GV_principal);
        imageView.setImageResource(numero_imagen[position]);
        textView.setText(nombres_menus[position]);
        return convertView;
    }
}
