package com.example.agame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Apuestas extends AppCompatActivity {

    private EditText txtStaticImporte, txtStaticGanancias, txtImporte, txtGanancias;
    private Button aceptar;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apuestas);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        txtStaticImporte = findViewById(R.id.staticImporte);
        txtStaticGanancias = findViewById(R.id.staticGanancias);
        txtImporte = findViewById(R.id.Importe);
        txtGanancias = findViewById(R.id.Ganancias);
        aceptar = findViewById(R.id.aceptar);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Apuestas.this, today.class);
            }
        });

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem opcion_menu) {
        if (opcion_menu.getItemId() == R.id.action_profile) {
            Intent j = new Intent(Apuestas.this, profile.class);
            startActivity(j);
        }
        if (opcion_menu.getItemId() == R.id.hoy) {
            Intent i = new Intent(Apuestas.this, today.class);
            startActivity(i);
        }
        if (opcion_menu.getItemId() == R.id.Partidos) {
            Intent m = new Intent(Apuestas.this, matches.class);
            startActivity(m);
        }
        if (opcion_menu.getItemId() == R.id.transferencias) {
            Intent n = new Intent(Apuestas.this, GirarPantalla.class);
            startActivity(n);
        }
        return super.onOptionsItemSelected(opcion_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu miMenu) {
        getMenuInflater().inflate(R.menu.main_menu, miMenu);
        getMenuInflater().inflate(R.menu.second_menu, miMenu);

        return true;
    }
}