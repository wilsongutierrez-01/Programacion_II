package com.ugb.miprimercalculadora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class Registrar extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText correo;
    EditText contra;
    EditText contra2;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        mAuth = FirebaseAuth.getInstance();

        correo = findViewById(R.id.txtCorreo);
        contra = findViewById(R.id.txtContra);
        contra2 = findViewById(R.id.txtContraVer);

        btn = findViewById(R.id.btnRegistrar);
        btn.setOnClickListener(v -> {
            userRegistrer();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void userRegistrer (){
        if (contra.getText().toString().equals(contra2.getText().toString())){
            mAuth.createUserWithEmailAndPassword(correo.getText().toString(), contra2.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                               // Log.d(TAG, 'createUserWithEmail:success');
                                Toast.makeText(getApplicationContext(),"Usuario creado",
                                        Toast.LENGTH_LONG).show();

                                FirebaseUser user = mAuth.getCurrentUser();

                                Intent Inico = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(Inico);
                               // updateUI(user);
                            }else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(getApplicationContext(), "El usuario ya existe", Toast.LENGTH_LONG).show();

                                }else {
                                    // Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Autentication failed.", Toast.LENGTH_LONG).show();
                                    //updateUI(null);
                                }
                            }

                        }
                    });

        }else{
            Toast.makeText(this,"Contraseñas no coinciden",Toast.LENGTH_LONG).show();
        }

    }

}