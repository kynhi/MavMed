package com.example.login.mavmed;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.mavmed.data.LoginContract;
import com.example.login.mavmed.data.LoginDatabaseHelper;
public class LoginActivity extends Activity {
    Button login,register;
    EditText username,password;

    TextView attempts_count;
    int counter = 3;
    private SQLiteDatabase myDb; //my Database

    String usernameHolder, passwordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase LoginDatabase;
    LoginDatabaseHelper LoginDbHelper;
    Cursor cursor;
    String TempPassword = "NOT_FOUND" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button)findViewById(R.id.Login);
        username = (EditText)findViewById(R.id.Username);
        password = (EditText)findViewById(R.id.Password);

        register = (Button)findViewById(R.id.Register);
        attempts_count = (TextView)findViewById(R.id.Counter);
        attempts_count.setVisibility(View.GONE);

        LoginDbHelper = new LoginDatabaseHelper(this);


        //Adding click listener to log in button.
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usernameHolder = username.getText().toString();
                passwordHolder = password.getText().toString();
                // Checking EditText is empty or no using TextUtils.
                if(TextUtils.isEmpty(usernameHolder) || TextUtils.isEmpty(passwordHolder)){

                    EditTextEmptyHolder = false ;

                }
                else {

                    EditTextEmptyHolder = true ;
                }

                if (EditTextEmptyHolder) {

                    LoginDatabase = LoginDbHelper.getWritableDatabase();

                    // Adding search username query to cursor.
                    cursor = LoginDatabase.query(LoginContract.LoginEntry.TABLE_NAME, null, " " +
                            LoginContract.LoginEntry.COLUMN_USERNAME + "=?", new String[]{usernameHolder}, null, null, null);
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
                    TempPassword = "NOT_FOUND" ;
                }else{
                    Toast.makeText(LoginActivity.this,"Please Enter UserName or Password.",Toast.LENGTH_LONG).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Opening new user registration activity using intent on button click.
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}