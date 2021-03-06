package com.example.adityakhairari.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Alarm_Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        Log.e("in receiver", "good job!");

        // fetch stringkey from the intent
        // tells the app whether the user pressed the alarm on button or the alarm off button

        String string = intent.getExtras().getString("stringkey");



        // fetch the intkey from the intent
        // tells the app which value the user picked from the drop down menu/spinner
        Integer alarm_int = intent.getExtras().getInt("intkey");



        // create an intent to the ringtone service
        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        // pass the extra string from Receiver to the Ringtone Playing Service
        service_intent.putExtra("stringkey", string);

        // pass the extra integer from the Receiver to the Ringtone Playing Service
        service_intent.putExtra("intkey", alarm_int);


        // start the ringtone service
        context.startService(service_intent);


    }
}
