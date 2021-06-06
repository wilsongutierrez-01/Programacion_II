package com.ugb.miprimercalculadora;

import android.content.Context;
import android.os.PowerManager;

public class wakeLocker {
    private static PowerManager.WakeLock wakeLock;

    public static void acquire(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "WakeLock");
        wakeLock.acquire();
    }
    public static void relase(){
        if (wakeLock!=null) wakeLock.release(); wakeLock = null;
    }
}
