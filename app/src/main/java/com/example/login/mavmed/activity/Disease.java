package com.example.login.mavmed.activity;

import java.util.ArrayList;

/*Disease class definition*/
public class Disease extends ArrayList<String>{
    public boolean does_have(Object o) {
        String paramStr = (String)o;
        for (String s : this) {
            if (paramStr.equalsIgnoreCase(s)) return true;
        }
        return false;
    }
    public Disease(String name, ArrayList<String> list) {
        this.name = name;
        this.symptoms = list;
    };
    public boolean query(String query) {
        //user will give a symptom, must iterate over all symptoms
        //and return the diseases with the correct symptoms
        return symptoms.contains(query.toString().toLowerCase().trim());

    }
    String name;
    ArrayList<String> symptoms = new ArrayList<String>();
}