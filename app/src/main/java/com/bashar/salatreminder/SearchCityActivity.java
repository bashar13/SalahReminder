package com.bashar.salatreminder;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchCityActivity extends AppCompatActivity {

    double myLat, myLong;
    TextView textv;
    String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_city);

        AutoCompleteTextView autocompleteView = (AutoCompleteTextView)findViewById(R.id.autoComplete);
        autocompleteView.setAdapter(new PlaceAutoCompleteAdapter(this, R.layout.search_city_list_item));

        textv = (TextView)findViewById(R.id.temp_text);

        autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                cityName = (String) parent.getItemAtPosition(position);
                SharedPreferencesManager.storeStringPref(getApplicationContext(), "cityName", cityName);
                //Toast.makeText(SearchCityActivity.this, cityName, Toast.LENGTH_SHORT).show();

                getLattitudeLongitue(cityName);
            }
        });

    }

    public void getLattitudeLongitue(String cityName) {
        if(Geocoder.isPresent()){
            try {
                //String location = "theNameOfTheLocation";
                Geocoder gc = new Geocoder(this);
                List<Address> addressList= gc.getFromLocationName(cityName, 1); // get the found Address Objects
                //String result;
                if (addressList != null && addressList.size() > 0) {
                    Address address = addressList.get(0);
                    /*StringBuilder sb = new StringBuilder();
                    sb.append(address.getLatitude()).append("\n");
                    sb.append(address.getLongitude()).append("\n");
                    result = sb.toString();
                    textv.setText(result);*/
                    myLat = address.getLatitude();
                    myLong = address.getLongitude();
                    SharedPreferencesManager.storeDoublePref(getApplicationContext(), "Lattitude", myLat );
                    SharedPreferencesManager.storeDoublePref(getApplicationContext(), "Longitude", myLong);
                    //Log.d("Lat Long", result);
                    //getCity(myLat, myLong);
                }
            } catch (IOException e) {
                Log.e("SearchActivity", "Unable to connect to Geocoder", e);
            }
        }
    }
}
