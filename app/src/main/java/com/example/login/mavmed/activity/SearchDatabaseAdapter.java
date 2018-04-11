package com.example.login.mavmed.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Bobby Kemp on 3/25/2018.
 * This is a database helper class used to establish the database that will be queried with the symptom-disease searches
 */

public class SearchDatabaseAdapter {

    SearchHelper helper;
    public SearchDatabaseAdapter(Context context){
        helper = new SearchHelper(context);
    }
    public long insertData(String disease, String symptom){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SearchHelper.TABLE_NAME_DISEASES, disease);
        contentValues.put(SearchHelper.TABLE_NAME_SYMPTOMS, symptom);
        long id = db.insert(SearchHelper.TABLE_NAME_DISEASES, null, contentValues); //id < 0 if failed
        return id;
    }

    static class SearchHelper extends SQLiteOpenHelper {

        private static final String DB_NAME = "symptomdiseasesearch.db";
        private static final String TABLE_NAME_SYMPTOMS = "SYMPTOMS";
        private static final String TABLE_NAME_DISEASES = "DISEASES";
        private static final String TABLE_NAME_SYMPTOMS_DISEASES = "SYMPTOMS_DISEASES";
        private static final int DB_VERSION = 1;
        private static final String S_id = "S_id";
        private static final String D_id = "D_id";
        private static final String S_name = "S_name";
        private static final String D_name = "D_name";
        private static final String sqlSymptoms = "CREATE TABLE " + TABLE_NAME_SYMPTOMS +  "(" + S_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + S_name + " VARCHAR(255));";
        private static final String sqlDiseases = "CREATE TABLE " + TABLE_NAME_DISEASES + "(" + D_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + D_name + " VARCHAR(255))";
        private static final String sqlSymptomsDiseases = "CREATE TABLE " + TABLE_NAME_SYMPTOMS_DISEASES + "(" + S_id + " INTEGER, " + D_id + " INTEGER, FOREIGN KEY(" + S_id + ") " +
                "REFERENCES SYMPTOMS(" + S_id + "), FOREIGN KEY(" + D_id + ") REFERENCES DISEASES(" + D_id + "));";
        private static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME_SYMPTOMS + ", DROP TABLE " + TABLE_NAME_DISEASES + ", DROP TABLE " + TABLE_NAME_SYMPTOMS_DISEASES;
        private Context context;
        private SQLiteDatabase db;

        public SearchHelper(Context context){
            super(context, DB_NAME, null, DB_VERSION);
            this.context = context;
            db = getWritableDatabase();
            //Toast.makeText(context, "constructor called", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            try{
                db.execSQL(sqlDiseases);
                db.execSQL(sqlSymptoms);
                db.execSQL(sqlSymptomsDiseases);
                //Toast.makeText(context, "onCreate called", Toast.LENGTH_LONG).show();
            }catch (SQLException e){
                e.printStackTrace();
                //Toast.makeText(context, "error thrown in oncreate", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try{
                //Toast.makeText(context, "onupgrade called", Toast.LENGTH_SHORT).show();
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (SQLException e){
                //Toast.makeText(context, "error thrown in upgrade", Toast.LENGTH_SHORT).show();
            }
        }

        public ArrayList<Cursor> getData(String Query){
            //get writable database
            SQLiteDatabase sqlDB = this.getWritableDatabase();
            String[] columns = new String[] { "message" };
            //an array list of cursor to save two cursors one has results from the query
            //other cursor stores error message if any errors are triggered
            ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
            MatrixCursor Cursor2= new MatrixCursor(columns);
            alc.add(null);
            alc.add(null);

            try{
                String maxQuery = Query ;
                //execute the query results will be save in Cursor c
                Cursor c = sqlDB.rawQuery(maxQuery, null);

                //add value to cursor2
                Cursor2.addRow(new Object[] { "Success" });

                alc.set(1,Cursor2);
                if (null != c && c.getCount() > 0) {

                    alc.set(0,c);
                    c.moveToFirst();

                    return alc ;
                }
                return alc;
            } catch(SQLException sqlEx){
                Log.d("printing exception", sqlEx.getMessage());
                //if any exceptions are triggered save the error message to cursor an return the arraylist
                Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
                alc.set(1,Cursor2);
                return alc;
            } catch(Exception ex){
                Log.d("printing exception", ex.getMessage());

                //if any exceptions are triggered save the error message to cursor an return the arraylist
                Cursor2.addRow(new Object[] { ""+ex.getMessage() });
                alc.set(1,Cursor2);
                return alc;
            }
        }
    }



}
