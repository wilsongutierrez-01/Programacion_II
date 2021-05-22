package com.ugb.miprimercalculadora;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    Context miContext;
    static String nombreDB = "db_usuarios";
    static String tblUsuarios = "CREATE TABLE tblusuarios(idUsuario integer primary key autoincrement, usuario text, correo text, contra text)";

    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nombreDB, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tblUsuarios);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Cursor admin_usuarios (String accion, String [] datos){
        Cursor datosCursor = null;
        SQLiteDatabase sqLiteDatabaseW = getWritableDatabase();
        SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();

        try{
            switch (accion){
                case "consultar":
                    datosCursor = sqLiteDatabaseR.rawQuery("select * from tblusuarios order by usuario", null);
                    break;

                case "nuevo":
                    sqLiteDatabaseW.execSQL("INSERT INTO tblusuarios(usuario, correo, contra) VALUES ('"+datos[1]+"', '"+datos[2]+"', '"+datos[3]+"')");
                    break;

                case "modificar":
                    sqLiteDatabaseW.execSQL("UPDATE tblusuarios SET usuario = '"+datos[1]+"', correo = '"+datos[2]+"', contra = '"+datos[3]+"' " +
                            "WHERE idUsuario = '"+datos[0]+"'");
                    break;

                case "eliminar":
                    sqLiteDatabaseW.execSQL("DELETE FROM tblusuarios WHERE idUsuario = '"+datos[0]+"'");
                    break;
            }

        }catch (Exception e){
            Toast.makeText(miContext.getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }



        return datosCursor;
    }
}
