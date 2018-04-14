package com.example.login.mavmed.activity;

/**
 * Created by Francis on 04/05/2018.
 */
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.mavmed.R;

public class Email extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        Button Compose = (Button) findViewById(R.id.sendbttn);
        Compose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail();
            }
        });
    }

    protected void sendEmail() {
        Log.i("Send email", "");
        EditText recipient = (EditText)findViewById(R.id.editText);
        EditText subject = (EditText)findViewById(R.id.editText2);
        EditText body = (EditText)findViewById(R.id.editText3);
        String[] TO = {recipient.getText().toString()};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, body.getText().toString());

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email"));
            Log.i("Finished sending email", "Done!");
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Email.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
