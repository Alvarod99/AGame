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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;

public class Banco extends AppCompatActivity {
    //Datos del xml
    //RadioButton
    private RadioButton rbVersaldo = null;
    private RadioButton rbIngresar = null;
    private RadioButton rbRetirar = null;

    //TextView
    private TextView tvVerSaldo = null;

    //EditText
    private EditText txtIngresar = null;
    private EditText txtRetirar = null;

    //Botón
    private Button btnAceptar;

    //Llevar cuanta del saldo
    double saldoFinal;

    //Firebase
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    DatabaseReference BASE_DE_DATOS;


    @SuppressLint({"MissingInflatedId", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banco);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        //Botones
        rbVersaldo=findViewById(R.id.btnVersaldo);
        rbIngresar=findViewById(R.id.btnIngresar);
        rbRetirar=findViewById(R.id.btnRetirar);
        btnAceptar=findViewById(R.id.btnAceptar);

        //TextView
        tvVerSaldo=findViewById(R.id.tvVerSaldo);
        txtIngresar=findViewById(R.id.txtIngresar);
        txtRetirar=findViewById(R.id.txtRetirar);

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        BASE_DE_DATOS = FirebaseDatabase.getInstance().getReference("Usuarios_de_app");

        BASE_DE_DATOS.child(user.getUid()).addValueEventListener(new ValueEventListener() {
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
        });


    }

    @SuppressLint("SetTextI18n")
    public void accion(View view){//aqui enlazamos los RB con los txt respectivos
        if(tvVerSaldo != null){
            tvVerSaldo.setVisibility(View.INVISIBLE);
        }
        if(txtIngresar != null){
            txtIngresar.setVisibility(View.INVISIBLE);
        }
        if(txtRetirar != null){
            txtRetirar.setVisibility(View.INVISIBLE);
        }


        if(rbVersaldo != null && rbVersaldo.isChecked()){
            tvVerSaldo.setVisibility(View.VISIBLE);
            tvVerSaldo.setText("Tu saldo actual es de: "+saldoFinal);
        }
        if(rbIngresar != null && rbIngresar.isChecked()){
            txtIngresar.setVisibility(View.VISIBLE);
        }
        if(rbRetirar != null && rbRetirar.isChecked()){
            txtRetirar.setVisibility(View.VISIBLE);
        }
    }

    //Botón para aceptar
    public void botonAceptar(View view){
        tvVerSaldo.setVisibility(View.INVISIBLE);
        txtIngresar.setVisibility(View.INVISIBLE);
        txtRetirar.setVisibility(View.INVISIBLE);




        if(rbVersaldo != null && rbVersaldo.isChecked()){
            tvVerSaldo.setText("Tu saldo actual es de: "+saldoFinal);
        }
        if(rbIngresar != null && rbIngresar.isChecked()){
            double ingresar = Double.parseDouble(txtIngresar.getText().toString());
            ingresar = Double.parseDouble(txtIngresar.getText().toString());
            saldoFinal = saldoFinal + ingresar;
            tvVerSaldo.setText("Tu saldo actual es de: "+saldoFinal);
            tvVerSaldo.setVisibility(view.VISIBLE);
            Toast.makeText(this, "Su operación se ha realizado con éxito", Toast.LENGTH_LONG).show();
        }
        if(rbRetirar != null && rbRetirar.isChecked()){
            double retirar = Double.parseDouble(txtRetirar.getText().toString());
                if((saldoFinal - retirar) < 0.0){
                    tvVerSaldo.setVisibility(view.VISIBLE);
                    tvVerSaldo.setText("Está intentando retirar más dinero del que tiene ingresado");
                }
                else{
                    saldoFinal = saldoFinal - retirar;
                    tvVerSaldo.setText("Tu saldo actual es de: "+saldoFinal);
                    tvVerSaldo.setVisibility(view.VISIBLE);
                    Toast.makeText(this, "Operación realizada exitosamente", Toast.LENGTH_LONG).show();
                }
        }
        String saldo = String.valueOf(saldoFinal);
        String id = user.getUid();

        //Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Usuarios_de_app").child(id).child("Saldo").setValue(saldo);

    }

    //Menús
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem opcion_menu) {
        if (opcion_menu.getItemId() == R.id.action_profile) {
            Intent j = new Intent(Banco.this, profile.class);
            startActivity(j);
        }
        if (opcion_menu.getItemId() == R.id.hoy) {
            Intent i = new Intent(Banco.this, today.class);
            startActivity(i);
        }
        if (opcion_menu.getItemId() == R.id.Partidos) {
            Intent m = new Intent(Banco.this, matches.class);
            startActivity(m);
        }
        if (opcion_menu.getItemId() == R.id.transferencias) {
            Intent n = new Intent(Banco.this, Banco.class);
            startActivity(n);
        }
        return super.onOptionsItemSelected(opcion_menu);
    }

    //Menús
    @Override public boolean onCreateOptionsMenu(Menu miMenu){
        getMenuInflater().inflate(R.menu.main_menu,miMenu);
        getMenuInflater().inflate(R.menu.second_menu,miMenu);

        return true;
    }

}