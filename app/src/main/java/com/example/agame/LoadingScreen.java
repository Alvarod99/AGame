package com.example.agame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LoadingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        //Esta pantalla estará x segundos en espera
        final int Duracion = 2500;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Pasado los segundos determinados en Duración, ejecutaremos activity_Login
                Intent i = new Intent(LoadingScreen.this,LoginActivity.class);
                startActivity(i);
            }
        },Duracion);
    }
}