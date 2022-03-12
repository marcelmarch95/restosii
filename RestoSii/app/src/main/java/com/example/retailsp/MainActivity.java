package com.example.retailsp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.retailsp.ui.pedido.Login;
import com.example.retailsp.ui.pedido.Producto;
import com.example.retailsp.ui.pedido.SunmiPrintHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static ArrayList<Producto> productos = new ArrayList<>();
    public static String vendedor;
    public static String sucursal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = new Intent(this, Login.class);
        intent.putExtra("MI_CLAVE", "MI TEXTO");
        startActivityForResult(intent, 1);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String reply =  data.getStringExtra("MI_RESPUESTA");

                setContentView(R.layout.activity_main);
                BottomNavigationView navView = findViewById(R.id.nav_view);
                // Passing each menu ID as a set of Ids because each
                // menu should be considered as top level destinations.
                AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                         R.id.navigation_home, R.id.navigation_pedido)
                        .build();
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

                NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
                NavigationUI.setupWithNavController(navView, navController);


                init();
            }
        }
    }

    private void init(){
        SunmiPrintHelper.getInstance().initSunmiPrinterService(this);
    }



}