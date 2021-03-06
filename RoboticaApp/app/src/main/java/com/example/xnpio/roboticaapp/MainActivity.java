package com.example.xnpio.roboticaapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ServerSocket sc;
    private Socket so;
    private String mensajeRecibido;
    private DataOutputStream salida;
    private BufferedReader entrada;

    private ImageView leftArrow;
    private ImageView upArrow;
    private ImageView rightArrow;

    private Random random;

    private int PUERTO = 8888;

    public BooVariable bv;

    public int numerito = 3;

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

        leftArrow = (ImageView) findViewById(R.id.leftArrow);
        rightArrow = (ImageView) findViewById(R.id.rightArrow);
        upArrow = (ImageView) findViewById(R.id.upArrow);

        bv = new BooVariable();

        random = new Random();

        bv.setListener(new BooVariable.ChangeListener() {
            @Override
            public void onChange() {
                Toast.makeText(MainActivity.this,"blah", Toast.LENGTH_LONG).show();
                Log.e("MSG","CUICK!");
                /*if(bv.isBoo()){
                    leftArrow.setImageResource(R.drawable.left_arrow2);
                    upArrow.setImageResource(R.drawable.up_arrow);
                    rightArrow.setImageResource(R.drawable.right_arrow);
                }else{
                    leftArrow.setImageResource(R.drawable.left_arrow);
                    upArrow.setImageResource(R.drawable.up_arrow);
                    rightArrow.setImageResource(R.drawable.right_arrow2);
                }*/
            }
        });

        //randomDir();


        startServerSocket();
    }



    private void startServerSocket() {

        Thread thread = new Thread(new Runnable() {

            private String stringData = null;

            @Override
            public void run() {
                while (true) {
                    try {

                        Log.e("SERVER1", "INIT");
                        sc = new ServerSocket(PUERTO);

                        so = new Socket();

                        //System.out.println("Esperando una conexión:");

                        so = sc.accept();


                        Log.e("SERVER1", "ACCEPT");

//Canales de entrada y salida de datos

                        entrada = new BufferedReader(new InputStreamReader(so.getInputStream()));

                        salida = new DataOutputStream(so.getOutputStream());

                        mensajeRecibido = entrada.readLine();

                        Log.e("SERVER1", "RECIBIDO");

                        //mTextMessage.setText(mensajeRecibido);


                        Log.e("SERVER1", mensajeRecibido);

                        if (mensajeRecibido.equals("0")){
                            bv.setBoo(false);
                            //leftArrow.setImageResource(R.drawable.left_arrow2);
                            //upArrow.setImageResource(R.drawable.up_arrow);
                            //rightArrow.setImageResource(R.drawable.right_arrow);
                        } else if (mensajeRecibido.equals("1")) {
                            //leftArrow.setImageResource(R.drawable.left_arrow);
                            //upArrow.setImageResource(R.drawable.up_arrow2);
                            //rightArrow.setImageResource(R.drawable.right_arrow);
                        } else if (mensajeRecibido.equals("2")) {
                            bv.setBoo(true);
                            //leftArrow.setImageResource(R.drawable.left_arrow);
                            //upArrow.setImageResource(R.drawable.up_arrow);
                            //rightArrow.setImageResource(R.drawable.right_arrow2);
                        }

                        //System.out.println(mensajeRecibido);

                        //System.out.println("Cerrando conexión...");

                        sc.close();//Aqui se cierra la conexión con el cliente

                    } catch (Exception e)

                    {

                        Log.e("SERVER1", "ERROR");
                        Log.e("SERVER1", e.toString());

                    }

                }
            }
        });
        thread.start();
    }





    /*private void startServerSocket(){

        runOnUiThread(new Runnable() {
            private String stringData = null;

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

                    if(mensajeRecibido.equals("0")){
                        leftArrow.setImageResource(R.drawable.left_arrow2);
                        upArrow.setImageResource(R.drawable.up_arrow);
                        rightArrow.setImageResource(R.drawable.right_arrow);
                    }
                    else if(mensajeRecibido.equals("1")){
                        leftArrow.setImageResource(R.drawable.left_arrow);
                        upArrow.setImageResource(R.drawable.up_arrow2);
                        rightArrow.setImageResource(R.drawable.right_arrow);
                    }
                    else if(mensajeRecibido.equals("2")){
                        leftArrow.setImageResource(R.drawable.left_arrow);
                        upArrow.setImageResource(R.drawable.up_arrow);
                        rightArrow.setImageResource(R.drawable.right_arrow2);
                    }


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
    }
    */

    /*private void startServerSocket(){

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            private String stringData = null;

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

                    if(mensajeRecibido.equals("0")){
                        leftArrow.setImageResource(R.drawable.left_arrow2);
                        upArrow.setImageResource(R.drawable.up_arrow);
                        rightArrow.setImageResource(R.drawable.right_arrow);
                    }
                    else if(mensajeRecibido.equals("1")){
                        leftArrow.setImageResource(R.drawable.left_arrow);
                        upArrow.setImageResource(R.drawable.up_arrow2);
                        rightArrow.setImageResource(R.drawable.right_arrow);
                    }
                    else if(mensajeRecibido.equals("2")){
                        leftArrow.setImageResource(R.drawable.left_arrow);
                        upArrow.setImageResource(R.drawable.up_arrow);
                        rightArrow.setImageResource(R.drawable.right_arrow2);
                    }


                    //System.out.println(mensajeRecibido);

                    //System.out.println("Cerrando conexión...");

                    sc.close();//Aqui se cierra la conexión con el cliente

                }catch(Exception e )

                {

                    Log.e("SERVER1", "ERROR");
                    Log.e("SERVER1", e.toString());

                }
                handler.postDelayed(this, 500);

            }
        }, 500);
    }*/

    /*
    public void randomDir(){
        Random rand = new Random();
        int randomNumber = 0;
        //while(true){
        for(int j = 0; j < 10; j++){
                for(int i = 0; i < 100000; i++){
                    Log.e("NUmber", Integer.toString(randomNumber));
                }

                randomNumber = rand.nextInt(3);


        }
    }
    */

    public void randomDir(){
        int randomNumber = 0;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                int randomNumber = random.nextInt(6);
                if(randomNumber == 0){
                    leftArrow.setImageResource(R.drawable.left_arrow2);
                    upArrow.setImageResource(R.drawable.up_arrow);
                    rightArrow.setImageResource(R.drawable.right_arrow);
                    Log.e("NUmber", "0");
                }
                else if(randomNumber == 1){
                    leftArrow.setImageResource(R.drawable.left_arrow);
                    upArrow.setImageResource(R.drawable.up_arrow2);
                    rightArrow.setImageResource(R.drawable.right_arrow);
                    Log.e("NUmber", "1");
                }
                else{
                    leftArrow.setImageResource(R.drawable.left_arrow);
                    upArrow.setImageResource(R.drawable.up_arrow);
                    rightArrow.setImageResource(R.drawable.right_arrow2);
                    Log.e("NUmber", "2");
                }
                handler.postDelayed(this, 500);
            }
        }, 500);
    }

}

