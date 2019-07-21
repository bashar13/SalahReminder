package com.bashar.salatreminder;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class PrayTimeManager {

    public static String[] prayTime(Context context) {

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);

        double timezone = TimeZone.getDefault().getOffset(now.getTime()) / 3600000;
        Log.d("Timezone", TimeZone.getDefault().getDisplayName());

        PrayTime prayers = new PrayTime();

        switch(SharedPreferencesManager.getIntPref(context, "TIME_FORMAT", 0)) {
            case 0:
                prayers.setTimeFormat(prayers.Time12);
                break;
            case 1:
                prayers.setTimeFormat(prayers.Time24);
                break;
            default:
                break;
        }
        //prayers.setTimeFormat(prayers.Time12);
        switch(SharedPreferencesManager.getIntPref(context, "CALCULATION_METHOD", 0)){
            case 0:
                prayers.setCalcMethod(prayers.Jafari);
                break;
            case 1:
                prayers.setCalcMethod(prayers.Karachi);
                break;
            case 2:
                prayers.setCalcMethod(prayers.ISNA);
                break;
            case 3:
                prayers.setCalcMethod(prayers.MWL);
                break;
            case 4:
                prayers.setCalcMethod(prayers.Makkah);
                break;
            case 5:
                prayers.setCalcMethod(prayers.Egypt);
                break;
            case 6:
                prayers.setCalcMethod(prayers.Tehran);
                break;
            default:
                break;
        }
        //prayers.setCalcMethod(prayers.Makkah);
        switch(SharedPreferencesManager.getIntPref(context, "JURISTIC_METHOD", 0)) {
            case 0:
                prayers.setAsrJuristic(prayers.Shafii);
                break;
            case 1:
                prayers.setAsrJuristic(prayers.Hanafi);
                break;
            default:
                break;
        }
        //prayers.setAsrJuristic(prayers.Shafii);
        switch(SharedPreferencesManager.getIntPref(context, "LATTITUDE_SELECTION", 0)){
            case 0:
                prayers.setAdjustHighLats(prayers.None);
                break;
            case 1:
                prayers.setAdjustHighLats(prayers.MidNight);
                break;
            case 2:
                prayers.setAdjustHighLats(prayers.OneSeventh);
                break;
            case 3:
                prayers.setAdjustHighLats(prayers.AngleBased);
                break;
            default:
                break;

        }
        //prayers.setAdjustHighLats(prayers.AngleBased);
        int[] offsets = { 0, 0, 0, 0, 0, 0, 0 }; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);



        double myLat = SharedPreferencesManager.getDoublePref(context, "LATTITUDE", 0);
        double myLong = SharedPreferencesManager.getDoublePref(context, "LONGITUDE", 0);
        ArrayList prayerTimes = prayers.getPrayerTimes(cal, myLat,
                myLong, timezone);
        //ArrayList prayerNames = prayers.getTimeNames();
        String[] timeArray = new String[7];
        for(int i=0; i<7; i++) {
            timeArray[i] = prayerTimes.get(i).toString();
        }
        return timeArray;
    }
}
