package com.example.agame;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.agame.models.Sport;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class today extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String API_BASE_URL = "https://api.the-odds-api.com";
    private TextView sportText;
    private TextView tvDescripcion;
    private IbetRESTAPIService apiService;
    public final String[] sport_titles = new String[]{"Futbol", "Baloncesto"};
    public final String[] home_teams = new String[]{"Real Madrid", "Milan"};
    public final String[] away_teams = new String[]{"Manchester City", "Inter"};
    public final Double[] prices1 = new Double[]{Double.valueOf("2.06"), Double.valueOf("1.38")};
    public final Double[] prices2 = new Double[]{Double.valueOf("1.80"), Double.valueOf("1.98")};
    public final Double[] pricesX = new Double[]{Double.valueOf("1.5"), Double.valueOf("3.68")};

    private ListView listView;
    List<rowpartidos> RowPartidos;
    private ArrayAdapter<rowpartidos> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(IbetRESTAPIService.class);
        this.getSport();

        RowPartidos = new ArrayList<rowpartidos>();
        for (int i = 0; i < home_teams.length; i++) {
            rowpartidos item = new rowpartidos(sport_titles[i], home_teams[i], away_teams[i], prices1[i], prices2[i], pricesX[i]);
            RowPartidos.add(item);
        }
        for (int j = 0; j < away_teams.length; j++) {
            rowpartidos item = new rowpartidos(sport_titles[j], home_teams[j], away_teams[j], prices1[j], prices2[j], pricesX[j]);
            RowPartidos.add(item);
        }


        listView = (ListView) findViewById(R.id.listView);
        RowArrayAdapter adapter = new RowArrayAdapter(this, R.layout.rowpartidos, RowPartidos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }

    public void getSport() {
        Call<List<Sport>> call = apiService.getSports();


        /*call.enqueue(new Callback<List<Sport>>() {
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
        });*/
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

    @Override
    public boolean onCreateOptionsMenu(Menu miMenu) {
        getMenuInflater().inflate(R.menu.main_menu, miMenu);
        getMenuInflater().inflate(R.menu.second_menu, miMenu);

        return true;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast toast = Toast.makeText(getApplicationContext(), "Item" + (position + 1) + ": " + RowPartidos.get(position), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

    }

}
