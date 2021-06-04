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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Enviar a Profile
        btn = findViewById(R.id.btnPerfil);
        btn.setOnClickListener(v -> {
           profile();
        });


    }

    //Enviar a Profile
    private void profile(){
        Intent profile = new Intent(getApplicationContext(), Profile.class);
        startActivity(profile);
    }



}