package com.ugb.miprimercalculadora;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {


    RelativeLayout relativeLayout;

    Button btn;
    private CallbackManager mCallbackManeger;
    private FirebaseAuth mAuth;
    public static final int RC_SING_In = 9876;
    private GoogleSignInClient mGoogleSignClien;
    private LoginButton loginButton;
    private final static String TAG = "FacebookAuthentication";
    private FirebaseAuth.AuthStateListener authStateListener;
    private Drawable Drawabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        relativeLayout=findViewById(R.id.linearL);
        Drawable d =getResources().getDrawable(R.drawable.fondoooo);
        relativeLayout.setBackground(d);

        //Google button
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        findViewById(R.id.sign_in_button).setOnClickListener(this::onClick);

        //Firebase Inicializacion
        mAuth = FirebaseAuth.getInstance();
        //SDK Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());

        //Pantallita Google
        createResquet();

       /* btn = findViewById(R.id.sign_in_button);
        btn.setOnClickListener(v -> {
            signIn();
        });*/
        //Iniciar sesion Facebook
        loginButton = findViewById(R.id.login_button);
        mCallbackManeger = CallbackManager.Factory.create();
        loginButton.registerCallback(mCallbackManeger, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //.d(TAG, "onSucces" + loginResult);
                handleFacebookToken(loginResult.getAccessToken());
                Intent home = new Intent(getApplicationContext(), Home.class);
                startActivity(home);

            }

            @Override
            public void onCancel() {
               // Log.d(TAG, "onSucces");

            }

            @Override
            public void onError(FacebookException error) {
                //Log.d(TAG, "onErrors" + error);

            }
        });


    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }
    }

    /*Desde aca empieza para iniciar Sesion con Google
     ****************************************************************************************************************
     ***************************************************************************************************************
     */

    //Creacion de Token
    private void createResquet() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignClien = GoogleSignIn.getClient(this, gso);

    }

    //Metodo SignIn

    private void signIn() {
        Intent signIntent = mGoogleSignClien.getSignInIntent();
        startActivityForResult(signIntent, RC_SING_In);
    }

    //Activity Result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManeger.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SING_In) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (Exception e) {

            }
        }
    }

    //Metodo de FirabaseAuth para google
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

    /*
    Termina Inicar Sesion Google
    *******************************************************************************************************************
    ********************************************************************************************************************
     */

    /*
    ******************************************************************************************************
        Iniciar Sesion Facebook
        ****************************************************************************************************
        ******************************************************************************************************
     */

    private void handleFacebookToken(AccessToken token) {
       // Log.d(TAG, "handleFacebookToken" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Log.d(TAG, "signInWithCredential:successful");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                          /*  Intent home = new Intent(getApplicationContext(), Home.class);
                            startActivity(home);*/
                        } else {
                           // Log.d(TAG, "signInWithCredential:failure");
                            Toast.makeText(getApplicationContext(), "Error al iniciar", Toast.LENGTH_LONG);

                        }

                    }
                });
    }



       /*
    ******************************************************************************************************
        Termina Iniciar Sesion Facebook
        * uihiu
        ****************************************************************************************************
        ******************************************************************************************************
     */

    //OnStar para mantener sesion iniciada
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        if (currentUser != null) {
            Intent home = new Intent(getApplicationContext(), Home.class);
            startActivity(home);
        }

    }

}