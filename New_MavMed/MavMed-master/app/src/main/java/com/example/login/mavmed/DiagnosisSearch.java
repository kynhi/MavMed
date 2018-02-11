package com.example.login.mavmed;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.mavmed.data.LoginContract;
import com.example.login.mavmed.data.LoginDatabaseHelper;

public class DiagnosisSearch extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.MavMed.MESSAGE";
    Button diagnose;

    EditText search;
    Boolean EditTextEmptyHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_search);

        Intent intent = new Intent(this, DisplaySearchResults.class);
        diagnose = (Button)findViewById(R.id.Diagnose);

        diagnose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Checking EditText is empty or no using TextUtils.

                if (EditTextEmptyHolder) {

                }
            }
        });
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplaySearchResults.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
