package com.example.login.mavmed;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.example.login.mavmed.data.LoginContract.*;
import com.example.login.mavmed.data.LoginDatabaseHelper;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    EditText Email, Password, Name ;
    Button Register;
    String NameHolder, EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    final String TAG = "MavMed";
    //SQLiteDatabase LoginDatabase;
    //String SQLiteDataBaseQueryHolder ;
    //LoginDatabaseHelper LoginDbHelper;
    //Cursor cursor;
    //String F_Result = "Not_Found";
    // Creating Progress dialog.
    ProgressDialog progressDialog;

    // Creating FirebaseAuth object.
    FirebaseAuth firebaseAuth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Register = (Button)findViewById(R.id.buttonRegister);

        Email = (EditText)findViewById(R.id.editEmail);
        Password = (EditText)findViewById(R.id.editPassword);
        //Name = (EditText)findViewById(R.id.editName);

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
                EmptyEditTextAfterDataInsert();


            }
        });
    }
/*    // SQLite database build method.
    public void LoginDataBaseBuild(){
        LoginDatabase = openOrCreateDatabase(LoginDatabaseHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
    // SQLite table build method.
    public void SQLiteTableBuild() {

        LoginDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + LoginEntry.TABLE_NAME +
                "(" +LoginEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    LoginEntry.COLUMN_USERNAME + " VARCHAR, " +
                    LoginEntry.COLUMN_PASSWORD + " VARCHAR, " +
                    LoginEntry.COLUMN_EMAIL + " VARCHAR);" );
    }*/
    // Insert data into SQLite database method.
    public void InsertDataIntoFirebase(){

        // If editText is not empty then this block will executed.
        if(EditTextEmptyHolder == true)
        {

            // SQLite query to insert data into table.
            //SQLiteDataBaseQueryHolder = "INSERT INTO "+LoginEntry.TABLE_NAME+" " +
             //       "(username,email,password) VALUES('"+NameHolder+"', '"+EmailHolder+"', '"+PasswordHolder+"');";

            // Executing query.
            //LoginDatabase.execSQL(SQLiteDataBaseQueryHolder);

            // Closing SQLite database object.
            //LoginDatabase.close();

            UserRegistrationFunction();

        }
        // This block will execute if any of the registration EditText is empty.
        else {

            // Printing toast message if any of EditText is empty.
            Toast.makeText(RegisterActivity.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();

        }

    }

    // Empty edittext after done inserting process method.
    public void EmptyEditTextAfterDataInsert(){

        Email.getText().clear();

        Password.getText().clear();

    }
    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus(){

        // Getting value from All EditText and storing into String Variables.
        //NameHolder = Name.getText().toString() ;
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();

        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){

            EditTextEmptyHolder = false ;
        }
        else {

            EditTextEmptyHolder = true ;
        }
    }
/*    // Checking Email is already exists or not.
    public void CheckingEmailAlreadyExistsOrNot(){

        // Opening SQLite database write permission.
        LoginDatabase = LoginDbHelper.getWritableDatabase();

        // Adding search email query to cursor.
        cursor = LoginDatabase.query(LoginEntry.TABLE_NAME, null, " " +
                                        LoginEntry.COLUMN_EMAIL + "=?", new String[]{EmailHolder},
                                        null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                // If Email is already exists then Result variable value set as Email Found.
                F_Result = "Email Found";

                // Closing cursor.
                cursor.close();
            }
        }

        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult();

    }*/


/*    // Checking result
    public void CheckFinalResult(){
        // Checking whether email is already exists or not.
        if(F_Result.equalsIgnoreCase("Email Found"))
        {

            // If email is exists then toast msg will display.
            Toast.makeText(RegisterActivity.this,"Email Already Exists",Toast.LENGTH_LONG).show();

        }
        else {

            // If email already dose n't exists then user registration details will entered to SQLite database.
            InsertDataIntoSQLiteDatabase();
        }
        F_Result = "Not_Found" ;
    }*/
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
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {

                            // If something goes wrong.
                            Toast.makeText(RegisterActivity.this, "You already register with the email please try sign in with Email and Password.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                    }
                });
    }
}
