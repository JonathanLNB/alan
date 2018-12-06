package com.topicosweb.alanp.practica3.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class ClienteRealm extends RealmObject {
    @PrimaryKey
    private long id;
    @Required
    private String nombre;
    @Required
    private String correo;
    private String direccion;
    private String imagen;
    private int ordenes;

    public void setId(long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setOrdenes(int ordenes) {
        this.ordenes = ordenes;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public int getOrdenes() {
        return ordenes;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getImagen() {
        return imagen;
    }
}
