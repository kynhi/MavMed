package com.example.login.mavmed.activity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
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

        ArrayList<String> allergic_reaction = new ArrayList<String>();
        allergic_reaction.add("sneezing");
        allergic_reaction.add("itchy eyes");
        allergic_reaction.add("red eyes");
        allergic_reaction.add("watering eyes");
        allergic_reaction.add("tightness in chest");
        allergic_reaction.add("swelling");
        allergic_reaction.add("dry skin");
        allergic_reaction.add("red skin");

        ArrayList<String> arthritis = new ArrayList<String>();
        arthritis.add("joint pain");
        arthritis.add("joint inflammation");
        arthritis.add("weakness");
        arthritis.add("joint restricted movement");

        ArrayList<String> bladder_cancer = new ArrayList<String>();
        bladder_cancer.add("blood in urine");
        bladder_cancer.add("frequent urination");
        bladder_cancer.add("burning urination");

        ArrayList<String> bronchitis = new ArrayList<String>();
        bronchitis.add("hacking cough");
        bronchitis.add("yellow-grey mucus in cough");
        bronchitis.add("sore throat");
        bronchitis.add("runny nose");
        bronchitis.add("aches and pains");
        bronchitis.add("tiredness");

        ArrayList<String> chickenpox = new ArrayList<String>();
        chickenpox.add("red rashes");
        chickenpox.add("red blisters");

        ArrayList<String> chronic_pancreatitis = new ArrayList<String>();
        chronic_pancreatitis.add("abdominal pain");
        chronic_pancreatitis.add("nausea");
        chronic_pancreatitis.add("vomiting");

        ArrayList<String> common_cold = new ArrayList<String>();
        common_cold.add("sore throat");
        common_cold.add("blocked nose");
        common_cold.add("runny nose");
        common_cold.add("sneezing");
        common_cold.add("coughing");
        common_cold.add("fever");
        common_cold.add("headache");
        common_cold.add("muscle ache");

        ArrayList<String> constipation = new ArrayList<String>();
        constipation.add("stomach ache");
        constipation.add("bloated stomach");
        constipation.add("loss of appetite");
        constipation.add("nausea");

        /*Disease instances*/
        Disease heartAttack = new Disease("Heart attack", heart_attack);
        Disease foodPoisoning = new Disease("Food poisoning", food_poisoning);
        Disease heatStroke = new Disease("Heat stroke", heat_stroke);
        Disease allergicReaction = new Disease("Allergic reaction", allergic_reaction);
        Disease _arthritis = new Disease("Arthritis", arthritis);
        Disease bladderCancer = new Disease("Bladder cancer", bladder_cancer);
        Disease _bronchitis = new Disease("Bronchitis", bronchitis);
        Disease chronicPancreatitis = new Disease("Chronic Pancreatitis", chronic_pancreatitis);
        Disease commonCold = new Disease("Common Cold", common_cold);
        Disease _constipation = new Disease("Constipation", constipation);

        /*Add disease instances to array list*/
        final ArrayList<Disease> diseases = new ArrayList<Disease>();
        diseases.add(heartAttack);
        diseases.add(foodPoisoning);
        diseases.add(heatStroke);
        diseases.add(allergicReaction);
        diseases.add(_arthritis);
        diseases.add(bladderCancer);
        diseases.add(_bronchitis);
        diseases.add(chronicPancreatitis);
        diseases.add(commonCold);
        diseases.add(_constipation);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean not_there = false;
                listDataHeader.clear();
                listHash.clear();
                String symptom = searchBox.getText().toString();
                int count = -1;
                diseaseList.setText(null);

                /*Iterating over disease list*/
                for(Disease dis: diseases) {
                    if(dis.query(symptom) || dis.name.equalsIgnoreCase(symptom)) //tells us if the disease has this symptom
                    {
                        ++count;
                        listDataHeader.add(dis.name);
                        final List<String> list = new ArrayList<>();
                        listHash.put(listDataHeader.get(count),list);
                        list.addAll(dis.symptoms);
                    }
                    else{   //the query does not match anything in the arraylist
                        not_there = true;
                    }
                }
                listAdapter = new ExpandableListAdapter(getContext(),listDataHeader,listHash);
                listView.setAdapter(listAdapter);
                if(not_there){
                    Toast toast = new Toast(getContext());
                    toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
                    toast.makeText(getContext(), "Sorry, I don't recognize any disease related to \"" + symptom + "\"", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
