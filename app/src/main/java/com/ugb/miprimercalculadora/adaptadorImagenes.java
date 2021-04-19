package com.ugb.miprimercalculadora;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class adaptadorImagenes extends BaseAdapter {
    Context context;
    ArrayList<productos> datosProductosArrayList;
    LayoutInflater layoutInflater;
    productos misProductos;

    public adaptadorImagenes(Context context, ArrayList<productos> datosProductosArrayList) {
        this.context = context;
        this.datosProductosArrayList = datosProductosArrayList;
    }

    @Override
    public int getCount() {
        return datosProductosArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return datosProductosArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(datosProductosArrayList.get(position).getIdProducto());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View Visor = layoutInflater.inflate(R.layout.listview_imagenes,parent,false);
        TextView temp = Visor.findViewById(R.id.lblTitulo);
        ImageView img = Visor.findViewById(R.id.imgPhoto);

        try {
            misProductos = datosProductosArrayList.get(position);
            temp.setText(misProductos.getProducto());

            temp = Visor.findViewById(R.id.lblcodigo);
            temp.setText("codigo: " + misProductos.getUrlPhoto());

            temp = Visor.findViewById(R.id.lblprecio);
            temp.setText("$" + misProductos.getPrecio());

            try{
                Bitmap photo = BitmapFactory.decodeFile(misProductos.getUrlPhoto());
                img.setImageBitmap(photo);

            }catch (Exception e){
                mensajeToast(e.getMessage());
            }


        }catch (Exception e){
            mensajeToast(e.getMessage());
        }

        return Visor;
    }
   private void mensajeToast(String msg){
        Toast.makeText(context.getApplicationContext(),msg, Toast.LENGTH_LONG).show();
   }
}
