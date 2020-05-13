package com.example.esyspos;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class RecyclerPedido implements Serializable {
    RecyclerView recyclerView;

    public RecyclerPedido(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
