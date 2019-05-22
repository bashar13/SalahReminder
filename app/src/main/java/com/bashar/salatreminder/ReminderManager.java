package com.bashar.salatreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class ReminderManager {

    private static final String TAG = "ReminderManager.java";

    public static void setReminder(Context context, int request_code, String time, String extra_value) {

        switch(SharedPreferencesManager.getIntPref(context, "REMINDER_TYPE", 1)) {
            case 1:
                createNotification(context, request_code, time, extra_value);
                break;
            case 2:
                createAlarm(context, request_code + 5, time, extra_value);
                break;
            case 3:
                createNotification(context, request_code, time, extra_value);
                createAlarm(context, request_code + 5, time, extra_value);
                break;
        }

    }

    public static void cancelReminder(Context context, int request_code){

        switch(SharedPreferencesManager.getIntPref(context, "REMINDER_TYPE", 1)) {
            case 1:
                cancelNotification(context, request_code);
                break;
            case 2:
                cancelAlarm(context, request_code + 5);
                break;
            case 3:
                cancelNotification(context, request_code);
                cancelAlarm(context, request_code + 5);
                break;
        }
    }


    private static void createAlarm(Context context, int request_code, String time, String extra_value){

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        int time_format = SharedPreferencesManager.getIntPref(context, "TIME_FORMAT", 0);
        Calendar calendar = setReminderTime(time, time_format);

        Intent intent = new Intent(context, MyAlarmActivity.class);
        //intent.putExtra("SALAT", extra_value);
        PendingIntent pending = PendingIntent.getActivity(context, request_code, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pending);
        Log.d(TAG, "ALARM set at "+ time);

    }

    private static void createNotification(Context context, int request_code, String time, String extra_value) {

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        int time_format = SharedPreferencesManager.getIntPref(context, "TIME_FORMAT", 0);
        Calendar calendar = setReminderTime(time, time_format);

        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("SALAT", extra_value);
        PendingIntent pending = PendingIntent.getBroadcast(context, request_code, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pending);

        Log.d(TAG, "NOTIFICATION set at "+ time);

    }

    private static void cancelAlarm(Context context, int request_code){

        Intent intent = new Intent(context, MyAlarmActivity.class);
        PendingIntent pending = PendingIntent.getActivity(context, request_code, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        ((AlarmManager)context.getSystemService(Context.ALARM_SERVICE)).cancel(pending);
        Log.d(TAG, "ALARM cancel " + request_code);

    }

    private static void cancelNotification(Context context, int request_code){

        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pending = PendingIntent.getBroadcast(context, request_code, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        ((AlarmManager)context.getSystemService(Context.ALARM_SERVICE)).cancel(pending);

        Log.d(TAG, "NOTIFICATION cancel " + request_code);

    }
    
    public static void cancelAllReminder(Context context) {
        for(int i=1; i<=5; i++){
            cancelReminder(context, i);
        }
    }
    
    public static void setAllReminder(Context context){
        String prayerTimes[] = PrayTimeManager.prayTime(context);

        if(SharedPreferencesManager.getBooleanPref(context, "FAJR_ALARM", false)){
            if(SharedPreferencesManager.getBooleanPref(context, "FAJR_EDITED", false)){
                setReminder(context, 1,
                        SharedPreferencesManager.getStringPref(context, "FAJR_EDITED_TIME", null),
                        "Fajr");
            } else {
               setReminder(context, 1, prayerTimes[0], "Fajr");
            }

        }
        if(SharedPreferencesManager.getBooleanPref(context, "DHUR_ALARM", false)){
            if(SharedPreferencesManager.getBooleanPref(context, "DHUR_EDITED", false)){
                setReminder(context, 2,
                        SharedPreferencesManager.getStringPref(context, "DHUR_EDITED_TIME", null),
                        "Dhur");
            } else {
                setReminder(context, 2, prayerTimes[2], "Dhur");
            }

        }
        if(SharedPreferencesManager.getBooleanPref(context, "ASR_ALARM", false)){
            if(SharedPreferencesManager.getBooleanPref(context, "ASR_EDITED", false)){
                setReminder(context, 3,
                        SharedPreferencesManager.getStringPref(context, "ASR_EDITED_TIME", null),
                        "Asr");
            } else {
                setReminder(context, 3, prayerTimes[3], "Asr");
            }

        }
        if(SharedPreferencesManager.getBooleanPref(context, "MAG_ALARM", false)){
            if(SharedPreferencesManager.getBooleanPref(context, "MAG_EDITED", false)){
                setReminder(context, 4,
                        SharedPreferencesManager.getStringPref(context, "MAG_EDITED_TIME", null),
                        "Maghrib");
            } else {
                setReminder(context, 4, prayerTimes[5], "Maghrib");
            }

        }
        if(SharedPreferencesManager.getBooleanPref(context, "ESHA_ALARM", false)){
            if(SharedPreferencesManager.getBooleanPref(context, "ESHA_EDITED", false)){
                setReminder(context, 5,
                        SharedPreferencesManager.getStringPref(context, "ESHA_EDITED_TIME", null),
                        "Esha");
            } else {
                setReminder(context, 5, prayerTimes[6], "Esha");
            }

        }
    }



    private static Calendar setReminderTime(String time, int time_format) {
        int hour = Integer.parseInt((new StringBuilder(String.valueOf(Character.toString(time.charAt(0)))))
                .append(Character.toString(time.charAt(1))).toString());
        int min = Integer.parseInt((new StringBuilder(String.valueOf(Character.toString(time.charAt(3)))))
                .append(Character.toString(time.charAt(4))).toString());
        if(time_format == 0) {
            String time_f = (new StringBuilder(String.valueOf(Character.toString(time.charAt(6)))))
                    .append(Character.toString(time.charAt(7))).toString();
            //Toast.makeText(getActivity(),time_f,Toast.LENGTH_LONG).show();
            if (!time_f.equals("PM") && !time_f.equals("pm")) {
                if (hour == 12) {
                    //Toast.makeText(getActivity(),"am",Toast.LENGTH_LONG).show();
                    hour = 0;
                }
            }
            else {
                if (hour < 12) {
                    //Toast.makeText(getActivity(),time_f + "+ pm",Toast.LENGTH_LONG).show();
                    hour += 12;
                }
            }
        }

        Calendar calendar;
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        if (System.currentTimeMillis() > calendar.getTimeInMillis())
        {
            calendar.add(Calendar.DATE, 1);
        }

        return calendar;
    }


}
