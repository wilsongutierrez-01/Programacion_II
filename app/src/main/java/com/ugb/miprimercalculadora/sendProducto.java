package com.ugb.miprimercalculadora;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class sendProducto extends AsyncTask<String, String, String> {
    Context context;
    utilidades uc = new utilidades();
    String resp;
    HttpURLConnection urlConnection;

    public sendProducto(Context context) {
        this.context = context;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... pms) {
        String jsonResponse = null;
        String jsonDatos =pms[0];
        BufferedReader lectorBuffer;

        try {
            URL url = new URL(uc.url_mto);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/jason");
            urlConnection.setRequestProperty("Accept", "application/jason");

            Writer escritor = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            escritor.write(jsonDatos);
            escritor.close();

            InputStream entrada = urlConnection.getInputStream();
            if (entrada == null){
                return null;
            }

            lectorBuffer = new BufferedReader( new InputStreamReader(entrada));
            resp = lectorBuffer.toString();

            String line;
            StringBuffer cadenaBuffer = new StringBuffer();
            while ((line = lectorBuffer.readLine()) != null){
                cadenaBuffer.append(line+"\n");
            }

            if (cadenaBuffer.length() == 0){
                return null;
            }
            jsonResponse = cadenaBuffer.toString();
            return jsonResponse;

        }catch (Exception e){
            Log.d("ENVIANDO", "error" + e.getMessage());
        }
        return null;
    }
}
