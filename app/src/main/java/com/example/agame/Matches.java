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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agame.models.Bookmaker;
import com.example.agame.models.Event;
import com.example.agame.models.Market;
import com.example.agame.models.Outcome;
import com.example.agame.models.Sport;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Matches extends AppCompatActivity {

    private Spinner Spinner = null;
    private static final String API_BASE_URL = "https://api.the-odds-api.com";
    Button price1, price2, priceX;
    private IbetRESTAPIService apiServiceCuota, apiServiceSport;
    private String sportGroup;
    private String key;
    private ListView listView;
    ArrayAdapter<RowPartidos> adapter;
    List<RowPartidos> rowPartidos;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        price1 = findViewById(R.id.Price1);
        price2 = findViewById(R.id.Price2);
        priceX = findViewById(R.id.PriceX);

        /*Aqui creo 2 apiService ya que vamos a necesitar recuperar todos
        los deportes de la api primero y luego recuperar
         los eventos de ese deporte con sus cuotas*/

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiServiceSport = retrofit1.create(IbetRESTAPIService.class);
        rowPartidos = new ArrayList<RowPartidos>();


        Spinner = findViewById(R.id.spinner);

        String [] lista = {"Seleccionar deporte", "American football" , "Baseball", "Basketball", "Cricket", "Ice Hockey" , "Rugby", "Soccer"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,lista);
        Spinner.setAdapter(adapter);

    }


    public void getSports(View view){
        List<String> sportKey = new ArrayList<>();
        Call<List<Sport>> call = apiServiceSport.getSports();
        sportGroup = Spinner.getSelectedItem().toString();
        Toast.makeText(this, sportGroup, Toast.LENGTH_SHORT).show();
        call.enqueue( new Callback<List<Sport>>(){
            @Override
            public void onResponse(Call<List<Sport>> call, Response<List<Sport>> response) {
                List<Sport> sportList = response.body();
                for( Sport sport : sportList){
                    if(sport.getGroup().equals(sportGroup)){
                        sportKey.add(sport.getKey());
                    }

                }
                for (String key : sportKey){
                    sportsByEvent(key);
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

    public void sportsByEvent(String key){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL + "/v4/sports/" + key + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiServiceCuota = retrofit.create(IbetRESTAPIService.class);
        Call<List<Event>> call = apiServiceCuota.getSportByEvents();


        listView = (ListView) findViewById(R.id.listView);
        adapter = new RowArrayAdapter(this, R.layout.rowpartidos, rowPartidos);
        listView.setAdapter(adapter);

        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                List<Event> eventList = response.body();
                int i =0;
                if (eventList != null) {
                    for (Event event : eventList) {
                        if (i < 1) {
                            List<Bookmaker> bookmarkers = event.getBookmakers();
                            for (Bookmaker bookmarker : bookmarkers) {
                                if (bookmarker.getKey().equalsIgnoreCase("betfair")) {
                                    List<Market> markets = bookmarker.getMarkets();
                                    for (Market market : markets) {
                                        if (market.getKey().equalsIgnoreCase("h2h")) {
                                            List<Outcome> outcomes = market.getOutcomes();
                                            if (outcomes.size() < 3) {
                                                RowPartidos item = new RowPartidos(event.getSport_title(), event.getHome_team(),
                                                        event.getAway_team(), event.getId(), outcomes.get(0).getPrice(), outcomes.get(1).getPrice(), 0.0);
                                                rowPartidos.add(item);
                                                adapter.notifyDataSetChanged();
                                                i++;

                                            } else {

                                                RowPartidos item = new RowPartidos(event.getSport_title(), event.getHome_team(),
                                                        event.getAway_team(), event.getId(), outcomes.get(0).getPrice(), outcomes.get(1).getPrice(), outcomes.get(2).getPrice());
                                                rowPartidos.add(item);
                                                adapter.notifyDataSetChanged();
                                                i++;

                                            }

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
            Intent j = new Intent(Matches.this, Profile.class);
            startActivity(j);
        }
        if (opcion_menu.getItemId() == R.id.hoy) {
            Intent i = new Intent(Matches.this, Today.class);
            startActivity(i);
        }
        if (opcion_menu.getItemId() == R.id.Partidos) {
            Intent m = new Intent(Matches.this, Matches.class);
            startActivity(m);
        }
        if (opcion_menu.getItemId() == R.id.transferencias) {
            Intent n = new Intent(Matches.this, GirarPantalla.class);
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