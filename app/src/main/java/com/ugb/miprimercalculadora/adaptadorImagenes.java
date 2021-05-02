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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View Visor = layoutInflater.inflate(R.layout.listview_imagenes,parent,false);
        TextView temp = Visor.findViewById(R.id.lblTittle);
        ImageView img = Visor.findViewById(R.id.imgShow);

        try {
            misProductos = datosProductosArrayList.get(position);
            temp.setText(misProductos.getTittle());

            temp = Visor.findViewById(R.id.lblSynopsis);
            temp.setText("Synopsis: \n" + misProductos.getSynopsis());

            temp = Visor.findViewById(R.id.lblBuy);
            temp.setText("$" + misProductos.getBuy());

            temp = Visor.findViewById(R.id.lblTime);
            temp.setText("Time: " + misProductos.getTime());

            Bitmap photo = BitmapFactory.decodeFile(misProductos.getPhotos());
            img.setImageBitmap(photo);


        }catch (Exception e){
            mensajeToast(e.getMessage());
        }

        return Visor;
    }
   private void mensajeToast(String msg){
        Toast.makeText(context.getApplicationContext(),msg, Toast.LENGTH_LONG).show();
   }
}
