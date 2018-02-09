package com.example.login.mavmed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {

    Button d_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        d_search = (Button)findViewById(R.id.d_search);

        d_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Opening new user registration activity using intent on button click.
                Intent intent = new Intent(DashboardActivity.this, DiagnosisSearch.class);
                startActivity(intent);
            }
        });
    }
}
