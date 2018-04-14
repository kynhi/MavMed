package com.example.login.mavmed.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.mavmed.R;
import com.example.login.mavmed.adapter.ExpandableListAdapter2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class MedicalRecordFragment extends Fragment {

    private ExpandableListView listView;
    private ExpandableListAdapter2 listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;
    int category=0;

    public MedicalRecordFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // this code is equal set content view in onCreate() method
        View rootView = inflater.inflate(R.layout.fragment_medical_record, container, false);

        listView = (ExpandableListView)rootView.findViewById(R.id.lvExp);
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Allergies");
        listDataHeader.add("Immunization");
        listDataHeader.add("Medication");
        final List<String> allergies = new ArrayList<>();
        final List<String> immune = new ArrayList<>();
        final List<String> med = new ArrayList<>();
        listHash.put(listDataHeader.get(0),allergies);
        listHash.put(listDataHeader.get(1),immune);
        listHash.put(listDataHeader.get(2),med);

        allergies.add("Peanut");
        allergies.add("Siracha");
        immune.add("Cheese");
        immune.add("Bugs");
        med.add("icecream");
        med.add("cookies");


        listAdapter = new ExpandableListAdapter2(getContext(),listDataHeader,listHash);
        listView.setAdapter(listAdapter);
//BMI DIALOG
        Button mShowDialog = (Button) rootView.findViewById(R.id.button_bmi);
        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.bmi_medicalrecord, null);
                final EditText weight = (EditText) mView.findViewById(R.id.et_weight);
                final EditText heightft = (EditText) mView.findViewById(R.id.et_heightft);
                final EditText heightin = (EditText) mView.findViewById(R.id.et_heightin);
                final TextView result = (TextView) mView.findViewById(R.id.tv_result);
                final TextView condition = (TextView) mView.findViewById(R.id.tv_condition);
                final Button calculate = (Button) mView.findViewById(R.id.button_bmiCalc);
                final Button done = (Button) mView.findViewById(R.id.button_done);
                calculate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    boolean good = true;
                    double finalcalc = -1;
                    //TEST FOR PARSING ERROR
                        try {
                            Double.parseDouble(weight.getText().toString());
                        } catch (NumberFormatException e) {
                            good = false;
                        }
                        try {
                            Double.parseDouble(heightft.getText().toString());
                        } catch (NumberFormatException e) {
                            good = false;
                        }
                        try {
                            Double.parseDouble(heightin.getText().toString());
                        } catch (NumberFormatException e) {
                            good = false;
                        }
//BMI CALCULATOR
                    if (good == true) {
                        double numerator = 703 * Double.parseDouble(weight.getText().toString());
                        double denominator = (Double.parseDouble(heightft.getText().toString()) * 12) + Double.parseDouble(heightin.getText().toString());
                        denominator = denominator * denominator;
                        finalcalc = (double) numerator / (double) denominator;
                    }
//BMI CONDITION CASE
                    if (finalcalc < 18.5 && finalcalc != -1) {condition.setText("Underweight"); result.setText(String.format( "%.1f", finalcalc ));}
                    else if ((finalcalc > 18.5) && (finalcalc < 20.0)) {condition.setText("Healthy"); result.setText(String.format( "%.1f", finalcalc ));}
                    else if ((finalcalc > 20.0) && (finalcalc < 40.0)) {condition.setText("Overweight"); result.setText(String.format( "%.1f", finalcalc ));}
                    else if (finalcalc > 40.0) {condition.setText("Obese"); result.setText(String.format( "%.1f", finalcalc ));}
                    else {
                            Toast.makeText(getActivity(),"Please Fill in Empty Field", Toast.LENGTH_LONG).show();
                            result.setText("0.0");
                            condition.setText("Unknown");
                        }
                    }

                });
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
//CLOSE THE DIALOG IF PRESS DONE
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }

        });


        mShowDialog = (Button) rootView.findViewById(R.id.button_addMR);
        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.add_medicalrecord, null);
                final EditText inputname = (EditText) mView.findViewById(R.id.et_MRtext);
                final Button addconfirm = (Button) mView.findViewById(R.id.button_addMR_dialog);
                final Button cancel = (Button) mView.findViewById(R.id.button_cancel_MR_dialog);//adding all the input and process
                addconfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // get catogory variable from Main Activity
                        MainActivity activity = (MainActivity) getActivity();
                        category = activity.getCategory();
                        Log.d("MEdicalRecordFragment","Category" + Integer.toString(category));

                        if (!inputname.getText().toString().isEmpty() && category == 0) {
                            Toast.makeText(getActivity(),
                                    "New Medical Record Added!", Toast.LENGTH_LONG).show();
                            allergies.add(inputname.getText().toString());
                            inputname.setText(null); // reset input text after sucessfully added
                            listAdapter.notifyDataSetChanged(); //refresh the list view data

                        } else if (!inputname.getText().toString().isEmpty() && category == 1) {
                            Toast.makeText(getActivity(),
                                    "New Medical Record Added!", Toast.LENGTH_LONG).show();
                            immune.add(inputname.getText().toString());
                            inputname.setText(null);
                            listAdapter.notifyDataSetChanged();

                        } else if (!inputname.getText().toString().isEmpty() && category == 2) {
                            Toast.makeText(getActivity(),
                                    "New Medical Record Added!", Toast.LENGTH_LONG).show();
                            med.add(inputname.getText().toString());
                            inputname.setText(null);
                            listAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(),
                                    "Please Fill in Empty Field", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity activity = (MainActivity) getActivity();
                        activity.resetCategory(); // reset input radio
                        dialog.dismiss();
                    }
                });
                dialog.show();
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

