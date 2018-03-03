package com.example.bobbykemp.selectable_scroll_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    boolean[] result = new boolean[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button send = (Button) findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DisplayResults.class);
                intent.putExtra("query", result);
                startActivity(intent);
            }
        });
    }

    public void onCheckboxClicked(View view) {
        boolean is_checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.checkbox_cough:
                if (is_checked)
                    //result = result + " cough";
                    result[0] = true;
                else
                    result[0] = false;
                break;
            case R.id.checkbox_diarrhea:
                if (is_checked)
                    //result = result + " diarrhea";
                    result[1] = true;
                else
                    result[1] = false;
                break;
            case R.id.checkbox_flumps:
                if (is_checked)
                    //result = result + " torn-flumps";
                    result[2] = true;
                else
                    result[2] = false;
                break;
            case R.id.checkbox_gloop:
                if (is_checked)
                    //result = result + " pulled-gloop";
                    result[3] = true;
                else
                    result[3] = false;
                break;
            case R.id.checkbox_headache:
                if (is_checked)
                    //result = result + " headache";
                    result[4] = true;
                else
                    result[4] = false;
                break;
            case R.id.checkbox_heartburn:
                if (is_checked)
                    //result = result + " heartburn";
                    result[5] = true;
                else
                    result[5] = false;
                break;
            case R.id.checkbox_indigestion:
                if (is_checked)
                    //result = result + " indigestion";
                    result[6] = true;
                else
                    result[6] = false;
                break;
        }
    }


}


