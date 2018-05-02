package com.example.login.mavmed.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.login.mavmed.R;
import com.example.login.mavmed.adapter.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DiagnosisSearchFragment extends Fragment {

    /*Hello from Texas!*/

    private DatabaseHelper db;
    long id_num;
    String query;

    List<Row> possible_diseases;
    List<Row> all_symptoms;
    List<String> strings;
    ArrayList<String> help_text = new ArrayList<>();
    List<Row> symptoms_for_this_disease;
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> listItems = new ArrayList<String>();

    int count = 0; //counter

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    public DiagnosisSearchFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_diagnosissearch, container, false);

        //        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
//        ListView listView = (ListView) findViewById(R.id.my_list_view);
//        listView.setAdapter(adapter);

        // get the listview
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);

        final Context context = this.getContext();

//        Button button = (Button) rootView.findViewById(R.id.search_button);
        Button query_add = (Button) rootView.findViewById(R.id.query_add);
        Button search_multi = (Button) rootView.findViewById(R.id.search_multi);
        Button clear_list = (Button) rootView.findViewById(R.id.clear_list);
        ImageButton clear_text = (ImageButton) rootView.findViewById(R.id.clear_text);
        final ListView symptoms = (ListView) rootView.findViewById(R.id.symptoms);

        /*Get a new database helper*/
        db = new DatabaseHelper(context);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if(!prefs.getBoolean("firstTime", false)) {
            // run your one time code
            /*Add symptoms to the database*/                /*S_ID*/
            db.insertSymptom("bloating");           //1
            db.insertSymptom("diarrhea");           //2
            db.insertSymptom("cough");              //3
            db.insertSymptom("headache");           //4
            db.insertSymptom("nausea");             //5
            db.insertSymptom("fever");              //6
            db.insertSymptom("throat irritation");  //7
            db.insertSymptom("fatigue");            //8

                                                /*D_ID*/
            db.insertDisease("irritable bowel syndrome");    //1
            db.insertDisease("lactose intolerance");
            db.insertDisease("asthma");
            db.insertDisease("acute sinusitis");
            db.insertDisease("heartburn");
            db.insertDisease("influenza (flu)");
            db.insertDisease("migraine headache");
            db.insertDisease("viral gastroenteritis");
            db.insertDisease("motion sickness");
            db.insertDisease("diverticulitis");
            db.insertDisease("common cold");
            db.insertDisease("whooping cough");
            db.insertDisease("drug allergy");
            db.insertDisease("iron deficiency anemia");
            db.insertDisease("hypothyroidism");
            db.insertDisease("viral pneumonia");
            db.insertDisease("strep throat");
            db.insertDisease("appendicitis");

            db.insertSymptomToDisease(1, 1);
            db.insertSymptomToDisease(2, 1);
            db.insertSymptomToDisease(1, 2);
            db.insertSymptomToDisease(2, 2);
            db.insertSymptomToDisease(3, 3);
            db.insertSymptomToDisease(3, 4);
            db.insertSymptomToDisease(4, 4);
            db.insertSymptomToDisease(5, 5);
            db.insertSymptomToDisease(2, 6);
            db.insertSymptomToDisease(3, 6);
            db.insertSymptomToDisease(4, 6);
            db.insertSymptomToDisease(6, 6);
            db.insertSymptomToDisease(4, 7);
            db.insertSymptomToDisease(2, 8);
            db.insertSymptomToDisease(5, 8);
            db.insertSymptomToDisease(5, 9);
            db.insertSymptomToDisease(2, 10);
            db.insertSymptomToDisease(3, 11);
            db.insertSymptomToDisease(6, 11);
            db.insertSymptomToDisease(7, 11);
            db.insertSymptomToDisease(3, 12);
            db.insertSymptomToDisease(5, 13);
            db.insertSymptomToDisease(8, 13);
            db.insertSymptomToDisease(8, 14);
            db.insertSymptomToDisease(8, 15);
            db.insertSymptomToDisease(6, 16);
            db.insertSymptomToDisease(6, 17);
            db.insertSymptomToDisease(7, 17);
            db.insertSymptomToDisease(5, 18);
            db.insertSymptomToDisease(6, 18);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

//        query = "vomiting"; //might break without this
//        possible_diseases = db.getDiseases(query);
        all_symptoms = db.getAllSymptoms(); //for suggested search

        for (Row row : all_symptoms) { //for suggested search
            list.add(row.get_s_name());
//            Message.message(this, "adding " + row.get_s_name());
        }

        //for suggested search
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, list);
        final AutoCompleteTextView editText = (AutoCompleteTextView) rootView.findViewById(R.id.search_text);
        editText.setAdapter(adapter);
        editText.setThreshold(1);

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        /*For multiquery*/
        final ArrayList<String> multi_q = new ArrayList<>();

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, multi_q);
        symptoms.setAdapter(adapter2);

//        final ArrayAdapter<String> finalAdapter = adapter;
        symptoms.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = multi_q.get(position);
                multi_q.remove(position);
                adapter2.notifyDataSetChanged();
                Message.message(getActivity(), item + " removed from symptoms");
            }
        });

        clear_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multi_q.clear();
                listDataHeader.clear();
                listDataChild.clear();
                adapter2.notifyDataSetChanged();
                Message.message(context, "All symptoms removed");

            }
        });

        query_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = editText.getText().toString().trim().toLowerCase();
                if(!query.isEmpty()){
                    multi_q.add(query);
                    adapter2.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(context, "Please enter a symptom to add to the search list", Toast.LENGTH_LONG).show();
                }
            }
        });

        help_text.add("Please try a different combination of symptoms");

        clear_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });

        search_multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!multi_q.isEmpty()) {
                    count = 0;
                    listDataHeader.clear();
                    listDataChild.clear();

                    possible_diseases = db.getDiseasesMulti(multi_q);

                    if(possible_diseases.isEmpty()){
                        listDataHeader.add("Sorry, I can't find any diseases related to those symptoms!");
                        listDataChild.put(listDataHeader.get(0), help_text);
                    }

                    for (Row row : possible_diseases) {    //possible_diseases contains all diseases connected to the query

                    /*Parent header name*/
                        listDataHeader.add(row.get_d_name());

                    /*Create child list*/
                        List<String> symptoms_to_strings = new ArrayList<>();

                        symptoms_for_this_disease = db.getSymptomsByDisease(row.get_d_name()); //gets all symptoms connected to this disease

                    /*Convert all rows into strings to add to the child list*/
                        for (Row subrow : symptoms_for_this_disease) {
                            symptoms_to_strings.add(subrow.get_s_name());
                        }
                    /*-----------------*/

                        listDataChild.put(listDataHeader.get(count++), symptoms_to_strings); // Header, Child data

                    }

                    listAdapter = new ExpandableListAdapter(context, listDataHeader, listDataChild);

                    // setting list adapter
                    expListView.setAdapter(listAdapter);
                }
                else{
                    Toast.makeText(context, "Your symptoms list is empty! Please add some symptoms before pressing search", Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
