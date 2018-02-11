package com.example.login.mavmed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MedicalRecordActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_record);

        listView = (ExpandableListView)findViewById(R.id.lvExp);
        initData();
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Allergies");
        listDataHeader.add("Immunization");
        listDataHeader.add("Medication");


        List<String> allergies = new ArrayList<>();
        allergies.add("Peanut");
        allergies.add("Siracha");

        List<String> immune = new ArrayList<>();
        immune.add("Cheese");
        immune.add("Bugs");

        List<String> med = new ArrayList<>();
        med.add("icecream");

        listHash.put(listDataHeader.get(0),allergies);
        listHash.put(listDataHeader.get(1),immune);
        listHash.put(listDataHeader.get(2),med);
    }
}
