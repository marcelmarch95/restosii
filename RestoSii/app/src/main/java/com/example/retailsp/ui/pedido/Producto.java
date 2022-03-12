package com.example.retailsp.ui.pedido;

import android.os.Parcel;
import android.os.Parcelable;

public class Producto implements Parcelable {
    private String codigo;
    private String nombre;
    private int precio;
    private String unidad;
    private double stock;
    private double cantidad;
    private double pendiente;

    public Producto(String codigo, String nombre, int precio, String unidad, double stock, double cantidad, double pendiente) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.unidad = unidad;
        this.stock = stock;
        this.cantidad = cantidad;
        this.pendiente = pendiente;
    }

    public Producto(){

    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    protected Producto(Parcel in) {
        codigo = in.readString();
        nombre = in.readString();
        precio = in.readInt();
        unidad = in.readString();
        stock = in.readFloat();
        pendiente = in.readFloat();
    }

    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public String getUnidad() {
        return unidad;
    }

    public double getStock() {
        return stock;
    }

    public double getPendiente() {
        return pendiente;
    }

    public void setPendiente(double pendiente) {
        this.pendiente = pendiente;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(codigo);
        dest.writeString(nombre);
        dest.writeInt(precio);
        dest.writeString(unidad);
        dest.writeDouble(stock);
        dest.writeDouble(cantidad);
        dest.writeDouble(pendiente);
    }
}
