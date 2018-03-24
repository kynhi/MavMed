package com.example.login.mavmed.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.mavmed.R;
import com.example.login.mavmed.data.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class UserProfileFragment extends Fragment {
    TextView name, email, gender,birthday;
    private DatabaseReference mDatabase;
    public UserProfileFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);
        name = (TextView)rootView.findViewById(R.id.tv_user_name);
        email = (TextView)rootView.findViewById(R.id.tv_user_email);
        gender = (TextView)rootView.findViewById(R.id.tv_user_gender);
        birthday = (TextView)rootView.findViewById(R.id.tv_user_birthday);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        String userID = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userID);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData userinfo = dataSnapshot.getValue(UserData.class);
                // [START_EXCLUDE]
                name.setText(userinfo.getFullName());
                email.setText(userinfo.getEmail());
                gender.setText(userinfo.getGender());
                birthday.setText(userinfo.getBirthYear());
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText( getActivity(),"Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mDatabase.addValueEventListener(postListener);
        // Inflate the layout for this fragment
        return rootView;
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
