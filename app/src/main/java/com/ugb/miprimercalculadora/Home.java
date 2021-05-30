package com.ugb.miprimercalculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {
    private Button btn;
    private FirebaseAuth mAuth;
    private TextView nombre, correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Inicializar Firebase
        mAuth = FirebaseAuth.getInstance();

        //Enlacer TextViews
        correo = findViewById(R.id.txtCorreoH);
        nombre = findViewById(R.id.txtNombreH);

        //Obtener datos de Google
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null){
            nombre.setText(signInAccount.getEmail());
            correo.setText(signInAccount.getDisplayName());
        }

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