/**
 * @author Md Khairul Bashar
 * @date 8/17/2015.
 */

package com.bashar.salatreminder;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;



public class FragmentSalahTime extends Fragment {

    TextView txtCurDate, txtSunrise, textFajr, textDhur, textAsr, textMag, textEsha, timeZoneText;
    ImageButton but_fajr, but_dhur, but_asr, but_mag, but_esha;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.salah_time, container, false);

        txtCurDate = view.findViewById(R.id.cur_date);
        txtSunrise = view.findViewById(R.id.sunset_sunrise);
        textFajr = view.findViewById(R.id.textFajrTime);
        textDhur = view.findViewById(R.id.textDhurTime);
        textAsr = view.findViewById(R.id.textAsrTime);
        textMag = view.findViewById(R.id.textMagTime);
        textEsha = view.findViewById(R.id.textEshaTime);

        but_fajr = view.findViewById(R.id.fajrAlarm);
        but_dhur = view.findViewById(R.id.dhurAlarm);
        but_asr = view.findViewById(R.id.asrAlarm);
        but_mag = view.findViewById(R.id.magAlarm);
        but_esha = view.findViewById(R.id.eshaAlarm);

        timeZoneText = view.findViewById(R.id.time_zone);

        alarmButtonsOnCLickListenerMethods();
        editTimeTextOnClickListenerMethods();
        getSetPreferenceValues();
        return view;
}
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_salah, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.reset_time:
                String title = "Reset Time", message;
                if(SharedPreferencesManager.getBooleanPref(getContext(), "FAJR_EDITED", false) ||
                        SharedPreferencesManager.getBooleanPref(getContext(), "DHUR_EDITED", false) ||
                        SharedPreferencesManager.getBooleanPref(getContext(), "ASR_EDITED", false) ||
                        SharedPreferencesManager.getBooleanPref(getContext(), "MAG_EDITED", false) ||
                        SharedPreferencesManager.getBooleanPref(getContext(), "ESHA_EDITED", false)) {
                    message = "This will reset the prayer times according to current settings from your customized time and will also cancel the alarms. Are you sure you want to reset?";
                    showDialog(title, message, "No");
                }
                else {
                    message = "You have not customized any prayer time. To set prayer time manually tap on the time of your desired waqt.";
                    showDialog(title, message, "OK");
                }


                return true;
        }

        return false;
    }

    public void getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        String formattedDate = df.format(c.getTime());
        txtCurDate.setText(formattedDate);
    }

    public void getTime() {

        String prayerTimes[] = PrayTimeManager.prayTime(getContext());
        String sunrise = getResources().getString(R.string.sunrise) + ": " + prayerTimes[1]+"     " + getResources().getString(R.string.sunset) + ": " + prayerTimes[4];
        txtSunrise.setText(sunrise);
        textFajr.setText(prayerTimes[0]);
        textDhur.setText(prayerTimes[2]);
        textAsr.setText(prayerTimes[3]);
        textMag.setText(prayerTimes[5]);
        textEsha.setText(prayerTimes[6]);
    }

    public void getTimePicker(final String waq)
    {
        //Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
        final Calendar mcurrentTime = Calendar.getInstance();
        boolean flag;
        final SimpleDateFormat mSDF;
        int i;
        int j;

        TimePickerDialog timepickerdialog;
        if (SharedPreferencesManager.getIntPref(getContext(), "TIME_FORMAT", 0) == 0)
        {
            flag = false;
            mSDF = new SimpleDateFormat("hh:mm a");
        } else
        {
            flag = true;
            mSDF = new SimpleDateFormat("HH:mm");
        }
        i = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        j = mcurrentTime.get(Calendar.MINUTE);

        timepickerdialog = new TimePickerDialog(getActivity(), R.style.dialogStyle, new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker timepicker, int k, int l)
            {
                String time;
                mcurrentTime.set(Calendar.HOUR_OF_DAY, k);
                mcurrentTime.set(Calendar.MINUTE, l);
                time = mSDF.format(mcurrentTime.getTime());
                if (waq.equals("fajr")) {
                    textFajr.setText(time);
                    but_fajr.setImageResource(R.drawable.ic_alarm_off);
                    ReminderManager.cancelReminder(getContext(), 1);
                    SharedPreferencesManager.storeBooleanPref(getContext(), "FAJR_EDITED", true);
                    SharedPreferencesManager.storeStringPref(getContext(), "FAJR_EDITED_TIME", time);
                }

                else if (waq.equals("dhur")){
                    textDhur.setText(time);
                    but_dhur.setImageResource(R.drawable.ic_alarm_off);
                    ReminderManager.cancelReminder(getContext(), 2);
                    SharedPreferencesManager.storeBooleanPref(getContext(), "DHUR_EDITED", true);
                    SharedPreferencesManager.storeStringPref(getContext(), "DHUR_EDITED_TIME", time);
                }

                else if (waq.equals("asr")) {
                    textAsr.setText(time);
                    but_asr.setImageResource(R.drawable.ic_alarm_off);
                    ReminderManager.cancelReminder(getContext(), 3);
                    SharedPreferencesManager.storeBooleanPref(getContext(), "ASR_EDITED", true);
                    SharedPreferencesManager.storeStringPref(getContext(), "ASR_EDITED_TIME", time);
                }

                else if (waq.equals("mag")) {
                    textMag.setText(time);
                    but_mag.setImageResource(R.drawable.ic_alarm_off);
                    ReminderManager.cancelReminder(getContext(), 4);
                    SharedPreferencesManager.storeBooleanPref(getContext(), "MAG_EDITED", true);
                    SharedPreferencesManager.storeStringPref(getContext(), "MAG_EDITED_TIME", time);
                }

                else if (waq.equals("esha")) {
                    textEsha.setText(time);
                    but_esha.setImageResource(R.drawable.ic_alarm_off);
                    ReminderManager.cancelReminder(getContext(), 5);
                    SharedPreferencesManager.storeBooleanPref(getContext(), "ESHA_EDITED", true);
                    SharedPreferencesManager.storeStringPref(getContext(), "ESHA_EDITED_TIME", time);
                }
            }

        }, i, j, flag);
        timepickerdialog.setTitle("Select Time");
        timepickerdialog.show();
    }

    public void getSetPreferenceValues() {

        getCurrentDate();
        getTime();
        setEditedTime();
        setAlarmIcon();
        timeZoneText.setText(SharedPreferencesManager.getStringPref(getContext(), "CITY_NAME", null));
        ReminderManager.cancelAllReminder(getContext());
        ReminderManager.setAllReminder(getContext());
    }

    public void setEditedTime() {
        if(SharedPreferencesManager.getBooleanPref(getContext(), "FAJR_EDITED", false))
            textFajr.setText(SharedPreferencesManager.getStringPref(getContext(), "FAJR_EDITED_TIME", null));
        if(SharedPreferencesManager.getBooleanPref(getContext(), "DHUR_EDITED", false))
            textDhur.setText(SharedPreferencesManager.getStringPref(getContext(), "DHUR_EDITED_TIME", null));
        if(SharedPreferencesManager.getBooleanPref(getContext(), "ASR_EDITED", false))
            textAsr.setText(SharedPreferencesManager.getStringPref(getContext(), "ASR_EDITED_TIME", null));
        if(SharedPreferencesManager.getBooleanPref(getContext(), "MAG_EDITED", false))
            textMag.setText(SharedPreferencesManager.getStringPref(getContext(), "MAG_EDITED_TIME", null));
        if(SharedPreferencesManager.getBooleanPref(getContext(), "ESHA_EDITED", false))
            textEsha.setText(SharedPreferencesManager.getStringPref(getContext(), "ESHA_EDITED_TIME", null));
    }

    public void setAlarmIcon() {
        if(SharedPreferencesManager.getBooleanPref(getContext(), "FAJR_ALARM", false)) {
            but_fajr.setImageResource(R.drawable.ic_alarm);
            String time = textFajr.getText().toString();
            ReminderManager.setReminder(getContext(), 1, time, "Fajr Time");
        }
        else
            but_fajr.setImageResource(R.drawable.ic_alarm_off);

        if(SharedPreferencesManager.getBooleanPref(getContext(), "DHUR_ALARM", false)) {
            but_dhur.setImageResource(R.drawable.ic_alarm);
            String time = textDhur.getText().toString();
            ReminderManager.setReminder(getContext(), 2, time, "Dhur Time");
        }
        else
            but_dhur.setImageResource(R.drawable.ic_alarm_off);

        if(SharedPreferencesManager.getBooleanPref(getContext(), "ASR_ALARM", false)) {
            but_asr.setImageResource(R.drawable.ic_alarm);
            String time = textAsr.getText().toString();
            ReminderManager.setReminder(getContext(), 3, time, "Asr Time");
        }
        else
            but_asr.setImageResource(R.drawable.ic_alarm_off);

        if(SharedPreferencesManager.getBooleanPref(getContext(), "MAG_ALARM", false)) {
            but_mag.setImageResource(R.drawable.ic_alarm);
            String time = textMag.getText().toString();
            ReminderManager.setReminder(getContext(), 4, time, "Maghrib Time");
        }
        else
            but_mag.setImageResource(R.drawable.ic_alarm_off);

        if(SharedPreferencesManager.getBooleanPref(getContext(), "ESHA_ALARM", false)) {
            but_esha.setImageResource(R.drawable.ic_alarm);
            String time = textEsha.getText().toString();
            ReminderManager.setReminder(getContext(), 5, time, "Esha Time");
        }
        else
            but_esha.setImageResource(R.drawable.ic_alarm_off);
    }

    public void showDialog(String title, String message, final String butText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.dialogStyle);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(butText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if(butText.equals("Cancel")) {
                            System.exit(1);
                        }
                        else
                            dialog.cancel();
                    }
                });
        if(butText.equals("No")) {
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    SharedPreferencesManager.storeBooleanPref(getContext(), "FAJR_EDITED", false);
                    SharedPreferencesManager.storeBooleanPref(getContext(), "DHUR_EDITED", false);
                    SharedPreferencesManager.storeBooleanPref(getContext(), "ASR_EDITED", false);
                    SharedPreferencesManager.storeBooleanPref(getContext(), "MAG_EDITED", false);
                    SharedPreferencesManager.storeBooleanPref(getContext(), "ESHA_EDITED", false);

                    SharedPreferencesManager.storeBooleanPref(getContext(), "FAJR_ALARM", false );
                    SharedPreferencesManager.storeBooleanPref(getContext(), "DHUR_ALARM", false );
                    SharedPreferencesManager.storeBooleanPref(getContext(), "ASR_ALARM", false );
                    SharedPreferencesManager.storeBooleanPref(getContext(), "MAG_ALARM", false );
                    SharedPreferencesManager.storeBooleanPref(getContext(), "ESHA_ALARM", false );

                    ReminderManager.cancelAllReminder(getContext());

                    getSetPreferenceValues();
                    dialog.cancel();
                }
            });
        } else if(butText.equals("Cancel")) {
            builder.setNegativeButton("Enable Location", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                }
            });
        }

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void alarmButtonsOnCLickListenerMethods() {
        but_fajr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SharedPreferencesManager.getBooleanPref(getContext(), "FAJR_ALARM", false)) {
                    SharedPreferencesManager.storeBooleanPref(getContext(), "FAJR_ALARM", false);
                    ReminderManager.cancelReminder(getContext(), 1);
                    but_fajr.setImageResource(R.drawable.ic_alarm_off);
                }
                else {
                    SharedPreferencesManager.storeBooleanPref(getContext(), "FAJR_ALARM", true);
                    but_fajr.setImageResource(R.drawable.ic_alarm);
                    String time = textFajr.getText().toString();

                    ReminderManager.setReminder(getContext(), 1, time, "Fajr");
                }
            }
        });
        but_dhur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SharedPreferencesManager.getBooleanPref(getContext(), "DHUR_ALARM", false)) {
                    SharedPreferencesManager.storeBooleanPref(getContext(), "DHUR_ALARM", false);
                    ReminderManager.cancelReminder(getContext(), 2);
                    but_dhur.setImageResource(R.drawable.ic_alarm_off);
                }
                else {
                    SharedPreferencesManager.storeBooleanPref(getContext(), "DHUR_ALARM", true);
                    but_dhur.setImageResource(R.drawable.ic_alarm);
                    String time = textDhur.getText().toString();
                    ReminderManager.setReminder(getContext(), 2, time, "Dhur");
                }

            }
        });
        but_asr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SharedPreferencesManager.getBooleanPref(getContext(), "ASR_ALARM", false)) {
                    SharedPreferencesManager.storeBooleanPref(getContext(), "ASR_ALARM", false);
                    ReminderManager.cancelReminder(getContext(), 3);
                    but_asr.setImageResource(R.drawable.ic_alarm_off);
                }
                else {
                    SharedPreferencesManager.storeBooleanPref(getContext(), "ASR_ALARM", true);
                    but_asr.setImageResource(R.drawable.ic_alarm);
                    String time = textAsr.getText().toString();
                    ReminderManager.setReminder(getContext(), 3, time, "Asr");
                }

            }
        });
        but_mag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPreferencesManager.getBooleanPref(getContext(), "MAG_ALARM", false)) {
                    SharedPreferencesManager.storeBooleanPref(getContext(), "MAG_ALARM", false);
                    ReminderManager.cancelReminder(getContext(), 4);
                    but_mag.setImageResource(R.drawable.ic_alarm_off);
                } else {
                    SharedPreferencesManager.storeBooleanPref(getContext(), "MAG_ALARM", true);
                    but_mag.setImageResource(R.drawable.ic_alarm);
                    String time = textMag.getText().toString();
                    ReminderManager.setReminder(getContext(), 4, time, "Maghrib");
                }
            }
        });
        but_esha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPreferencesManager.getBooleanPref(getContext(), "ESHA_ALARM", false)) {
                    SharedPreferencesManager.storeBooleanPref(getContext(), "ESHA_ALARM", false);
                    ReminderManager.cancelReminder(getContext(), 5);
                    but_esha.setImageResource(R.drawable.ic_alarm_off);
                } else {
                    SharedPreferencesManager.storeBooleanPref(getContext(), "ESHA_ALARM", true);
                    but_esha.setImageResource(R.drawable.ic_alarm);
                    String time = textEsha.getText().toString();
                    ReminderManager.setReminder(getContext(), 5, time, "Esha");
                }


            }
        });
    }

    private void editTimeTextOnClickListenerMethods() {
        textFajr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTimePicker("fajr");
            }
        });

        textDhur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTimePicker("dhur");
            }
        });

        textAsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTimePicker("asr");
            }
        });

        textMag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTimePicker("mag");
            }
        });

        textEsha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTimePicker("esha");
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        getSetPreferenceValues();
    }
}
