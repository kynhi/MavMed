package com.example.login.mavmed;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class MedicalRecordActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    int category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_record);

        listView = (ExpandableListView)findViewById(R.id.lvExp);
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


        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);

        final Button mShowDialog = (Button) findViewById(R.id.button_addMR);
        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MedicalRecordActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.add_medicalrecord, null);
                final EditText inputname = (EditText) mView.findViewById(R.id.et_MRtext);
                final Button addconfirm = (Button) mView.findViewById(R.id.button_addMR_dialog);
                final Button cancel = (Button) mView.findViewById(R.id.button_cancel_MR_dialog);
//adding all the input and process
                addconfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!inputname.getText().toString().isEmpty() && category == 0) {
                            Toast.makeText(MedicalRecordActivity.this,
                                    "New Medical Record Added!", Toast.LENGTH_LONG).show();
                            allergies.add(inputname.getText().toString());
                        } else if (!inputname.getText().toString().isEmpty() && category == 1) {
                            Toast.makeText(MedicalRecordActivity.this,
                                    "New Medical Record Added!", Toast.LENGTH_LONG).show();
                            immune.add(inputname.getText().toString());
                        } else if (!inputname.getText().toString().isEmpty() && category == 2) {
                            Toast.makeText(MedicalRecordActivity.this,
                                    "New Medical Record Added!", Toast.LENGTH_LONG).show();
                            med.add(inputname.getText().toString());
                        } else {
                            Toast.makeText(MedicalRecordActivity.this,
                                    "Please Fill in Empty Field", Toast.LENGTH_LONG).show();

                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

    }
    public void checkButton(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_allergies:
                if (checked)
                    category = 0;
                    break;
            case R.id.radio_immune:
                if (checked)
                    category = 1;
                break;
            case R.id.radio_med:
                if (checked)
                    category = 2;
                break;
        }
    }
}
