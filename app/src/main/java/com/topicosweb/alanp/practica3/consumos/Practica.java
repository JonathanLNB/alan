package com.topicosweb.alanp.practica3.consumos;


import com.topicosweb.alanp.practica3.modelos.Cliente;
import com.topicosweb.alanp.practica3.modelos.Orden;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Practica {
    @GET("customers")
    Call<ArrayList<Cliente>> getClientes();

    @GET("orders")
    Call<ArrayList<Orden>> getOrdenes(@Query("customer") Integer id);
}
