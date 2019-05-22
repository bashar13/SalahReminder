package com.bashar.salatreminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FragmentAboutList extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_legal_information, container, false);

        ArrayList<String> listData = new ArrayList<String>();
        listData.add("About App");
        listData.add("Legal Information");
        listData.add("");


        ListView lv = (ListView)view.findViewById(R.id.list_view);
        lv.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.legal_list_item, listData));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                switch (position){
                    case 0: {
                        Intent intent = new Intent(getActivity(), AboutActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 1: {
                        Intent intent = new Intent(getActivity(), LegalInformationActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
        return view;
    }
}
