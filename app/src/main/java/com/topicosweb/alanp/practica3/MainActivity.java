package com.topicosweb.alanp.practica3;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.topicosweb.alanp.practica3.adaptadores.ClienteAdapter;
import com.topicosweb.alanp.practica3.adaptadores.OrdenAdapter;
import com.topicosweb.alanp.practica3.consumos.Config;
import com.topicosweb.alanp.practica3.consumos.Practica;
import com.topicosweb.alanp.practica3.consumos.UnsafeOkHttpClient;
import com.topicosweb.alanp.practica3.modelos.Cliente;
import com.topicosweb.alanp.practica3.modelos.Orden;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rClientes;
    private FloatingActionButton bFavorito;
    private Gson gson;
    private ArrayList<Cliente> clientes;
    private Retrofit retrofit;
    private Practica restClient;
    private ClienteAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rClientes = (RecyclerView) findViewById(R.id.rClientes);
        bFavorito = (FloatingActionButton) findViewById(R.id.bFavoritos);
        rClientes.setHasFixedSize(true);
        rClientes.setLayoutManager(new LinearLayoutManager(this));
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        gson = new GsonBuilder()
                .setLenient()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restClient = retrofit.create(Practica.class);
        bFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Favoritos.class);
                startActivity(i);
            }
        });
        loadClientes();
    }

    private void loadClientes() {
        Call<ArrayList<Cliente>> call = restClient.getClientes();
        call.enqueue(new Callback<ArrayList<Cliente>>() {
            @Override
            public void onResponse(Call<ArrayList<Cliente>> call, Response<ArrayList<Cliente>> response) {
                if (response.isSuccessful()) {
                    clientes = response.body();
                    for (Cliente c : clientes)
                        loadOrdenes(c, false);
                    adaptador = new ClienteAdapter(clientes, MainActivity.this);
                    rClientes.setAdapter(adaptador);
                } else
                    Toast.makeText(MainActivity.this, "Error, en la conexión", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ArrayList<Cliente>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error, en la conexión", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadOrdenes(final Cliente c, final boolean tipo) {
        Call<ArrayList<Orden>> call = restClient.getOrdenes(c.getId());
        call.enqueue(new Callback<ArrayList<Orden>>() {
            @Override
            public void onResponse(Call<ArrayList<Orden>> call, Response<ArrayList<Orden>> response) {
                if (response.isSuccessful()) {
                    if(tipo){
                        Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.lista);
                        RecyclerView rOrdenes = (RecyclerView) dialog.findViewById(R.id.rOrdener);
                        rOrdenes.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        rOrdenes.setHasFixedSize(true);
                        OrdenAdapter adapter = new OrdenAdapter(response.body(), MainActivity.this);
                        rOrdenes.setAdapter(adapter);
                        dialog.show();
                    }
                    else {
                        c.setOrdenes(response.body().size());
                        adaptador.notifyDataSetChanged();
                    }
                } else
                    Toast.makeText(MainActivity.this, "Error, en la conexión", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ArrayList<Orden>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error, en la conexión", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void mostrarOrdenes(Integer id) {
        loadOrdenes(new Cliente(id), true);
    }

    public void mostrarClientes(Cliente p) {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.detalle);
        ImageView iFoto = (ImageView)dialog.findViewById(R.id.iFoto);
        TextView vNombre= (TextView) dialog.findViewById(R.id.vNombre);
        TextView vCorreo= (TextView) dialog.findViewById(R.id.vCorreo);
        TextView vDireccion= (TextView) dialog.findViewById(R.id.vDireccion);
        TextView vTotalO= (TextView) dialog.findViewById(R.id.vTotalO);
        Picasso.get().load(p.getAvatarUrl()).into(iFoto);
        vNombre.setText(p.getNombre());
        vCorreo.setText(p.getCorreo());
        vDireccion.setText(p.getDireccion());
        vTotalO.setText(getString(R.string.ordenes_totales) + " " + p.getOrdenes());
        dialog.show();
    }
}
