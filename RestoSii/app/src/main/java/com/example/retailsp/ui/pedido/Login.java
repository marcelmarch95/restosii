package com.example.retailsp.ui.pedido;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.retailsp.MainActivity;
import com.example.retailsp.R;
import com.example.retailsp.ui.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login extends AppCompatActivity {


    Button button1, button2, button3, button4, button5,
            button6,button7,button8, button9, button0,buttonx, buttone;
    public EditText pass;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        MainActivity.vendedor = null;

        pass = (EditText) findViewById(R.id.editTextNumberPassword);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        buttone = (Button) findViewById(R.id.buttone);
        buttonx = (Button) findViewById(R.id.buttonx);

        progressDialog=new ProgressDialog(this);

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.setText(pass.getText()+"0");
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.setText(pass.getText()+"1");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.setText(pass.getText()+"2");
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.setText(pass.getText()+"3");
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.setText(pass.getText()+"4");
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.setText(pass.getText()+"5");
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.setText(pass.getText()+"6");
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.setText(pass.getText()+"7");
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.setText(pass.getText()+"8");
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.setText(pass.getText()+"9");
            }
        });
        buttone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTask().execute();
            }
        });
        buttonx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.setText("");
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    class MyTask extends AsyncTask<Void,Void,Void> {
        String nombre = "";

        @Override
        protected void onPreExecute() {

            progressDialog.setMessage("Buscando Usuario...");
            progressDialog.show();

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try{

                ConnectionClass c = new ConnectionClass();
                Connection conn = c.CONN();
                Statement st = conn.createStatement();
                String sql = "Select * From usuario where contrasena = '"+pass.getText()+"';";

                final ResultSet rs = st.executeQuery(sql);


                //Get data size
                while(rs.next()){
                    nombre = rs.getString("nombre");
                    MainActivity.vendedor = nombre;

                }
                conn.close();

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void Result){
            //MyAdapter adapter = new MyAdapter(MainActivity.this);
            // list.setAdapter(adapter);

            progressDialog.hide();
            super.onPostExecute(Result);
            if (MainActivity.vendedor != null){

                Intent replyIntent = new Intent();
                replyIntent.putExtra("MI_RESPUESTA", MainActivity.vendedor);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
            pass.setText("");

        }
    }
}