package com.example.retailsp.ui.pedido;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.retailsp.R;


public class CargandoFragment extends Fragment {

    private ProgressBar progressBar;
    private TextView porcentaje;
    private TextView texto;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cargando, container, false);
        this.progressBar = root.findViewById(R.id.progressBar2);
        this.porcentaje = root.findViewById(R.id.porcentaje);
        this.porcentaje.setVisibility(View.INVISIBLE);
        this.progressBar.setVisibility(View.INVISIBLE);
        this.texto = root.findViewById(R.id.texto);
        return root;
    }

    public void actualizarCarga(int carga){
        this.progressBar.setVisibility(View.VISIBLE);
        this.porcentaje.setVisibility(View.VISIBLE);
        this.porcentaje.setText(String.valueOf(carga)+"%");
        this.texto.setText("Enviando Reporte");
        this.progressBar.setProgress(carga);
        this.progressBar.requestLayout();
    }
}