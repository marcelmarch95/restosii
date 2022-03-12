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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retailsp.R;
import com.example.retailsp.adapters.ModeloVistaMesaAdapter;
import com.example.retailsp.modelo.Familia;
import com.example.retailsp.modelo.ModeloVistaFamSubf;
import com.example.retailsp.modelo.ModeloVistaMesa;
import com.example.retailsp.modelo.Producto;
import com.example.retailsp.modelo.Subfamilia;
import com.example.retailsp.ui.ConnectionClass;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SelectorMesasFragment extends Fragment implements View.OnClickListener{
    public EditText codigo, nombre, precio, stock, minimo;
    ProgressDialog progressDialog;
    View root;
    public  Context context;
    private RecyclerView rvMusicas;
    private GridLayoutManager glm;
    private ModeloVistaMesaAdapter adapter;
    private Button back;
    private Button seccion1;
    private Button seccion2;
    private Button seccion3;
    private Integer seccion;
    //private PerfilComerciante padre;
    private ArrayList<ModeloVistaMesa> mesas = new ArrayList<>();
    private ArrayList<ModeloVistaFamSubf> familias = new ArrayList<>();
    private ArrayList<ModeloVistaFamSubf> subfamilias = new ArrayList<>();
    private ArrayList<Producto> productos = new ArrayList<>();
    private Button volver;
    private TextView motivo;
    FragmentManager fragmentManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_selector_mesas, container, false);

        progressDialog=new ProgressDialog(this.getActivity());

        seccion1 = (Button) root.findViewById(R.id.seccion1);
        seccion2 = (Button) root.findViewById(R.id.seccion2);
        seccion3 = (Button) root.findViewById(R.id.seccion3);
        motivo = (TextView) root.findViewById(R.id.motivo);
        fragmentManager = getFragmentManager();

        seccion1.setOnClickListener(this);
        seccion2.setOnClickListener(this);
        seccion3.setOnClickListener(this);

        motivo.setText("para tomar pedido");

        setHasOptionsMenu(true);

        return root;
    }



    @Override
    public void onClick(View view) {
        if (view == this.seccion1){

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            CargandoFragment cf = new CargandoFragment();
            fragmentTransaction.replace(R.id.nav_host_fragment, cf);
            fragmentTransaction.commit();

            seccion = 1;
            new obtenerMesas().execute();
        }
        else if (view==this.seccion2){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            CargandoFragment cf = new CargandoFragment();
            fragmentTransaction.replace(R.id.nav_host_fragment, cf);
            fragmentTransaction.commit();

            seccion = 2;
            new obtenerMesas().execute();
        }
        else if (view==this.seccion3){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            CargandoFragment cf = new CargandoFragment();
            fragmentTransaction.replace(R.id.nav_host_fragment, cf);
            fragmentTransaction.commit();

            seccion = 3;
            new obtenerMesas().execute();
        }
    }

    class MyTask extends AsyncTask<Void,Void,Void> {


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

    class obtenerMesas extends AsyncTask<Void,Void,Void> {



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

                String sql2 = "";

                if (seccion==1){
                    sql2 = "SELECT * FROM mesas where numero<31 and numero>=1";
                }
                else if (seccion==2){
                    sql2 = "SELECT * FROM mesas where numero<61 and numero>=31";
                }
                else {
                    sql2 = "SELECT * FROM mesas where numero<91 and numero>=61";
                }

                ResultSet rs = st2.executeQuery(sql2);
                while(rs.next())
                {
                    ModeloVistaMesa mvm = new ModeloVistaMesa(rs.getInt(1),rs.getString(2));
                    mesas.add(mvm);
                }

                conn2.close();



            }catch (Exception e){
                e.printStackTrace();
            }

            try{

                ConnectionClass c1 = new ConnectionClass();
                Connection conn1 = c1.CONN();
                Statement st1 = conn1.createStatement();

                String sql1 = "SELECT * FROM familias where visible = 1 ORDER BY nombre;";


                ResultSet rs = st1.executeQuery(sql1);
                while(rs.next())
                {
                    ModeloVistaFamSubf mvf = new ModeloVistaFamSubf();
                    Familia f = new Familia();
                    f.setCodigo(rs.getString(1));
                    f.setNombre(rs.getString(2));
                    f.setVisible(rs.getInt(3));
                    mvf.setFamilia(f);
                    familias.add(mvf);
                }
                conn1.close();
            }catch (Exception e){
                e.printStackTrace();
            }

            try{

                ConnectionClass c3 = new ConnectionClass();
                Connection conn3 = c3.CONN();
                Statement st3 = conn3.createStatement();

                String sql3 = "SELECT * FROM subfamilias ORDER BY nombre;";


                ResultSet rs = st3.executeQuery(sql3);
                while(rs.next())
                {
                    ModeloVistaFamSubf mvf = new ModeloVistaFamSubf();
                    Subfamilia sf = new Subfamilia();
                    sf.setCodigo(rs.getString(1));
                    sf.setNombre(rs.getString(2));
                    sf.setFamilia(rs.getString(3));
                    mvf.setSubfamilia(sf);
                    subfamilias.add(mvf);
                }
                conn3.close();
            }catch (Exception e){
                e.printStackTrace();
            }

            try{

                ConnectionClass c3 = new ConnectionClass();
                Connection conn3 = c3.CONN();
                Statement st3 = conn3.createStatement();

                String sql3 = "SELECT * FROM productos ORDER BY nombre;";


                ResultSet rs = st3.executeQuery(sql3);
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
                conn3.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void Result){

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            MesasVistaFragment cf = new MesasVistaFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("mesas", mesas);
            bundle.putSerializable("familias", familias);
            bundle.putSerializable("subfamilias", subfamilias);
            bundle.putSerializable("productos", productos);
            bundle.putSerializable("escuenta", false);
            cf.setArguments(bundle);

            fragmentTransaction.replace(R.id.nav_host_fragment, cf);
            fragmentTransaction.commit();

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


}

