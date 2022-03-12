package com.example.retailsp.adapters;


import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.example.retailsp.R;
import com.example.retailsp.modelo.ModeloVistaFamSubf;
import com.example.retailsp.modelo.ModeloVistaProducto;
import com.example.retailsp.modelo.Producto;
import com.example.retailsp.ui.pedido.MesaVistaFragment;
import com.example.retailsp.ui.pedido.MesasVistaFragment;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class ModeloVistaProductoAdapter extends RecyclerView.Adapter<ModeloVistaProductoAdapter.MusicaViewHolder>  {

    private ArrayList<ModeloVistaProducto> data;
    private View.OnClickListener listener;


    public ModeloVistaProductoAdapter(ArrayList<ModeloVistaProducto> data, View.OnClickListener ls) {
        this.data = data;
        this.listener = ls;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public ArrayList<ModeloVistaProducto> getData() {
        return data;
    }

    public void setData(ArrayList<ModeloVistaProducto> data) {
        this.data = data;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MusicaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MusicaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_modeloproducto, parent, false), this.listener);
    }

    @Override
    public void onBindViewHolder(MusicaViewHolder holder, int position) {
        ModeloVistaProducto producto = data.get(position);

        holder.famsubf.setText(producto.getProducto().getNombre());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MusicaViewHolder extends RecyclerView.ViewHolder{

        Button famsubf;

        public MusicaViewHolder(View itemView, View.OnClickListener lis) {
            super(itemView);
            famsubf = (Button) itemView.findViewById(R.id.producto);
            famsubf.setOnClickListener(lis);
        }

    }



}