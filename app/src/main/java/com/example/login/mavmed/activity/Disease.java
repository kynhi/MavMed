package com.example.login.mavmed.activity;

import java.util.ArrayList;

/*Disease class definition*/
class Disease{
    public Disease(String name, ArrayList<String> list) {
        this.name = name;
        this.symptoms = list;
    };
    public boolean query(String query) {
        //user will give a symptom, must iterate over all symptoms
        //and return the diseases with the correct symptoms
        return symptoms.contains(query.toString());
    }
    String name;
    ArrayList<String> symptoms = new ArrayList<String>();
}
