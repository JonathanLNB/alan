package com.topicosweb.alanp.practica3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.topicosweb.alanp.practica3.adaptadores.ClienteRealmAdapter;
import com.topicosweb.alanp.practica3.realm.ClienteRealm;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class Favoritos extends AppCompatActivity {
    private RecyclerView rFavoritos;
    private ClienteRealmAdapter adaptador;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        rFavoritos = (RecyclerView) findViewById(R.id.rFavoritos);
        rFavoritos.setHasFixedSize(true);
        rFavoritos.setLayoutManager(new LinearLayoutManager(this));
        loadFavoritos();
    }
    private void loadFavoritos() {
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("myrealm.realm").build();
        Realm.setDefaultConfiguration(configuration);

        mRealm = Realm.getDefaultInstance();
        RealmResults<ClienteRealm> clientes = mRealm
                .where(ClienteRealm.class)
                .findAll();
        adaptador = new ClienteRealmAdapter(clientes, this);
        rFavoritos.setAdapter(adaptador);
    }
}
