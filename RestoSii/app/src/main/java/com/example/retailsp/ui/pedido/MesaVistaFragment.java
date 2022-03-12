package com.example.retailsp.ui.pedido;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.ColorSpace;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class MesaVistaFragment extends Fragment implements View.OnClickListener{
    public EditText codigo, nombre, precio, stock, minimo, comentario;
    ProgressDialog progressDialog;
    View root;
    private Button volverafamilia, btnenviar;
    public  Context context;
    private RecyclerView rvMusicas;
    private RecyclerView rvMusicas2;
    private GridLayoutManager glm;
    private String familiapresionada;
    private ModeloVistaFamSubfAdapter adapter;
    private ModeloVistaProductoAdapter adapter2;
    private ModeloVistaProductoCarritoAdapter adapter3;
    private ArrayList<ModeloVistaFamSubf> familias = new ArrayList<>();
    private ArrayList<ModeloVistaFamSubf> subfamilias = new ArrayList<>();
    private ArrayList<ModeloVistaFamSubf> datos = new ArrayList<>();
    private ArrayList<ModeloVistaProducto> datos2 = new ArrayList<>();
    private ArrayList<ModeloVistaProducto> datoscarrito = new ArrayList<>();
    private ArrayList<Producto> productos = new ArrayList<>();
    private Button back;
    private int mesa;
    private String mesero;
    public ArrayList<Button> listabtneliminar = new ArrayList<>();
    private int idtemp = 0;
    private int seccion;
    private boolean enfamilia = true;
    private boolean ensubfamilia = false;
    private boolean enproducto = false;
    private ImageButton enviarcomentario;
    private boolean ultimoesproducto = false;
    private TextView meserotv, mesatv;
    private boolean esvolver;
    FragmentManager fragmentManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mesa, container, false);

        progressDialog=new ProgressDialog(this.getActivity());

        btnenviar = (Button) root.findViewById(R.id.btnenviar);
        btnenviar.setOnClickListener(this);
        meserotv = (TextView) root.findViewById(R.id.mesero);
        mesatv = (TextView) root.findViewById(R.id.mesatv);
        comentario = (EditText) root.findViewById(R.id.comentario);
        enviarcomentario = (ImageButton) root.findViewById(R.id.enviarcomentario);
        enviarcomentario.setOnClickListener(this);
        volverafamilia = root.findViewById(R.id.volverafamilia);
        volverafamilia.setOnClickListener(this);
        volverafamilia.setEnabled(false);
        fragmentManager = getFragmentManager();

        esvolver = (boolean) getArguments().getSerializable("esvolver");

        rvMusicas = (RecyclerView) root.findViewById(R.id.rv_musicas);
        rvMusicas2 = (RecyclerView) root.findViewById(R.id.rv_musicas2);

        if (esvolver){
            listabtneliminar = (ArrayList<Button>) getArguments().getSerializable("listaeli");
            ultimoesproducto = getArguments().getBoolean("ultimoesproducto");
            datoscarrito = (ArrayList<ModeloVistaProducto>) getArguments().getSerializable("carrito");
            idtemp = (int) getArguments().getSerializable("idtemp");

            glm = new GridLayoutManager(getActivity(), 1);
            rvMusicas2.setLayoutManager(glm);
            adapter3 = new ModeloVistaProductoCarritoAdapter(datoscarrito, this,this,null);
            rvMusicas2.setAdapter(adapter3);
            rvMusicas2.scrollToPosition(datoscarrito.size()-1);

        }

        mesa = (Integer) getArguments().getSerializable("mesa");
        mesero = (String) getArguments().getSerializable("mesero");
        familias = (ArrayList<ModeloVistaFamSubf>) getArguments().getSerializable("familias");
        datos = familias;
        subfamilias = (ArrayList<ModeloVistaFamSubf>) getArguments().getSerializable("subfamilias");
        productos = (ArrayList<Producto>) getArguments().getSerializable("productos");


        glm = new GridLayoutManager(getActivity(), 5);
        rvMusicas.setLayoutManager(glm);
        adapter = new ModeloVistaFamSubfAdapter(datos, this);
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

                DetallePedidoFragment cf = new DetallePedidoFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("mesa", mesa);
                bundle.putSerializable("mesero", MainActivity.vendedor);
                bundle.putSerializable("carrito", datoscarrito);
                bundle.putSerializable("mesa", mesa);
                bundle.putSerializable("familias", familias);
                bundle.putSerializable("subfamilias", subfamilias);
                bundle.putSerializable("productos", productos);
                bundle.putSerializable("listaeli", listabtneliminar);
                bundle.putSerializable("ultimoesproducto", ultimoesproducto);
                bundle.putSerializable("idtemp",idtemp);
                cf.setArguments(bundle);

                fragmentTransaction.replace(R.id.nav_host_fragment, cf);
                fragmentTransaction.commit();
                /*System.out.println("tamaño : " + datoscarrito.size());

                for (ModeloVistaProducto mvp: datoscarrito){
                    if (mvp.isEsomentario()){
                        System.out.println("COM " + mvp.getComentario() + " $0");
                    }
                    else {
                        System.out.println(mvp.getProducto().getCodigo() + " " + mvp.getProducto().getNombre() + " " + mvp.getProducto().getTotal());
                    }
                }*/
            }
            return;
        }
        if (listabtneliminar.contains(view)){
            Button bt = (Button) view;
            System.out.println("Eliminar un producto con id: " + bt.getText());

            List<String> salida = new ArrayList<>();

            StringTokenizer arr = new StringTokenizer(bt.getText().toString(), "\n");
            while(arr.hasMoreTokens()){
                salida.add(arr.nextToken());
            }

            String tipo = salida.get(1);
            String codigo = salida.get(0);
            String idtempp = salida.get(2);

            ModeloVistaProducto eliminar = null;

            for (ModeloVistaProducto mvp: datoscarrito){
                if (mvp.getIdtemp()==Integer.valueOf(idtempp)){
                    eliminar = mvp;
                }
            }
            if (eliminar!=null){

                //SI SE ELIMINA UN COMENTARIO
                if (codigo.compareTo("-1")==0){
                    listabtneliminar.clear();
                    datoscarrito.remove(eliminar);
                    glm = new GridLayoutManager(getActivity(), 1);
                    rvMusicas2.setLayoutManager(glm);
                    adapter3 = new ModeloVistaProductoCarritoAdapter(datoscarrito, this,this,null);
                    rvMusicas2.setAdapter(adapter3);
                    if (listabtneliminar.size()>0){
                        ultimoesproducto = true;
                    }
                }
                else {
                    System.out.println("SE ELIMINA UN PRODUCTO");
                    Button eliminando = null;
                    Button despues = null;
                    int poseliminado = -1;

                    String bb = ((Button) view).getText().toString();
                    StringTokenizer el2 = new StringTokenizer(bb, "\n");
                    List<String> eli2 = new ArrayList<>();
                    while(el2.hasMoreTokens()){
                        eli2.add(el2.nextToken());
                    }
                    int ideliminando = Integer.valueOf(eli2.get(2));
                    int iddespues = 0;

                    for (int i=0; i<listabtneliminar.size(); i++){
                        String aa = listabtneliminar.get(i).getText().toString();

                        List<String> eli = new ArrayList<>();

                        StringTokenizer el = new StringTokenizer(aa, "\n");
                        while(el.hasMoreTokens()){
                            eli.add(el.nextToken());
                        }
                        int codt = Integer.valueOf(eli.get(2));
                        //System.out.println("comparando " + codt +" con " + cod2);

                        if (codt == ideliminando) {
                            System.out.println("ENCONTRADO EL QUE SE ESTA ELIMINANDO ");
                            eliminando = listabtneliminar.get(i);


                            System.out.println("ideliminando es " + ideliminando);
                            System.out.println("listabtneliminar.size() " + listabtneliminar.size());
                            if (ideliminando<idtemp){
                                System.out.println("entro al iffffffffffff");
                                for (Button vv: listabtneliminar){
                                    System.out.println("btn " + vv.getText().toString());
                                    List<String> ad = new ArrayList<>();

                                    StringTokenizer add = new StringTokenizer(vv.getText().toString(), "\n");
                                    while(add.hasMoreTokens()){
                                        ad.add(add.nextToken());
                                    }
                                    int tt = Integer.valueOf(ad.get(2));
                                    System.out.println("evaluando tt: " + tt);
                                    if (tt>ideliminando){
                                        if (iddespues==0){
                                            iddespues = tt;
                                            despues = vv;
                                            System.out.println("ahora iddespues es : " + iddespues);
                                        }
                                        else if (iddespues>tt) {
                                            iddespues = tt;
                                            despues = vv;
                                            System.out.println("ahora iddespues es : " + iddespues);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    System.out.println("eliminando: " + eliminando.getText().toString());
                    if (eliminando!=null && despues!=null){

                        System.out.println("despues :" + despues.getText().toString());
                        System.out.println("ELIMINADO!=NULL");
                        List<String> eli = new ArrayList<>();

                        StringTokenizer el = new StringTokenizer(despues.getText().toString(), "\n");
                        while(el.hasMoreTokens()){
                            eli.add(el.nextToken());
                        }
                        System.out.println("el despues es " + despues.getText().toString());
                        if (eli.get(0).compareTo("-1")==0){
                            System.out.println("el de despues es comentario");
                            ModeloVistaProducto eliminar2 = null;

                            for (ModeloVistaProducto mvp: datoscarrito){
                                System.out.println("comparando id " + mvp.getIdtemp() + " con " + Integer.valueOf(eli.get(2)));
                                if (mvp.getIdtemp()==Integer.valueOf(eli.get(2))){
                                    eliminar2 = mvp;
                                }
                            }
                            System.out.println("antes listado carrito size: " + datoscarrito.size());
                            System.out.println("EL PRODUCTO TENIA UN COMENTARIO QUE FUE BORRADO");
                            datoscarrito.remove(eliminar2);
                            System.out.println("despues listado carrito size: " + datoscarrito.size());

                            if (listabtneliminar.size()>0){
                                ultimoesproducto = true;
                            }
                        }
                    }
                    listabtneliminar.clear();
                    datoscarrito.remove(eliminar);
                    glm = new GridLayoutManager(getActivity(), 1);
                    rvMusicas2.setLayoutManager(glm);
                    adapter3 = new ModeloVistaProductoCarritoAdapter(datoscarrito, this,this,null);
                    rvMusicas2.setAdapter(adapter3);
                }
            }
            return;
        }
        else if (view == this.enviarcomentario){
            if (this.listabtneliminar.size()==0)
                return;

            if (comentario.getText().length()>0 && ultimoesproducto){
                String com = comentario.getText().toString();
                ultimoesproducto = false;
                comentario.setText("");

                listabtneliminar.clear();

                ModeloVistaProducto coment = new ModeloVistaProducto();
                coment.setEsomentario(true);
                idtemp+=1;
                coment.setIdtemp(idtemp);
                coment.setComentario(com);
                datoscarrito.add(coment);
                glm = new GridLayoutManager(getActivity(), 1);
                rvMusicas2.setLayoutManager(glm);
                adapter3 = new ModeloVistaProductoCarritoAdapter(datoscarrito, this,this,null);
                rvMusicas2.setAdapter(adapter3);
                rvMusicas2.scrollToPosition(datoscarrito.size()-1);
            }
            return;
        }
        if (view == volverafamilia){
            glm = new GridLayoutManager(getActivity(), 5);
            rvMusicas.setLayoutManager(glm);
            datos = familias;
            adapter = new ModeloVistaFamSubfAdapter(datos, this);
            rvMusicas.setAdapter(adapter);
            volverafamilia.setEnabled(false);
            rvMusicas2.scrollToPosition(datoscarrito.size()-1);

            enfamilia = true;
            ensubfamilia = false;
            enproducto = false;
        }
        else if (enfamilia){
            String famtext = ((Button) view).getText().toString();
            familiapresionada = famtext;

            ArrayList<ModeloVistaFamSubf> subfamiliasnu = new ArrayList<>();
            for (ModeloVistaFamSubf m: subfamilias){
                if (m.getSubfamilia().getFamilia().compareTo(famtext)==0)
                    subfamiliasnu.add(m);
            }

            if (subfamiliasnu.size()==0){
                ArrayList<ModeloVistaProducto> productosnu = new ArrayList<>();
                for (Producto p: productos){
                    if (p.getFamilia().compareTo(famtext)==0) {
                        ModeloVistaProducto mv = new ModeloVistaProducto();
                        mv.setProducto(p);
                        productosnu.add(mv);
                    }
                }
                datos2 = productosnu;
                glm = new GridLayoutManager(getActivity(), 5);
                rvMusicas.setLayoutManager(glm);
                adapter2 = new ModeloVistaProductoAdapter(datos2, this);
                rvMusicas.setAdapter(adapter2);
                rvMusicas2.scrollToPosition(datoscarrito.size()-1);

                enfamilia = false;
                enproducto = true;
                ensubfamilia = false;
                volverafamilia.setEnabled(true);
            }
            else {
                datos = subfamiliasnu;
                glm = new GridLayoutManager(getActivity(), 5);
                rvMusicas.setLayoutManager(glm);
                adapter = new ModeloVistaFamSubfAdapter(datos, this);
                rvMusicas.setAdapter(adapter);
                rvMusicas2.scrollToPosition(datoscarrito.size()-1);

                enfamilia = false;
                enproducto = false;
                ensubfamilia = true;

                volverafamilia.setEnabled(true);
            }

        }
        else if (ensubfamilia){
            String subfamtext = ((Button) view).getText().toString();
            ArrayList<ModeloVistaProducto> productosnu = new ArrayList<>();
            for (Producto p: productos){
                if (p.getSubfamilia().compareTo(subfamtext)==0 && p.getFamilia().compareTo(familiapresionada)==0) {
                    ModeloVistaProducto mv = new ModeloVistaProducto();
                    mv.setProducto(p);
                    productosnu.add(mv);
                }
            }
            datos2 = productosnu;
            glm = new GridLayoutManager(getActivity(), 5);
            rvMusicas.setLayoutManager(glm);
            adapter2 = new ModeloVistaProductoAdapter(datos2, this);
            rvMusicas.setAdapter(adapter2);
            rvMusicas2.scrollToPosition(datoscarrito.size()-1);

            enfamilia = false;
            enproducto = true;
            ensubfamilia = false;
            volverafamilia.setEnabled(true);
        }
        else if (enproducto){
            listabtneliminar.clear();
            String productotxt = ((Button) view).getText().toString();

            ModeloVistaProducto seleccionado = null;

            for (ModeloVistaProducto m: datos2){
                if (m.getProducto().getNombre().compareTo(productotxt)==0 && m.getProducto().getFamilia().compareTo(familiapresionada)==0){
                    seleccionado = m;
                }
            }
            if (seleccionado!=null){
                ModeloVistaProducto seleccionadonuevo = new ModeloVistaProducto();
                idtemp+=1;
                seleccionadonuevo.setIdtemp(idtemp);
                seleccionadonuevo.setComentario(seleccionado.getComentario());
                seleccionadonuevo.setProducto(seleccionado.getProducto());
                seleccionadonuevo.setEsomentario(seleccionado.isEsomentario());

                datoscarrito.add(seleccionadonuevo);
                glm = new GridLayoutManager(getActivity(), 1);
                rvMusicas2.setLayoutManager(glm);
                adapter3 = new ModeloVistaProductoCarritoAdapter(datoscarrito, this,this,null);
                rvMusicas2.setAdapter(adapter3);
                rvMusicas2.scrollToPosition(datoscarrito.size()-1);
                ultimoesproducto=true;

                System.out.println("ahora el carro tiene: " + listabtneliminar.size());
            }
        }
    }

    class obtenerMesas extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {


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
                    //mesas.add(mvm);
                }

                conn2.close();



            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void Result){

            super.onPostExecute(Result);
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


}

