package com.ugb.miprimercalculadora;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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

        //Obtener datos de usuario
        userinfo();

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
        userinfo();
        //updateUI(currentUser);
    }

    //Obtener la informacion de los ususarios
    private void userinfo (){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

            nombre.setText(name);
            correo.setText(email);
            ImageView imageView = findViewById(R.id.imgPhoto);
            Glide.with(getApplicationContext()).load(user.getPhotoUrl()).into(imageView);

        }

    }
}