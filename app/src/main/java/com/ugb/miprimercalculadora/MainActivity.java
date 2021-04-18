package com.ugb.miprimercalculadora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DB miDB;
    Cursor datosProductosCursos = null;
    FloatingActionButton btn;
    ListView ltsProductos;
    ArrayList<productos> productosArrayList = new ArrayList<productos>();
    ArrayList<productos> productosrrayListCopy = new ArrayList<productos>();
    productos misProductos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn = findViewById(R.id.btnAgregarProductos);
        try {
            comprobardatos();
        }catch (Exception e){
            mostrarMsgToast(e.getMessage());
        }

        btn.setOnClickListener(v-> {
            agregarProductos("nuevo", new String[]{});
        } );



}
//Mostrar mensje
    private void mostrarMsgToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }

    //metodo agregar producto

    private void agregarProductos(String accion, String [] datos ){
        try {
            Bundle parametroProducto = new Bundle();
            parametroProducto.putString("accion",accion);
            parametroProducto.putStringArray("datos",datos);
            Intent nuevoProducto = new Intent(getApplicationContext(), agregarProducto.class);
            nuevoProducto.putExtras(parametroProducto);
            startActivity(nuevoProducto);
        }catch (Exception e){
            mostrarMsgToast(e.getMessage());
        }
    }

    //Comprobar datos

    private void comprobardatos() {
        miDB = new DB(getApplicationContext(),"",null,1);
        datosProductosCursos = miDB.admin_productos("consultar",null);

        if( datosProductosCursos.moveToFirst() ){
            mostrarProductos();


        } else {
            mostrarMsgToast("Porfavor agregar datos");
            agregarProductos("nuevo", new String[]{});
        }
    }
    //mostrar productos
    private void mostrarProductos() {
        ltsProductos = findViewById(R.id.ltsproducotos);
        productosArrayList.clear();
        productosrrayListCopy.clear();

        do{
            misProductos = new productos(
                    datosProductosCursos.getString(0),//idproducto
                    datosProductosCursos.getString(1),//codigo
                    datosProductosCursos.getString(2),//Producto
                    datosProductosCursos.getString(3),//marca
                    datosProductosCursos.getString(4),//descripcion
                    datosProductosCursos.getString(5),//presentacion
                    datosProductosCursos.getString(6), //precio
                    datosProductosCursos.getString(7) //urldefoto
            );
            productosArrayList.add(misProductos);
        }while(datosProductosCursos.moveToNext());

        try {
            adaptadorImagenes adaptadorImagenes = new adaptadorImagenes(getApplicationContext(), productosArrayList);
            ltsProductos.setAdapter(adaptadorImagenes);

            registerForContextMenu(ltsProductos);
            productosrrayListCopy.addAll(productosArrayList);

        }catch (Exception e){
            mostrarMsgToast(e.getMessage());
        }

    };
}




class productos{
    String idProducto;
    String codigo;
    String producto;
    String marca;
    String descripcion;
    String presentacion;
    String precio;
    String urlPhoto;


    public productos(String idProducto, String codigo, String producto, String marca, String descripcion, String presentacion, String precio, String urlPhoto) {
        this.idProducto = idProducto;
        this.codigo = codigo;
        this.producto = producto;
        this.marca = marca;
        this.descripcion = descripcion;
        this.presentacion = presentacion;
        this.precio = precio;
        this.urlPhoto = urlPhoto;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }
}

