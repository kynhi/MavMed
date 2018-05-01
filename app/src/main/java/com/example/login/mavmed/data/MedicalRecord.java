package com.example.login.mavmed.data;

import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nhi K luong on 4/27/2018.
 */

public class MedicalRecord {
    List<String> allergies = new ArrayList<>();
    List<String> immune =  new ArrayList<>();
    List<String> med =  new ArrayList<>();
    public MedicalRecord (){};

    public MedicalRecord(List<String> a,List<String>im, List<String> m){
        allergies = a;
        immune = im;
        med = m;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public List<String> getImmune() {
        return immune;
    }

    public List<String> getMed() {
        return med;
    }
}
