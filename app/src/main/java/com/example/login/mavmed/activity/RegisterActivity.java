package com.example.login.mavmed.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.login.mavmed.R;
import com.example.login.mavmed.data.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText Email, Password, Name ,Birthday;
    Button Register;
    String NameHolder, EmailHolder, PasswordHolder, BirthdayHolder,GenderHolder;
    Boolean EditTextEmptyHolder;
    RadioGroup Gender;
    final String TAG = "MavMed";
    ProgressDialog progressDialog;
    // Reference to Database
    private DatabaseReference mDatabase;
    UserData newuser;
    // Creating FirebaseAuth object.
    FirebaseAuth firebaseAuth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Register = (Button)findViewById(R.id.buttonRegister);

        //radio group button for gender

        Name = (EditText) findViewById(R.id.editFullname);
        Email = (EditText)findViewById(R.id.editEmail);
        Password = (EditText)findViewById(R.id.editPassword);
        Birthday = (EditText) findViewById(R.id.editBirthday);
        Gender = (RadioGroup) findViewById(R.id.radioGroup);
        //LoginDbHelper = new LoginDatabaseHelper(this);
        // Creating object instance.
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        // Adding click listener to register button.
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Checking EditText is empty or Not.
                CheckEditTextStatus();
                // Method to check Email is already exists or not.
                InsertDataIntoFirebase();
                // Empty EditText After done inserting process.
                //EmptyEditTextAfterDataInsert();
            }
        });
    }

    public void InsertDataIntoFirebase(){

        // If editText is not empty then this block will executed.
        if(EditTextEmptyHolder == true)
        {
            UserRegistrationFunction();
        }
        // This block will execute if any of the registration EditText is empty.
        else {
            // Printing toast message if any of EditText is empty.
            Toast.makeText(RegisterActivity.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();
        }
    }

    // Empty edittext after done inserting process method.
/*    public void EmptyEditTextAfterDataInsert(){

        Email.getText().clear();
        Birthday.getText().clear();
        Password.getText().clear();
        Name.getText().clear();

    }*/
    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus(){

        // Getting value from All EditText and storing into String Variables.
        NameHolder = Name.getText().toString() ;
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();
        BirthdayHolder = Birthday.getText().toString();

        int selected=Gender.getCheckedRadioButtonId();
        RadioButton genderChose=(RadioButton) findViewById(selected);
        GenderHolder = genderChose.getText().toString();

        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)||
                TextUtils.isEmpty(BirthdayHolder)|| TextUtils.isEmpty(NameHolder)){

            EditTextEmptyHolder = false ;
        }
        else {

            EditTextEmptyHolder = true ;
        }
    }

    // Creating UserRegistrationFunction
    public void UserRegistrationFunction() {

        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Registering Your Data on Server");
        progressDialog.show();
        // Creating createUserWithEmailAndPassword method and pass email and password inside it.
        firebaseAuth.createUserWithEmailAndPassword(EmailHolder, PasswordHolder).
                addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // Checking if user is registered successfully.
                        if (task.isSuccessful()) {
                            // Printing toast message after done inserting.
                            Toast.makeText(RegisterActivity.this,"User Registered Successfully, Check Email for Confirmation", Toast.LENGTH_LONG).show();
                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            FirebaseUser user = auth.getCurrentUser();
                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "Email sent.");
                                            }
                                        }
                                    });
                            // Create UserData object to send to database
                            newuser = new UserData(NameHolder,BirthdayHolder,EmailHolder,GenderHolder);

                            //point to user child on database
                            String userID = user.getUid();
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("users").child(userID).setValue(newuser);
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {

                            // If something goes wrong.
//                            Toast.makeText(RegisterActivity.this, "You already register with the email please try sign in with Email and Password.", Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                            startActivity(intent);
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                Password.setError("Your Password must be at least 8 characters long");
                                Password.requestFocus();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                Email.setError("Please Enter a valid Email address");
                                Email.requestFocus();
                            } catch(FirebaseAuthUserCollisionException e) {
                                Email.setError("This user has already existed, please try login");
                                Email.requestFocus();
                            } catch(Exception e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                    }
                });
    }

}