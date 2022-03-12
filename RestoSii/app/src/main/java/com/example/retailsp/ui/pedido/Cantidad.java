package com.example.retailsp.ui.pedido;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.retailsp.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import com.example.retailsp.R;

import java.util.ArrayList;

public class Cantidad extends BottomSheetDialogFragment {

    TextView t, pendiente;
    EditText textcant;
    Button anadir,menos,mas;
    ArrayList<Producto> array;
    Producto item;

    public static Cantidad newInstance() {
        Cantidad fragment = new Cantidad();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        array = new ArrayList<>();

    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.cantidad, null);

        anadir = contentView.findViewById(R.id.anadir);
        menos = contentView.findViewById(R.id.button2);
        mas = contentView.findViewById(R.id.button3);
        textcant = contentView.findViewById(R.id.textcant);
        textcant.setText( "1" );

        anadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean contiene = false;
                int posicion = 0;
                for(int i = 0; i< MainActivity.productos.size();i++){
                    if (MainActivity.productos.get(i).getCodigo().equals(item.getCodigo())){
                        contiene = true;
                        posicion = i;
                        break;
                    }
                }

                Log.i("Mensaje",""+textcant.getText());

                if (!(""+textcant.getText()).equals("")){
                    if (Double.parseDouble(""+textcant.getText())>(item.getStock()-item.getPendiente())){

                    }else{
                        if (Double.parseDouble(""+textcant.getText())>0){
                            if (contiene) {
                                double cantidadActual = MainActivity.productos.get(posicion).getCantidad();
                                if (cantidadActual+Double.parseDouble(""+textcant.getText())>(item.getStock()-item.getPendiente())){

                                }else{
                                    MainActivity.productos.get(posicion).setCantidad(cantidadActual+Double.parseDouble(""+textcant.getText()));
                                }


                            } else{
                                Log.i("texto",""+textcant.getText());
                                item.setCantidad(Double.parseDouble(""+textcant.getText()));
                                MainActivity.productos.add(item);
                            }
                        }
                    }



                }

                dismiss();
            }
        });

        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double cant = Double.parseDouble(""+textcant.getText());
                double total = cant-1;
                if (total<0){
                    textcant.setText( "0" );
                }else{
                    textcant.setText( total+"" );
                }

            }

        });

        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double cant = Double.parseDouble(""+textcant.getText());
                double total = cant+1;

                if (total<0){
                    textcant.setText( "0" );
                }else{
                    textcant.setText( total+"" );
                }
            }

        });

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            array = bundle.getParcelableArrayList("array");
            item = bundle.getParcelable("item");
            item.setCantidad(100);

            t = contentView.findViewById(R.id.nombre);
            pendiente = contentView.findViewById(R.id.pendiente);
            t.setText(item.getNombre());
            pendiente.setText("Disponible: "+(item.getStock()-item.getPendiente()));
        }

        dialog.setContentView(contentView);
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }







}