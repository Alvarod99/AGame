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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText email, password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.Correo);
        password = findViewById(R.id.Contraseña);
        btn_login = findViewById(R.id.Login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUser = email.getText().toString().trim();
                String passUser = password.getText().toString().trim();

                if(emailUser.isEmpty() || passUser.isEmpty()){//si los campos están vacíos
                    Toast.makeText(LoginActivity.this, "Rellene todos los datos", Toast.LENGTH_SHORT).show();
                }
                else{
                    loginUser(emailUser,passUser);
                }
            }
        });

    }
    private void loginUser(String emailUser, String passUser){
        mAuth.signInWithEmailAndPassword(emailUser,passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    Toast.makeText(LoginActivity.this,"Bienvenido "+emailUser,Toast.LENGTH_SHORT).show();
                    Intent j = new Intent(LoginActivity.this, Today.class);
                    startActivity(j);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Ha ocurrido un problema", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Correo electrónico y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void Registrarse(View view){
        Intent i = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            i = new Intent(this, Registrarse.class);
        }
        startActivity(i);
    }
}