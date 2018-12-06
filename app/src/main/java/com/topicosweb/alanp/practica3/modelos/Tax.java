
package com.topicosweb.alanp.practica3.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tax implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("subtotal")
    @Expose
    private String subtotal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

}
