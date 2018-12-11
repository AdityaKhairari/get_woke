package com.example.adityakhairari.alarm;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Spinner;

import android.widget.Toast;
import java.util.Calendar;
import android.text.format.DateFormat;


public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {

    Context context;
    AlarmManager alarm_manager;
    TextView update_text;
    PendingIntent pending_intent;
    int alarmSound;
    boolean setbool;
    int hr;
    int min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.context = this;


        // initialize alarm_manager
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //initialize alarm on/off indicator
        update_text = (TextView) findViewById(R.id.update_text);

        updateIndicator("Alarm off!");


        // create intent for Alarm Receiver
        final Intent intentOff = new Intent(this.context, Alarm_Receiver.class);



        // create spinner (dropdown) in the main UI
        Spinner spinner = (Spinner) findViewById(R.id.ringtone_picker);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ringtones, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // Setting onclick listener to onItemSelected method
        spinner.setOnItemSelectedListener(this);




        //initialize alarm set button
        Button setAlarm = (Button) findViewById(R.id.setAlarm);
        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setbool) {
                    Toast.makeText(context, "Please disable the alarm first", Toast.LENGTH_SHORT).show();
                } else {
                    DialogFragment timePicker = new Timepickerfragment();
                    timePicker.show(getSupportFragmentManager(), "time picker");
                }
            }
        });



        // initialize the disable button
        Button alarm_off = (Button) findViewById(R.id.snooze);
        alarm_off.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (setbool) {
                    // changing alarm indicator to off
                    updateIndicator("Alarm off!");

                    // cancel the pending intent for alarm
                    alarm_manager.cancel(pending_intent);

                    // put extra string into my_intent
                    // tells the clock that you pressed the "alarm off" button
                    intentOff.putExtra("extra", "alarm off");

                    // also put an extra int into the alarm off section
                    // to prevent crashes in a Null Pointer Exception
                    intentOff.putExtra("whale_choice", alarmSound);


                    // stop the ringtone
                    //sendBroadcast(my_intent);

                    Calendar currcal = Calendar.getInstance();
                    int curhour = currcal.get(Calendar.HOUR_OF_DAY);
                    int curmin = currcal.get(Calendar.MINUTE);

                    setbool = false;

                    if ((curhour == hr && curmin >= min) || (curhour == hr + 1 && curmin <= ((min + 15) % 60))) {
                        Intent intent = new Intent (MainActivity.this, SecondActivity.class);
                        startActivity(intent);
                    }


                } else {
                    Toast.makeText(context, "Please set the alarm first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // the update text function
    private void updateIndicator(String str) {
        update_text.setText(str);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        setbool = true;
        hr = hourOfDay;
        min = minute;
        // initialize the alarm textview
        TextView alarmTime = (TextView) findViewById(R.id.alarmtime);
        //recognizing users timesettings and displaying alatime accordingly
        if (DateFormat.is24HourFormat(this)) {
            if (hourOfDay < 12) {
                if (hourOfDay < 10 && minute < 10) {
                    alarmTime.setText("0" + hourOfDay + " : " + "0" + minute);
                } else if (hourOfDay < 10) {
                    alarmTime.setText("0" + hourOfDay + " : " + minute);
                } else if (minute < 10) {
                    alarmTime.setText(hourOfDay + " : " + "0" + minute);
                } else {
                    alarmTime.setText(hourOfDay + " : " + minute);
                }
            } else {
                if (minute < 10) {
                    alarmTime.setText(hourOfDay + " : " + "0" + minute);
                } else {
                    alarmTime.setText(hourOfDay + " : " + minute);
                }
            }
        } else {
            if (hourOfDay < 12) {

                if (minute < 10) {
                    alarmTime.setText(hourOfDay + " : " + "0" + minute + " am");
                } else {
                    alarmTime.setText(hourOfDay + " : " + minute + " am");
                }
            } else if (hourOfDay == 12) {
                if (minute < 10) {
                    alarmTime.setText(hourOfDay + " : " + "0" + minute + " pm");
                } else {
                    alarmTime.setText(hourOfDay + " : " + minute + " pm");
                }
            } else {

                if (minute < 10) {
                    alarmTime.setText((hourOfDay - 12) + " : " + "0" + minute + " pm");
                } else {
                    alarmTime.setText((hourOfDay - 12) + " : " + minute + " pm");
                }
            }
        }





        // an instance of a calendar
        Calendar alarm = Calendar.getInstance();
        // calendar instance for current time
        Calendar now = Calendar.getInstance();



        // setting alarm calendar instance to the time selected by user on time picker
        alarm.set(Calendar.HOUR_OF_DAY, hourOfDay);
        alarm.set(Calendar.MINUTE, minute);


        // checking if alarm is set for next day
        if (alarm.before(now)) alarm.add(Calendar.DAY_OF_MONTH, 1);


        // create an intent to the Alarm Receiver class
        final Intent intentOn = new Intent(this.context, Alarm_Receiver.class);

        updateIndicator("Alarm is set");

        // put in extra string into my_intent
        // tells the clock that you pressed the "alarm on" button
        intentOn.putExtra("extra", "alarm on");

        // put in an extra int into my_intent
        // tells the clock that you want a certain value from the drop-down menu/spinner
        intentOn.putExtra("whale_choice", alarmSound);



        // create a pending intent that delays the intent
        pending_intent = PendingIntent.getBroadcast(MainActivity.this, 0,
                intentOn, PendingIntent.FLAG_UPDATE_CURRENT);

        // set alarm manager according to pending intent
        alarm_manager.set(AlarmManager.RTC_WAKEUP, alarm.getTimeInMillis(),
                pending_intent);


    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        alarmSound = (int) id;

        // printing message showing users selection from the alarm sounds
        // id indicates long (number) of the item selected from drop down
        // getitematposition returns object at the chosen position in the dropdown

        if (id == 0) {
            Toast.makeText(parent.getContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(parent.getContext(), parent.getItemAtPosition(position).toString()
                    + " is selected as Alarm Ringtone", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
}
