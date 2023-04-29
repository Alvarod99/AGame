package com.example.agame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem opcion_menu) {
        if (opcion_menu.getItemId() == R.id.hoy) {
            Intent i = new Intent(profile.this, today.class);
            startActivity(i);
        }
        if (opcion_menu.getItemId() == R.id.Partidos) {
            Intent m = new Intent(profile.this, matches.class);
            startActivity(m);
        }
        if (opcion_menu.getItemId() == R.id.Noticias) {
            Intent n = new Intent(profile.this, news.class);
            startActivity(n);
        }
        if (opcion_menu.getItemId() == R.id.transferencias) {
            Intent n = new Intent(profile.this, Banco.class);
            startActivity(n);
        }
        return super.onOptionsItemSelected(opcion_menu);
    }

    @Override public boolean onCreateOptionsMenu(Menu miMenu){
        getMenuInflater().inflate(R.menu.second_menu,miMenu);

        return true;
    }
}