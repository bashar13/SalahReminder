package com.bashar.salatreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jahid on 8/17/2015.
 */
public class FragmentReminder extends Fragment {

    //ImageButton but_edit;
    //EditText edit_msg;
    //RadioButton radio_noti, radio_alarm, radio_both;
    //String edit_txt_msg;
    //Switch switch_msg;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reminder_settings, container, false);

        //but_edit = (ImageButton)view.findViewById(R.id.but_edit);
        //edit_msg = (EditText)view.findViewById(R.id.alert_sms);
        final RadioButton radio_alarm = (RadioButton)view.findViewById(R.id.radio_alarm);
        final RadioButton radio_noti = (RadioButton)view.findViewById(R.id.radio_noti);
        final RadioButton radio_both = (RadioButton)view.findViewById(R.id.radio_both);
        final RadioButton radio_adhan = (RadioButton)view.findViewById(R.id.radio_adhan);
        final RadioButton radio_default_ring = (RadioButton)view.findViewById(R.id.radio_default_ring);
        //switch_msg = (Switch)view.findViewById(R.id.switch_data);

        //edit_msg.setText(edit_txt_msg);
        //edit_msg.setEnabled(false);

       /* switch_msg.setChecked(switch_bool);

        switch_msg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                storePref();
                if (switch_msg.isChecked()) {
                    //Toast.makeText(getActivity(), "SMS ON", Toast.LENGTH_LONG).show();
                    if(!checkBoxBool)
                        showAlertDialog();
                    createPendingIntentSms();
                    scheduleSms();

                }
                else {
                    //Toast.makeText(getActivity(), "SMS OFF", Toast.LENGTH_LONG).show();
                    ((AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE)).cancel(pending_fajr_sms);
                    ((AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE)).cancel(pending_dhur_sms);
                    ((AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE)).cancel(pending_asr_sms);
                    ((AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE)).cancel(pending_mag_sms);
                    ((AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE)).cancel(pending_esha_sms);
                }
            }
        });*/

        switch(SharedPreferencesManager.getIntPref(getContext(), "REMINDER_TYPE", 1)) {
            case 1:
                radio_noti.setChecked(true);
                radio_alarm.setChecked(false);
                radio_both.setChecked(false);
                radio_noti.setTextColor(getResources().getColor(R.color.colorPrimary));
                radio_both.setTextColor(Color.BLACK);
                radio_alarm.setTextColor(Color.BLACK);
                break;
            case 2:
                radio_alarm.setChecked(true);
                radio_noti.setChecked(false);
                radio_both.setChecked(false);
                radio_noti.setTextColor(Color.BLACK);
                radio_both.setTextColor(Color.BLACK);
                radio_alarm.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 3:
                radio_both.setChecked(true);
                radio_alarm.setChecked(false);
                radio_noti.setChecked(false);
                radio_noti.setTextColor(Color.BLACK);
                radio_both.setTextColor(getResources().getColor(R.color.colorPrimary));
                radio_alarm.setTextColor(Color.BLACK);
                break;
        }

        radio_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radio_alarm.isChecked()){
                    radio_noti.setChecked(false);
                    radio_both.setChecked(false);
                    radio_noti.setTextColor(Color.BLACK);
                    radio_both.setTextColor(Color.BLACK);
                    radio_alarm.setTextColor(getResources().getColor(R.color.colorPrimary));
                    updateReminder(2);

                }
            }
        });
        radio_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radio_noti.isChecked()){
                    radio_alarm.setChecked(false);
                    radio_both.setChecked(false);
                    radio_noti.setTextColor(getResources().getColor(R.color.colorPrimary));
                    radio_both.setTextColor(Color.BLACK);
                    radio_alarm.setTextColor(Color.BLACK);
                    updateReminder(1);

                }
            }
        });
        radio_both.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radio_both.isChecked()){
                    radio_noti.setChecked(false);
                    radio_alarm.setChecked(false);
                    radio_noti.setTextColor(Color.BLACK);
                    radio_both.setTextColor(getResources().getColor(R.color.colorPrimary));
                    radio_alarm.setTextColor(Color.BLACK);

                    updateReminder(3);

                }
            }
        });

        switch (SharedPreferencesManager.getIntPref(getContext(), "ALARM_TONE_TYPE", 1)) {
            case 1:
                radio_adhan.setChecked(true);
                radio_default_ring.setChecked(false);
                radio_adhan.setTextColor(getResources().getColor(R.color.colorPrimary));
                radio_default_ring.setTextColor(Color.BLACK);
                break;
            case 2:
                radio_default_ring.setChecked(true);
                radio_adhan.setChecked(false);
                radio_default_ring.setTextColor(getResources().getColor(R.color.colorPrimary));
                radio_adhan.setTextColor(Color.BLACK);
                break;

        }

        radio_adhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radio_adhan.isChecked()){
                    radio_default_ring.setChecked(false);
                    radio_default_ring.setTextColor(Color.BLACK);
                    radio_adhan.setTextColor(getResources().getColor(R.color.colorPrimary));
                    SharedPreferencesManager.storeIntPref(getContext(), "ALARM_TONE_TYPE", 1);
                }
            }
        });
        radio_default_ring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radio_default_ring.isChecked()){
                    radio_adhan.setChecked(false);
                    radio_default_ring.setTextColor(getResources().getColor(R.color.colorPrimary));
                    radio_adhan.setTextColor(Color.BLACK);
                    SharedPreferencesManager.storeIntPref(getContext(), "ALARM_TONE_TYPE", 2);

                }
            }
        });

        /*but_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_msg.isEnabled()) {
                    edit_msg.setEnabled(false);
                    but_edit.setImageResource(R.drawable.ic_edit);
                    edit_txt_msg = edit_msg.getText().toString();
                    storePref();
                } else {
                    edit_msg.setEnabled(true);
                    but_edit.setImageResource(R.drawable.ic_done);
                }
            }
        });*/
        return view;
    }

    public void updateReminder(int reminderType) {
        ReminderManager.cancelAllReminder(getContext());
        SharedPreferencesManager.storeIntPref(getContext(), "REMINDER_TYPE", reminderType);
        ReminderManager.setAllReminder(getContext());
    }

    /*public void showAlertDialog()
    {
        View checkBoxView = View.inflate(getActivity(), R.layout.chekbox, null);
        final CheckBox checkBox = (CheckBox) checkBoxView.findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // Save to shared preferences
            }
        });
        checkBox.setText("Do not show this again.");

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("SMS charge alert");
        builder.setMessage("If you use SMS Reminder, regular SMS charge may apply as per your operators policy.")
                .setView(checkBoxView)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(checkBox.isChecked())
                            checkBoxBool = true;
                        else
                            checkBoxBool = false;
                        storePref();
                        dialog.cancel();
                    }
                })
                .show();
    }*/
}
