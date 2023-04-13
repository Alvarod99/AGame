package com.example.agame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onCreateOptionsMenu2(Menu menu) {
        getMenuInflater().inflate(R.menu.second_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_profile) {
            Intent j = new Intent(MenuActivity.this, profile.class);
            startActivity(j);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected2(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.hoy:
                Intent j = new Intent(MenuActivity.this, today.class);
                startActivity(j);
                break;
            case R.id.Partidos:
                Intent i = new Intent(MenuActivity.this, matches.class);
                startActivity(i);
                break;
            case R.id.Noticias:
                Intent k = new Intent(MenuActivity.this, news.class);
                startActivity(k);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}