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
    Intent nuevoProducto;
    ListView ltsProductos;
    ArrayList<productos> productosArrayList=new ArrayList<productos>();
    ArrayList<productos> productosrrayListCopy=new ArrayList<productos>();
    productos misProductos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btnAgregarProductos);
        btn.setOnClickListener(v-> {
            agregarProductos("nuevo", new String[]{});
        } );
        obtenerDatosProductos();
        buscarProductos();

    }
    //agragando menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)menuInfo;
        datosProductosCursos.moveToPosition(adapterContextMenuInfo.position);
        menu.setHeaderTitle(datosProductosCursos.getString(1));
    }

    //Obtener elemtnos selecionados

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try {
            switch (item.getItemId()) {
                case R.id.mnxagregar:
                    agregarProductos("nuevo", new String[]{});
                    break;
                case R.id.mnxmodificar:
                    String[] datos = {
                            datosProductosCursos.getString(0),//idProducto
                            datosProductosCursos.getString(1),//codigo
                            datosProductosCursos.getString(2),//producto
                            datosProductosCursos.getString(3),//marca
                            datosProductosCursos.getString(4),//descripcion
                            datosProductosCursos.getString(5), //presentacion
                            datosProductosCursos.getString(6), //precio
                            datosProductosCursos.getString(7) //UrlImg
                    };
                    agregarProductos("modificar", datos);
                    break;
                case R.id.mnxeliminar:
                    eliminarProducto();
                    break;
            }
        }catch (Exception ex){
            mostrarMsgToast(ex.getMessage());
        }
        return super.onContextItemSelected(item);
    }

    //Eliminar Produtos
    private void eliminarProducto(){
        try {
            AlertDialog.Builder confirmacion = new AlertDialog.Builder(MainActivity.this);
            confirmacion.setTitle("Esta seguro de eliminar el registro?");
            confirmacion.setMessage(datosProductosCursos.getString(1));
            confirmacion.setPositiveButton("Si", (dialog, which) -> {
                miDB = new DB(getApplicationContext(), "", null, 1);
                datosProductosCursos = miDB.admin_productos("eliminar", new String[]{datosProductosCursos.getString(0)});//idProducto
                obtenerDatosProductos();
                mostrarMsgToast("Registro Eliminado con exito...");
                dialog.dismiss();//cerrar el cuadro de dialogo
            });
            confirmacion.setNegativeButton("No", (dialog, which) -> {
                mostrarMsgToast("Se cancelo la eliminiacion...");
                dialog.dismiss();
            });
            confirmacion.create().show();
        }catch (Exception ex){
            mostrarMsgToast(ex.getMessage());
        }
    }


//Agregar producto
    private void agregarProductos(String accion, String[] datos){
        try {
            Bundle parametrosProductos = new Bundle();
            parametrosProductos.putString("accion", accion);
            parametrosProductos.putStringArray("datos", datos);

            Intent agregarProductos = new Intent(getApplicationContext(), agregarProducto.class);
            agregarProductos.putExtras(parametrosProductos);
            startActivity(agregarProductos);
        }catch (Exception e){
            mostrarMsgToast(e.getMessage());
        }
    }
    // Obtener datos de Productos
    private void obtenerDatosProductos(){
        miDB = new DB(getApplicationContext(),"",null,1);
        datosProductosCursos = miDB.admin_productos("consultar",null);
        if( datosProductosCursos.moveToFirst() ){//si hay datos que mostrar
            mostrarDatosProductos();
        } else {//sino que llame para agregar nuevos amigos...
            mostrarMsgToast("No hay datos de amigos que mostrar, por favor agregue nuevos amigos...");
            agregarProductos("nuevo", new String[]{});
        }
    }

    //MOstrar datos productos
    private void mostrarDatosProductos(){
        ltsProductos = findViewById(R.id.ltsproducotos);
        productosArrayList.clear();
        productosrrayListCopy.clear();
        do{
            misProductos = new productos(
                    datosProductosCursos.getString(0),//idProducto
                    datosProductosCursos.getString(1),//codigo
                    datosProductosCursos.getString(2),//producto
                    datosProductosCursos.getString(3),//marca
                    datosProductosCursos.getString(4),//descripcion
                    datosProductosCursos.getString(5), //presentacion
                    datosProductosCursos.getString(6), //precio
                    datosProductosCursos.getString(7) //UrlImg
            );
            productosArrayList.add(misProductos);
        }while(datosProductosCursos.moveToNext());
        adaptadorImagenes adaptadorImagenes = new adaptadorImagenes(getApplicationContext(), productosArrayList);
        ltsProductos.setAdapter(adaptadorImagenes);

        registerForContextMenu(ltsProductos);

        productosrrayListCopy.addAll(productosArrayList);
    }

    //Buscar productos
    private void buscarProductos() {
        TextView tempVal = findViewById(R.id.txtBuscarProductos);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    productosArrayList.clear();
                    if( tempVal.getText().toString().trim().length()<1 ){//si no esta escribiendo, mostramos todos los registros
                        productosArrayList.addAll(productosrrayListCopy);
                    } else {//si esta buscando entonces filtramos los datos
                        for (productos am : productosrrayListCopy){
                            String producto = am.getProducto();
                            String codigo = am.getCodigo();
                            String marca = am.getMarca();
                            String precio = am.getPrecio();

                            String buscando = tempVal.getText().toString().trim().toLowerCase();//escribe en la caja de texto...

                            if(producto.toLowerCase().trim().contains(buscando) ||
                                    codigo.trim().contains(buscando) ||
                                    marca.trim().toLowerCase().contains(buscando) ||
                                    precio.trim().toLowerCase().contains(buscando)
                            ){
                                productosArrayList.add(am);
                            }
                        }
                    }
                    adaptadorImagenes adaptadorImagenes = new adaptadorImagenes(getApplicationContext(), productosArrayList);
                    ltsProductos.setAdapter(adaptadorImagenes);
                }catch (Exception e){
                    mostrarMsgToast(e.getMessage());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
    String presentacion;
    String precio;
    String urlImg;



    public productos(String idProducto, String codigo, String producto, String  marca, String descripcion, String precio, String presentacion, String urlImg) {
        this.idProducto = idProducto;
        this.codigo = codigo;
        this.producto = producto;
        this.marca = marca;
        this.descripcion = descripcion;
        this.presentacion = presentacion;
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

    public String getPresentacion(){return presentacion;}

    public void setPresentacion (){this.presentacion = presentacion;}


}

