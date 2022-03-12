package com.example.retailsp.ui.pedido;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.retailsp.R;

import java.util.ArrayList;

public class ProductosAdapter extends ArrayAdapter<Producto> {
    private ArrayList<Producto> productos;
    private Context mContext;
    private int resourceLayout;

    public ProductosAdapter(@NonNull Context context, int resource, ArrayList<Producto> p) {
        super(context, resource, p);
        this.productos = p;
        this.mContext = context;
        this.resourceLayout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        Producto p = productos.get(position);
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(resourceLayout,null);
        }
        TextView t = view.findViewById(R.id.nombre);
        TextView precio = view.findViewById(R.id.precio);
        TextView codigo = view.findViewById(R.id.codigo);
        TextView cantidad = view.findViewById(R.id.cantidad);
        TextView pendiente = view.findViewById(R.id.pendiente);
        TextView medida = view.findViewById(R.id.medida);
        TextView cancarro = view.findViewById(R.id.cancarro);
        TextView total = view.findViewById(R.id.total);

        t.setText(p.getNombre());
        precio.setText("$"+p.getPrecio());
        codigo.setText(p.getCodigo());

        medida.setText(p.getUnidad());
        if (p.getCantidad() != 0.0){
            cancarro.setText("Cantidad: "+p.getCantidad());
            if (p.getUnidad().equals("UN")){
                cancarro.setText("Cantidad: "+(int) p.getCantidad());
            }
            total.setText("$"+(int) (p.getPrecio()*p.getCantidad()));
        }else{
            cantidad.setText("Stock: "+p.getStock());
            if (p.getUnidad().equals("UN")){
                cantidad.setText("Stock: "+(int) p.getStock());
            }
            pendiente.setText("P: "+ p.getPendiente());

        }

        return view;

    }
}
