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

import com.example.agame.models.ApuestaUsuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Apuestas extends AppCompatActivity {

    private EditText ETImporte;
    private TextView txtGanancias, txtSaldo;
    String id;
    private double saldoFinal;
    private double apostar;
    private double posibleApuesta;
    private Double Cuota;
    private String idPartido;
    private String Resultado;


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


        ETImporte = findViewById(R.id.Importe);
        txtGanancias = findViewById(R.id.Ganancias);
        txtSaldo = findViewById(R.id.Saldo);

        Intent i = getIntent();
        Cuota = i.getExtras().getDouble("Cuota");
        idPartido = i.getExtras().getString("id");
        Resultado = i.getExtras().getString("Resultado");


        txtGanancias.setText(Cuota.toString()+"€");//de este modo pondremos cuanto ganaría apostando txtImporte

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        BASE_DE_DATOS = FirebaseDatabase.getInstance().getReference("Usuarios_de_app");
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        BASE_DE_DATOS.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String Saldo = ""+ snapshot.child("Saldo").getValue();
                    saldoFinal = Double.parseDouble(Saldo);//meto en saldoFinal el Saldo de Firebase
                    txtSaldo.setText("Tu saldo es de: "+saldoFinal);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void verGanancias(View view){
        posibleApuesta = Double.parseDouble(ETImporte.getText().toString());
        if((saldoFinal-posibleApuesta)>0.0) {
            double apostado = Cuota * posibleApuesta;
            txtGanancias.setText(apostado +"€");//de este modo pondremos cuanto ganaría apostando txtImporte
        }
    }

    @SuppressLint("SetTextI18n")
    public void botonAceptarApuesta(View view){
        //Si tiene esa cantidad ingresada en la app, se le permite apostar, sino no
        apostar = Double.parseDouble(ETImporte.getText().toString());
        if((saldoFinal - apostar) < 0.0) {
            txtSaldo.setText("Está intentando apostar más dinero del que tiene ingresado");
        }
        else if(Cuota == 0.0){//Cuando hay empate y no existe esa cuota
            Toast.makeText(this, "No es posible realizar apuesta con esta cuota", Toast.LENGTH_SHORT).show();
        } else{
            saldoFinal = saldoFinal - apostar;
            double ganancias = Cuota * apostar;
            txtGanancias.setText(Double.toString(ganancias));//de este modo pondremos cuanto ganaría apostando txtImporte
            Toast.makeText(this, "Su apuesta se ha realizado exitosamente", Toast.LENGTH_LONG).show();


            ApuestaUsuario apuesta = new ApuestaUsuario(id, idPartido, ganancias,Cuota, Resultado);
            BASE_DE_DATOS.child(firebaseAuth.getCurrentUser().getUid()).child("Apuestas").push().setValue(apuesta);

        }

        String saldo = String.valueOf(saldoFinal);
        String id = user.getUid();

        //Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Usuarios_de_app").child(id).child("Saldo").setValue(saldo);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem opcion_menu) {
        if (opcion_menu.getItemId() == R.id.action_profile) {
            Intent j = new Intent(Apuestas.this, Profile.class);
            startActivity(j);
        }
        if (opcion_menu.getItemId() == R.id.hoy) {
            Intent i = new Intent(Apuestas.this, Today.class);
            startActivity(i);
        }
        if (opcion_menu.getItemId() == R.id.Partidos) {
            Intent m = new Intent(Apuestas.this, Matches.class);
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