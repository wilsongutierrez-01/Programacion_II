package com.ugb.miprimercalculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TabHost tbhConversores;
    Button btnConvertir;
    TextView tempVal;
    Spinner spnOpcionDe, spnOpcionA;
    conversores miConversor = new conversores();
    RelativeLayout contenidoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contenidoView = findViewById(R.id.contenidoView);
        tbhConversores = findViewById(R.id.tbhConversores);
        tbhConversores.setup();

        tbhConversores.addTab(tbhConversores.newTabSpec("Monedas").setContent(R.id.tabMonedas).setIndicator("M",getResources().getDrawable(R.drawable.xdmonedas)));
        tbhConversores.addTab(tbhConversores.newTabSpec("Longitud").setContent(R.id.tabLongitud).setIndicator("L",getResources().getDrawable(R.drawable.xdlongitud)));


    }


}

class conversores{
    double[][] conversor = {
            {1.0,8.75,7.77, 24.03,34.8,611.10},/*Monedas*/
            {1.0, 100.0,39.37,3.28},/*Longitud*/
            {1.0}/*Masa*/
    };
    public double convertir(int opcion, int de, int a, double cantidad){
        return conversor[opcion][a] / conversor[opcion][de] * cantidad;
    }
}





