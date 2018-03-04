package com.example.login.mavmed.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.mavmed.R;
import com.example.login.mavmed.data.LoginDatabaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends Activity {

    Button login,register,skip;
    EditText email,password;


    TextView attempts_count;
    int counter = 3;
    private SQLiteDatabase myDb; //my Database

    String EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase LoginDatabase;
    LoginDatabaseHelper LoginDbHelper;
    Cursor cursor;
    String TempPassword = "NOT_FOUND" ;
    // Creating progress dialog.
    ProgressDialog progressDialog;

    // Creating FirebaseAuth object.
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button)findViewById(R.id.Login);
        email = (EditText)findViewById(R.id.Email);
        password = (EditText)findViewById(R.id.Password);

        skip = (Button)findViewById(R.id.Override);
        register = (Button)findViewById(R.id.Register);
        attempts_count = (TextView)findViewById(R.id.Counter);
        attempts_count.setText(Integer.toString(counter));

        LoginDbHelper = new LoginDatabaseHelper(this);
        progressDialog =  new ProgressDialog(LoginActivity.this);

        // Assign FirebaseAuth instance to FirebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance();

        //Adding click listener to log in button.
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EmailHolder = email.getText().toString();
                PasswordHolder = password.getText().toString();
                // Checking EditText is empty or no using TextUtils.
                if (TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)) {

                    EditTextEmptyHolder = false;

                } else {

                    EditTextEmptyHolder = true;
                }

                if (EditTextEmptyHolder) {

/*
                    LoginDatabase = LoginDbHelper.getWritableDatabase();

                    // Adding search username query to cursor.
                    //cursor = LoginDatabase.query(LoginContract.LoginEntry.TABLE_NAME, null, " " +
                    //        LoginContract.LoginEntry.COLUMN_USERNAME + "=?", new String[]{emailHolder}, null, null, null);
                    while (cursor.moveToNext()) {

                        if (cursor.isFirst()) {

                            cursor.moveToFirst();

                            // Storing Password associated with entered email.
                            TempPassword = cursor.getString(cursor.getColumnIndex(LoginContract.LoginEntry.COLUMN_PASSWORD));

                            // Closing cursor.
                            cursor.close();
                        }
                    }
                    if(TempPassword.equalsIgnoreCase(passwordHolder))
                    {

                        Toast.makeText(LoginActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();

                        // Going to Dashboard activity after login success message.
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }

                    else {
                        Toast.makeText(LoginActivity.this,"UserName or Password is Wrong, Please Try Again.",Toast.LENGTH_LONG).show();
                        attempts_count.setVisibility(View.VISIBLE);
                        attempts_count.setBackgroundColor(Color.RED);
                        counter--;
                        attempts_count.setText(Integer.toString(counter));

                        if (counter == 0) {
                            login.setEnabled(false);
                        }
                    }
                    TempPassword = "NOT_FOUND" ;*/
                    LoginFunction();
                } else {
                    Toast.makeText(LoginActivity.this, "Please Enter UserName or Password.", Toast.LENGTH_LONG).show();
                }
            }
            }
        );

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast.makeText(LoginActivity.this,"Login Skipped",Toast.LENGTH_LONG).show();

                // Going to Dashboard activity after login success message.
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                // Opening new user registration activity using intent on button click.
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        }
        );
    }
    // Creating login function.
    public void LoginFunction(){

        // Setting up message in progressDialog.
        progressDialog.setMessage("Please Wait");

        // Showing progressDialog.
        progressDialog.show();

        // Calling  signInWithEmailAndPassword function with firebase object and passing EmailHolder and PasswordHolder inside it.
        firebaseAuth.signInWithEmailAndPassword(EmailHolder, PasswordHolder)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If task done Successful.
                        if(task.isSuccessful()) {

                            // Hiding the progress dialog.
                            progressDialog.dismiss();
                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            FirebaseUser user = auth.getCurrentUser();
                            if (user.isEmailVerified() == true){
                                // Closing the current Login Activity.
                                finish();
                                // Opening the UserProfileActivity.
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                             } else{
                                Toast.makeText(LoginActivity.this, "Your Email is not verified please check your email for verification email from us", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {

                            // Hiding the progress dialog.
                            progressDialog.dismiss();

                            // Showing toast message when email or password not found in Firebase Online database.
                            Toast.makeText(LoginActivity.this, "Email or Password Not found, Please Try Again", Toast.LENGTH_LONG).show();
                            attempts_count.setVisibility(View.VISIBLE);
                            attempts_count.setBackgroundColor(Color.RED);
                            counter--;
                            attempts_count.setText(Integer.toString(counter));
                            if (counter == 0){
                                login.setEnabled(false);
                                Toast.makeText(LoginActivity.this, "You have no attempt left", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

    }
}