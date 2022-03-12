package com.example.retailsp.ui.pedido;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.retailsp.MainActivity;
import com.example.retailsp.R;
import com.example.retailsp.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class Eliminar extends BottomSheetDialogFragment {

    TextView t;
    EditText textcant;
    Button anadir;
    ArrayList<Producto> array;
    Producto item;

    public static Eliminar newInstance() {
        Eliminar fragment = new Eliminar();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        array = new ArrayList<>();


    }



    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.eliminar, null);



        anadir = contentView.findViewById(R.id.anadir);
        textcant = contentView.findViewById(R.id.textcant);

        anadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicion = 0;
                for(int i = 0; i< MainActivity.productos.size();i++){
                    if (MainActivity.productos.get(i).getCodigo().equals(item.getCodigo())){

                        posicion = i;
                        MainActivity.productos.remove(posicion);
                        break;
                    }
                }


                NotificationsFragment.refresh();
                dismiss();
            }
        });


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            array = bundle.getParcelableArrayList("array");
            item = bundle.getParcelable("item");
            item.setCantidad(100);

            t = contentView.findViewById(R.id.nombre);
            t.setText(item.getNombre());
        }

        dialog.setContentView(contentView);
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    @Override
    public void onCancel(DialogInterface dialog)
    {
        super.onCancel(dialog);
    }







}