package com.ugb.miprimercalculadora;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.BoringLayout;

public class internterDetectec {
    private Context context;

    public internterDetectec(Context context) {
        this.context = context;
    }

    public Boolean internetConnection(){
        ConnectivityManager verifyConnection = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (verifyConnection != null){
            NetworkInfo[] infos = verifyConnection.getAllNetworkInfo();
            if(infos != null){
                for(int i = 0; i < infos.length; i++ ){
                    if (infos [i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
