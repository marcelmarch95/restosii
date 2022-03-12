package com.example.retailsp.ui;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.example.retailsp.MainActivity;
import com.example.retailsp.ui.pedido.Producto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 */
public class ConnectionClass {

    //private static String url = "jdbc:mysql://192.168.1.102:3306/retail";
    //private static String url = "jdbc:mysql://192.168.0.10:3306/retail";
    private static String url = "jdbc:mysql://192.168.1.200:3306/rest";
    private static String user = "mauricio";
    private static String passwd = "M945889627mH";

        public Connection CONN() {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Connection conn = null;
            String ConnURL = null;
            try {

                Class.forName("com.mysql.jdbc.Driver").newInstance();

                conn = DriverManager.getConnection(url, user, passwd);


                conn = DriverManager.getConnection(ConnURL);
            } catch (SQLException se) {
                Log.e("ERRO", se.getMessage());
            } catch (ClassNotFoundException e) {
                Log.e("ERRO", e.getMessage());
            } catch (Exception e) {
                Log.e("ERRO", e.getMessage());
            }
            return conn;
        }


}



