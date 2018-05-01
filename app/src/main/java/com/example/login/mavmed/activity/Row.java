package com.example.login.mavmed.activity;

/**
 * Created by rober on 4/28/2018.
 */

public class Row {
    public static final String TABLE_NAME_SYMPTOM = "symptoms";
    public static final String TABLE_NAME_DISEASE = "diseases";
    public static final String TABLE_NAME_SYMPTOM_TO_DISEASE = "symptom_to_disease";

    public static final String COLUMN_SYMPTOM_ID = "Symptom_ID";
    public static final String COLUMN_SYMPTOM_NAME = "Symptom_name";

    public static final String COLUMN_DISEASE_ID = "Disease_ID";
    public static final String COLUMN_DISEASE_NAME = "Disease_name";
    public static final String COLUMN_ID_S = "Symptom_ID";
    public static final String COLUMN_ID_D = "Disease_ID";


    /*private int id;
    private String name;*/

    private int s_id;
    private int d_id;

    private String s_name;
    private String d_name;


    // Create table SQL query
    public static final String CREATE_TABLE_SYMPTOM =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_SYMPTOM + "("
                    + COLUMN_SYMPTOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_SYMPTOM_NAME + " VARCHAR(255)"
                    + ");";

    public static final String CREATE_TABLE_DISEASE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_DISEASE + "("
                    + COLUMN_DISEASE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_DISEASE_NAME + " VARCHAR(255)"
                    + ");";

    public static final String CREATE_TABLE_SYMPTOM_TO_DISEASE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_SYMPTOM_TO_DISEASE + "("
                    + COLUMN_ID_S + " INTEGER,"
                    + COLUMN_ID_D + " INTEGER,"
                    + "FOREIGN KEY (" + COLUMN_ID_S + ") REFERENCES " + TABLE_NAME_SYMPTOM + "(" + COLUMN_SYMPTOM_ID + "),"
                    + "FOREIGN KEY (" + COLUMN_ID_D + ") REFERENCES " + TABLE_NAME_DISEASE + "(" + COLUMN_DISEASE_ID + ")"
                    + ");";

    public Row() {
    }

    public Row(int d_id, int s_id, String d_name, String s_name) {
        this.d_id = d_id;
        this.s_id = s_id;
        this.s_name = s_name;
        this.d_name = d_name;
    }

    /*public int getId() {
        return id;
    }*/

    public int get_s_Id() {
        return s_id;
    }

    public int get_d_Id() {
        return d_id;
    }

    /*public String getName() {
        return name;
    }*/

    /*public void setName(String name) {
        this.name = name;
    }*/

    public void set_s_name(String name) {
        this.s_name = name;
    }

    public void set_d_name(String name) {
        this.d_name = name;
    }

    public String get_s_name() {
        return s_name;
    }

    public String get_d_name() {
        return d_name;
    }

    /*public void setId(int id) {
        this.id = id;
    }*/

    public void set_s_Id(int id) {
        this.s_id = id;
    }

    public void set_d_Id(int id) {
        this.d_id = id;
    }


}
