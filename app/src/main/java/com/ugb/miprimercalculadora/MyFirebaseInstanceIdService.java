package com.ugb.miprimercalculadora;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MyFirebaseInstanceIdService extends MyFirebaseMessaginService {
    public String obtenerToken(){
        AtomicReference<String> token = null;
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()){
                return;
            }
            token.set(task.getResult());
        });
        return token.get();
    }
}
