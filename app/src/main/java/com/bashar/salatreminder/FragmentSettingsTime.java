/**
 * @file FragmentSettingsTime.java
 * @brief Settings activity (fragment). Update and store user settings
 * @author Md Khairul Bashar
 * Date 08/01/2019
 */


package com.bashar.salatreminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by Jahid on 8/17/2015.
 */
public class FragmentSettingsTime extends Fragment {

    //private TextView textTimeZone, txtCurrentTime, txtTimeZoneTime;
    private SimpleDateFormat sdf;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.time_settings, container, false);

        TextView textCity = (TextView)view.findViewById(R.id.location_name);
        Spinner cal_method = (Spinner)view.findViewById(R.id.spinner_calculation);
        Spinner juristic = (Spinner)view.findViewById(R.id.spinner_juristic);
        Spinner high_lat = (Spinner)view.findViewById(R.id.spinner_latitude);
        Spinner time_format = (Spinner)view.findViewById(R.id.spinner_format);

        String[] idArray = TimeZone.getAvailableIDs();

        sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss");

        ArrayAdapter<String> idAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, idArray);

        idAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //timezone.setAdapter(idAdapter);
        //timezone.setSelection(sel_timezone);
        textCity.setText(SharedPreferencesManager.getStringPref(getContext(),"CITY_NAME", null));
        cal_method.setSelection(SharedPreferencesManager.getIntPref(getContext(),"CALCULATION_METHOD", 0));
        juristic.setSelection(SharedPreferencesManager.getIntPref(getContext(),"JURISTIC_METHOD", 0));
        high_lat.setSelection(SharedPreferencesManager.getIntPref(getContext(),"LATTITUDE_SELECTION", 0));
        time_format.setSelection(SharedPreferencesManager.getIntPref(getContext(), "TIME_FORMAT", 0));

        getGMTTime();

        textCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "CLicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), SearchCityActivity.class);
                startActivity(intent);
            }
        });

        cal_method.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int sel_cal = parent.getSelectedItemPosition();

                SharedPreferencesManager.storeIntPref(getContext(),"CALCULATION_METHOD", sel_cal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        juristic.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int sel_jur = parent.getSelectedItemPosition();

                SharedPreferencesManager.storeIntPref(getContext(),"JURISTIC_METHOD", sel_jur);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        high_lat.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int sel_lat = parent.getSelectedItemPosition();

                SharedPreferencesManager.storeIntPref(getContext(), "HIGH_LAT", sel_lat);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        time_format.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int sel_time = parent.getSelectedItemPosition();

                SharedPreferencesManager.storeIntPref(getContext(), "TIME_FORMAT", sel_time);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*timezone.setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        getGMTTime();
                        selectedId = (String) (parent
                                .getItemAtPosition(position));
                        sel_timezone = parent.getSelectedItemPosition();
                        String check = String.valueOf(sel_timezone);

                        TimeZone timezone = TimeZone.getTimeZone(selectedId);
                        time_zone_name = timezone.getDisplayName();
                        //Toast.makeText(getActivity(), check ,Toast.LENGTH_SHORT).show();

                        TimeZoneOffset = timezone.getOffset(Calendar.getInstance().getTimeInMillis())
                                / (1000 * 60 * 60);

                        //float hrs = TimeZoneOffset / 60;
                        //float mins = TimeZoneOffset % 60;

                        miliSeconds = miliSeconds + timezone.getRawOffset();

                        resultdate = new Date(miliSeconds);
                        System.out.println(sdf.format(resultdate));

                        //textTimeZone.setText(TimeZoneName + " : GMT " + hrs + "."
                          //      + mins);
                        //txtTimeZoneTime.setText("" + sdf.format(resultdate));
                        miliSeconds = 0;

                        storeAlarmPref();
                        storeSettingsPref();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });*/
        return view;
    }

    private void getGMTTime() {
        Calendar current = Calendar.getInstance();
        //txtCurrentTime.setText("" + current.getTime());

        long miliSeconds = current.getTimeInMillis();

        TimeZone tzCurrent = current.getTimeZone();
        int offset = tzCurrent.getRawOffset();
        if (tzCurrent.inDaylightTime(new Date())) {
            offset = offset + tzCurrent.getDSTSavings();
        }

        miliSeconds = miliSeconds - offset;

        Date resultdate = new Date(miliSeconds);
        System.out.println(sdf.format(resultdate));
    }

    /*public void storeAlarmPref() {
        SharedPreferences.Editor editor = alarm_pref.edit();
        editor.putBoolean("FAJR_EDITED", false);
        editor.putBoolean("DHUR_EDITED", false);
        editor.putBoolean("ASR_EDITED", false);
        editor.putBoolean("MAG_EDITED", false);
        editor.putBoolean("ESHA_EDITED", false);
    }*/

    /*public void getCity(double myLat, double myLong) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(myLat, myLong, 3);
            String cityName = addresses.get(0).getLocality();
            //String stateName = addresses.get(0).get;
            String countryName = addresses.get(0).getCountryName();

            //Toast.makeText(getActivity(), "You are in " + cityName + ", "+ countryName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void onResume(){
        super.onResume();
        /*getPref();
        getCity(myLat, myLong);*/
    }
}
