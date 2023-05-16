package com.example.agame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.agame.models.Bookmaker;
import com.example.agame.models.Event;
import com.example.agame.models.Market;
import com.example.agame.models.Outcome;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class today extends AppCompatActivity{
    private static final String API_BASE_URL = "https://api.the-odds-api.com";
    Button price1, price2, priceX;
    private IbetRESTAPIService apiService;

    private ListView listView;
    List<RowPartidos> RowPartidos;
    ArrayAdapter<RowPartidos> adapter;
    List<RowPartidos> rowPartidos;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        price1 = findViewById(R.id.Price1);
        price2 = findViewById(R.id.Price2);
        priceX = findViewById(R.id.PriceX);

        /*price1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(today.this,Apuestas.class);
                startActivity(i);
            }
        });*/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(IbetRESTAPIService.class);
        rowPartidos = new ArrayList<RowPartidos>();


        listView = (ListView) findViewById(R.id.listView);
        adapter = new RowArrayAdapter(this, R.layout.rowpartidos, rowPartidos);
        listView.setAdapter(adapter);
        this.getEvent();

/*
        price2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(today.this,Apuestas.class);
                startActivity(i);
            }
        });

        priceX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(today.this,Apuestas.class);
                startActivity(i);
            }
        });*/

    }

    public void getEvent(){
        //Toast.makeText(today.this, "aqui", Toast.LENGTH_LONG).show();
        Call<List<Event>> call = apiService.getUpcomingEvents();


        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                List<Event> eventList = response.body();
                if (eventList != null) {
                    for (Event event : eventList) {
                        List<Bookmaker> bookmarkers = event.getBookmakers();
                        for (Bookmaker bookmarker : bookmarkers) {
                            if (bookmarker.getKey().equalsIgnoreCase("betfair")) {
                                List<Market> markets = bookmarker.getMarkets();
                                for (Market market : markets) {
                                    if(market.getKey().equalsIgnoreCase("h2h")){
                                        List<Outcome> outcomes = market.getOutcomes();
                                        if (outcomes.size() < 3) {
                                            RowPartidos item = new RowPartidos(event.getSport_title(), event.getHome_team(),
                                                    event.getAway_team(), outcomes.get(0).getPrice(), outcomes.get(1).getPrice(), 0.0);
                                            rowPartidos.add(item);
                                            adapter.notifyDataSetChanged();

                                        } else {

                                            RowPartidos item = new RowPartidos(event.getSport_title(), event.getHome_team(),
                                                    event.getAway_team(), outcomes.get(0).getPrice(), outcomes.get(1).getPrice(), outcomes.get(2).getPrice());
                                            rowPartidos.add(item);
                                            adapter.notifyDataSetChanged();

                                        }

                                    }
                                }
                            }

                        }
                    }

                }

            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
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
        if (opcion_menu.getItemId() == R.id.transferencias) {
            Intent n = new Intent(today.this, GirarPantalla.class);
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
