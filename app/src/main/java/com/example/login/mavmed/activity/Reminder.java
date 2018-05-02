package com.example.login.mavmed.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.login.mavmed.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

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
        final Button calendar_button = (Button) rootView.findViewById(R.id.calendar);
        final EditText apptTitle = (EditText) rootView.findViewById(R.id.apptTitle);
        final EditText apptLocation = (EditText) rootView.findViewById(R.id.apptLocation);
        final EditText apptDate = (EditText) rootView.findViewById(R.id.apptDate);
//        final EditText apptTime = (EditText) rootView.findViewById(R.id.apptTime);
//        Button set_reminder = (Button) rootView.findViewById(R.id.set_reminder);

        final Calendar calendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                apptDate.setText(sdf.format(calendar.getTime()));
            }
        };

        apptDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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

        calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = apptTitle.getText().toString();
                String location = apptLocation.getText().toString();

                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                calIntent.setData(CalendarContract.Events.CONTENT_URI);
                calIntent.putExtra(CalendarContract.Events.TITLE, title);
                calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
//                Message.message(getContext(), String.valueOf(calendar.get(Calendar.YEAR)) + "/" + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "/" + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

                GregorianCalendar gcal = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        gcal.getTimeInMillis());
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                        gcal.getTimeInMillis());

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
