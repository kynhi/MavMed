package com.example.login.mavmed.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rober on 4/28/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // Row Version
    private static final int DATABASE_VERSION = 4;

    // Row Name
    private static final String DATABASE_NAME = "mavmed_db";

    Context this_context;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this_context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create symptoms table
        db.execSQL(Row.CREATE_TABLE_SYMPTOM);
        db.execSQL(Row.CREATE_TABLE_DISEASE);
        db.execSQL(Row.CREATE_TABLE_SYMPTOM_TO_DISEASE);
        Message.message(this_context, "created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Row.TABLE_NAME_SYMPTOM_TO_DISEASE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + Row.TABLE_NAME_SYMPTOM + ";");
        db.execSQL("DROP TABLE IF EXISTS " + Row.TABLE_NAME_DISEASE+ ";");

        Message.message(this_context, "upgraded");

        // Create tables again
        onCreate(db);
    }

    public long insertSymptom(String name) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Row.COLUMN_SYMPTOM_NAME, name);

        // insert row
        long id = db.insert(Row.TABLE_NAME_SYMPTOM, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertDisease(String name) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Row.COLUMN_DISEASE_NAME, name);

        // insert row
        long id = db.insert(Row.TABLE_NAME_DISEASE, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertSymptomToDisease(int symptom, int disease) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Row.COLUMN_SYMPTOM_ID, symptom);
        values.put(Row.COLUMN_DISEASE_ID, disease);

        // insert row
        long id = db.insert(Row.TABLE_NAME_SYMPTOM_TO_DISEASE, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public List<Row> getAllSymptoms() {
        List<Row> symptoms = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + Row.TABLE_NAME_SYMPTOM;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Row symptom = new Row();
                symptom.set_s_Id(cursor.getInt(cursor.getColumnIndex(Row.COLUMN_SYMPTOM_ID)));
                symptom.set_s_name(cursor.getString(cursor.getColumnIndex(Row.COLUMN_SYMPTOM_NAME)));

                symptoms.add(symptom);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return symptoms;
    }

    public List<Row> getAllDiseases() {
        List<Row> symptoms = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + Row.TABLE_NAME_DISEASE + ";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Row symptom = new Row();
                symptom.set_d_Id(cursor.getInt(cursor.getColumnIndex(Row.COLUMN_DISEASE_ID)));
                symptom.set_d_name(cursor.getString(cursor.getColumnIndex(Row.COLUMN_DISEASE_NAME)));

                symptoms.add(symptom);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return symptoms;
    }

    /* Gets all diseases related to symptom "query" */
    public List<Row> getDiseases(String query) {
        List<Row> diseases = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT " + Row.TABLE_NAME_DISEASE + "." + Row.COLUMN_DISEASE_NAME + " " +
                "FROM " + Row.TABLE_NAME_SYMPTOM_TO_DISEASE + " " +
                "INNER JOIN " + Row.TABLE_NAME_DISEASE + " " +
                "ON " + Row.TABLE_NAME_SYMPTOM_TO_DISEASE + "." + Row.COLUMN_DISEASE_ID + " = " + Row.TABLE_NAME_DISEASE + "." + Row.COLUMN_DISEASE_ID + " " +
                "INNER JOIN " + Row.TABLE_NAME_SYMPTOM + " " +
                "ON " + Row.TABLE_NAME_SYMPTOM_TO_DISEASE + "." + Row.COLUMN_SYMPTOM_ID + " = " + Row.TABLE_NAME_SYMPTOM + "." + Row.COLUMN_SYMPTOM_ID + " " +
                "WHERE " + Row.TABLE_NAME_SYMPTOM + "." + Row.COLUMN_SYMPTOM_NAME + " LIKE \"%" + query + "%\";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Row symptom = new Row();
                symptom.set_d_name(cursor.getString(cursor.getColumnIndex(Row.COLUMN_DISEASE_NAME)));

                diseases.add(symptom);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return diseases;
    }

    /* Gets all diseases related to MULTIPLE symptoms in "query" */
    public List<Row> getDiseasesMulti(ArrayList<String> query) {
        List<Row> diseases = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        for(String s : query) {
            builder.append("\"" + s + "\", ");
        }
        String str = builder.toString();
        str = str.substring(0, str.length()-2);

        // Select All Query
        String selectQuery = "SELECT " + Row.TABLE_NAME_DISEASE + "." + Row.COLUMN_DISEASE_NAME + " " +
                "FROM " + Row.TABLE_NAME_SYMPTOM_TO_DISEASE + " " +
                "INNER JOIN " + Row.TABLE_NAME_DISEASE + " " +
                "ON " + Row.TABLE_NAME_SYMPTOM_TO_DISEASE + "." + Row.COLUMN_DISEASE_ID + " = " + Row.TABLE_NAME_DISEASE + "." + Row.COLUMN_DISEASE_ID + " " +
                "INNER JOIN " + Row.TABLE_NAME_SYMPTOM + " " +
                "ON " + Row.TABLE_NAME_SYMPTOM_TO_DISEASE + "." + Row.COLUMN_SYMPTOM_ID + " = " + Row.TABLE_NAME_SYMPTOM + "." + Row.COLUMN_SYMPTOM_ID + " " +
                "WHERE " + Row.TABLE_NAME_SYMPTOM + "." + Row.COLUMN_SYMPTOM_NAME + " IN (" + str + ")" +
                "GROUP BY " + Row.TABLE_NAME_DISEASE + "." + Row.COLUMN_DISEASE_NAME + " " +
                "HAVING COUNT(*) = " + query.size() + ";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Row row = new Row();
                row.set_d_name(cursor.getString(cursor.getColumnIndex(Row.COLUMN_DISEASE_NAME)));

                diseases.add(row);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return diseases;
    }

    /*Gets every symptom associated with the disease in query*/
    public List<Row> getSymptomsByDisease(String query) {
        List<Row> symptoms = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT " + Row.TABLE_NAME_SYMPTOM + "." + Row.COLUMN_SYMPTOM_NAME + " " +
                "FROM " + Row.TABLE_NAME_SYMPTOM_TO_DISEASE + " " +
                "INNER JOIN " + Row.TABLE_NAME_DISEASE + " " +
                "ON " + Row.TABLE_NAME_SYMPTOM_TO_DISEASE + "." + Row.COLUMN_DISEASE_ID + " = " + Row.TABLE_NAME_DISEASE + "." + Row.COLUMN_DISEASE_ID + " " +
                "INNER JOIN " + Row.TABLE_NAME_SYMPTOM + " " +
                "ON " + Row.TABLE_NAME_SYMPTOM_TO_DISEASE + "." + Row.COLUMN_SYMPTOM_ID + " = " + Row.TABLE_NAME_SYMPTOM + "." + Row.COLUMN_SYMPTOM_ID + " " +
                "WHERE " + Row.TABLE_NAME_DISEASE + "." + Row.COLUMN_DISEASE_NAME + " LIKE \"%" + query + "%\";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Row row = new Row();
                row.set_s_name(cursor.getString(cursor.getColumnIndex(Row.COLUMN_SYMPTOM_NAME)));

                symptoms.add(row);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return symptoms;
    }
}
