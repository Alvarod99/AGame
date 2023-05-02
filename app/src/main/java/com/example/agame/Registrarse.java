package com.example.agame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Registrarse extends AppCompatActivity {

    Button btnRegistro;
    EditText nombre, apellido, correo, contrasena, fecha_nacimiento;
    FirebaseAuth mAuth;

   // LocalDate fecha_actual = LocalDate.now();//esto debería devolverme la fecha actual


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        mAuth = FirebaseAuth.getInstance();

        nombre = findViewById(R.id.Nombre);
        apellido = findViewById(R.id.Apellido);
        correo = findViewById(R.id.Correo);
        contrasena = findViewById(R.id.Contraseña);
        fecha_nacimiento=findViewById(R.id.Date);

        btnRegistro = findViewById(R.id.botonRegistro);


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correoUsuario = correo.getText().toString().trim();
                String contrasenaUsuario = contrasena.getText().toString().trim();
                String nombreUsuario = nombre.getText().toString().trim();
                String apellidoUsuario = apellido.getText().toString().trim();
                String fecha = fecha_nacimiento.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(correoUsuario).matches()){//El correo debe contener un @
                    correo.setError("Correo no válido");
                    correo.setFocusable(true);
                }
                else if(contrasenaUsuario.length()<5){
                    contrasena.setError("La contraseña debe contener mínimo 6 caracteres");
                    contrasena.setFocusable(true);
                } else if (nombreUsuario.isEmpty() || apellidoUsuario.isEmpty() || correoUsuario.isEmpty() || fecha.isEmpty()) {
                    Toast.makeText(Registrarse.this, "Rellene todos los datos", Toast.LENGTH_SHORT).show();
                } else{
                    registroUsuario(correoUsuario, contrasenaUsuario);
                }
            }
        });
    }

    //Función para registrar un usuario
    public void registroUsuario(String nombreUsuario, String contrasenaUsuario)
        {
            mAuth.createUserWithEmailAndPassword(nombreUsuario, contrasenaUsuario).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //Si el registro ha sido exitoso
                    if(task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        //Datos a registrar
                        assert user != null; //el usuario es distinto de null
                        String id = user.getUid();
                        String Nombre = nombre.getText().toString();
                        String Apellido = apellido.getText().toString();
                        String Correo = correo.getText().toString();
                        String Contrasena = contrasena.getText().toString();
                        String Fecha = fecha_nacimiento.getText().toString();

                        HashMap<Object,String> datosUsuario = new HashMap<>();
                        datosUsuario.put("id",id);
                        datosUsuario.put("Nombre",Nombre);
                        datosUsuario.put("Apellido",Apellido);
                        datosUsuario.put("Correo",Correo);
                        datosUsuario.put("Contraseña",Contrasena);
                        datosUsuario.put("Fecha de nacimiento",Fecha);

                        //inicializamos la instacia a la base de datos de firebase
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        //creamos la BD
                        DatabaseReference reference = database.getReference("Usuarios_de_app");
                        reference.child(id).setValue(datosUsuario);
                        Toast.makeText(Registrarse.this, "El registro se ha realizado correctamente", Toast.LENGTH_SHORT).show();
                        //Una vez registrado, vamos a a activity_menu
                        startActivity(new Intent(Registrarse.this, MenuActivity.class));

                    }
                    else{
                        Toast.makeText(Registrarse.this, "Ha ocurrido  un error al registrarse", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Registrarse.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}
