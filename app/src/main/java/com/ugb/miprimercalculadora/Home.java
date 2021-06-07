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
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class Home extends AppCompatActivity {
    private Button btn;
    String miToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try {
            obtenerDatos();

        }catch (Exception e){
            mensajeToast(e.getMessage() + "Home");
        }

        //Enviar a Profile
        btn = findViewById(R.id.btnPerfil);
        btn.setOnClickListener(v -> {
           profile();
        });

        btn = findViewById(R.id.btnChat);
        btn.setOnClickListener(v -> {
            try {
                mostrarListaUsuarios();
            }catch (Exception e){
                 mensajeToast(e.getMessage() + "Chat");
            }

        });


    }

    //Enviar a Profile
    private void profile(){
        Intent profile = new Intent(getApplicationContext(), Profile.class);
        startActivity(profile);
    }


    //Enviar datos a chats
    private void guardarUsuario(){
       /* try{
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("usuarios");
           // String nombre = tempVal.getText().toString(),key = databaseReference.push().getKey();
            String name = user.getUserName(),key = databaseReference.push().getKey();
            if( miToken=="" || miToken==null ){
                miToken();
            }
            if( miToken!=null && miToken!="" ){
                usuarios user = new usuarios(name, users.getCorreo(), miToken, users.urlPhotoFirestore);
                if(key!=null){
                    databaseReference.child(key).setValue(user).addOnSuccessListener(aVoid -> {
                        mensajeToast("Usuario registrado con exito");
                    });
                } else {
                    mensajeToast("NO se inserto el usuario en la base de datos de firebase");
                }
            } else{
                mensajeToast("NO pude obtener el identificar de tu telefono, por favor intentalo mas tarde.");
            }
        }catch (Exception ex){
            mensajeToast(ex.getMessage());
        }*/
    }
//Para mostrar los usuarios
    private void    mostrarListaUsuarios (){
        Intent userList = new Intent(getApplicationContext(), lista_usuarios.class);
        startActivity(userList);
    }


        public void  obtenerDatos(){
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

                    try{
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("usuarios");
                        // String nombre = tempVal.getText().toString(),key = databaseReference.push().getKey();
                        String nombre = name, key = databaseReference.push().getKey();
                        if( miToken=="" || miToken==null ){
                            miToken();
                        }
                        if( miToken!=null && miToken!="" ){
                            usuarios users = new usuarios(nombre, email, miToken, photoUrl);
                            if(key!=null){
                                databaseReference.child(key).setValue(users).addOnSuccessListener(aVoid -> {
                                    mensajeToast("Usuario registrado con exito");
                                });
                            } else {
                                mensajeToast("NO se inserto el usuario en la base de datos de firebase");
                            }
                        } else{
                            mensajeToast("NO pude obtener el identificar de tu telefono, por favor intentalo mas tarde.");
                        }
                    }catch (Exception ex){
                        mensajeToast(ex.getMessage());
                    }


                }
            }

        }



    // Obtener nuestro token
    private void miToken (){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()){
                return;
            }
            miToken = task.getResult();
        });
    }

    // Mensaje Toast
    private void mensajeToast (String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }



}