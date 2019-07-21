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

    TextView textCity;
    Spinner cal_method, juristic, high_lat, time_format;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.time_settings, container, false);

        textCity = view.findViewById(R.id.location_name);
        cal_method = view.findViewById(R.id.spinner_calculation);
        juristic = view.findViewById(R.id.spinner_juristic);
        high_lat = view.findViewById(R.id.spinner_latitude);
        time_format = view.findViewById(R.id.spinner_format);

        updateView();
        onCLickListenerMethods();

        return view;
    }


    private void updateView() {
        textCity.setText(SharedPreferencesManager.getStringPref(getContext(),"CITY_NAME", null));
        cal_method.setSelection(SharedPreferencesManager.getIntPref(getContext(),"CALCULATION_METHOD", 0));
        juristic.setSelection(SharedPreferencesManager.getIntPref(getContext(),"JURISTIC_METHOD", 0));
        high_lat.setSelection(SharedPreferencesManager.getIntPref(getContext(),"LATTITUDE_SELECTION", 0));
        time_format.setSelection(SharedPreferencesManager.getIntPref(getContext(), "TIME_FORMAT", 0));
    }

    private void onCLickListenerMethods() {
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

                SharedPreferencesManager.storeIntPref(getContext(), "LATTITUDE_SELECTION", sel_lat);
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
    }

    @Override
    public void onResume(){
        super.onResume();
        updateView();
    }
}
