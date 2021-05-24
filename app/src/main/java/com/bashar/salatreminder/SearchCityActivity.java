package com.bashar.salatreminder;

import android.location.Address;
import android.location.Geocoder;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class SearchCityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_city);

        AutoCompleteTextView autocompleteView = (AutoCompleteTextView)findViewById(R.id.autoComplete);
        autocompleteView.setAdapter(new PlaceAutoCompleteAdapter(this, R.layout.search_city_list_item));

        TextView textv = (TextView)findViewById(R.id.temp_text);

        autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String cityName = (String) parent.getItemAtPosition(position);
                SharedPreferencesManager.storeStringPref(getApplicationContext(), "CITY_NAME", cityName);
                //Toast.makeText(SearchCityActivity.this, cityName, Toast.LENGTH_SHORT).show();

                getLattitudeLongitue(cityName);
                finish();
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

                    double myLat = address.getLatitude();
                    double myLong = address.getLongitude();
                    SharedPreferencesManager.storeDoublePref(getApplicationContext(), "LATTITUDE", myLat );
                    SharedPreferencesManager.storeDoublePref(getApplicationContext(), "LONGITUDE", myLong);
                }
            } catch (IOException e) {
                Log.e("SearchActivity", "Unable to connect to Geocoder", e);
            }
        }
    }
}
