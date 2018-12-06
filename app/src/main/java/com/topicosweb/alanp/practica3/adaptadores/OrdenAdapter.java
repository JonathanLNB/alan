package com.topicosweb.alanp.practica3.adaptadores;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.topicosweb.alanp.practica3.R;
import com.topicosweb.alanp.practica3.modelos.Orden;

import java.util.ArrayList;


public class OrdenAdapter extends RecyclerView.Adapter<OrdenAdapter.ViewHolder> {
    private ArrayList<Orden> ordenes;
    private Activity context;

    public OrdenAdapter(ArrayList<Orden> ordenes, Activity context) {
        this.ordenes = ordenes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.ordenes, parent, false);
        OrdenAdapter.ViewHolder viewHolder = new OrdenAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Orden o = ordenes.get(i);
        String productos = "";
        holder.vId.setText(""+o.getId());
        for(int e = 0; e < o.getLineItems().size(); e++) {
            if (e + 1 == o.getLineItems().size())
                productos += o.getLineItems().get(e).getName();
            else
                productos += o.getLineItems().get(e).getName() + ", ";
        }
        holder.vProductos.setText(productos);
    }

    @Override
    public int getItemCount() {
        return ordenes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView vId;
        TextView vProductos;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vId = (TextView) itemView.findViewById(R.id.vId);
            vProductos = (TextView) itemView.findViewById(R.id.vProductos);
        }
    }
}
