package com.ugb.miprimercalculadora;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.CaseMap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Provider;
import java.util.ArrayList;
import java.util.MissingFormatArgumentException;

public class MainActivity extends AppCompatActivity {
    Button btnGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGoogle = findViewById(R.id.btnCrearCuenta);
        btnGoogle.setOnClickListener(v -> {
            crearCuenta();
        });
}

protected void crearCuenta(){
        try {

        }catch (Exception e){
            mensaje(e.getMessage());

        }
        Intent crearCuenta = new Intent(getApplicationContext(), login_activity.class);
            startActivity(crearCuenta);
}

private void mensaje (String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
}

}