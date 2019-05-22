package com.bashar.salatreminder;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.Actions;

import java.io.IOException;

/**
 * Created by Jahid on 8/24/2015.
 */
public class MyAlarmActivity extends Activity implements View.OnClickListener {

    private MediaPlayer mMediaPlayer;
    PendingIntent pendingAlarm;
    ImageView alarm_img;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_layout);
        Intent intent = new Intent();
        //pendingAlarm = PendingIntent.getActivity(this, 1, intent, 0);
        //String extra_s = intent.getStringExtra("SALAT");

       /* RotateAnimation anim = new RotateAnimation(0f, 350f, 15f, 15f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(700);*/

        TextView waqt_name = (TextView) findViewById(R.id.waqt_name);
        //alarm_img = (ImageView) findViewById(R.id.alarm_image);
        Button stopAlarm = (Button) findViewById(R.id.stopAlarm);

        //alarm_img.startAnimation(anim);

        stopAlarm.setOnClickListener(this);
        /*stopAlarm.setOnClickListener(new OnClickListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                mMediaPlayer.stop();
                finish();
                return false;
            }
        });*/

        playSound(this, getAlarmUri());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(Api).build();
        //client = new GoogleApiClient.Builder(this).addApi(Api).build();
    }

    private void playSound(Context context, Uri alert) {
        switch (SharedPreferencesManager.getIntPref(context, "ALARM_TONE_TYPE", 1)) {
            case 1:
                mMediaPlayer= MediaPlayer.create(MyAlarmActivity.this, R.raw.adhan_makkah);
                mMediaPlayer.start();
                break;
            case 2:
                try {
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setDataSource(context, alert);
                    final AudioManager audioManager = (AudioManager) context
                            .getSystemService(Context.AUDIO_SERVICE);
                    if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                        mMediaPlayer.prepare();
                        mMediaPlayer.start();
                    }
                } catch (IOException e) {
                    System.out.println("OOPS");
                }
                break;
        }
    }

    //Get an alarm sound. Try for an alarm. If none set, try notification,
    //Otherwise, ringtone.
    private Uri getAlarmUri() {
        Uri alert = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        mMediaPlayer.stop();
        //alarm_img.setAnimation(null);
        finish();
        onStop();
    }

    public Action getAction() {
        return Actions.newView("MyAlarm Page", "android-app://com.bashar.salahreminder/http/host/path");
    }

    @Override
    protected void onStart() {
        super.onStart();
   /* If you’re logging an action on an item that has already been added to the index,
   you don’t have to add the following update line. See
   https://firebase.google.com/docs/app-indexing/android/personal-content#update-the-index for
   adding content to the index */
        //FirebaseAppIndex.getInstance().update(getIndexable());
        //FirebaseUserActions.getInstance().start(getAction());
    }

    @Override
    protected void onStop() {
        //FirebaseUserActions.getInstance().end(getAction());
        super.onStop();
    }
}

