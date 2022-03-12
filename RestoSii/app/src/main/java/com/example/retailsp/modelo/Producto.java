package com.example.retailsp.modelo;

import java.io.Serializable;

public class Producto implements Serializable {
    private String codigo;
    private String nombre;
    private String familia;
    private String subfamilia;
    private String total;
    private float stock;
    private boolean mueve;
    private boolean impresora1;
    private boolean impresora2;
    private boolean impresora3;
    private boolean impresora4;

    public Producto()  {
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

    public String getSubfamilia() {
        return subfamilia;
    }

    public void setSubfamilia(String subfamilia) {
        this.subfamilia = subfamilia;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public float getStock() {
        return stock;
    }

    public void setStock(float stock) {
        this.stock = stock;
    }

    public boolean isMueve() {
        return mueve;
    }

    public void setMueve(boolean mueve) {
        this.mueve = mueve;
    }

    public boolean isImpresora1() {
        return impresora1;
    }

    public void setImpresora1(boolean impresora1) {
        this.impresora1 = impresora1;
    }

    public boolean isImpresora2() {
        return impresora2;
    }

    public void setImpresora2(boolean impresora2) {
        this.impresora2 = impresora2;
    }

    public boolean isImpresora3() {
        return impresora3;
    }

    public void setImpresora3(boolean impresora3) {
        this.impresora3 = impresora3;
    }

    public boolean isImpresora4() {
        return impresora4;
    }

    public void setImpresora4(boolean impresora4) {
        this.impresora4 = impresora4;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", familia='" + familia + '\'' +
                ", subfamilia='" + subfamilia + '\'' +
                ", total='" + total + '\'' +
                ", stock=" + stock +
                ", mueve=" + mueve +
                ", impresora1=" + impresora1 +
                ", impresora2=" + impresora2 +
                ", impresora3=" + impresora3 +
                ", impresora4=" + impresora4 +
                '}';
    }
}
