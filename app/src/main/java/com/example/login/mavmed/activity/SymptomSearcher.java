package com.example.login.mavmed.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.login.mavmed.R;

import java.util.ArrayList;

public class SymptomSearcher extends AppCompatActivity{

    Button search = (Button) findViewById(R.id.searchButton);
    EditText searchBox = (EditText) findViewById(R.id.searchBox);
    TextView diseaseList = (TextView) findViewById(R.id.diseaseList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.symptom_searcher);

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

        /*Disease instances*/
        Disease heartAttack = new Disease("Heart attack", heart_attack);
        Disease foodPoisoning = new Disease("Food poisoning", food_poisoning);
        Disease heatStroke = new Disease("Heat stroke", heat_stroke);
        Disease allergicReaction = new Disease("Allergic reactoin", allergic_reaction);
        Disease _arthritis = new Disease("Arthritis", arthritis);
        Disease bladderCancer = new Disease("Bladder cancer", bladder_cancer);
        Disease _bronchitis = new Disease("Bronchitis", bronchitis);

        /*Add disease instances to array list*/
        final ArrayList<Disease> diseases = new ArrayList<Disease>();
        diseases.add(heartAttack);
        diseases.add(foodPoisoning);
        diseases.add(heatStroke);
        diseases.add(allergicReaction);
        diseases.add(_arthritis);
        diseases.add(bladderCancer);
        diseases.add(_bronchitis);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diseaseList.setText(null);
                String symptom = searchBox.getText().toString();
                /*Iterating over disease list*/
                for(Disease dis: diseases) {
                    if(dis.query(symptom))
                    {
                        diseaseList.append(dis.name + "\n");
                    }
                }
            }
        });

    }
}

