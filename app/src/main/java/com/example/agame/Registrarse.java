package com.example.agame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.Map;

public class Registrarse extends AppCompatActivity {

    Button botonRegistro;
    EditText nombre, apellido, correo, contrasena;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        nombre = findViewById(R.id.Nombre);
        apellido = findViewById(R.id.Apellido);
        correo = findViewById(R.id.Correo);
        contrasena = findViewById(R.id.Contraseña);
        botonRegistro = findViewById(R.id.botonRegistro);

        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreUsuario = nombre.getText().toString().trim();
                String apellidoUsuario = apellido.getText().toString().trim();
                String correoUsuario = correo.getText().toString().trim();
                String contrasenaUsuario = contrasena.getText().toString().trim();
                if (nombreUsuario.isEmpty() || apellidoUsuario.isEmpty() || correoUsuario.isEmpty() || contrasenaUsuario.isEmpty()) {
                    Toast.makeText(Registrarse.this, "Complete todos los datos", Toast.LENGTH_LONG).show();
                } else {
                    registroUsuario(nombreUsuario, apellidoUsuario, correoUsuario, contrasenaUsuario);
                }
            }

        });
    }

    public void registroUsuario(String nombreUsuario, String apellidoUsuario, String correoUsuario, String contrasenaUsuario)
        {
            mAuth.createUserWithEmailAndPassword(correoUsuario, contrasenaUsuario).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", id);
                    map.put("nombre", nombreUsuario);
                    map.put("apellido", apellidoUsuario);
                    map.put("correo", correoUsuario);
                    map.put("contrasena", contrasenaUsuario);

                    mFirestore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            finish();
                            startActivity(new Intent(Registrarse.this, MainActivity.class));
                            Toast.makeText(Registrarse.this, "Los datos se guardaron correctamente", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registrarse.this, "Error al guardar los datos", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Registrarse.this, "Ha ocurrido un error al registrarse", Toast.LENGTH_LONG).show();
                }
            });
    }
    public void RegistradoExito(View view){
        Intent i = new Intent(this,MenuActivity.class);
        startActivity(i);
    }

}
