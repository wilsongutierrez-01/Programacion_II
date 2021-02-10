package com.ugb.miprimercalculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Calcular(View view) {
        TextView tempVal = (TextView) findViewById(R.id.txtnum1);
        double num1 = Double.parseDouble(tempVal.getText().toString());

        tempVal = (TextView) findViewById(R.id.txtnum2);
        double num2 = Double.parseDouble(tempVal.getText().toString());

        double respuesta = 1;

        RadioButton optCalculadora = findViewById(R.id.optSuma);
        if (optCalculadora.isChecked()) {
            respuesta = num1 + num2;
        }
        optCalculadora = findViewById(R.id.optResta);
        if (optCalculadora.isChecked()) {
            respuesta = num1 - num2;
        }
        optCalculadora = findViewById(R.id.optMultiplicacion);
        if (optCalculadora.isChecked()) {
            respuesta = num1 * num2;
        }
        optCalculadora = findViewById(R.id.optDivision);
        if (optCalculadora.isChecked()) {
            respuesta = num1 / num2;
        }
        optCalculadora = findViewById(R.id.optFactorial);
        if (optCalculadora.isChecked()) {
            for (int i = 2; i <= num1; i++) {
                respuesta *= i;
            }
        }
        optCalculadora = findViewById(R.id.optPorcentaje);
        if (optCalculadora.isChecked()) {
            respuesta = (num1 * num2) / 100;
        }
        optCalculadora = findViewById(R.id.optExponenciacion);
        if (optCalculadora.isChecked()) {
            respuesta = Math.pow(num1, num2);
        }
        optCalculadora = findViewById(R.id.optRaizcuadrada);
        if (optCalculadora.isChecked()) {
            respuesta = Math.sqrt(num1);
        }
        optCalculadora = findViewById(R.id.optRaizCubica);
        if (optCalculadora.isChecked()) {
            respuesta = Math.cbrt(num1);
        }
        optCalculadora = findViewById(R.id.optRaizn);
        if (optCalculadora.isChecked()) {
            respuesta = (float) Math.pow(num1, 1 / num2);
        }
        optCalculadora = findViewById(R.id.optModulo);
        if (optCalculadora.isChecked()) {
            respuesta = num1 % num2;
        }
        optCalculadora = findViewById(R.id.optMayorde2numeros);
        if (optCalculadora.isChecked()) {
            if (num1 > num2) {
                respuesta = num1;
            } else {
                respuesta = num2;
            }
        }


        tempVal = findViewById(R.id.lblRespuesta);
        tempVal.setText("Respuesta: " + respuesta);

    }
}





