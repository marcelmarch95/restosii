package com.example.retailsp.adapters;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;
import com.example.retailsp.R;
import com.example.retailsp.modelo.ModeloVistaMesa;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class ModeloVistaMesaAdapter extends RecyclerView.Adapter<ModeloVistaMesaAdapter.MusicaViewHolder>  {

    private ArrayList<ModeloVistaMesa> data;
    private View.OnClickListener listener;

    public ModeloVistaMesaAdapter() {
    }

    public ModeloVistaMesaAdapter(ArrayList<ModeloVistaMesa> data, View.OnClickListener ls) {
        this.data = data;
        this.listener = ls;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public ArrayList<ModeloVistaMesa> getData() {
        return data;
    }

    public void setData(ArrayList<ModeloVistaMesa> data) {
        this.data = data;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MusicaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MusicaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_modelomesa, parent, false), this.listener);
    }

    @Override
    public void onBindViewHolder(MusicaViewHolder holder, int position) {
        ModeloVistaMesa mesa = data.get(position);

        holder.mesa.setText(String.valueOf(mesa.getNumero()));

        System.out.println("estado mesa " + mesa.getNumero() + " es: " + mesa.getEstado());

        if (mesa.getEstado().compareTo("d")==0)
            holder.mesa.setBackgroundColor(Color.GREEN);
        if (mesa.getEstado().compareTo("o")==0)
            holder.mesa.setBackgroundColor(Color.RED);
        if (mesa.getEstado().compareTo("p")==0)
            holder.mesa.setBackgroundColor(Color.YELLOW);

        /*holder.veretiro.setText(retiro.getRetiro().getId());
        holder.empresa.setText(retiro.getDireccion().getCalle() + " #" + retiro.getDireccion().getNumero() + " " + retiro.getDireccion().getRegion());
        holder.fecha.setText("Retiro para el " + retiro.getTramo().getFecha());
        holder.hora.setText("Tramo " + retiro.getTramo().getHorainicio() + " - " + retiro.getTramo().getHorafinal());
*/

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MusicaViewHolder extends RecyclerView.ViewHolder{

        Button mesa;

        public MusicaViewHolder(View itemView, View.OnClickListener lis) {
            super(itemView);
            mesa = (Button) itemView.findViewById(R.id.famsubf);
            mesa.setOnClickListener(lis);
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