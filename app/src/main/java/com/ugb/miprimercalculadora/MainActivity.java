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
    conversores miConversor = new conversores();
    Precios misprecios = new Precios();
    Button btnConvertir;
    Button btnprecio;
    TextView tempVal;
    double tempval;
    Spinner spnOpcionDe, spnOpcionA;
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
            case R.id.mnxConversor:
                tbhConversores.setCurrentTab(0);
                break;

            case R.id.mnxCuota:
                tbhConversores.setCurrentTab(1);
                break;


        }
        return super.onOptionsItemSelected(item);
    }
       //ella no te ama
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //TabhostFinalizado

        contenidoView = findViewById(R.id.contenidoView);
        tbhConversores = findViewById(R.id.tbhConversores);
        tbhConversores.setup();

        tbhConversores.addTab(tbhConversores.newTabSpec("Coversor").setContent(R.id.tabConversor).setIndicator("", getResources().getDrawable(R.drawable.xdarea)));
        tbhConversores.addTab(tbhConversores.newTabSpec("Cuota").setContent(R.id.tabCouta).setIndicator("", getResources().getDrawable(R.drawable.ic_grifo_de_agua)));

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
        btnprecio = findViewById(R.id.btnPrecio);
        btnprecio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    tempVal = (TextView) findViewById(R.id.txtcant);
                    double metro = Double.parseDouble(tempVal.getText().toString());
                    tempVal.setText("Su total a pagar: " + misprecios.Cuouta(metro, tempval));
                }catch (Exception e){
                    tempVal = findViewById(R.id.lblResultado);
                    Toast.makeText(getApplicationContext(), "Verifique sus  datos ingresados" + e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });



    }
    class conversores {
        double[][] conversor = {
                {1, 10.7639, 1.431, 1.19599, 0.0015903307888, 0.0001434,0.0001},/*Iniciamos con Metros Cuadrados*/

        };
        public double convertir(int opcion, int de, int a, double cantidad){

            return conversor[opcion][a] / conversor[opcion][de] * cantidad;
        }
    }
//for Pe
    class Precios {
    public double Cuouta(double m3, double precio) {
        double exc1 = 18 , exc2 = 28;
        double val1 = 0.45, val2 = 0.65;
        double cumi = 6.00;
        double valexc1 = 4.50;

        if (m3 >= 1 && m3 <= 18) {
            precio = cumi;
        }

        if (m3 >= 19 && m3 <= 28) {
            precio = ((m3 - exc1) * val1) + cumi;
        }

        if ( m3 >= 29){
            precio = ((m3 - exc2) * val2) + valexc1 + cumi;
        }


        return precio;
    }
}
}

