package com.ugb.miprimercalculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {
    private Button btn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();

        btn = findViewById(R.id.btnSalir);
        btn.setOnClickListener(v -> {
            cerrarSesion();
        });

    }

    //Cerrar sesion
    private void cerrarSesion(){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getApplicationContext(), "Saliendo..", Toast.LENGTH_LONG).show();
        Intent iniciarSesion = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(iniciarSesion);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
}