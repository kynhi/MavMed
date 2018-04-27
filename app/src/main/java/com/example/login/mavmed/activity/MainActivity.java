package com.example.login.mavmed.activity;

/**
 * Created by Nhi K luong on 3/4/2018.
 */

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.login.mavmed.R;
import com.example.login.mavmed.data.ImageUploadInfo;
import com.example.login.mavmed.data.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static android.content.ContentValues.TAG;
import static com.google.android.gms.internal.zzbfq.NULL;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();

    int category  = -1;

    // Creating FirebaseAuth object.
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ImageView userIcon;

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        String userID = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("image upload").child("imageURL");

        //setContentView(R.layout.fragment_navigation_drawer);
        RelativeLayout relayout =  findViewById(R.id.nav_header_container);
        userIcon = (ImageView) relayout.findViewById(R.id.userIC);

        ValueEventListener postListenter = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String url = (String) dataSnapshot.getValue();
                loadImagefromUrl(url);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(MainActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        databaseReference.addValueEventListener(postListenter);
        // display the first navigation drawer view on app launch
        displayView(0);


    }
    private void loadImagefromUrl(String url){
            Picasso.get().load(url).into(userIcon);
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

        if(id == R.id.action_search){
            Fragment fragment = null;

            fragment = new DiagnosisSearchFragment();
            String title = getString(R.string.title_diagnosissearch);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title

            getSupportActionBar().setTitle(title);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new DiagnosisSearchFragment();
                title = getString(R.string.title_diagnosissearch);
                break;
            case 2:
                fragment = new MedicalRecordFragment();
                title = getString(R.string.title_medical_record);
                break;
            case 3:
                fragment = new UserProfileFragment();
                title= getString(R.string.title_user_profile);
                break;
            case 4:
                fragment = new Reminder();
                title = getString(R.string.title_reminder);
                break;
            case 5:
                fragment = new MapsFragment();
                title = getString(R.string.title_make_appointment);
                break;
            case 6:
                // Destroying login season.
                firebaseAuth.signOut();

                // Finishing current Main activity.
                finish();

                // Redirect to Login Activity after click on logout button.
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

                // Showing toast message on logout.
                Toast.makeText(MainActivity.this, "Logged Out Successfully.", Toast.LENGTH_LONG).show();
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
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

    public int getCategory() {
        return category;
    }
    public void resetCategory() {category = -1;}

}