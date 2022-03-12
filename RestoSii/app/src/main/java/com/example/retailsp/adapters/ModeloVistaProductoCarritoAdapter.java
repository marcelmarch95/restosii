package com.example.retailsp.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.retailsp.R;
import com.example.retailsp.modelo.ModeloVistaProducto;
import com.example.retailsp.ui.pedido.DetallePedidoFragment;
import com.example.retailsp.ui.pedido.MesaVistaFragment;

import java.util.ArrayList;


public class ModeloVistaProductoCarritoAdapter extends RecyclerView.Adapter<ModeloVistaProductoCarritoAdapter.MusicaViewHolder>  {

    private ArrayList<ModeloVistaProducto> data;
    private View.OnClickListener listener;
    private MesaVistaFragment padre;
    private DetallePedidoFragment padre2;

    public ModeloVistaProductoCarritoAdapter() {
    }

    public ModeloVistaProductoCarritoAdapter(ArrayList<ModeloVistaProducto> data, View.OnClickListener ls, MesaVistaFragment padre, DetallePedidoFragment padre2) {
        this.data = data;
        this.listener = ls;
        this.padre = padre;
        this.padre2 = padre2;
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
        return new MusicaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_modeloproductocarrito, parent, false), this.listener);
    }

    @Override
    public void onBindViewHolder(MusicaViewHolder holder, int position) {
        if (padre!=null){
            ModeloVistaProducto producto = data.get(position);

            if (producto.isEsomentario()){
                holder.eliminar.setText("-1" + "\n" + "E" + "\n" + producto.getIdtemp());
                holder.nombre.setText(producto.getComentario());
                holder.precio.setText("COM");
            }
            else {
                holder.eliminar.setText(producto.getProducto().getCodigo() + "\n" + "E" + "\n" + producto.getIdtemp());
                holder.nombre.setText(producto.getProducto().getNombre());
                holder.nombre.setText(holder.nombre.getText().toString().toUpperCase());
                holder.precio.setText("$ " + producto.getProducto().getTotal());
            }
            padre.listabtneliminar.add(holder.eliminar);
        }
        else if (padre2!=null){
            ModeloVistaProducto producto = data.get(position);

            if (producto.isEsomentario()){
                holder.nombre.setText(producto.getComentario());
                holder.precio.setText("COM ");
            }
            else {
                holder.nombre.setText(producto.getProducto().getNombre());
                holder.nombre.setText(holder.nombre.getText().toString().toUpperCase());
                holder.precio.setText("$ " + producto.getProducto().getTotal());
            }
        }
        else {
            ModeloVistaProducto producto = data.get(position);

            if (producto.isEsomentario()){
                holder.nombre.setText(producto.getComentario());
                holder.precio.setText("COM ");
            }
            else {
                holder.nombre.setText(producto.getProducto().getNombre());
                holder.nombre.setText(holder.nombre.getText().toString().toUpperCase());
                holder.precio.setText("$ " + producto.getProducto().getTotal());
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MusicaViewHolder extends RecyclerView.ViewHolder{

        Button eliminar;
        TextView nombre;
        TextView precio;

        public MusicaViewHolder(View itemView, View.OnClickListener lis) {
            super(itemView);

            if (padre!=null){
                eliminar = (Button) itemView.findViewById(R.id.borrar);
                eliminar.setOnClickListener(lis);
                eliminar.setTextColor(0xFF0000);
                nombre = (TextView) itemView.findViewById(R.id.product_name);
                precio = (TextView) itemView.findViewById(R.id.product_price);
            }
            else if (padre2!=null) {
                eliminar = (Button) itemView.findViewById(R.id.borrar);
                eliminar.setVisibility(View.INVISIBLE);
                nombre = (TextView) itemView.findViewById(R.id.product_name);
                precio = (TextView) itemView.findViewById(R.id.product_price);
            }
            else {
                eliminar = (Button) itemView.findViewById(R.id.borrar);
                eliminar.setVisibility(View.INVISIBLE);
                nombre = (TextView) itemView.findViewById(R.id.product_name);
                precio = (TextView) itemView.findViewById(R.id.product_price);
            }
        }

    }



}