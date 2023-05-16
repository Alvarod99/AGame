package com.example.agame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class matches extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem opcion_menu) {
        if (opcion_menu.getItemId() == R.id.action_profile) {
            Intent j = new Intent(matches.this, profile.class);
            startActivity(j);
        }
        if (opcion_menu.getItemId() == R.id.hoy) {
            Intent i = new Intent(matches.this, today.class);
            startActivity(i);
        }
        if (opcion_menu.getItemId() == R.id.Partidos) {
            Intent m = new Intent(matches.this, matches.class);
            startActivity(m);
        }
        if (opcion_menu.getItemId() == R.id.transferencias) {
            Intent n = new Intent(matches.this, GirarPantalla.class);
            startActivity(n);
        }
        return super.onOptionsItemSelected(opcion_menu);
    }
    @Override public boolean onCreateOptionsMenu(Menu miMenu){
        getMenuInflater().inflate(R.menu.main_menu,miMenu);
        getMenuInflater().inflate(R.menu.second_menu,miMenu);

        return true;
    }
}