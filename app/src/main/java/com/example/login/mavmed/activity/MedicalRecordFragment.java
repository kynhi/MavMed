package com.example.login.mavmed.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.login.mavmed.R;
import com.example.login.mavmed.adapter.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class MedicalRecordFragment extends Fragment {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    int category;
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

        //---------------
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
        //---------------

        listAdapter = new ExpandableListAdapter(getContext(),listDataHeader,listHash);
        listView.setAdapter(listAdapter);

        final Button mShowDialog = (Button) rootView.findViewById(R.id.button_addMR);
        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.add_medicalrecord, null);
                final EditText inputname = (EditText) mView.findViewById(R.id.et_MRtext);
                final Button addconfirm = (Button) mView.findViewById(R.id.button_addMR_dialog);
                final Button cancel = (Button) mView.findViewById(R.id.button_cancel_MR_dialog);
//adding all the input and process
                addconfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity activity = (MainActivity) getActivity();
                        category= activity.getCategory();
                        if (!inputname.getText().toString().isEmpty() && category == 0) {
                            Toast.makeText(getActivity(),
                                    "New Medical Record Added!", Toast.LENGTH_LONG).show();
                            allergies.add(inputname.getText().toString());
                        } else if (!inputname.getText().toString().isEmpty() && category == 1) {
                            Toast.makeText(getActivity(),
                                    "New Medical Record Added!", Toast.LENGTH_LONG).show();
                            immune.add(inputname.getText().toString());
                        } else if (!inputname.getText().toString().isEmpty() && category == 2) {
                            Toast.makeText(getActivity(),
                                    "New Medical Record Added!", Toast.LENGTH_LONG).show();
                            med.add(inputname.getText().toString());
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
