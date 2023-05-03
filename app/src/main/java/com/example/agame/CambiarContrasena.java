package com.example.agame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CambiarContrasena extends AppCompatActivity {


    TextView misCredenciales, correoActual, correoActualTXT;
    EditText ContrasenaNueva, ActualPassword;
    Button Aceptar;

    DatabaseReference Usuarios_de_app;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasena);

        misCredenciales = findViewById(R.id.misCredenciales);
        correoActual = findViewById(R.id.correoActual);
        correoActualTXT = findViewById(R.id.correoActualTXT);
        ContrasenaNueva = findViewById(R.id.ContrasenaNueva);
        ActualPassword = findViewById(R.id.ActualPassword);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        Usuarios_de_app = FirebaseDatabase.getInstance().getReference("Usuarios_de_app");

        progressDialog = new ProgressDialog(CambiarContrasena.this);

        //Vamos a consultar su correo y contraseña
        Query query = Usuarios_de_app.orderByChild("Correo").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    //Obtenemos los valores
                    String Correo = ""+ds.child("Correo").getValue();
                    String Contrasena = ""+ds.child("Contraseña").getValue();

                    //Seteamos datos en los TextView
                    correoActual.setText(Correo);
                    ActualPassword.setText(Contrasena);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Cambiamos la contraseña
        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contrasena_anterior = ActualPassword.getText().toString().trim();
                String contrasena_nueva = ContrasenaNueva.getText().toString().trim();

                if(TextUtils.isEmpty(contrasena_anterior)){
                    Toast.makeText(CambiarContrasena.this, "El campo Contraseña Actual está vacío", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(contrasena_nueva)){
                    Toast.makeText(CambiarContrasena.this, "El campo Nueva contraseña está vacío", Toast.LENGTH_SHORT).show();

                }
                if(!contrasena_nueva.equals("") && contrasena_nueva.length()>=5){
                    CambioPassword(contrasena_anterior,contrasena_nueva);
                }
                else{
                    ContrasenaNueva.setError("La contraseña debe ser mayor a 5 caracteres");
                    ContrasenaNueva.setFocusable(true);
                }
            }
        });

    }

    //Método para cambiar de contraseña
    private void CambioPassword(String contrasena_anterior, String contrasena_nueva){
        progressDialog.show();
        progressDialog.setTitle("Actualizando");
        progressDialog.setMessage("Espere por favor");
        user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(),contrasena_anterior);
        user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                user.updatePassword(contrasena_nueva).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        String value = ContrasenaNueva.getText().toString().trim();
                        HashMap<String,Object> result = new HashMap<>();
                        result.put("Contraseña",value);
                        //Actualizamos la nueva contraseña en la BD
                        Usuarios_de_app.child(user.getUid()).updateChildren(result).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(CambiarContrasena.this, "Contraseña cambiada con exito", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                            }
                        });
                        //Luego cerraremos sesión para que inicie sesión con la nueva contraseña
                        firebaseAuth.signOut();
                        startActivity(new Intent(CambiarContrasena.this, LoginActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(CambiarContrasena.this, "La contraseña actual no es la correcta", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*//Método para acceder a otras actividades a través del meú
    public boolean onOptionsItemSelected(@NonNull MenuItem opcion_menu) {
        if (opcion_menu.getItemId() == R.id.hoy) {
            Intent i = new Intent(CambiarContrasena.this, today.class);
            startActivity(i);
        }
        if (opcion_menu.getItemId() == R.id.Partidos) {
            Intent m = new Intent(CambiarContrasena.this, matches.class);
            startActivity(m);
        }
        if (opcion_menu.getItemId() == R.id.Noticias) {
            Intent n = new Intent(CambiarContrasena.this, news.class);
            startActivity(n);
        }
        if (opcion_menu.getItemId() == R.id.transferencias) {
            Intent n = new Intent(CambiarContrasena.this, Banco.class);
            startActivity(n);
        }
        return super.onOptionsItemSelected(opcion_menu);
    }

    //Menús
    @Override public boolean onCreateOptionsMenu(Menu miMenu){
        getMenuInflater().inflate(R.menu.second_menu,miMenu);
        getMenuInflater().inflate(R.menu.main_menu,miMenu);

        return true;
    }*/
    //Método para volver a la actividad anterior
    public boolean onNavigateUp(){
        onBackPressed();
        return super.onNavigateUp();
    }
}