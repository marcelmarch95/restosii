package com.example.retailsp.adapters;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.example.retailsp.R;
import com.example.retailsp.modelo.ModeloVistaFamSubf;
import com.example.retailsp.modelo.ModeloVistaMesa;
import com.example.retailsp.ui.pedido.MesaVistaFragment;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class ModeloVistaFamSubfAdapter extends RecyclerView.Adapter<ModeloVistaFamSubfAdapter.MusicaViewHolder>  {

    private ArrayList<ModeloVistaFamSubf> data;
    private View.OnClickListener listener;

    public ModeloVistaFamSubfAdapter() {
    }

    public ModeloVistaFamSubfAdapter(ArrayList<ModeloVistaFamSubf> data, View.OnClickListener ls) {
        this.data = data;
        this.listener = ls;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public ArrayList<ModeloVistaFamSubf> getData() {
        return data;
    }

    public void setData(ArrayList<ModeloVistaFamSubf> data) {
        this.data = data;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MusicaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MusicaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_modelofamsubf, parent, false), this.listener);
    }

    @Override
    public void onBindViewHolder(MusicaViewHolder holder, int position) {
        ModeloVistaFamSubf famsubf = data.get(position);

        if (famsubf.getSubfamilia()==null){
            holder.famsubf.setText(famsubf.getFamilia().getNombre());
        }
        else {
            holder.famsubf.setText(famsubf.getSubfamilia().getNombre());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MusicaViewHolder extends RecyclerView.ViewHolder{

        Button famsubf;

        public MusicaViewHolder(View itemView, View.OnClickListener lis) {
            super(itemView);
            famsubf = (Button) itemView.findViewById(R.id.famsubf);
            famsubf.setOnClickListener(lis);
        }

    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

}