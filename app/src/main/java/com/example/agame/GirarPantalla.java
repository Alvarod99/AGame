package com.example.agame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class GirarPantalla extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girar_pantalla);

        final int Duracion = 1000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Pasado los segundos determinados en Duración, ejecutaremos activity_Login
                Intent i = new Intent(GirarPantalla.this,Banco.class);
                startActivity(i);
            }
        },Duracion);
    }
}