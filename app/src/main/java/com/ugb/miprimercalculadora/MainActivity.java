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
    Button btnConvertir;
    TextView tempVal;
    Spinner spnOpcionDe, spnOpcionA;
    conversores miConversor = new conversores();
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
        tbhConversores.addTab(tbhConversores.newTabSpec("Masa").setContent(R.id.tabMasa).setIndicator("",getResources().getDrawable(R.drawable.ic_peso)));
        tbhConversores.addTab(tbhConversores.newTabSpec("Almacenamiento").setContent(R.id.tabAlmacenamiento).setIndicator("",getResources().getDrawable(R.drawable.xdalmacenar)));
        tbhConversores.addTab(tbhConversores.newTabSpec("Tiempo").setContent(R.id.tabTiempo).setIndicator("",getResources().getDrawable(R.drawable.xdtiempo)));
        tbhConversores.addTab(tbhConversores.newTabSpec("Temperatura").setContent(R.id.tabTemperatura).setIndicator("",getResources().getDrawable(R.drawable.xdtemperatura)));
        tbhConversores.addTab(tbhConversores.newTabSpec("Volumen").setContent(R.id.tabVolumen).setIndicator("",getResources().getDrawable(R.drawable.xdvolumen)));
        tbhConversores.addTab(tbhConversores.newTabSpec("Area").setContent(R.id.tabArea).setIndicator("",getResources().getDrawable(R.drawable.xdarea)));

        //Algoritmo moneda
        btnConvertir = findViewById(R.id.btnCalcular);
        btnConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tempVal = (TextView) findViewById(R.id.txtcantidad);
                    double cantidad = Double.parseDouble(tempVal.getText().toString());

                    spnOpcionDe = findViewById(R.id.cboDe);
                    spnOpcionA = findViewById(R.id.cboA);
                    tempVal = findViewById(R.id.lblRespuesta);
                    tempVal.setText("Respuesta: " + miConversor.convertir(0, spnOpcionDe.getSelectedItemPosition(), spnOpcionA.getSelectedItemPosition(), cantidad));
                }catch (Exception e){
                    tempVal = findViewById(R.id.lblRespuesta);
                    tempVal.setText("Por favor ingrese los valores correspondiente");
                    Toast.makeText(getApplicationContext(), "Por ingrese los valores correspondiente "+ e.getMessage(),Toast.LENGTH_SHORT).show();
                    /*Snackbar snackbar = Snackbar.make(contenidoView, "Por favor ingrese los valores correspondiente", Snackbar.LENGTH_LONG);
                    snackbar.show();*/
                }
            }
        });

        //Algoritmo longitud
        btnConvertir = findViewById(R.id.btnCalcularL);
        btnConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tempVal = findViewById(R.id.txtCantidadL);
                    double cantidad = Double.parseDouble(tempVal.getText().toString());

                    spnOpcionDe = findViewById(R.id.cboDeL);
                    spnOpcionA = findViewById(R.id.cboAL);
                    tempVal = findViewById(R.id.lblRespuestaL);

                    tempVal.setText("Respuesta: " + miConversor.convertir(1, spnOpcionDe.getSelectedItemPosition(), spnOpcionA.getSelectedItemPosition(), cantidad));
                }catch (Exception e){
                    tempVal = findViewById(R.id.lblRespuestaL);
                    tempVal.setText("Por favor ingrese los valores correspondiente");
                    Toast.makeText(getApplicationContext(), "Por ingrese los valores correspondiente "+ e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Algoritmo mmasa
        btnConvertir = findViewById(R.id.btnCalcularMa);
        btnConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tempVal = (TextView) findViewById(R.id.txtcantidadMa);
                    double cantidad = Double.parseDouble(tempVal.getText().toString());

                    spnOpcionDe = findViewById(R.id.cboDeMa);
                    spnOpcionA = findViewById(R.id.cboAMa);
                    tempVal = findViewById(R.id.lblRespuestaMa);
                    tempVal.setText("Respuesta: " + miConversor.convertir(2, spnOpcionDe.getSelectedItemPosition(), spnOpcionA.getSelectedItemPosition(), cantidad));
                }catch (Exception e){
                    tempVal = findViewById(R.id.lblRespuestaMa);
                    tempVal.setText("Por favor ingrese los valores correspondiente");
                    Toast.makeText(getApplicationContext(), "Por ingrese los valores correspondiente "+ e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Algoritmo Almacenamiento
        btnConvertir = findViewById(R.id.btnCalcularAl);
        btnConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tempVal = (TextView) findViewById(R.id.txtcantidadAl);
                    double cantidad = Double.parseDouble(tempVal.getText().toString());

                    spnOpcionDe = findViewById(R.id.cboDeAl);
                    spnOpcionA = findViewById(R.id.cboAAl);
                    tempVal = findViewById(R.id.lblRespuestaAl);
                    tempVal.setText("Respuesta: " + miConversor.convertir(3, spnOpcionDe.getSelectedItemPosition(), spnOpcionA.getSelectedItemPosition(), cantidad));
                }catch (Exception e){
                    tempVal = findViewById(R.id.lblRespuestaAl);
                    tempVal.setText("Por favor ingrese los valores correspondiente");
                    Toast.makeText(getApplicationContext(), "Por ingrese los valores correspondiente "+ e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });





    }


}


class conversores{
    double[][] conversor = {
            {1.00,8.75,7.77, 24.03,34.8,611.10,0.82,19.93,105.00,5.39},/*Monedas*/
            {1.0, 100.0,39.37,3.28,0.001,1.093613,0.000621,0.836,0.0001,1.000},/*Longitud*/
            {1.0,16.00,0.453592,4.535924,453.5924,45.35924,4535.924,45359.24,453592.4,0.0005},/*Masa*/
            {1.0,0.68,7.629395,8.0,976.5625,0.953674,0.008,0.001,0.000008,0.000001},/*Almacenamiento*/
            {1.0,60.0,3600.0,3600000.0,3600000000.0,0.041667,0.005952,0.000114,1.14155e-5,1.14155e-6},/*Tiempo*/
            {1.0,-17.22222,255.9278},/*Temperatura*/
            {1.0,0.333333,0.166667,0.0200833,0.005208,0.001302,0.004929,4.928922,4.928922,0.000005},/*Volumen*/
            {1.0,0.111111,0.000000035870064,144.0,0.00000009290304,0.000009,0.092903,929.0304,92903.04,0.000024},/*Area*/
    };
    public double convertir(int opcion, int de, int a, double cantidad){
        return conversor[opcion][a] / conversor[opcion][de] * cantidad;
    }
}





