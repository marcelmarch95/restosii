package com.example.retailsp.modelo;

import android.widget.Button;

import java.io.Serializable;

public class ModeloVistaFamSubf implements Serializable {

    private Familia familia;
    private Subfamilia subfamilia;
    private Button boton;

    public ModeloVistaFamSubf(){

    }

    public Button getBoton() {
        return boton;
    }

    public void setBoton(Button boton) {
        this.boton = boton;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public Subfamilia getSubfamilia() {
        return subfamilia;
    }

    public void setSubfamilia(Subfamilia subfamilia) {
        this.subfamilia = subfamilia;
    }



}
