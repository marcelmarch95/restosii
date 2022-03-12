package com.example.retailsp.ui.notifications;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.retailsp.MainActivity;
import com.example.retailsp.R;
import com.example.retailsp.ui.ConnectionClass;
import com.example.retailsp.ui.pedido.BitmapUtil;
import com.example.retailsp.ui.pedido.BluetoothUtil;
import com.example.retailsp.ui.pedido.Cantidad;
import com.example.retailsp.ui.pedido.ESCUtil;
import com.example.retailsp.ui.pedido.Eliminar;
import com.example.retailsp.ui.pedido.Producto;
import com.example.retailsp.ui.pedido.ProductosAdapter;
import com.example.retailsp.ui.pedido.SunmiPrintHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotificationsFragment extends Fragment implements View.OnClickListener {

    private NotificationsViewModel notificationsViewModel;

    Button enviar;
    ArrayList<Producto> array;
    public static TextView totaltxt;
    public static ListView listaDeProductos;
    public static ProductosAdapter adapter;
    ProgressDialog progressDialog;
    public int numero;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_opciones, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        final Button enviar = root.findViewById(R.id.enviar);
        listaDeProductos = root.findViewById(R.id.lista_enviar);
        totaltxt = root.findViewById(R.id.texttot);


        adapter = new ProductosAdapter(getActivity().getApplicationContext(),
                R.layout.itemdelista, MainActivity.productos);

        listaDeProductos.setAdapter(adapter);

        enviar.setOnClickListener(this);

        progressDialog=new ProgressDialog(this.getActivity());

        int total = 0;
        for (Producto p : MainActivity.productos){
            total += p.getCantidad()*p.getPrecio();
        }

        totaltxt.setText("Total $"+total);


        listaDeProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Producto item = MainActivity.productos.get(position);
                Eliminar bottomSheetDialog = Eliminar.newInstance();

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("array", (ArrayList<? extends Parcelable>) MainActivity.productos);
                bundle.putParcelable("item", item);
                bottomSheetDialog.setArguments(bundle);
                bottomSheetDialog.show(getActivity().getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");

                ProductosAdapter adapter = new ProductosAdapter(getActivity().getApplicationContext(),
                        R.layout.itemdelista, MainActivity.productos);

                listaDeProductos.setAdapter(adapter);

            }


        });

        return root;
    }


    public static void refresh(){


        listaDeProductos.setAdapter(adapter);
        int total = 0;
        for (Producto p : MainActivity.productos){
            total += p.getCantidad()*p.getPrecio();
        }

        totaltxt.setText("Total $"+total);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.enviar:
                new MyTask().execute();



                break;

        }

    }

    public void imprimirtexto(String texto, int size, boolean bold, boolean underline){
        if (!BluetoothUtil.isBlueToothPrinter) {
            SunmiPrintHelper.getInstance().printText(texto, size, bold, underline);

        }
    }

    public void imprimircodigo(){
        String codigo = "P"+numero;
        int encode = 8;
        int position = 2;
        int symbology;

        if(encode > 7){
            symbology = 8;
        }else{
            symbology = encode;
        }
        Bitmap bitmap = BitmapUtil.generateBitmap(codigo, symbology, 400, 100);
        if (bitmap != null) {
            //mImageView.setImageDrawable(new BitmapDrawable(bitmap));
        }else{

        }

        SunmiPrintHelper.getInstance().printBitmap(bitmap, 0);

        int height = 120;
        int width = 2;
        if (!BluetoothUtil.isBlueToothPrinter) {
            //text = "1 Toast";
            //toast = Toast.makeText(context, text, duration);
            //toast.show();
            //SunmiPrintHelper.getInstance().printBarCode(codigo, encode, height, width, position);
            // SunmiPrintHelper.getInstance().feedPaper();
        } else {
            //text = "2 Toast";
            //toast = Toast.makeText(context, text, duration);
            //toast.show();
            // BluetoothUtil.sendData(ESCUtil.getPrintBarCode(codigo, encode, height, width, position));
            //  BluetoothUtil.sendData(ESCUtil.nextLine(4));
        }
    }

    class MyTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {

            progressDialog.setMessage("Obteniendo Información...");
            progressDialog.show();

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try{

                ConnectionClass c = new ConnectionClass();
                Connection conn = c.CONN();
                Statement st = conn.createStatement();
                String sql = "Select MAX(`numeroPedido`) From pedido;";

                final ResultSet rs = st.executeQuery(sql);

                //Get data size
                while(rs.next()){

                    numero = rs.getInt(1) + 1;

                }

                conn.close();

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void Result){


            progressDialog.hide();
            new Insertar().execute();
            super.onPostExecute(Result);

        }
    }

    class Insertar extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {

            progressDialog.setMessage("Registrando Datos...");
            progressDialog.show();

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Date cDate = new Date();
            String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
            String hora = new SimpleDateFormat("HH:mm:ss").format(cDate);
            for (Producto a : MainActivity.productos){
                Log.i("Insert",a.getCodigo()+"");
                try{

                    ConnectionClass c = new ConnectionClass();
                    Connection conn = c.CONN();
                    Statement st = conn.createStatement();
                    String sql = "INSERT INTO `pedido` (`codigo`, `pos`, `producto`, `estado`, `vendedor`, " +
                            "`fecha`, `hora`, `comentario`, `numeroPedido`, `cantidad`, `precio`) VALUES " +
                            "(NULL, '100', '"+a.getCodigo()+"', 'a', '"+MainActivity.vendedor+"', '"+fDate+"', '"+hora+"', 'BOLETA', "+numero+", " +
                             a.getCantidad()+", "+a.getPrecio()+");";

                    st.executeUpdate(sql);

                    conn.close();

                }catch (Exception e){
                    e.printStackTrace();
                }

                try{

                    ConnectionClass c2 = new ConnectionClass();
                    Connection conn2 = c2.CONN();
                    Statement st2 = conn2.createStatement();
                    Log.d("", "Cantidad "+ a.getCantidad());

                    String sql = "update `stock` set `pendiente` = `pendiente`+'"+a.getCantidad()+"' where `producto` ='"+a.getCodigo()+"' " +
                            "and `bodega` = '"+MainActivity.sucursal+"';";
                    Log.d("", sql);
                    st2.executeUpdate(sql);

                    conn2.close();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void Result){


            progressDialog.hide();
            super.onPostExecute(Result);
            Date cDate = new Date();
            String fDate = new SimpleDateFormat("dd-MM-yyyy").format(cDate);
            String hora = new SimpleDateFormat("HH:mm:ss").format(cDate);
            imprimirtexto("      Preventa",40,true,false);
            SunmiPrintHelper.getInstance().feedPaper();

            imprimirtexto("Atendido Por: "+MainActivity.vendedor+"\n",26,true,false);
            imprimirtexto("Documento: "+"BOLETA\n",26,true,false);
            imprimirtexto("Fecha: "+fDate+" "+hora,26,true,false);
            SunmiPrintHelper.getInstance().feedPaper();
            imprimircodigo();
            imprimirtexto("       P"+numero, 36, true, false);
            SunmiPrintHelper.getInstance().feedPaper();

            MainActivity.productos.clear();
            refresh();

        }
    }





}