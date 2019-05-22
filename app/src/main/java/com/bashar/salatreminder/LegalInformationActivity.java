package com.bashar.salatreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class LegalInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal_information);

        ArrayList<String> listData = new ArrayList<String>();
        listData.add("Terms and Condition");
        listData.add("Privacy Policy");
        listData.add("");


        ListView lv = (ListView)findViewById(R.id.list_view);
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.legal_list_item, listData));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(LegalInformationActivity.this, TermsAndCondActivity.class);
                intent.putExtra("itemNo", position);
                startActivity(intent);
            }
        });
    }
}
