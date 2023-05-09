package com.example.agame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agame.models.Sport;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class today extends AppCompatActivity {
    private static final String API_BASE_URL = "https://api.the-odds-api.com";
    private TextView sportText;
    private TextView tvDescripcion;
    private IbetRESTAPIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        sportText = (TextView) findViewById(R.id.sportText);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(IbetRESTAPIService.class);
        this.getSport();

    }
    public void getSport(){
        Toast.makeText(today.this, "aqui", Toast.LENGTH_LONG).show();
        Call<List<Sport>> call = apiService.getSports();


        call.enqueue(new Callback<List<Sport>>() {
            @Override
            public void onResponse(Call<List<Sport>> call, Response<List<Sport>> response) {
                List<Sport> sportList = response.body();
                if (sportList != null) {
                    for (Sport sport : sportList) {
                        sportText.append("key: " + sport.getKey());
                        sportText.append("group: " + sport.getGroup());
                        sportText.append("title: " + sport.getTitle());
                        sportText.append("description: " + sport.getDescription());
                        sportText.append("Is the sport active: " + sport.getActive().toString());
                        sportText.append("Has outrights: " + sport.getHas_outrights().toString());
                        sportText.append("\n\n");
                    }
                }



            }

            @Override
            public void onFailure(Call<List<Sport>> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        "ERROR: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem opcion_menu) {
        if (opcion_menu.getItemId() == R.id.action_profile) {
            Intent j = new Intent(today.this, profile.class);
            startActivity(j);
        }
        if (opcion_menu.getItemId() == R.id.hoy) {
            Intent i = new Intent(today.this, today.class);
            startActivity(i);
        }
        if (opcion_menu.getItemId() == R.id.Partidos) {
            Intent m = new Intent(today.this, matches.class);
            startActivity(m);
        }
        if (opcion_menu.getItemId() == R.id.Noticias) {
            Intent n = new Intent(today.this, news.class);
            startActivity(n);
        }
        if (opcion_menu.getItemId() == R.id.transferencias) {
            Intent n = new Intent(today.this, GirarPantalla.class);
            startActivity(n);
        }
        return super.onOptionsItemSelected(opcion_menu);
    }
    @Override public boolean onCreateOptionsMenu(Menu miMenu) {
        getMenuInflater().inflate(R.menu.main_menu, miMenu);
        getMenuInflater().inflate(R.menu.second_menu, miMenu);

        return true;
    }

}