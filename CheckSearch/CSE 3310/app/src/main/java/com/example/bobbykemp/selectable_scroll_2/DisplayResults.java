package com.example.bobbykemp.selectable_scroll_2;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DisplayResults extends AppCompatActivity {

    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results);

        Resources res = getResources();
        String[] symptoms = res.getStringArray(R.array.symptoms);

        Bundle bundle = getIntent().getExtras();
        boolean results[] = bundle.getBooleanArray("query");

        for(int i = 0; i < 7; i++){
            if(results[i] == true)
                message = message + symptoms[i] + "\n";
        }

        TextView view = (TextView) findViewById(R.id.textView);
        view.setText(message);
    }

}
