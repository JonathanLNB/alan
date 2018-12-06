package com.topicosweb.alanp.practica3.adaptadores;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.squareup.picasso.Picasso;
import com.topicosweb.alanp.practica3.MainActivity;
import com.topicosweb.alanp.practica3.modelos.Cliente;
import com.topicosweb.alanp.practica3.R;
import com.topicosweb.alanp.practica3.realm.ClienteRealm;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder> {
    private ArrayList<Cliente> clientes;
    private Activity context;
    private Realm mRealm;

    public ClienteAdapter(ArrayList<Cliente> clientes, Activity context) {
        this.clientes = clientes;
        this.context = context;
        Realm.init(context);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("myrealm.realm").build();
        Realm.setDefaultConfiguration(configuration);
        mRealm = Realm.getDefaultInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.listado, parent, false);
        ClienteAdapter.ViewHolder viewHolder = new ClienteAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cliente p = clientes.get(position);
        Picasso.get().load(p.getAvatarUrl()).into(holder.iFoto);
        holder.vNombre.setText(p.getNombre());
        holder.vCorreo.setText(p.getCorreo());
        holder.vOrdenes.setText(context.getString(R.string.ordenes_totales) + " " + p.getOrdenes());
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout sSwipe;
        CardView cOrden;
        CardView cAgregar;
        CardView cInfo;
        CircleImageView iFoto;
        TextView vNombre;
        TextView vCorreo;
        TextView vOrdenes;
        boolean open = false;

        public ViewHolder(View itemView) {
            super(itemView);
            sSwipe = (SwipeLayout) itemView.findViewById(R.id.sSwipe);
            iFoto = (CircleImageView) itemView.findViewById(R.id.iFoto);
            vNombre = (TextView) itemView.findViewById(R.id.vNombre);
            vCorreo = (TextView) itemView.findViewById(R.id.vCorreo);
            vOrdenes = (TextView) itemView.findViewById(R.id.vOrdenes);
            cInfo = (CardView) itemView.findViewById(R.id.cInfo);
            cOrden = (CardView) itemView.findViewById(R.id.cOrdenes);
            cAgregar = (CardView) itemView.findViewById(R.id.cAgregar);
            sSwipe.addDrag(SwipeLayout.DragEdge.Bottom, sSwipe.findViewById(R.id.lFondo));
            sSwipe.setTopSwipeEnabled(false);
            sSwipe.setLeftSwipeEnabled(false);
            sSwipe.setRightSwipeEnabled(true);
            sSwipe.setBottomSwipeEnabled(false);
            sSwipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (open)
                        sSwipe.close();
                    else
                        sSwipe.open();
                    open = !open;
                }
            });
            cInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) context).mostrarClientes(clientes.get(getAdapterPosition()));
                }
            });
            cOrden.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) context).mostrarOrdenes(clientes.get(getAdapterPosition()).getId());
                }
            });
            cAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRealm.beginTransaction();
                    ClienteRealm c = mRealm.createObject(ClienteRealm.class, clientes.get(getAdapterPosition()).getId());
                    c.setCorreo(clientes.get(getAdapterPosition()).getCorreo());
                    c.setNombre(clientes.get(getAdapterPosition()).getNombre());
                    c.setDireccion(clientes.get(getAdapterPosition()).getDireccion());
                    c.setImagen(clientes.get(getAdapterPosition()).getAvatarUrl());
                    c.setOrdenes(clientes.get(getAdapterPosition()).getOrdenes());
                    mRealm.commitTransaction();
                    Toast.makeText(context, "El Usuario: "+clientes.get(getAdapterPosition()).getNombre()+" se agrego a favoritos", Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}