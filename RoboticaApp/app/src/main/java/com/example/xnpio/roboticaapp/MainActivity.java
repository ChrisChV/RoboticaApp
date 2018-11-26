package com.example.xnpio.roboticaapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ServerSocket sc;
    private Socket so;
    private String mensajeRecibido;
    private DataOutputStream salida;
    private BufferedReader entrada;

    private int PUERTO = 8888;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        startServerSocket();
    }

    private void startServerSocket() {

        Thread thread = new Thread(new Runnable() {

            private String stringData = null;

            @Override
            public void run() {

                try
                {

                    Log.e("SERVER1","INIT");
                    sc = new ServerSocket(PUERTO);

                    so = new Socket();

                    //System.out.println("Esperando una conexión:");

                    so = sc.accept();


                    Log.e("SERVER1","ACCEPT");

//Canales de entrada y salida de datos

                    entrada = new BufferedReader(new InputStreamReader(so.getInputStream()));

                    salida = new DataOutputStream(so.getOutputStream());

                    mensajeRecibido = entrada.readLine();

                    Log.e("SERVER1", "RECIBIDO");

                    //mTextMessage.setText(mensajeRecibido);


                    Log.e("SERVER1", mensajeRecibido);

                    //System.out.println(mensajeRecibido);

                    //System.out.println("Cerrando conexión...");

                    sc.close();//Aqui se cierra la conexión con el cliente

                }catch(Exception e )

                {

                    Log.e("SERVER1", "ERROR");
                    Log.e("SERVER1", e.toString());

                }

            }
        });
        thread.start();
    }


    /*
    public void initServer()


    {

        try
        {


            Log.e("SERVER1","INIT");
            sc = new ServerSocket(PUERTO);

            so = new Socket();

            //System.out.println("Esperando una conexión:");

            so = sc.accept();


            Log.e("SERVER1","ACCEPT");

//Canales de entrada y salida de datos

            entrada = new BufferedReader(new InputStreamReader(so.getInputStream()));

            salida = new DataOutputStream(so.getOutputStream());

            mensajeRecibido = entrada.readLine();

            Log.e("SERVER1", "RECIBIDO");

            mTextMessage.setText(mensajeRecibido);

            //System.out.println(mensajeRecibido);

            //System.out.println("Cerrando conexión...");

            sc.close();//Aqui se cierra la conexión con el cliente

        }catch(Exception e )

        {

            Log.e("SERVER1", "ERROR");
            Log.e("SERVER1", e.toString());

        }

    }
    */

}

