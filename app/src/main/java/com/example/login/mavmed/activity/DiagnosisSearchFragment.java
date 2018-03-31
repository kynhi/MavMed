package com.example.login.mavmed.activity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.mavmed.R;
import com.example.login.mavmed.adapter.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DiagnosisSearchFragment extends Fragment {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    boolean[] result = new boolean[7];
    boolean cough = true;
    boolean headache = true;
    boolean heartburn = true;
    boolean diarrhea = true;
    boolean flumps = true;
    boolean gloop = true;
    boolean indigestion = true;

    public DiagnosisSearchFragment() {
        // Required empty public constructor
    }

    private TextWatcher textWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
//            Message.message(getContext(), "text has changed");
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            Message.message(getContext(), "text has not been changed");
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
//            Message.message(getContext(), "text is being changed");
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SearchDatabaseAdapter searchDatabaseHelper = new SearchDatabaseAdapter(getContext());
        //SQLiteDatabase db = searchDatabaseHelper.getWritableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.symptom_searcher, container, false);

        Button send = (Button) rootView.findViewById(R.id.senddiagsearch);
        Button search = (Button) rootView.findViewById(R.id.searchButton);
        final EditText searchBox = (EditText) rootView.findViewById(R.id.searchBox);
        final TextView diseaseList = (TextView) rootView.findViewById(R.id.diseaseList);
        searchBox.addTextChangedListener(textWatcher);

        /*Expandable list stuff*/
        listView = (ExpandableListView)rootView.findViewById(R.id.symptom_expand);
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        /*List of diseases and symptoms*/
        ArrayList<String> heart_attack = new ArrayList<String>();
        heart_attack.add("chest pain");
        heart_attack.add("extreme weakness");
        heart_attack.add("anxiety");
        heart_attack.add("shortness of breath");
        heart_attack.add("irregular heartbeat");
        heart_attack.add("vomiting"); ///test

        ArrayList<String> food_poisoning = new ArrayList<String>();
        food_poisoning.add("diarrhea");
        food_poisoning.add("nausea");
        food_poisoning.add("vomiting");
        food_poisoning.add("fever");
        food_poisoning.add("muscle aches");

        ArrayList<String> heat_stroke = new ArrayList<String>();
        heat_stroke.add("throbbing headache");
        heat_stroke.add("dizziness");
        heat_stroke.add("red skin");
        heat_stroke.add("hot skin");
        heat_stroke.add("dry skin");

        /*Disease instances*/
        Disease heartAttack = new Disease("Heart attack", heart_attack);
        Disease foodPoisoning = new Disease("Food poisoning", food_poisoning);
        Disease heatStroke = new Disease("Heat stroke", heat_stroke);

        /*Add disease instances to array list*/
        final ArrayList<Disease> diseases = new ArrayList<Disease>();
        diseases.add(heartAttack);
        diseases.add(foodPoisoning);
        diseases.add(heatStroke);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listDataHeader.clear();
                listHash.clear();
                String symptom = searchBox.getText().toString();
                int count = -1;
                diseaseList.setText(null);
//                listDataHeader.add("hello");
//                final List<String> hello = new ArrayList<>();
//                listHash.put(listDataHeader.get(0),hello);
                /*Iterating over disease list*/
                for(Disease dis: diseases) {
                    if(dis.query(symptom)) //tells us if the disease has this symptom
                    {
                        ++count;
//                        diseaseList.append(dis.name + "\n");
//                        diseaseList.append("Having symptoms: ");
//                        diseaseList.append(dis.symptoms.toString());
//                        diseaseList.append("\n");
                        listDataHeader.add(dis.name);
                        final List<String> list = new ArrayList<>();
                        listHash.put(listDataHeader.get(count),list);
                        list.addAll(dis.symptoms);
                    }
                }
                listAdapter = new ExpandableListAdapter(getContext(),listDataHeader,listHash);
                listView.setAdapter(listAdapter);
            }
        });

//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment fragment = null;
//
//                fragment = new MedicalRecordFragment();
//                String title = getString(R.string.title_medical_record);
//
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container_body, fragment);
//                fragmentTransaction.commit();
//
//                // set the toolbar title
//
//                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
//            }
//        });

//        CheckBox checkBox_cough = (CheckBox) rootView.findViewById(R.id.checkbox_cough);
//        CheckBox checkBox_headache = (CheckBox) rootView.findViewById(R.id.checkbox_headache);
//        CheckBox checkBox_flumps = (CheckBox) rootView.findViewById(R.id.checkbox_flumps);
//        CheckBox checkBox_diarrhea = (CheckBox) rootView.findViewById(R.id.checkbox_diarrhea);
//        CheckBox checkBox_indigestion = (CheckBox) rootView.findViewById(R.id.checkbox_indigestion);
//        CheckBox checkBox_gloop = (CheckBox) rootView.findViewById(R.id.checkbox_gloop);
//        CheckBox checkBox_heartburn = (CheckBox) rootView.findViewById(R.id.checkbox_heartburn);


//        checkBox_cough.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (view.isEnabled()){
//                    if(cough){
//                        Toast toast = Toast.makeText(getActivity(), "You have a cough", Toast.LENGTH_SHORT);
//                        toast.show();
//                        cough = false;
//                    }
//                    else{
//                        Toast toast = Toast.makeText(getActivity(), "You do not have a cough", Toast.LENGTH_SHORT);
//                        toast.show();
//                        cough = true;
//                    }
//                }
//            }
//        });
//        checkBox_headache.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (view.isEnabled()){
//                    if(headache){
//                        Toast toast = Toast.makeText(getActivity(), "You have a headache", Toast.LENGTH_SHORT);
//                        toast.show();
//                        headache = false;
//                    }
//                    else{
//                        Toast toast = Toast.makeText(getActivity(), "You do not have a headache", Toast.LENGTH_SHORT);
//                        toast.show();
//                        headache = true;
//                    }
//                }
//            }
//        });
//        checkBox_flumps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (view.isEnabled()){
//                    if(flumps){
//                        Toast toast = Toast.makeText(getActivity(), "You have a flumps", Toast.LENGTH_SHORT);
//                        toast.show();
//                        flumps = false;
//                    }
//                    else{
//                        Toast toast = Toast.makeText(getActivity(), "You do not have a flumps", Toast.LENGTH_SHORT);
//                        toast.show();
//                        flumps = true;
//                    }
//                }
//            }
//        });
//        checkBox_diarrhea.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (view.isEnabled()){
//                    if(diarrhea){
//                        Toast toast = Toast.makeText(getActivity(), "You have diarrhea", Toast.LENGTH_SHORT);
//                        toast.show();
//                        diarrhea = false;
//                    }
//                    else{
//                        Toast toast = Toast.makeText(getActivity(), "You do not have diarrhea", Toast.LENGTH_SHORT);
//                        toast.show();
//                        diarrhea = true;
//                    }
//                }
//            }
//        });
//        checkBox_indigestion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (view.isEnabled()) {
//                    if (indigestion) {
//                        Toast toast = Toast.makeText(getActivity(), "You have indigestion", Toast.LENGTH_SHORT);
//                        toast.show();
//                        indigestion = false;
//                    } else {
//                        Toast toast = Toast.makeText(getActivity(), "You do not have indigestion", Toast.LENGTH_SHORT);
//                        toast.show();
//                        indigestion = true;
//                    }
//                }
//            }
//        });
//        checkBox_gloop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (view.isEnabled()) {
//                    if (gloop) {
//                        Toast toast = Toast.makeText(getActivity(), "You have a gloop", Toast.LENGTH_SHORT);
//                        toast.show();
//                        gloop = false;
//                    } else {
//                        Toast toast = Toast.makeText(getActivity(), "You do not have a gloop", Toast.LENGTH_SHORT);
//                        toast.show();
//                        gloop = true;
//                    }
//                }
//            }
//        });
//        checkBox_heartburn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (view.isEnabled()) {
//                    if (heartburn) {
//                        Toast toast = Toast.makeText(getActivity(), "You have heartburn", Toast.LENGTH_SHORT);
//                        toast.show();
//                        heartburn = false;
//                    } else {
//                        Toast toast = Toast.makeText(getActivity(), "You do not have heartburn", Toast.LENGTH_SHORT);
//                        toast.show();
//                        heartburn = true;
//                    }
//                }
//            }
//        });


        // Inflate the layout for this fragment
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
