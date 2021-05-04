package com.ugb.miprimercalculadora;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.security.identity.CipherSuiteNotSupportedException;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class agregarProducto extends AppCompatActivity {
    FloatingActionButton btnAtras;
    ImageView imgFotoProducto, imgFotoProductos;
    Intent tomarFotoIntent;
    String Photos;
    String _id;
    String rev;
    String accion="nuevo";
    Uri Photosuri;
    Button btn;
    int op;
    DB miDB;
    TextView tempVal;
    utilidades url;
    internterDetectec di;
    private static final int PICK_IMAGE = 100;
    final int COD_SELECCIONA=10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_productos);

        miDB = new DB (getApplicationContext(),"",null,1);

        btnAtras = findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(v -> {
            mostrarVistaPrincipal();
        });

        imgFotoProducto = findViewById(R.id.imgFotoProducto);
        imgFotoProducto.setOnClickListener(v -> {

            AlertDialog.Builder foto = new  AlertDialog.Builder(agregarProducto.this);
            foto.setTitle("Imagen");
            foto.setMessage("Obetner imagen desde:");
            foto.setPositiveButton("Galeria", ((dialog, which) ->{
                op = 1;
                seleccionarImagen();
            }));

            foto.setNegativeButton("Camara", ((dialog, which) ->{
                op = 2;
                tomarFotoProducto();
            }));

           foto.create().show();
        });

        btn = findViewById(R.id.btnGuardarProducto);
        btn.setOnClickListener(v->{


            agregarProducto();
        });
        mostrarDatosProductos();
    }
    //agregar producto
    private void agregarProducto () {

        try {
            tempVal = findViewById(R.id.txtTittle);
            String Tittle = tempVal.getText().toString();

            tempVal = findViewById(R.id.txtSynopsis);
            String Synopsis = tempVal.getText().toString();

            tempVal = findViewById(R.id.txtBuy);
            String Buy = tempVal.getText().toString();

            tempVal = findViewById(R.id.txtTime);
            String Time = tempVal.getText().toString();

            JSONObject productosData = new JSONObject();
            if (accion.equals("modificar") && _id.length() > 0 && rev.length() > 0 ) {
                productosData.put("_id", _id);
                productosData.put("_rev", rev);
            }
            productosData.put("Tittle", Tittle);
            productosData.put("Synopsis", Synopsis);
            productosData.put("Time", Time);
            productosData.put("Buy", Buy);

            productosData.put("Photos", Photos);
            String [] datos = {_id,Tittle,Synopsis,Time,Buy,Photos};

            di = new internterDetectec(getApplicationContext());
           if (di.internetConnection()){
                sendProducto objSaveProduc = new sendProducto(getApplicationContext());
                String resp = objSaveProduc.execute(productosData.toString()).get();
            }


            miDB.admin_productos(accion,datos);
            mostrarMsgToast("Producto guardado con exito");
            mostrarVistaPrincipal();
        }catch (Exception e){
            mostrarMsgToast(e.getMessage());
        }
        mostrarDatosProductos();
    }
    //Tomar foto producto

    private void tomarFotoProducto(){
        tomarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (tomarFotoIntent.resolveActivity(getPackageManager()) != null){
            File photoProducto = null;
            try {
                photoProducto = crearImagenProducto();
            }catch (Exception e){
                mostrarMsgToast(e.getMessage());
            }

            if ( photoProducto !=null){
                try {
                    Uri uriPhotoProducto = FileProvider.getUriForFile(agregarProducto.this, "com.ugb.miprimercalculadora.fileprovider", photoProducto);
                    tomarFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhotoProducto);
                    startActivityForResult(tomarFotoIntent, 1);

                }catch ( Exception e){
                    mostrarMsgToast(e.getMessage());
                }

            }else {
                mostrarMsgToast("No fue posible tomar la foto");

            }
        }
    }

    private void mostrarDatosProductos() {
        try {
            Bundle parametros= getIntent().getExtras();
            accion = parametros.getString("accion");
            if (accion.equals("modificar")){
                JSONObject datos = new JSONObject(parametros.getString("datos")).getJSONObject("value");


                _id = datos.getString("_id");
                rev = datos.getString("_rev");

                tempVal = findViewById(R.id.txtTittle);
                tempVal.setText(datos.getString("Tittle"));

                tempVal = findViewById(R.id.txtSynopsis);
                tempVal.setText(datos.getString("Synopsis"));
                tempVal = findViewById(R.id.txtTime);
                tempVal.setText(datos.getString("Time"));
                tempVal = findViewById(R.id.txtBuy);
                tempVal.setText(datos.getString("Buy"));
                Photos = datos.getString("urlPhoto");
                Bitmap img = BitmapFactory.decodeFile(Photos);
                imgFotoProducto.setImageBitmap(img);
            }

        }catch (Exception e){
            mostrarMsgToast(e.getMessage());

        }

    }

    //Mostrar principal
    private void mostrarVistaPrincipal(){
        Intent iprincipal = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(iprincipal);
    }

    //Mensaje Toast
    private void mostrarMsgToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }


    //Crear Producto

    private File crearImagenProducto () throws Exception{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreimagen = "imagen_" + timeStamp + "_";
        File dirAlmacenamiento = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        if ( dirAlmacenamiento.exists()==false){
            dirAlmacenamiento.mkdirs();
        }
        File image = File.createTempFile(nombreimagen,".jpg",dirAlmacenamiento);
        Photos = image.getAbsolutePath();
        return image;
    }

public static String getRealUrl(final Context context, final Uri uri){
        final Boolean Kit = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (Kit && DocumentsContract.isDocumentUri(context,uri)){
            Uri finaluri = null;

            finaluri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String loc = finaluri.toString();

            return loc;
        }


        return null;

}


    //Activity Result
    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (op == 1){
            try {
                if (requestCode==2 && resultCode==RESULT_OK){
                    Bitmap imagenBitmap = BitmapFactory.decodeFile(Photos);
                    imgFotoProducto.setImageBitmap(imagenBitmap);
                }

            }catch (Exception e){
                mostrarMsgToast(e.getMessage() + "Error desde Galeria");
            }

        }
        if ( op == 2){
            try {
                if (requestCode==1 && resultCode==RESULT_OK){
                    Bitmap imagenBitmap = BitmapFactory.decodeFile(Photos);
                    imgFotoProducto.setImageBitmap(imagenBitmap);
                }

            }catch (Exception e){
                mostrarMsgToast(e.getMessage() + "Error desde camara");
            }

        }


    }

    //Seleccionar de Galeria
    /*
          if (opciones[i].equals("Cargar Imagen")){
                        Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),COD_SELECCIONA);
     */

    public void  seleccionarImagen (){
        Intent galery  = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        if (galery.resolveActivity(getPackageManager())!= null){
            File photo = null;
            try {
                photo = crearImagenProducto();

            }catch (Exception e){
                mostrarMsgToast(e.getMessage());
            }
            if (photo != null){
                try {
                    Uri uriPhotoProducto = FileProvider.getUriForFile(agregarProducto.this, "com.ugb.miprimercalculadora.fileprovider", photo);
                    galery.putExtra(MediaStore.EXTRA_OUTPUT,uriPhotoProducto);
                    startActivityForResult(galery,2);
                }catch (Exception e){
                    mostrarMsgToast(e.getMessage());
                }
            }else {
                mostrarMsgToast("No se pudo obtener");
            }

        }
    }

}
