package com.example.login.mavmed.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import android.widget.Toast;

import com.example.login.mavmed.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


  
public class DashboardActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{
    // Creating button.
    Button logout ;
    Button diagnose;
    Button medicalRecord;
    private Toolbar mToolbar;               //Toolbar
    private FragmentDrawer drawerFragment; //Fragment
    // Creating FirebaseAuth.
    FirebaseAuth firebaseAuth ;
    // Creating FirebaseUser.
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //Set up Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Set up Fragment for Navigation Drawer
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);
        //
        diagnose = (Button)findViewById(R.id.diag_search);
        logout = (Button) findViewById(R.id.Logout);
        medicalRecord = (Button) findViewById(R.id.MedicalRecord);
        diagnose.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(DashboardActivity.this, DiagnosisSearchFragment.class);
                                            startActivity(intent);
                                        }
                                    });
        medicalRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, MedicalRecordActivity.class);
                startActivity(intent);
            }
        });
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }
    private void displayView(int position) {
        android.support.v4.app.Fragment fragment= null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                //fragment = new FriendsFragment();
                title = getString(R.string.title_friends);
                break;
            case 2:
                //fragment = new MessagesFragment();
                title = getString(R.string.title_messages);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}