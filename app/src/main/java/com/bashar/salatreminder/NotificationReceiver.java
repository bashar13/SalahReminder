package com.bashar.salatreminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

/**
 * @file NotificationReceiver.java
 * @brief Receives pending intent created during setting notification and send notification on receiving the
 * pending intent.
 * @author Md Khairul Bashar
 * @Created by Md Khairul Bashar on 23/08/2015.
 * @lastModified 14/01/2019
 */
public class NotificationReceiver extends BroadcastReceiver {

    public static String TAG = "NotificationReceiver.java";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "Broadcast received");

        String notification_channel_id = "SALAH_REMINDER";
        createNotificationChannel(context, notification_channel_id);

        String extra = intent.getStringExtra("SALAT");
        String contentText = "Time for " + extra + " Salah";
        PendingIntent pendingintent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, notification_channel_id)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingintent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, mBuilder.build());

    }

    private void createNotificationChannel(Context context, String notification_channel_id) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(notification_channel_id, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /*public void sendSMS(int db_waqt, Context context) {
        String number, name, msg;
        msg = edit_txt_msg;
        Cursor c;
        c = dbcon.readData();
        boolean checkRec = c.moveToFirst();
        if(checkRec){
            do{
                if(c.getString(db_waqt).equals("yes")) {
                        number = c.getString(2);
                        try{

                *//*PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                            new Intent("SMS_DELIVERED"), 0); *//*

                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(number, null, msg,
                                    null, null);
                    *//*int count = dbcon.getcount(DBhelper.TABLE_MESSAGE);
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
                    }*//*
//					BatteryLevelReceiver Breceiver= new BatteryLevelReceiver();
//					Breceiver.msgTxt=messageText;

                        } catch (Exception ex){
                            Toast.makeText(context,"Your sms has failed...",
                                    Toast.LENGTH_LONG).show();
                            ex.printStackTrace();

                        }
                    } else {
                    //Toast.makeText(context, " Receipient Number not Set to send SMS!", Toast.LENGTH_LONG).show();
                }

                //Toast.makeText(getApplicationContext(), uNumber, Toast.LENGTH_LONG).show();

            } while(c.moveToNext());

        }
    }*/
}
