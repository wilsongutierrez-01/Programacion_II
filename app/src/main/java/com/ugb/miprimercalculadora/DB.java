package com.ugb.miprimercalculadora;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    static String nombreDB = "db_amigos";
    static String tblProducto = "CREATE TABLE tblproductos(idProducto integer primary key autoincrement, producto text, codigo text, marca text, descripcion text, presentacion text, preio text, urlPhoto text)";


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
        db.execSQL(tblProducto);

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
                datosCursor = sqLiteDataBaseR.rawQuery("select * from tblproductos order by nombre", null);
                break;

            case "nuevo":
                sqLiteDatabaseW.execSQL("INSERT INTO tblproductos(producto, codigo, marca, descripcion, presentacion, precio, urlPhoto) VALUES ('"+datos[1]+"', '"+datos[2]+"', '"+datos[3]+"', '"+datos[4]+"', " +
                                                                                                                                                                "'"+datos[5]+"', '"+datos[6]+"', '"+datos[7]+"')");
                break;

            case "modificar":
                sqLiteDatabaseW.execSQL("UPDATE tblproductos SET producto ='"+datos[1]+"', codigo='"+datos[2]+"', marca = '"+datos[3]+"', descripcion = '"+datos[4]+"', " +
                                                                                                                "presentacion = '"+datos[5]+"', precio = '"+datos[6]+"', urlPhoto = '"+datos[7]+"'");

            case "eliminar":
                sqLiteDatabaseW.execSQL("DELETE FROM tblproductos WHERE idProducto = '"+datos[0]+"'");

        }


        return datosCursor;


    }



}
