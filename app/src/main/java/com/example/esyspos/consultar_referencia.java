package com.example.esyspos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class consultar_referencia extends AppCompatActivity {


    EditText txtBuscarReferencia;
    RecyclerView rv_BuscarReferencia;
    Query_MySQL.consultar_precios_mysql mysql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_referencia);

        txtBuscarReferencia = findViewById(R.id.editText_buscar_referencia);
        rv_BuscarReferencia = findViewById(R.id.recyclerview_buscar_referencia);

        mysql = new Query_MySQL.consultar_precios_mysql(this);
        mysql.setRv_BuscarReferencia(rv_BuscarReferencia);
        mysql.setTxtBuscarReferencia(txtBuscarReferencia);

        mysql.execute();

        txtBuscarReferencia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {            }
            @Override
            public void afterTextChanged(Editable s) {
                mysql.filtrar(s.toString());
            }
        });

    }





}
