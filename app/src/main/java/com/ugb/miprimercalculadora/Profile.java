package com.ugb.miprimercalculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class Profile extends AppCompatActivity {
    private TextView nombre, correo, telefono, desde;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userInfo();

        btn = findViewById(R.id.btnCerrarSesion);
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

    //Mostrar informacion del Perfil
    private void userInfo (){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                String phone = profile.getPhoneNumber();
                Uri photoUrl = profile.getPhotoUrl();



                //Conectar TexView
                nombre = findViewById(R.id.txtUserName);
                nombre.setText(name);

                correo = findViewById(R.id.txtUserEmail);
                correo.setText(email);

                telefono = findViewById(R.id.txtUserPhone);
                telefono.setText(phone);

                desde = findViewById(R.id.txtUserDesde);
                desde.setText(providerId);

                ImageView imageView = findViewById(R.id.imgProfile);
                Glide.with(getApplicationContext()).load(photoUrl).into(imageView);
            }
        }
    }


}