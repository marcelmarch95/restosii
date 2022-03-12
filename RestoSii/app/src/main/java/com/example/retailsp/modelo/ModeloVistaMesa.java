package com.example.retailsp.modelo;

import java.io.Serializable;

public class ModeloVistaMesa implements Serializable {

    private Integer numero;
    private String estado;

    public ModeloVistaMesa(){

    }

    public ModeloVistaMesa(Integer numero, String estado){
        this.numero = numero;
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
}
