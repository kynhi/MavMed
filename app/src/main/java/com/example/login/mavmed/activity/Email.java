package com.example.login.mavmed.activity;

/**
 * Created by Francis on 04/05/2018.
 */
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.mavmed.R;

public class Email extends Fragment {

    public Email() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_email, container, false);

        super.onCreate(savedInstanceState);

        Button Compose = (Button) rootView.findViewById(R.id.sendbttn);
        Compose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail();
            }
        });
        return rootView;
    }

    protected void sendEmail() {
        View mView = getLayoutInflater().inflate(R.layout.activity_email, null);
        Log.i("Send email", "");

        EditText recipient = (EditText) mView.findViewById(R.id.editText);
        EditText subject = (EditText) mView.findViewById(R.id.editText2);
        EditText body = (EditText) mView.findViewById(R.id.editText3);
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
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
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
