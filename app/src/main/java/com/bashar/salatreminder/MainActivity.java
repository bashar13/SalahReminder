/** @file MainActivity.java
 * @brief Start activity of the app. Handles moving to different fragment view. Shows the activity_main.xml for the first
 * time. After that shows the FragmentSalahTime.java
 *
 * @author Md Khairul Bashar
 * @date 08/01/2019
 */

package com.bashar.salatreminder;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.api.PendingResult;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener {

    public boolean doubleBackToExitPressedOnce = false;
    private static final long MIN_DISTANCE_FOR_UPDATE = 10;
    private static final long MIN_TIME_FOR_UPDATE = 1000 * 60 * 2;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    Button buttonLocate;
    LinearLayout startLayout;
    public boolean isPrivacyChecked;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.setDrawerIndicatorEnabled(false);
        drawer.setDrawerLockMode(1);



        buttonLocate = (Button)findViewById(R.id.button_locate);
        final CheckBox checkPrivacy = (CheckBox)findViewById(R.id.privacy_check);
        startLayout = (LinearLayout)findViewById(R.id.start_layout);


        if(SharedPreferencesManager.getBooleanPref(this,"AGREEMENT_STATUS", false)){
            startLayout.setVisibility(LinearLayout.GONE);
            toggle.setDrawerIndicatorEnabled(true);
            drawer.setDrawerLockMode(0);
            int lastView = SharedPreferencesManager.getIntPref(this, "CURRENT_FRAGMENT", R.id.nav_time );
            updateViewFragment(lastView);

        }

        checkPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPrivacy.isChecked())
                    buttonLocate.setEnabled(true);
                else
                    buttonLocate.setEnabled(false);
            }
        });


        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Prevent CheckBox state from being toggled when link is clicked
                widget.cancelPendingInputEvents();
                // Do action for link text...
                Intent intent = new Intent(MainActivity.this, LegalInformationActivity.class);
                startActivity(intent);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // Show links with underlines (optional)
                ds.setUnderlineText(false);
            }
        };

        SpannableString linkText = new SpannableString(getResources().getString(R.string.privacy_check));
        linkText.setSpan(clickableSpan, 15, linkText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        CharSequence cs = TextUtils.expandTemplate(
                "^1", linkText);

        checkPrivacy.setText(cs);
        checkPrivacy.setLinkTextColor(getResources().getColor(R.color.colorPrimary));
// Finally, make links clickable
        checkPrivacy.setMovementMethod(LinkMovementMethod.getInstance());


        buttonLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isLocationEnabled()) {
                    showDialog("Location Access", getResources().getString(R.string.location_enable), "Enable Location", "Cancel", 1);
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission. ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                }
            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Location myLocation = getCurrentKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if(myLocation != null) {
                        double myLat = myLocation.getLatitude();
                        SharedPreferencesManager.storeDoublePref(this,"LATTITUDE", myLat);
                        double myLong = myLocation.getLongitude();
                        SharedPreferencesManager.storeDoublePref(this,"LONGITUDE", myLong);
                        getCity(myLat, myLong);
                        isPrivacyChecked = true;
                        SharedPreferencesManager.storeBooleanPref(this,"AGREEMENT_STATUS", isPrivacyChecked);
                        startLayout.setVisibility(LinearLayout.GONE);
                        toggle.setDrawerIndicatorEnabled(true);
                        drawer.setDrawerLockMode(0);
                        int lastView = SharedPreferencesManager.getIntPref(this,"CURRENT_FRAGMENT", R.id.nav_time);
                        updateViewFragment(lastView);
                    }
                    else {

                        Toast.makeText(MainActivity.this, "Unable to locate", Toast.LENGTH_SHORT).show();

                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    showDialog("Exit", "The app is unable to get accurate prayer times without location access. Do you want to exit the application", "Stay", "Exit", 0);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //Toast.makeText(this, "fragmentid" + id, Toast.LENGTH_SHORT).show();
        SharedPreferencesManager.storeIntPref(this, "currentFragment", id);

        updateViewFragment(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {


    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                SharedPreferencesManager.storeIntPref(this,"currentFragment", R.id.nav_time);
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please Tap BACK again to exit",
                    Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    public void updateViewFragment(int id) {

        Fragment fragment = null;
        if (id == R.id.nav_time) {
            fragment = new FragmentSalahTime();
        /*} else if (id == R.id.nav_rec) {
            fragment = new FragmentReceipientList();*/

        } else if (id == R.id.nav_settings) {
            fragment = new FragmentSettingsTime();

        } else if (id == R.id.nav_reminder) {
            fragment = new FragmentReminder();

        } else if (id == R.id.nav_help) {
            fragment = new FragmentHelp();

        } else if (id == R.id.nav_about) {
            fragment = new FragmentAboutList();
        }
        if (fragment != null) {
            FragmentManager fragmentmanager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentmanager.beginTransaction();
            ft.replace(R.id.screen_area, fragment);
            ft.commitAllowingStateLoss();

        }
    }

    public void showDialog(String title, String message, final String butPos, final String butNeg, final int callId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.dialogStyle);
        builder.setTitle(title).setMessage(message).setCancelable(false).setPositiveButton(butPos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (callId) {
                    case 0: {
                        dialogInterface.cancel();
                        break;
                    }case 1: {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
                        break;
                    }
                    default: break;


                }

            }
        }).setNegativeButton(butNeg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (callId){
                    case 0: {
                        System.exit(1);
                        break;
                    }case 1: {
                        dialogInterface.cancel();
                        break;
                    }
                    default:break;
                }

            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private Location getCurrentKnownLocation(String providersOnly){
        LocationManager manager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        Location location = null;

        int permissionCheckFine = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if( manager.isProviderEnabled(providersOnly) && permissionCheckFine == PackageManager.PERMISSION_GRANTED){
            manager.requestLocationUpdates(providersOnly, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
            if(manager != null){
                location = manager.getLastKnownLocation(providersOnly);
                //Toast.makeText(getApplicationContext(), "returned location", Toast.LENGTH_LONG).show();
                return location;
            }
        }
        //Toast.makeText(getApplicationContext(), "returned NULL location", Toast.LENGTH_LONG).show();
        return null;

    }

    public boolean isLocationEnabled() {
        final LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return false;

        }
        return true;
    }

    public void getCity(double myLat, double myLong) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String city = null;
        try {
            List<Address> addresses = geocoder.getFromLocation(myLat, myLong, 1);
            String cityName = addresses.get(0).getLocality();
            //String stateName = addresses.get(0).get;
            String countryName = addresses.get(0).getCountryName();

            city = cityName + ", "+ countryName;
            SharedPreferencesManager.storeStringPref(this,"CITY_NAME", city);

            //Toast.makeText(this, "You are in " + cityName + ", "+ countryName, Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();

        }


    }


}
