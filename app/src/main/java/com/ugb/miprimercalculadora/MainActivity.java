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
import java.util.ArrayList;
import java.util.MissingFormatArgumentException;

public class MainActivity extends AppCompatActivity {
    DB miDB;
    Cursor datosProductosCursos = null;
    FloatingActionButton btn;
    ListView ltsProductos;
    ArrayList<productos> productosArrayList = new ArrayList<productos>();
    ArrayList<productos> productosrrayListCopy = new ArrayList<productos>();
    productos misProductos;
    JSONArray jsonArrayProductos;
    JSONObject jsonObjectProductos;
    utilidades u;
    internterDetectec di;
    int position = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            di = new internterDetectec(getApplicationContext());
            btn = findViewById(R.id.btnAgregarProductos);
            btn.setOnClickListener(v-> {
                agregarProductos("nuevo");
            } );
          comprobardatos();
          buscarProducto();
}

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mymenu =getMenuInflater();
        mymenu.inflate(R.menu.menu,menu);

        try {
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
            position = adapterContextMenuInfo.position;

            menu.setHeaderTitle(jsonArrayProductos.getJSONObject(position).getJSONObject("value").getString("Tittle"));

        }catch (Exception e){
            mostrarMsgToast(e.getMessage());
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try {
            switch (item.getItemId()){
                case R.id.mnxagregar:
                    agregarProductos("nuevo");
                    break;

                case R.id.mnxmodificar:

                    agregarProductos("modificar");
                    break;

                case R.id.mnxeliminar:
                    eliminarProdcuto();
                    break;

            }
        }catch (Exception e){
            mostrarMsgToast(e.getMessage());
        }

        return super.onContextItemSelected(item);
    }

    //Mostrar mensje
    private void mostrarMsgToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }

    //Eliminar producto
    private void eliminarProdcuto(){
        try {
            jsonObjectProductos = jsonArrayProductos.getJSONObject(position).getJSONObject("value");
            AlertDialog.Builder confirmacion = new AlertDialog.Builder(MainActivity.this);
            confirmacion.setTitle("¿Seguro desea eliminar?");
            confirmacion.setMessage(jsonObjectProductos.getString("Tittle"));
            confirmacion.setPositiveButton("SI", ((dialog, which) ->{
                try {
                    if (di.internetConnection()){
                        conexionServer objEliminarprod = new conexionServer();
                        String resp = objEliminarprod.execute(u.url_mto + jsonObjectProductos.getString("_id") + "?rev=" + jsonObjectProductos.getString("_rev"), "DELETE").get();
                        JSONObject jsonRespDelete = new JSONObject(resp);
                        if (jsonRespDelete.getBoolean("ok")){
                            jsonArrayProductos.remove(position);
                            mostrarProductos();
                        }
                    }
                    miDB = new DB(getApplicationContext(),"",null, 1);
                    datosProductosCursos = miDB.admin_productos("eliminar", new String[]{jsonObjectProductos.getString("_id")});
                    comprobardatos();
                    mostrarMsgToast("Registro eliminado");
                    dialog.dismiss();

                }catch (Exception e){
                    mostrarMsgToast(e.getMessage());

                }
            }
                    ));
            confirmacion.setNegativeButton("NO", ((dialog, which) -> {
                mostrarMsgToast("Cancelando");
                dialog.dismiss();
            }));
            confirmacion.create().show();
        }catch (Exception e){
            mostrarMsgToast(e.getMessage());

        }
    }
    //Buscar producto
    public void buscarProducto(){
        TextView temp = findViewById(R.id.txtBuscarProductos);
        temp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    productosArrayList.clear();
                    if (temp.getText().toString().length()<1){
                        productosArrayList.addAll(productosrrayListCopy);
                    }else{
                        for (productos PB: productosrrayListCopy){
                            String tittle = PB.Tittle;
                            String synopsis = PB.Synopsis;
                            String time = PB.Time;
                            String buy = PB.Buy;
                            String buscando = temp.getText().toString().trim().toLowerCase();
                            if (tittle.toLowerCase().trim().contains(buscando) ||
                                    synopsis.toLowerCase().trim().contains(buscando)   ||
                                    time.toLowerCase().trim().contains(buscando)   ||
                                    buy.toLowerCase().trim().contains(buscando)  ){
                                productosArrayList.add(PB);
                            }
                        }
                    }
                    adaptadorImagenes productoEncontrado = new adaptadorImagenes(getApplicationContext(), productosArrayList);
                    ltsProductos.setAdapter(productoEncontrado);
                }catch (Exception e){
                    mostrarMsgToast(e.getMessage());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //metodo agregar producto

    private void agregarProductos(String accion ){
        try {
            Bundle parametroProducto = new Bundle();
            parametroProducto.putString("accion",accion);
            if (jsonArrayProductos.length() > 0){
                parametroProducto.putString("datos", jsonArrayProductos.getJSONObject(position).toString());

            }
            Intent nuevoProducto = new Intent(getApplicationContext(), agregarProducto.class);
            nuevoProducto.putExtras(parametroProducto);
            startActivity(nuevoProducto);
        }catch (Exception e){
            mostrarMsgToast(e.getMessage());
        }
    }

    //Obtener datos de amigos Offline
    private void obtenerDatosOffline (){
        try {
            miDB = new DB(getApplicationContext(), "", null, 1 );
            datosProductosCursos = miDB.admin_productos("consultar", null);
            if (datosProductosCursos.moveToFirst()){
                jsonObjectProductos = new JSONObject();
                JSONObject jsonValueObject = new JSONObject();
                jsonArrayProductos = new JSONArray();
                do {
                    jsonObjectProductos.put("_id", datosProductosCursos.getString(0 ));
                    jsonObjectProductos.put("_rev", datosProductosCursos.getString(0));
                    jsonObjectProductos.put("Tittle", datosProductosCursos.getString(1));
                    jsonObjectProductos.put("Synopsis", datosProductosCursos.getString(2));
                    jsonObjectProductos.put("Time", datosProductosCursos.getString(3));
                    jsonObjectProductos.put("Buy", datosProductosCursos.getString(4));
                    jsonObjectProductos.put("Photos", datosProductosCursos.getString(5));
                    jsonValueObject.put("value", jsonObjectProductos);

                    jsonArrayProductos.put(jsonValueObject);

                }while (datosProductosCursos.moveToNext());
                mostrarProductos();
            } else{
                mostrarMsgToast("Sin datos, porfavor agregue nuevos datos");
                agregarProductos("nuevo");
            }

        }catch (Exception e){
            mostrarMsgToast(e.getMessage());
        }

    }
    //Obtener datos Online
    private void obtenerDatosOnline (){
        try {
            conexionServer connection = new conexionServer();
            String resp = connection.execute(u.url_cst, "GET").get();
            jsonObjectProductos = new JSONObject(resp);
            jsonArrayProductos = jsonObjectProductos.getJSONArray("rows");
            mostrarProductos();
        }catch (Exception e){
            mostrarMsgToast(e.getMessage());
        }
    }

    //Comprobar datos, si hay se muestran; sino se envia a agregar

    private void comprobardatos() {
        if (di.internetConnection()){
            mostrarMsgToast("Datos desde nube");
            obtenerDatosOnline();
        }else {
            jsonArrayProductos = new JSONArray();
            mostrarMsgToast("Datos locales");
            obtenerDatosOffline();
        }

    }
    //mostrar productos
    private void mostrarProductos() {

       try {
           if (jsonArrayProductos.length()>0){
               ltsProductos = findViewById(R.id.ltsproducotos);
               productosArrayList.clear();
               productosrrayListCopy.clear();
               JSONObject jsonObject;
               for (int i=0; i<jsonArrayProductos.length(); i++){
                   jsonObject=jsonArrayProductos.getJSONObject(i).getJSONObject("value");
                   misProductos = new productos(jsonObject.getString("_id"), jsonObject.getString("_rev"),jsonObject.getString("Tittle"),jsonObject.getString("Synopsis"),
                           jsonObject.getString("Time"),jsonObject.getString("Buy"),jsonObject.getString("Photos"));

                   productosArrayList.add(misProductos);
               }
                   adaptadorImagenes adapter = new adaptadorImagenes(getApplicationContext(), productosArrayList);
                   ltsProductos.setAdapter(adapter);

                   registerForContextMenu(ltsProductos);

                   productosrrayListCopy.addAll(productosArrayList);


           }else{
               mostrarMsgToast("Sin registros");
               agregarProductos("nuevo");
           }

       }catch (Exception e){
           mostrarMsgToast(e.getMessage() + "Sigue fallando");
       }

    }

    private class conexionServer extends AsyncTask<String,String,String>{
        HttpURLConnection urlConnection;

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... parametros) {
            StringBuilder result = new StringBuilder();
            try {
                String uri = parametros[0];
                String metodo = parametros[1];
                URL url = new URL(uri);
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod(metodo);

                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String linea;
                while ((linea=bufferedReader.readLine()) != null){
                    result.append(linea);
                }
            }catch (Exception e){
                Log.i("GET", e.getMessage());
            }
            return result.toString();
        }
    }

    /*private class serverConnection extends AsyncTask<String, String, String> {
        HttpURLConnection urlConnection;

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... pms) {
            StringBuilder result = new StringBuilder();

            try {
                String uri = pms[0];
                String metodo = pms[1];
                URL url = new URL(uri);
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod(metodo);

                InputStream entrada = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader lectorBuffer = new BufferedReader(new InputStreamReader(entrada));
                String line;

                while ((line = lectorBuffer.readLine()) != null){
                    result.append(line);
                }

            }catch (Exception e){
                Log.i("GET", e.getMessage());
            }
            return result.toString();
        }
    }*/
}




class productos{
    String _id;
    String rev;
    String Tittle;
    String Synopsis;
    String Time;
    String Buy;
    String Photos;

    public productos(String _id, String rev, String tittle, String synopsis, String time, String buy, String photos) {
        this._id = _id;
        this.rev = rev;
        Tittle = tittle;
        Synopsis = synopsis;
        Time = time;
        Buy = buy;
        Photos = photos;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public String getTittle() {
        return Tittle;
    }

    public void setTittle(String tittle) {
        Tittle = tittle;
    }

    public String getSynopsis() {
        return Synopsis;
    }

    public void setSynopsis(String synopsis) {
        Synopsis = synopsis;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getBuy() {
        return Buy;
    }

    public void setBuy(String buy) {
        Buy = buy;
    }

    public String getPhotos() {
        return Photos;
    }

    public void setPhotos(String photos) {
        Photos = photos;
    }
}

