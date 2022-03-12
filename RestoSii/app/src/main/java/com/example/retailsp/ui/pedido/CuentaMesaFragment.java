package com.example.retailsp.ui.pedido;


import android.app.ProgressDialog;
import android.content.Context;
import android.icu.text.IDNA;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retailsp.R;
import com.example.retailsp.adapters.ModeloVistaProductoCarritoAdapter;
import com.example.retailsp.modelo.Familia;
import com.example.retailsp.modelo.ModeloVistaFamSubf;
import com.example.retailsp.modelo.ModeloVistaMesa;
import com.example.retailsp.modelo.ModeloVistaProducto;
import com.example.retailsp.modelo.Producto;
import com.example.retailsp.modelo.Subfamilia;
import com.example.retailsp.ui.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CuentaMesaFragment extends Fragment implements View.OnClickListener{

    ProgressDialog progressDialog;
    View root;
    private Button btnenviar;
    public  Context context;
    private RecyclerView rvMusicas;
    private GridLayoutManager glm;
    private TextView cantidad;
    private TextView total, propina, totconprop;
    private ModeloVistaProductoCarritoAdapter adapter;
    private ArrayList<ModeloVistaProducto> datoscarrito = new ArrayList<>();
    private int mesa;
    private String mesero;
    private TextView meserotv, mesatv;
    private FragmentManager fragmentManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cuenta, container, false);

        progressDialog=new ProgressDialog(this.getActivity());
        fragmentManager = getFragmentManager();

        btnenviar = (Button) root.findViewById(R.id.btnenviar);
        btnenviar.setOnClickListener(this);
        meserotv = (TextView) root.findViewById(R.id.mesero);
        mesatv = (TextView) root.findViewById(R.id.mesatv);

        cantidad = (TextView) root.findViewById(R.id.cantidad);
        total = (TextView) root.findViewById(R.id.total);
        propina = (TextView) root.findViewById(R.id.propina);
        totconprop = (TextView) root.findViewById(R.id.totconprop);

        mesa = (Integer) getArguments().getSerializable("mesa");
        mesero = (String) getArguments().getSerializable("mesero");
        datoscarrito = (ArrayList<ModeloVistaProducto>) getArguments().getSerializable("carrito");



        int cant = 0;
        int tot = 0;

        for (ModeloVistaProducto mvp: datoscarrito){
            if (!mvp.isEsomentario()){
                mvp.getProducto().setNombre(mvp.getProducto().getNombre().toUpperCase());
                cant++;
                tot+=Integer.valueOf(mvp.getProducto().getTotal());
            }
        }

        int propin = Math.round(Math.round((tot/10*0.1))*10);
        int tott = tot+propin;

        propina.setText("Propina $ " +  String.valueOf(propin));
        totconprop.setText("Total con propina $ " + String.valueOf(tott));

        cantidad.setText(cant + " productos");
        total.setText("$ " + tot);

        System.out.println("datos que llegaron : " + datoscarrito.size());

        rvMusicas = (RecyclerView) root.findViewById(R.id.rv_musicas);
        glm = new GridLayoutManager(getActivity(), 1);
        rvMusicas.setLayoutManager(glm);
        adapter = new ModeloVistaProductoCarritoAdapter(datoscarrito, this,null,null);
        rvMusicas.setAdapter(adapter);

        meserotv.setText("Mesero " + mesero);
        mesatv.setText("Mesa " + mesa);
        setHasOptionsMenu(true);

        return root;
    }


    @Override
    public void onClick(View view){
        if (view==this.btnenviar){
            if (datoscarrito.size()>0){
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                CargandoFragment cf = new CargandoFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment, cf);
                fragmentTransaction.commit();

                new CuentaMesaFragment.imprimirCuenta().execute();
            }
            return;
        }
    }

    class imprimirCuenta extends AsyncTask<Void,Void,Void> {



        @Override
        protected void onPreExecute() {

            /*progressDialog.setMessage("Obteniendo Mesas...");
            progressDialog.show();*/

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try{

                ConnectionClass c2 = new ConnectionClass();
                Connection conn2 = c2.CONN();
                Statement st2 = conn2.createStatement();

                String sql2 = "INSERT into imprimir VALUES (" + null + ", " + mesa + ", 's')";

                st2.executeUpdate(sql2);
                conn2.close();



            }catch (Exception e){
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                InformacionFragment cf = new InformacionFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("mensaje", "¡Cuenta no impresa!");
                bundle.putSerializable("estado", false);
                cf.setArguments(bundle);

                fragmentTransaction.replace(R.id.nav_host_fragment, cf);
                fragmentTransaction.commit();
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void Result){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            InformacionFragment cf = new InformacionFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("mensaje", "¡Cuenta impresa con éxito!");
            bundle.putSerializable("estado", true);
            cf.setArguments(bundle);

            fragmentTransaction.replace(R.id.nav_host_fragment, cf);
            fragmentTransaction.commit();

            super.onPostExecute(Result);
        }
    }

}

