package com.example.login.mavmed.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.login.mavmed.R;

/**
 * Created by Nhi K luong on 4/8/2018.
 */

public class DatePickerFragment extends android.support.v4.app.Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date_picker, container, false);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.Datepick);
        final Button button = (Button) view.findViewById(R.id.set);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), datePicker.getDayOfMonth()+""+datePicker.getMonth()+""+datePicker.getYear(),Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
