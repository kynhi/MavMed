package com.example.login.mavmed.activity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.login.mavmed.R;

/**
 * Created by Bobby Kemp on 3/24/2018.
 */

public class DisplayDiagSearch extends Fragment {

    SearchDatabaseAdapter searchDatabaseHelper;

    public DisplayDiagSearch(){
    //empty constructor necessary
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchDatabaseHelper = new SearchDatabaseAdapter(this.getContext());

        //SQLiteDatabase sqLiteDatabase = searchDatabaseHelper.getWritableDatabase();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_diagnosissearch, container, false);

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
