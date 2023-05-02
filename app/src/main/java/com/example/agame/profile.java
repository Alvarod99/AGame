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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.common.subtyping.qual.Bottom;

public class profile extends AppCompatActivity {

    TextView UnameTxt, Uname, UsurnameTXT, Usurname, UmailTXT, Umail, UpasswordTXT, Upassword, UdateTXT, Udate;
    Button Actualizar, ActualizarPassword;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    DatabaseReference BASE_DE_DATOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        //Nombre
        Uname = findViewById(R.id.Uname);
        UnameTxt = findViewById(R.id.UnameTXT);

        //Apellido
        Usurname = findViewById(R.id.Usurname);
        UsurnameTXT = findViewById(R.id.UsurnameTXT);

       //Correo
        Umail = findViewById(R.id.Umail);
        UmailTXT = findViewById(R.id.UmailTXT);

        //Contraseña
        Upassword = findViewById(R.id.Upassword);
        UpasswordTXT = findViewById(R.id.UpasswordTXT);

        //Fecha de nacimiento
        Udate = findViewById(R.id.Udate);
        UdateTXT = findViewById(R.id.UdateTXT);

        //Botones
        Actualizar = findViewById(R.id.Actualizar);
        ActualizarPassword = findViewById(R.id.ActualizarPassword);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        BASE_DE_DATOS = FirebaseDatabase.getInstance().getReference("Usuarios_de_app");

        BASE_DE_DATOS.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    //Para obtener los datos de Firebase. Estos se obtienen tal cual fueron registrados
                    String Nombre = ""+snapshot.child("Nombre").getValue();
                    String Apellido = ""+snapshot.child("Apellido").getValue();
                    String Correo = ""+snapshot.child("Correo").getValue();
                    String Contrasena = ""+snapshot.child("Contraseña").getValue();
                    String Fecha = ""+snapshot.child("Fechas de nacimiento").getValue();

                    Uname.setText(Nombre);
                    Usurname.setText(Apellido);
                    Umail.setText(Correo);
                    Upassword.setText(Contrasena);
                    Udate.setText(Fecha);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ActualizarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(profile.this,CambiarContrasena.class));
            }
        });

    }


    //Método para acceder a otras actividades a través del meú
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem opcion_menu) {
        if (opcion_menu.getItemId() == R.id.hoy) {
            Intent i = new Intent(profile.this, today.class);
            startActivity(i);
        }
        if (opcion_menu.getItemId() == R.id.Partidos) {
            Intent m = new Intent(profile.this, matches.class);
            startActivity(m);
        }
        if (opcion_menu.getItemId() == R.id.Noticias) {
            Intent n = new Intent(profile.this, news.class);
            startActivity(n);
        }
        if (opcion_menu.getItemId() == R.id.transferencias) {
            Intent n = new Intent(profile.this, Banco.class);
            startActivity(n);
        }
        return super.onOptionsItemSelected(opcion_menu);
    }

    //Menú
    @Override public boolean onCreateOptionsMenu(Menu miMenu){
        getMenuInflater().inflate(R.menu.second_menu,miMenu);

        return true;
    }


    //para volver a la activity anterior (no funciona todavía)
    public boolean anterior(){
        onBackPressed();

        return super.onSupportNavigateUp();
    }

    public void CambiarContrasena(View view) {
        Intent i = new Intent(profile.this,CambiarContrasena.class);
        startActivity(i);
    }
}