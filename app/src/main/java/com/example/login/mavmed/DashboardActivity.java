package com.example.login.mavmed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


  
public class DashboardActivity extends AppCompatActivity {
    // Creating button.
    Button logout ;
    Button diagnose;
    // Creating FirebaseAuth.
    FirebaseAuth firebaseAuth ;
    // Creating FirebaseUser.
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        diagnose = (Button)findViewById(R.id.diag_search);

        diagnose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, DiagnosisSearch.class);
                startActivity(intent);

        logout = (Button) findViewById(R.id.Logout);

        firebaseAuth =  FirebaseAuth.getInstance();
        // On activity start check whether there is user previously logged in or not.
        if(firebaseAuth.getCurrentUser() == null){

            // Finishing current Profile activity.
            finish();

            // If user already not log in then Redirect to LoginActivity .
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

            // Showing toast message.
            Toast.makeText(this, "Please Log in to continue", Toast.LENGTH_LONG).show();

        }
        // Adding firebaseAuth current user info into firebaseUser object.
        firebaseUser = firebaseAuth.getCurrentUser();
        // Adding click listener on logout button.
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Destroying login season.
                firebaseAuth.signOut();

                // Finishing current User Profile activity.
                finish();

                // Redirect to Login Activity after click on logout button.
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);

                // Showing toast message on logout.
                Toast.makeText(DashboardActivity.this, "Logged Out Successfully.", Toast.LENGTH_LONG).show();
            }
        });
    }


}
