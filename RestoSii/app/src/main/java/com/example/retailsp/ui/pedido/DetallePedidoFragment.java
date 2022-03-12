package com.example.retailsp.ui.pedido;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retailsp.MainActivity;
import com.example.retailsp.R;
import com.example.retailsp.adapters.ModeloVistaFamSubfAdapter;
import com.example.retailsp.adapters.ModeloVistaProductoAdapter;
import com.example.retailsp.adapters.ModeloVistaProductoCarritoAdapter;
import com.example.retailsp.modelo.ModeloVistaFamSubf;
import com.example.retailsp.modelo.ModeloVistaMesa;
import com.example.retailsp.modelo.ModeloVistaProducto;
import com.example.retailsp.modelo.Producto;
import com.example.retailsp.ui.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DetallePedidoFragment extends Fragment implements View.OnClickListener{

    ProgressDialog progressDialog;
    View root;
    private Button btnenviar;
    public  Context context;
    private RecyclerView rvMusicas;
    private GridLayoutManager glm;
    private TextView cantidad;
    private TextView total;
    private ModeloVistaProductoCarritoAdapter adapter;
    private ArrayList<ModeloVistaProducto> datos = new ArrayList<>();
    private ArrayList<ModeloVistaProducto> datoscarrito = new ArrayList<>();
    private Button volver;
    private int mesa, idtemp;
    private String mesero;
    private TextView meserotv, mesatv;
    private ArrayList<ModeloVistaFamSubf> familias = new ArrayList<>();
    private ArrayList<ModeloVistaFamSubf> subfamilias = new ArrayList<>();
    private ArrayList<Producto> productos = new ArrayList<>();
    public ArrayList<Button> listabtneliminar = new ArrayList<>();
    private boolean ultimoesproducto;
    private FragmentManager fragmentManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detallepedido, container, false);

        progressDialog=new ProgressDialog(this.getActivity());

        volver = (Button) root.findViewById(R.id.volver);
        volver.setOnClickListener(this);
        btnenviar = (Button) root.findViewById(R.id.btnenviar);
        btnenviar.setOnClickListener(this);
        meserotv = (TextView) root.findViewById(R.id.mesero);
        mesatv = (TextView) root.findViewById(R.id.mesatv);

        cantidad = (TextView) root.findViewById(R.id.cantidad);
        total = (TextView) root.findViewById(R.id.total);

        mesa = (Integer) getArguments().getSerializable("mesa");
        mesero = (String) getArguments().getSerializable("mesero");
        datos = (ArrayList<ModeloVistaProducto>) getArguments().getSerializable("carrito");

        familias = (ArrayList<ModeloVistaFamSubf>) getArguments().getSerializable("familias");
        subfamilias = (ArrayList<ModeloVistaFamSubf>) getArguments().getSerializable("subfamilias");
        productos = (ArrayList<Producto>) getArguments().getSerializable("productos");
        listabtneliminar = (ArrayList<Button>) getArguments().getSerializable("listaeli");
        ultimoesproducto = getArguments().getBoolean("ultimoesproducto");
        idtemp = (int) getArguments().getSerializable("idtemp");


        int cant = 0;
        int tot = 0;

        for (ModeloVistaProducto mvp: datos){
            if (!mvp.isEsomentario()){
                mvp.getProducto().setNombre(mvp.getProducto().getNombre().toUpperCase());
                cant++;
                tot+=Integer.valueOf(mvp.getProducto().getTotal());
            }
        }

        cantidad.setText(cant + " productos");
        total.setText("$ " + tot);

        System.out.println("datos que llegaron : " + datos.size());

        rvMusicas = (RecyclerView) root.findViewById(R.id.rv_musicas);
        glm = new GridLayoutManager(getActivity(), 1);
        rvMusicas.setLayoutManager(glm);
        adapter = new ModeloVistaProductoCarritoAdapter(datos, this,null,this);
        rvMusicas.setAdapter(adapter);

        meserotv.setText("Mesero " + mesero);
        mesatv.setText("Mesa " + mesa);
        setHasOptionsMenu(true);
        fragmentManager = getFragmentManager();

        return root;
    }


    @Override
    public void onClick(View view){
        if (view==this.btnenviar){
            if (datos.size()>0){
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                CargandoFragment cf = new CargandoFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment, cf);
                fragmentTransaction.commit();

                new DetallePedidoFragment.enviarPedido().execute();
            }
            return;
        }

        if (view == this.volver){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            MesaVistaFragment cf = new MesaVistaFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("mesa", mesa);
            bundle.putSerializable("familias", familias);
            bundle.putSerializable("subfamilias", subfamilias);
            bundle.putSerializable("productos", productos);
            bundle.putSerializable("mesero", mesero);
            bundle.putSerializable("carrito", datos);
            bundle.putSerializable("listaeli", listabtneliminar);
            bundle.putSerializable("ultimoesproducto", ultimoesproducto);
            bundle.putSerializable("esvolver", true);
            bundle.putSerializable("idtemp",idtemp);
            cf.setArguments(bundle);

            fragmentTransaction.replace(R.id.nav_host_fragment, cf);
            fragmentTransaction.commit();
        }
    }

    class enviarPedido extends AsyncTask<Void,Void,Void> {
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


                for (ModeloVistaProducto mvp: datos){
                    String sql = "";
                    if (mvp.isEsomentario()){
                        sql = "INSERT INTO celular values (" +null + ", " + mesa + ", '0', '" +
                                mvp.getComentario() + "', '" + mesero + "', 'a')";
                        System.out.println(sql);
                    }
                    else {
                        sql = "INSERT INTO celular values (" +null + ", " + mesa + ", '" + mvp.getProducto().getCodigo() +
                                "', " + "''" +  ", '" + mesero + "', 'a')";
                        System.out.println(sql);
                    }
                    st2.executeUpdate(sql);
                }
                Statement st3 = conn2.createStatement();
                String sql3 = "INSERT into imprimir VALUES (" + null + ", " + mesa + ", 'n')";
                st3.executeUpdate(sql3);

                conn2.close();


            }catch (Exception e){
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                InformacionFragment cf = new InformacionFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("mensaje", "¡Pedido no fue enviado!");
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
            bundle.putSerializable("mensaje", "¡Pedido enviado con éxito!");
            bundle.putSerializable("estado", true);
            cf.setArguments(bundle);

            fragmentTransaction.replace(R.id.nav_host_fragment, cf);
            fragmentTransaction.commit();

            super.onPostExecute(Result);
        }
    }


}

