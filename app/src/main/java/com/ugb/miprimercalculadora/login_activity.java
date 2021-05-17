package com.ugb.miprimercalculadora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class login_activity extends AppCompatActivity {
    TextView temp;
    String idUsuario, accion = "nuevo";
    DB miDB;
    Button crearCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        crearCuenta = findViewById(R.id.btnCrearCuenta);
        crearCuenta.setOnClickListener(v -> {
            agregarUsuario();
        });




    }
private void agregarUsuario (){
        temp = findViewById(R.id.txtUsuario);
        String usuario = temp.getText().toString();

        temp = findViewById(R.id.txtCorreo);
        String correo = temp.getText().toString();

        temp = findViewById(R.id.txtContraseña);
        String contra = temp.getText().toString();

        String datos[] = {idUsuario, usuario, correo, contra};
        miDB.admin_usuarios(accion,datos);
        mensaje("Usuario creado con correctamente");
        inicio();

}

//Para mensaje Toast
    private void mensaje(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    //mostrar inicio de Sesion
    private void inicio(){
        Intent inicio = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(inicio);

    }


}

