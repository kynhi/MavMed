package com.example.login.mavmed.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.login.mavmed.data.LoginContract.*;
/**
 * Created by Nhi K luong on 2/2/2018.
 */

public class LoginDatabaseHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LOGIN CREDENTIALS";
    public LoginDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
               final String CREATE_LOGIN_TABLE = "CREATE TABLE IF NOT EXISTS " + LoginEntry.TABLE_NAME +
                                "(" +LoginEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                                LoginEntry.COLUMN_USERNAME+ " VARCHAR, " +
                                LoginEntry.COLUMN_PASSWORD + "VARCHAR, " +
                                LoginEntry.COLUMN_EMAIL + " VARCHAR " +
                "); " ;
                sqLiteDatabase.execSQL(CREATE_LOGIN_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDb, int i, int i1) {
        sqLiteDb.execSQL("DROP TABLE IF EXISTS " + LoginEntry.TABLE_NAME);
        onCreate(sqLiteDb);
    }
}
