package com.example.agame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class Banco extends AppCompatActivity {

    private RadioButton saldo = null;
    private RadioButton ingresar = null;
    private RadioButton retirar = null;

    private TextView textView6 = null;

    private EditText editTextIngresar = null;
    private EditText editTextRetirar = null;


    @SuppressLint({"MissingInflatedId", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banco);
        saldo=findViewById(R.id.saldo);
        ingresar=findViewById(R.id.ingresar);
        retirar=findViewById(R.id.retirar);

        textView6=findViewById(R.id.textView6);

        editTextIngresar=findViewById(R.id.editTextIngresar);
        editTextRetirar=findViewById(R.id.editTextIngresar);
    }

    @SuppressLint("SetTextI18n")
    public void accion(View view){
        saldo.setVisibility(View.INVISIBLE);
        ingresar.setVisibility(View.INVISIBLE);
        retirar.setVisibility(View.INVISIBLE);

        if(saldo.isChecked()){
            saldo.setVisibility(View.VISIBLE);
            saldo.setText("Su saldo actual es de: ");
        }
        if(ingresar.isChecked()){
            ingresar.setVisibility(View.VISIBLE);
            ingresar.setText("Ingrese la cantidad a ingresar: ");
        }
        if(retirar.isChecked()){
            retirar.setVisibility(View.VISIBLE);
            retirar.setText("Escriba cuanto desea retirar: ");
        }
    }

}