package com.example.agame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem opcion_menu) {
        if (opcion_menu.getItemId() == R.id.action_profile) {
            Intent j = new Intent(MenuActivity.this, profile.class);
            startActivity(j);
        }
        return super.onOptionsItemSelected(opcion_menu);
    }
    @Override public boolean onCreateOptionsMenu(Menu miMenu){//Sigue sin funcionar
        getMenuInflater().inflate(R.menu.main_menu,miMenu);

        return true;
    }

}