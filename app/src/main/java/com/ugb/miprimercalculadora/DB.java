package com.ugb.miprimercalculadora;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    Context miContext;
    static String nombreDB = "db_movies";
    static String tblMovie = "CREATE TABLE tblmovies(id integer primary key autoincrement, Tittle text, Synopsis text, Time text, Buy text, Photos text)";


            /*this.idProducto = idProducto;
        this.codigo = codigo;
        this.producto = producto;
        this.marca = marca;
        this.descripcion = descripcion;
        this.presentacion = presentacion;
        this.precio = precio;
        this.urlImg = urlImg*/

    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nombreDB, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tblMovie);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor admin_productos (String accion, String [] datos ) {
           Cursor datosCursor = null;
           SQLiteDatabase sqLiteDatabaseW = getWritableDatabase();
           SQLiteDatabase sqLiteDataBaseR =getReadableDatabase();
           switch (accion){
               case "consultar":
                   datosCursor = sqLiteDataBaseR.rawQuery("select * from tblmovies order by Tittle", null);
                   break;

               case "nuevo":
                   sqLiteDatabaseW.execSQL("INSERT INTO tblmovies(Tittle, Synopsis, Time, Buy, Photos) VALUES ('"+datos[1]+"', '"+datos[2]+"', '"+datos[3]+"', '"+datos[4]+"', " +
                           "'"+datos[5]+"')");
                   break;

               case "modificar":
                   try{
                       sqLiteDatabaseW.execSQL("UPDATE tblmovies SET Tittle ='"+datos[1]+"', Synopsis='"+datos[2]+"', Time = '"+datos[3]+"', Buy = '"+datos[4]+"', " +
                               "Photos = '"+datos[5]+"'");

                   }catch (Exception e){
                       Toast.makeText(miContext.getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();


                   }

                   break;

               case "eliminar":
                   sqLiteDatabaseW.execSQL("DELETE FROM tblmovies WHERE id = '"+datos[0]+"'");
                   break;

           }


           return datosCursor;



    }

}
