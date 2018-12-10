package com.example.adityakhairari.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.Random;

public class RingtonePlayingService extends Service {

    MediaPlayer media_song;
    int startId;
    public boolean isRunning;
    Context context;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        // fetch the extra string from the alarm on/alarm off values
        String state = intent.getExtras().getString("extra");
        // fetch the whale choice integer values
        Integer ringtonechosen = intent.getExtras().getInt("whale_choice");

        Log.e("Ringtone extra is ", state);
        Log.e("Whale choice is ", ringtonechosen.toString());

        // put the notification here, test it out

        // notification
        // set up the notification service
        NotificationManager notify_manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        // set up an intent that goes to the Main Activity
        Intent intent_main_activity = new Intent(this.getApplicationContext(), MainActivity.class);
        // set up a pending intent
        PendingIntent pending_intent_main_activity = PendingIntent.getActivity(this, 0,
                intent_main_activity, 0);

        // make the notification parameters
        Notification notification_popup = new Notification.Builder(this)
                .setContentTitle("An alarm is going off!")
                .setContentText("Click me!")
                .setSmallIcon(R.drawable.ic_action_call)
                .setContentIntent(pending_intent_main_activity)
                .setAutoCancel(true)
                .build();




        // this converts the extra strings from the intent
        // to start IDs, values 0 or 1
        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                Log.e("Start ID is ", state);
                break;
            default:
                startId = 0;
                break;
        }


        // if else statements

        // if there is no music playing, and the user pressed "alarm on"
        // music should start playing
        if (!this.isRunning && startId == 1) {
            Log.e("there is no music, ", "and you want start");

            this.isRunning = true;
            this.startId = 0;

            // set up the start command for the notification
            notify_manager.notify(0, notification_popup);



            // play the whale sound depending on the passed whale choice id

            if (ringtonechosen == 0) {
                // play a randomly picked audio file

                int minNum = 1;
                int maxNum = 7;

                Random randomNum = new Random();
                int spinNum = randomNum.nextInt(maxNum + minNum);
                Log.e("random number is " , String.valueOf(spinNum));


                if (spinNum == 1) {
                    media_song = MediaPlayer.create(this, R.raw.bright_idea);
                    media_song.start();
                }
                else if (spinNum == 2) {
                    // create an instance of the media player
                    media_song = MediaPlayer.create(this, R.raw.christmas);
                    // start the ringtone
                    media_song.start();
                }
                else if (spinNum == 3) {
                    media_song = MediaPlayer.create(this, R.raw.computer_bounce);
                    media_song.start();
                }
                else if (spinNum == 4) {
                    media_song = MediaPlayer.create(this, R.raw.holiday);
                    media_song.start();
                }
                else if (spinNum == 5) {
                    media_song = MediaPlayer.create(this, R.raw.marimba1);
                    media_song.start();
                }
                else if (spinNum == 6) {
                    media_song = MediaPlayer.create(this, R.raw.marimba2);
                    media_song.start();
                }
                else if (spinNum == 7) {
                    media_song = MediaPlayer.create(this, R.raw.work_hard);
                    media_song.start();
                }
                else {
                    media_song = MediaPlayer.create(this, R.raw.marimba1);
                    media_song.start();
                }


            }
            else if (ringtonechosen == 1) {
                media_song = MediaPlayer.create(this, R.raw.bright_idea);
                media_song.start();
            }
            else if (ringtonechosen == 2) {
                media_song = MediaPlayer.create(this, R.raw.christmas);
                media_song.start();
            }
            else if (ringtonechosen == 3) {
                media_song = MediaPlayer.create(this, R.raw.computer_bounce);
                media_song.start();
            }
            else if (ringtonechosen == 4) {
                media_song = MediaPlayer.create(this, R.raw.holiday);
                media_song.start();
            }
            else if (ringtonechosen == 5) {
                media_song = MediaPlayer.create(this, R.raw.marimba1);
                media_song.start();
            }
            else if (ringtonechosen == 6) {
                media_song = MediaPlayer.create(this, R.raw.marimba2);
                media_song.start();
            }
            else if (ringtonechosen == 7) {
                media_song = MediaPlayer.create(this, R.raw.work_hard);
                media_song.start();
            }
            else {
                media_song = MediaPlayer.create(this, R.raw.marimba1);
                media_song.start();
            }










        }

        // if there is music playing, and the user pressed "alarm off"
        // music should stop playing
        else if (this.isRunning && startId == 0) {

            Log.e("there is music, ", "and you want end");

            // stop the ringtone
            media_song.stop();
            media_song.reset();

            this.isRunning = false;
            this.startId = 0;
        }

        // these are if the user presses random buttons
        // just to bug-proof the app
        // if there is no music playing, and the user pressed "alarm off"
        // do nothing
        else if (!this.isRunning && startId == 0) {

            Log.e("there is no music, ", "and you want end");

            this.isRunning = false;
            this.startId = 0;

        }

        // if there is music playing and the user pressed "alarm on"
        // do nothing

        else if (this.isRunning && startId == 1) {

            Log.e("there is music, ", "and you want start");

            this.isRunning = true;
            this.startId = 1;

        }

        // bugproof else statement
        else {
            Log.e("final_else ", "bugproofing else");

        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // what happens when destroyed (informing user)
        Log.e("onDestroy func called", "destroyed!!!!");

        super.onDestroy();

        this.isRunning = false;
    }


}