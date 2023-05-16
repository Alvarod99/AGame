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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.common.subtyping.qual.Bottom;

public class profile extends AppCompatActivity {

    TextView UnameTxt, Uname, UsurnameTXT, Usurname, UmailTXT, Umail, UdateTXT, Udate;
    Button CerrarSesion, actualizarContrasena;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    DatabaseReference BASE_DE_DATOS;

    @SuppressLint("MissingInflatedId")
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

        //Fecha de nacimiento
        Udate = findViewById(R.id.Udate);
        UdateTXT = findViewById(R.id.UdateTXT);

        //Botones
        CerrarSesion = (Button) findViewById(R.id.CerrarSesion);
        actualizarContrasena = (Button) findViewById(R.id.actualizarContrasena);

        //Firebase
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
                    String Fecha  = ""+snapshot.child("Fecha de nacimiento").getValue();

                    Uname.setText(Nombre);
                    Usurname.setText(Apellido);
                    Umail.setText(Correo);
                    Udate.setText(Fecha);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        CerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarsesion();
            }
        });

        actualizarContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Nos enviará a la activity_cambiar_contrasena
                Intent i= new Intent(profile.this,CambiarContrasena.class);
                startActivity(i);
            }
        });
    }


    //Método para cerrar sesión
    private void cerrarsesion(){
        firebaseAuth.signOut();
        Toast.makeText(this, "Se ha cerrado sesión", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(profile.this,LoginActivity.class));
    }

    protected void onStart(){
        verificacionSesion();
        super.onStart();
    }

    private void verificacionSesion(){
        if(user == null){
            startActivity(new Intent(profile.this,MenuActivity.class));
            finish();
        }

    }


    //Método para acceder a otras actividades a través del menú
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
        if (opcion_menu.getItemId() == R.id.transferencias) {
            Intent n = new Intent(profile.this, GirarPantalla.class);
            startActivity(n);
        }
        return super.onOptionsItemSelected(opcion_menu);
    }

    //Menú
    @Override public boolean onCreateOptionsMenu(Menu miMenu){
        getMenuInflater().inflate(R.menu.second_menu,miMenu);

        return true;
    }


    /*para volver a la activity anterior (no funciona todavía)
    public boolean anterior(){
        onBackPressed();

        return super.onSupportNavigateUp();
    }*/
}