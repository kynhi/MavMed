package com.example.login.mavmed.data;

import android.provider.BaseColumns;

/**
 * Created by Nhi K luong on 2/2/2018.
 */

public class LoginContract {
    public static final class LoginEntry implements BaseColumns {
        public static final String TABLE_NAME = "Login";
        public static final String COLUMN_ID = "Id";
        public static final String COLUMN_USERNAME = "Username";
        public static final String COLUMN_PASSWORD = "Password";
        public static final String COLUMN_EMAIL = "Email";
    }
}
