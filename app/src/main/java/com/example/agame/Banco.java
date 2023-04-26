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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Banco extends AppCompatActivity {
    private RadioButton rbVersaldo = null;
    private RadioButton rbIngresar = null;
    private RadioButton rbRetirar = null;

    private TextView tvVerSaldo = null;

    private EditText txtIngresar = null;
    private EditText txtRetirar = null;

    private double saldoIni=0.0;

    @SuppressLint({"MissingInflatedId", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banco);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        rbVersaldo=findViewById(R.id.btnVersaldo);
        rbIngresar=findViewById(R.id.btnIngresar);
        rbRetirar=findViewById(R.id.btnRetirar);

        tvVerSaldo=findViewById(R.id.tvVerSaldo);

        txtIngresar=findViewById(R.id.txtIngresar);
        txtRetirar=findViewById(R.id.txtIngresar);
    }

    @SuppressLint("SetTextI18n")
    public void accion(View view){
        if(rbVersaldo != null){
            rbVersaldo.setVisibility(View.INVISIBLE);
        }
        if(rbIngresar != null){
            rbIngresar.setVisibility(View.INVISIBLE);
        }
        if(rbRetirar != null){
            rbRetirar.setVisibility(View.INVISIBLE);
        }


        if(rbVersaldo != null && rbVersaldo.isChecked()){
            rbVersaldo.setVisibility(View.VISIBLE);
            rbVersaldo.setText("Tu saldo actual es de: "+saldoIni);
        }
        if(rbIngresar != null && rbIngresar.isChecked()){
            rbIngresar.setVisibility(View.VISIBLE);
        }
        if(rbRetirar != null && rbRetirar.isChecked()){
            rbRetirar.setVisibility(View.VISIBLE);
        }
    }

    public void botonAceptar(View view){
        rbVersaldo.setVisibility(View.INVISIBLE);
        rbIngresar.setVisibility(View.INVISIBLE);
        rbRetirar.setVisibility(View.INVISIBLE);

        if(rbVersaldo != null && rbVersaldo.isChecked()){
            rbVersaldo.setText("Tu saldo actual es de: "+saldoIni);
        }
        if(rbIngresar != null && rbIngresar.isChecked()){
            double ingresar = Double.parseDouble(null);
            if(rbIngresar != null && rbIngresar.getText() != null){
                ingresar = Double.parseDouble(rbIngresar.getText().toString());
                saldoIni = saldoIni + ingresar;
                tvVerSaldo.setText("Tu saldo actual es de: "+saldoIni);
                tvVerSaldo.setVisibility(view.VISIBLE);
                Toast.makeText(this, "Su operación se ha realizado con éxito", Toast.LENGTH_LONG).show();
            }
        }
        if(rbRetirar != null && rbRetirar.isChecked()){
            double retirar = Double.parseDouble(null);
            if(rbRetirar != null && rbRetirar.getText() != null) {
                if((saldoIni - retirar) < 0){
                    tvVerSaldo.setText("Está intentando retirar más dinero del que tiene ingresado");
                }
                else{
                    saldoIni = saldoIni - retirar;
                    tvVerSaldo.setText("Tu saldo actual es de: "+saldoIni);
                    tvVerSaldo.setVisibility(view.VISIBLE);
                    Toast.makeText(this, "Operación realizada exitosamente", Toast.LENGTH_LONG).show();
                }

            }


        }

    }



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
        if (opcion_menu.getItemId() == R.id.Noticias) {
            Intent n = new Intent(Banco.this, news.class);
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