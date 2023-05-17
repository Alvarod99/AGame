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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Apuestas extends AppCompatActivity {

    private EditText txtImporte;
    private TextView txtGanancias,txtStaticGanancias,txtStaticImporte;
    private Button aceptar;

    Double saldoFinal;
    Double Cuota;

    //Firebase
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    DatabaseReference BASE_DE_DATOS;

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

        Intent i = getIntent();
        Cuota = i.getExtras().getDouble("Cuota");
        txtGanancias.setText(Cuota.toString());

        /*BASE_DE_DATOS.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String Saldo = ""+ snapshot.child("Saldo").getValue();
                    saldoFinal = Double.parseDouble(Saldo);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonAceptar(view);
            }
        });

    }

    public void botonAceptar(View view){
        //Si tiene esa cantidad ingresada en la app, se le permite apostar, sino no
        Intent i = new Intent(Apuestas.this, today.class);
        startActivity(i);

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