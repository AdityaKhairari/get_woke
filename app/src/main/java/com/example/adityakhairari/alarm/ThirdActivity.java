package com.example.adityakhairari.alarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity implements SensorEventListener {

    Context context;

    TextView tv_steps;

    int x;

    SensorManager sensorManager;

    boolean running = false;

    int counter = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        this.context = this;

        tv_steps = (TextView) findViewById(R.id.tv_steps);

        //x = ((tv_steps.getText()).toString()).;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running  = false;
//        if you unregister the hardware, it will stop detecting steps
//        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            //tv_steps.setText(String.valueOf(event.values[0]));
            counter--;
            tv_steps.setText(String.valueOf(counter));
            if (counter <= 0) {

                final Intent intentOff = new Intent(context, Alarm_Receiver.class);
                intentOff.putExtra("stringkey", "alarm off");
                intentOff.putExtra("intkey", 2);
                sendBroadcast(intentOff);

                Intent fourthintent = new Intent (ThirdActivity.this, MainActivity.class);
                startActivity(fourthintent);
            }
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_MUTE) {
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
