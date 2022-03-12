package com.example.retailsp.modelo;

import java.io.Serializable;

public class Subfamilia implements Serializable {

    private String codigo;
    private String nombre;
    private String familia;

    public Subfamilia() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }
}
