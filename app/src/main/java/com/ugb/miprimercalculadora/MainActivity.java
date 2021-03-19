package com.ugb.miprimercalculadora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton btn;
    Intent nuevoProducto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btnAgregarProductos);
        btn.setOnClickListener(v-> {
            agregarProductos();

        } );



    }
    private void agregarProductos(){
        try {
            nuevoProducto = new Intent(getApplicationContext(), agregarProducto.class);
            startActivity(nuevoProducto);

        }catch (Exception e){
          mostrarMsgToast("Este es el error mensos");
        }

    }

    //Mensaje Toast
    private void mostrarMsgToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }


}

