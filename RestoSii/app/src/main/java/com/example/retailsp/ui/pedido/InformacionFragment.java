package com.example.retailsp.ui.pedido;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.retailsp.R;

public class InformacionFragment extends Fragment implements View.OnClickListener {

    private Button finalizar;
    private String mensaje;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_informacion, container, false);
        TextView texto = root.findViewById(R.id.text1);
        ImageView imageView = root.findViewById(R.id.imageView);
        finalizar = (Button) root.findViewById(R.id.finalizar);
        finalizar.setOnClickListener(this);



        String msj =  getArguments().getString("mensaje");
        boolean result =  getArguments().getBoolean("estado");

        this.mensaje = msj;

        if (!result){
            imageView.setImageResource(R.drawable.error);
        }

        texto.setText(msj);

        return root;
    }

    @Override
    public void onClick(View view) {
        if (view == this.finalizar) {


        }

    }
}