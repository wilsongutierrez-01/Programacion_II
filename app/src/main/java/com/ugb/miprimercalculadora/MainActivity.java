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
    ListView ltsAmigos;
    Cursor datosAmigosCursor = null;
    ArrayList<productos> amigosArrayList=new ArrayList<productos>();
    ArrayList<productos> amigosArrayListCopy=new ArrayList<productos>();
    productos misProductos;



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
class productos{
    String idProducto;
    String codigo;
    String producto;
    String marca;
    String descripcion;
    String precio;
    String urlImg;



    public productos(String idProducto, String codigo, String producto, String  marca, String descripcion, String precio, String urlImg) {
        this.idProducto = idProducto;
        this.codigo = codigo;
        this.producto = producto;
        this.marca = marca;
        this.descripcion = descripcion;
        this.precio = precio;
        this.urlImg = urlImg;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getMarca() {return marca;}

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }


}

