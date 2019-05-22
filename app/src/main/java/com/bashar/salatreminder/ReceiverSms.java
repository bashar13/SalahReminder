package com.bashar.salatreminder;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by Jahid on 8/31/2015.
 */
public class ReceiverSms extends BroadcastReceiver {
    SQLController dbcon;
    String edit_txt_msg;
    SharedPreferences reminder_pref;
    boolean switch_bool;
    @Override
    public void onReceive(Context context, Intent intent) {

        dbcon = new SQLController(context);
        dbcon.open();
        String extra_s = "extra";
        int db_waqt = 0;
        PendingIntent pendingintent;
        reminder_pref = context.getSharedPreferences("reminder_pref", Context.MODE_WORLD_WRITEABLE);
        getPref();

        pendingintent = PendingIntent.getActivity(context, 1, intent, 0);
        extra_s = intent.getStringExtra("SALAT");

        String waqt = (String.valueOf(extra_s));

        if(waqt.equals("Time for Fajr Prayer"))
            db_waqt = 3;
        else if(waqt.equals("Time for Dhur Prayer"))
            db_waqt = 4;
        else if(waqt.equals("Time for Asr Prayer"))
            db_waqt = 5;
        else if(waqt.equals("Time for Maghrib Prayer"))
            db_waqt = 6;
        else if(waqt.equals("Time for Esha Prayer"))
            db_waqt = 7;

        if(switch_bool)
            sendSMS(waqt, db_waqt, context);
    }

    public void sendSMS(String waqt, int db_waqt, Context context) {
        String number, name, msg;
        msg = edit_txt_msg + "\n" + waqt;
        Cursor c;
        c = dbcon.readData();
        boolean checkRec = c.moveToFirst();
        if(checkRec){
            do{
                if(c.getString(db_waqt).equals("yes")) {
                    number = c.getString(2);
                    try{

                /*PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                            new Intent("SMS_DELIVERED"), 0); */

                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(number, null, msg,
                                null, null);
                    /*int count = dbcon.getcount(DBhelper.TABLE_MESSAGE);
                    if (count <= 29)
                        dbcon.insertMessageData(messageText, messageDate,
                                uName, uNumber);
                    else {
                        Cursor cur = dbcon.readMessageData();
                        int id = cur.getInt(0);
                        dbcon.deleteMessageData(id);
                        cur.moveToNext();
                        dbcon.insertMessageData(messageText, messageDate,
                                uName, uNumber);
                    }*/
//					BatteryLevelReceiver Breceiver= new BatteryLevelReceiver();
//					Breceiver.msgTxt=messageText;

                    } catch (Exception ex){
                        Toast.makeText(context, "Your sms has failed...",
                                Toast.LENGTH_LONG).show();
                        ex.printStackTrace();

                    }
                } else {
                    //Toast.makeText(context, " Receipient Number not Set to send SMS!", Toast.LENGTH_LONG).show();
                }

                //Toast.makeText(getApplicationContext(), uNumber, Toast.LENGTH_LONG).show();

            } while(c.moveToNext());

        }
    }

    public void getPref(){
        edit_txt_msg = reminder_pref.getString("EDIT_TEXT_MSG", "Write your Message Here...");
        switch_bool = reminder_pref.getBoolean("SWITCH_SMS_BUT", false);
    }
}
