package com.example.retailsp.modelo;

import android.widget.Button;

import java.io.Serializable;

public class ModeloVistaProducto implements Serializable {

    private Producto producto;
    private boolean esomentario = false;
    private String comentario;
    private int idtemp;

    public ModeloVistaProducto(){

    }


    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIdtemp() {
        return idtemp;
    }

    public void setIdtemp(int idtemp) {
        this.idtemp = idtemp;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public boolean isEsomentario() {
        return esomentario;
    }

    public void setEsomentario(boolean esomentario) {
        this.esomentario = esomentario;
    }
}
