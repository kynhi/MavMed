package com.example.login.mavmed.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.login.mavmed.R;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class Reminder extends Fragment {

    public Reminder() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_reminder, container, false);

        Button pick_time = (Button) rootView.findViewById(R.id.button_remind);
        Button cancel = (Button) rootView.findViewById(R.id.alarm_disable);
        final Button calendar = (Button) rootView.findViewById(R.id.calendar);
        final EditText apptTitle = (EditText) rootView.findViewById(R.id.apptTitle);
        final EditText apptLocation = (EditText) rootView.findViewById(R.id.apptLocation);
//        Button set_reminder = (Button) rootView.findViewById(R.id.set_reminder);
//        final EditText editText = (EditText) rootView.findViewById(R.id.med_name);


        String docname = null;
        String docaddress = null;

        Bundle arguments = getArguments();
        if (arguments != null) {
            docname = arguments.getString("docname");
            docaddress = arguments.getString("docaddress");
        }

       /* Message.message(getContext(), docname);
        Message.message(getContext(), docaddress);*/

        apptTitle.setText(docname, TextView.BufferType.EDITABLE);
        apptLocation.setText(docaddress, TextView.BufferType.EDITABLE);

        pick_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timepickerappt");
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Message.message(getContext(), "Alarm cleared");

                Calendar calendar = Calendar.getInstance();
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

                /*Set up the alarm*/
                Intent intent = new Intent(getContext(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.cancel(pendingIntent);
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = apptTitle.getText().toString();
                String location = apptLocation.getText().toString();

                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                calIntent.setData(CalendarContract.Events.CONTENT_URI);
                calIntent.putExtra(CalendarContract.Events.TITLE, title);
                calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
                startActivity(calIntent);
            }
        });

        /*set_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                medname = editText.getText().toString();

            }
        });*/



        return rootView;
    }

    /*public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "time picker");
    }*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
