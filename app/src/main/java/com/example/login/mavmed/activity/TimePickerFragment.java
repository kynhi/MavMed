package com.example.login.mavmed.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.login.mavmed.R;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public TimePickerFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_reminder, container, false);
        Button cancel = (Button) v.findViewById(R.id.alarm_disable);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(alarmManager != null) {
                    alarmManager.cancel(pendingIntent);
                    Message.message(getContext(), "Successfully cancelled alarm");
                }*/
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        Message.message(getContext(), "Time set to " + hourOfDay + ":" + minute);

        Calendar calendar = Calendar.getInstance();
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

        /*Set up the alarm*/
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

    }

    /*public void cancelAlarm(){
        if(alarmManager != null)
            alarmManager.cancel(pendingIntent);
    }*/

}
