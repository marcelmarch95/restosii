package com.example.retailsp.ui.home;


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
import com.example.retailsp.ui.pedido.CargandoFragment;
import com.example.retailsp.ui.pedido.MesasVistaFragment;
import com.example.retailsp.ui.pedido.SelectorMesasFragment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnClickListener {

    public  Context context;
    private Button seccion1;
    private Button seccion2;
    private Button seccion3;
    private Integer seccion;
    private ArrayList<ModeloVistaMesa> mesas = new ArrayList<>();
    private ArrayList<ModeloVistaFamSubf> familias = new ArrayList<>();
    private ArrayList<ModeloVistaFamSubf> subfamilias = new ArrayList<>();
    private ArrayList<Producto> productos = new ArrayList<>();
    private Button volver;
    FragmentManager fragmentManager;
    private TextView motivo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_selector_mesas, container, false);

        seccion1 = (Button) root.findViewById(R.id.seccion1);
        seccion2 = (Button) root.findViewById(R.id.seccion2);
        seccion3 = (Button) root.findViewById(R.id.seccion3);
        motivo = (TextView) root.findViewById(R.id.motivo);

        seccion1.setOnClickListener(this);
        seccion2.setOnClickListener(this);
        seccion3.setOnClickListener(this);

        motivo.setText("para ver cuenta");

        setHasOptionsMenu(true);

        fragmentManager = getFragmentManager();
        return root;
    }


    @Override
    public void onClick(View view) {
        if (view == this.seccion1){
            seccion = 1;
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            CargandoFragment cf = new CargandoFragment();
            fragmentTransaction.replace(R.id.nav_host_fragment, cf);
            fragmentTransaction.commit();

            new HomeFragment.obtenerMesas().execute();
        }
        else if (view==this.seccion2){
            seccion = 2;
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            CargandoFragment cf = new CargandoFragment();
            fragmentTransaction.replace(R.id.nav_host_fragment, cf);
            fragmentTransaction.commit();

            new HomeFragment.obtenerMesas().execute();

        }
        else if (view==this.seccion3){
            seccion = 3;
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            CargandoFragment cf = new CargandoFragment();
            fragmentTransaction.replace(R.id.nav_host_fragment, cf);
            fragmentTransaction.commit();

            new HomeFragment.obtenerMesas().execute();
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


            return null;
        }

        @Override
        protected void onPostExecute(Void Result){

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            MesasVistaFragment cf = new MesasVistaFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("mesas", mesas);
            bundle.putSerializable("escuenta", true);
            cf.setArguments(bundle);

            fragmentTransaction.replace(R.id.nav_host_fragment, cf);
            fragmentTransaction.commit();

            super.onPostExecute(Result);
        }
    }
}
