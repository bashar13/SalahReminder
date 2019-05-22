package com.bashar.salatreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Jahid on 9/6/2015.
 */
public class ReceiverBoot extends BroadcastReceiver {

    SharedPreferences alarm_pref;
    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        alarm_pref = context.getSharedPreferences("alarm_pref", Context.MODE_WORLD_WRITEABLE);

        if(Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            //Toast.makeText(context, "Boot Completed", Toast.LENGTH_LONG).show();
            storeAlarmPref();
        }
    }

    public void storeAlarmPref(){
        SharedPreferences.Editor editor = alarm_pref.edit();
        editor.putBoolean("FAJR_ALARM", false);
        editor.putBoolean("DHUR_ALARM", false);
        editor.putBoolean("ASR_ALARM", false);
        editor.putBoolean("MAG_ALARM", false);
        editor.putBoolean("ESHA_ALARM", false);
        editor.commit();
    }
}
