package com.ugb.miprimercalculadora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TabHost tbhConversores;
    RelativeLayout contenidoView;

    //Invocaion de menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(getApplicationContext(),"Index: "+ item, Toast.LENGTH_LONG).show();
        int idMenu = item.getItemId();
        switch (idMenu){
            case R.id.mnxMoneda:
                tbhConversores.setCurrentTab(0);
                break;

            case R.id.mnxLongitud:
                tbhConversores.setCurrentTab(1);
                break;

            case R.id.mnxMasa:
                tbhConversores.setCurrentTab(2);
                break;

            case R.id.mnxAlmacenamiento:
                tbhConversores.setCurrentTab(3);
                break;

            case R.id.mnxTiempo:
                tbhConversores.setCurrentTab(4);
                break;

            case R.id.mnxTemperatura:
                tbhConversores.setCurrentTab(5);
                break;

            case R.id.mnxVolumen:
                tbhConversores.setCurrentTab(6);
                break;

            case R.id.mnxArea:
                tbhConversores.setCurrentTab(7);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Tabhost

        contenidoView = findViewById(R.id.contenidoView);
        tbhConversores = findViewById(R.id.tbhConversores);
        tbhConversores.setup();

        tbhConversores.addTab(tbhConversores.newTabSpec("Monedas").setContent(R.id.tabMonedas).setIndicator("",getResources().getDrawable(R.drawable.xdmonedas)));
        tbhConversores.addTab(tbhConversores.newTabSpec("Longitud").setContent(R.id.tabLongitud).setIndicator("",getResources().getDrawable(R.drawable.xdlongitud)));


                }
            }


