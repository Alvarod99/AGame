package com.example.agame;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
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

import org.checkerframework.checker.units.qual.C;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Registrarse extends AppCompatActivity {

    Button btnRegistro;
    EditText nombre, apellido, correo, contrasena, fecha_nacimiento;
    Double Saldo = 0.00;
    FirebaseAuth mAuth;
    int edad;


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

                //Obtener fecha de nacimiento
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);//desactiva el análisis flexible de fechas
                Date fechaNacimiento = null;
                try{
                    fechaNacimiento = sdf.parse(fecha);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Calcular edad
                if(fechaNacimiento != null) {
                    Calendar fechaNac = Calendar.getInstance();
                    fechaNac.setTime(fechaNacimiento);
                    Calendar fechaActual = Calendar.getInstance();
                    edad = fechaActual.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);

                    if (fechaActual.get(Calendar.MONTH) < fechaNac.get(Calendar.MONTH)) {
                        edad--;
                    }else if (fechaActual.get(Calendar.MONTH) == fechaNac.get(Calendar.MONTH) && fechaActual.get(Calendar.DAY_OF_MONTH) < fechaNac.get(Calendar.DAY_OF_MONTH)) {
                        edad--;
                    }

                    //Verificar si la fecha es una fecha real
                    if(fechaNac.after(fechaActual) || edad < 18){
                        fecha_nacimiento.setError("Debe ser mayor de edad para completar el registro o introducir una fecha válida");
                        fecha_nacimiento.setFocusable(true);
                        return;
                    }
                }

                //Condiciones para hacer el registro
                if(!Patterns.EMAIL_ADDRESS.matcher(correoUsuario).matches()){//El correo debe contener un @
                    correo.setError("Correo no válido");
                    correo.setFocusable(true);
                } else if(contrasenaUsuario.length()<5){//la contraseña debe ser mayor a 5 digitos
                    contrasena.setError("La contraseña debe contener mínimo 5 caracteres");
                    contrasena.setFocusable(true);
                } else if (nombreUsuario.isEmpty() || apellidoUsuario.isEmpty() || correoUsuario.isEmpty() || fecha.isEmpty()) {//No hace falta poner la contraseña ya que siempre se tiene que cumplir que haya
                    Toast.makeText(Registrarse.this, "Rellene todos los datos", Toast.LENGTH_SHORT).show();
                } else if (edad < 18) {//Verificamos si es mayor de edad
                    fecha_nacimiento.setError("Debe ser mayor de edad para completar el registro o introducir una fecha válida");
                    fecha_nacimiento.setFocusable(true);
                }else{
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
                    if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                             String saldo = String.valueOf(Saldo);
                            //Datos a registrar
                            assert user != null; //el usuario es distinto de null
                            String id = user.getUid();
                            String Nombre = nombre.getText().toString();
                            String Apellido = apellido.getText().toString();
                            String Correo = correo.getText().toString();
                            String Fecha = fecha_nacimiento.getText().toString();
                            String saldo2 = saldo.toString();

                            HashMap<Object, String> datosUsuario = new HashMap<>();
                            datosUsuario.put("id", id);
                            datosUsuario.put("Nombre", Nombre);
                            datosUsuario.put("Apellido", Apellido);
                            datosUsuario.put("Correo", Correo);
                            datosUsuario.put("Fecha de nacimiento", Fecha);
                            datosUsuario.put("Saldo",saldo2);

                            //inicializamos la instancia a la base de datos de firebase
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //creamos la BD
                            DatabaseReference reference = database.getReference("Usuarios_de_app");
                            reference.child(id).setValue(datosUsuario);
                            Toast.makeText(Registrarse.this, "El registro se ha realizado correctamente", Toast.LENGTH_SHORT).show();
                            //Una vez registrado, vamos a activity_menu
                            startActivity(new Intent(Registrarse.this, LoginActivity.class));

                        } else {
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
