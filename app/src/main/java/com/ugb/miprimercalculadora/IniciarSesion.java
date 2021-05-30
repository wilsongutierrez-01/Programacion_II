package com.ugb.miprimercalculadora;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.List;

public class IniciarSesion extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText correo, contra;
    private Button iniciarSesion, google;
    public static final int RC_SING_In = 9876;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleSignInClient mGoogleSignClien;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inicializar mAuth
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_iniciar_sesion);
        correo = findViewById(R.id.txtCorreo);
        contra = findViewById(R.id.txtContra);
        createResquet();

        iniciarSesion = findViewById(R.id.btnIniciarSesion);
        iniciarSesion.setOnClickListener(v -> {
            iniciarSesion();
        });

        google = findViewById(R.id.btnGoogle);
        google.setOnClickListener(v -> {
            signIn();
        });
    }

    //CrearId
    private void createResquet(){
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignClien = GoogleSignIn.getClient(this, gso);

    }
     //Metodo SignIn

    private void signIn(){
        Intent signIntent = mGoogleSignClien.getSignInIntent();
        startActivityForResult(signIntent, RC_SING_In);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SING_In){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            }catch (Exception e){

            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        if (currentUser != null){
            Intent home = new Intent(getApplicationContext(), Home.class);
            startActivity(home);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent home = new Intent(getApplicationContext(), Home.class);
                            startActivity(home);
                            Toast.makeText(getApplicationContext(), "Bienvenido a Mandaditos", Toast.LENGTH_LONG).show();

                        } else {
                            // If sign in fails, display a message to the user.
                             Toast.makeText(getApplicationContext(), "Error de inicio", Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }
}