package com.ugb.miprimercalculadora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IniciarSesion extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText correo, contra;
    private Button iniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inicializar mAuth
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_iniciar_sesion);
        correo = findViewById(R.id.txtCorreo);
        contra = findViewById(R.id.txtContra);

        iniciarSesion = findViewById(R.id.btnIniciarSesion);
        iniciarSesion.setOnClickListener(v -> {
            iniciarSesion();
        });
    }

    public void iniciarSesion(){
        mAuth.signInWithEmailAndPassword(correo.getText().toString(), contra.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            Toast.makeText(getApplicationContext(),"Iniciaste sesion", Toast.LENGTH_LONG).show();

                            //Llevar a Home
                            Intent Home = new Intent(getApplicationContext(), Home.class);
                            startActivity(Home);
                        }else {
                            Toast.makeText(getApplicationContext(), "Error de datos de usuario", Toast.LENGTH_LONG).show();

                        }

                    }
                });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
}