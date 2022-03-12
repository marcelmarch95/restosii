package com.example.retailsp.ui.pedido;


import android.app.ProgressDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retailsp.MainActivity;
import com.example.retailsp.R;
import com.example.retailsp.adapters.ModeloVistaMesaAdapter;
import com.example.retailsp.modelo.ModeloVistaProducto;
import com.example.retailsp.modelo.Producto;
import com.example.retailsp.modelo.ModeloVistaFamSubf;
import com.example.retailsp.modelo.ModeloVistaMesa;
import com.example.retailsp.modelo.Subfamilia;
import com.example.retailsp.ui.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MesasVistaFragment extends Fragment implements View.OnClickListener{
    public EditText codigo, nombre, precio, stock, minimo;
    ProgressDialog progressDialog;
    View root;
    public  Context context;
    private RecyclerView rvMusicas;
    private GridLayoutManager glm;
    private ModeloVistaMesaAdapter adapter;
    private Button back;
    //private PerfilComerciante padre;
    private ArrayList<ModeloVistaMesa> mesas = new ArrayList<>();
    private ArrayList<ModeloVistaFamSubf> familias = new ArrayList<>();
    private ArrayList<ModeloVistaFamSubf> subfamilias = new ArrayList<>();
    private ArrayList<Producto> productos = new ArrayList<>();
    private ArrayList<ModeloVistaProducto> carrito = new ArrayList<>();
    private FragmentManager fragmentManager;
    private boolean escuenta;
    private int mesa;

    private Integer seccion;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mesas, container, false);

        progressDialog=new ProgressDialog(this.getActivity());
        escuenta = getArguments().getBoolean("escuenta");
        fragmentManager = getFragmentManager();

        mesas = (ArrayList<ModeloVistaMesa>) getArguments().getSerializable("mesas");

        if (!escuenta){
            familias = (ArrayList<ModeloVistaFamSubf>) getArguments().getSerializable("familias");
            subfamilias = (ArrayList<ModeloVistaFamSubf>) getArguments().getSerializable("subfamilias");
            productos = (ArrayList<Producto>) getArguments().getSerializable("productos");
        }


        rvMusicas = (RecyclerView) root.findViewById(R.id.rv_musicas2);
        glm = new GridLayoutManager(getActivity(), 5);
        rvMusicas.setLayoutManager(glm);
        adapter = new ModeloVistaMesaAdapter(mesas, this);
        rvMusicas.setAdapter(adapter);



        setHasOptionsMenu(true);

        return root;
    }


    @Override
    public void onClick(View view) {
        Button mesab = (Button) view;
        mesa = Integer.valueOf(mesab.getText().toString());
        System.out.println("numero de mesa: " + mesa);

        if (!escuenta) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            MesaVistaFragment cf = new MesaVistaFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("mesa", mesa);
            bundle.putSerializable("esvolver", false);
            bundle.putSerializable("mesero", MainActivity.vendedor);
            bundle.putSerializable("familias", familias);
            bundle.putSerializable("subfamilias", subfamilias);
            bundle.putSerializable("productos", productos);
            cf.setArguments(bundle);

            fragmentTransaction.replace(R.id.nav_host_fragment, cf);
            fragmentTransaction.commit();
        }
        else {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            CargandoFragment cf = new CargandoFragment();
            fragmentTransaction.replace(R.id.nav_host_fragment, cf);
            fragmentTransaction.commit();

            new MesasVistaFragment.obtenerCuentaMesa().execute();
        }
    }


    class MyTask extends AsyncTask<Void,Void,Void> {

        ArrayList<Producto> lista = new ArrayList<>();

        @Override
        protected void onPreExecute() {

            progressDialog.setMessage("Obteniendo Productos...");
            progressDialog.show();

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {


            return null;
        }

        @Override
        protected void onPostExecute(Void Result){


            progressDialog.hide();
            super.onPostExecute(Result);

        }
    }

    class guardarProducto extends AsyncTask<Void,Void,Void> {



        @Override
        protected void onPreExecute() {

            progressDialog.setMessage("Guardando Producto...");
            progressDialog.show();

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try{

                ConnectionClass c2 = new ConnectionClass();
                Connection conn2 = c2.CONN();
                Statement st2 = conn2.createStatement();
                String sql2 = "Insert into productos VALUES " +
                        "( '"+codigo.getText()+"', '"+nombre.getText()+"','','',0,0,0,0,"+precio.getText()+",0, "+minimo.getText()+",1,0,'','UN','','');";

                st2.executeUpdate(sql2);
                conn2.close();

                ConnectionClass c1 = new ConnectionClass();
                Connection conn1 = c1.CONN();
                Statement st1 = conn1.createStatement();
                String sql1 = "Insert into productosucursal values ( null, 1, '"+codigo.getText()+"');";

                st1.executeUpdate(sql1);
                conn1.close();

                ConnectionClass c = new ConnectionClass();
                Connection conn = c.CONN();
                Statement st = conn.createStatement();
                String sql = "Insert into stock values ( null, '"+codigo.getText()+"', "+stock.getText()+ ", 1 , 0);";

                st.executeUpdate(sql);

                conn.close();


            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void Result){


            progressDialog.hide();
            codigo.setText("");
            nombre.setText("");
            stock.setText("");
            minimo.setText("");
            precio.setText("");
            super.onPostExecute(Result);

        }
    }

    class obtenerCuentaMesa extends AsyncTask<Void,Void,Void> {



        @Override
        protected void onPreExecute() {

            /*progressDialog.setMessage("Obteniendo Mesas...");
            progressDialog.show();*/

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<String> idproductos = new ArrayList<>();
            ArrayList<Producto> productos = new ArrayList<>();

            try{

                ConnectionClass c2 = new ConnectionClass();
                Connection conn2 = c2.CONN();
                Statement st2 = conn2.createStatement();

                String sql2 =  "SELECT * FROM pedido WHERE mesa = " + mesa + " AND estado = 'a' and producto!='0'";


                ResultSet rs = st2.executeQuery(sql2);
                while(rs.next())
                {
                    System.out.println("--- " + rs.toString());
                    String codpro = rs.getString("producto");
                    idproductos.add(codpro);
                }
                conn2.close();



            }catch (Exception e){
                e.printStackTrace();
            }

            try{

                ConnectionClass c1 = new ConnectionClass();
                Connection conn1 = c1.CONN();
                Statement st1 = conn1.createStatement();

                String sql1 = "SELECT * FROM productos ORDER BY nombre;";


                ResultSet rs = st1.executeQuery(sql1);
                while(rs.next())
                {
                    Producto p = new Producto();
                    p.setCodigo(rs.getString(1));
                    p.setNombre(rs.getString(2));
                    p.setFamilia(rs.getString(3));
                    p.setSubfamilia(rs.getString(4));
                    p.setTotal(rs.getString(9));
                    p.setStock(rs.getFloat(10));
                    p.setMueve(rs.getBoolean(12));
                    p.setImpresora1(rs.getBoolean(14));
                    p.setImpresora2(rs.getBoolean(15));
                    p.setImpresora3(rs.getBoolean(16));
                    p.setImpresora4(rs.getBoolean(17));
                    productos.add(p);

                }
                conn1.close();
            }catch (Exception e){
                e.printStackTrace();
            }

            for (String id: idproductos){
                ModeloVistaProducto mvp = new ModeloVistaProducto();
                for (Producto p: productos){
                    if (p.getCodigo().compareTo(id)==0){
                        mvp.setProducto(p);
                        mvp.setEsomentario(false);
                    }
                }
                carrito.add(mvp);
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void Result){


            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            CuentaMesaFragment cf = new CuentaMesaFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("mesa", mesa);
            bundle.putSerializable("mesero", MainActivity.vendedor);
            bundle.putSerializable("carrito", carrito);
            cf.setArguments(bundle);

            fragmentTransaction.replace(R.id.nav_host_fragment, cf);
            fragmentTransaction.commit();

            super.onPostExecute(Result);
        }
    }


}

