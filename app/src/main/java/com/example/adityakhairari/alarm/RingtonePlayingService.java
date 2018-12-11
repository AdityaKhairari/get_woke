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

    int startId;
    MediaPlayer media_song;
    public boolean musPlay;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        // fetch the extra string from the alarm on/alarm off values
        String fetchkey = intent.getExtras().getString("stringkey");

        // fetch the whale choice integer values
        Integer ringtonechosen = intent.getExtras().getInt("intkey");






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
        assert fetchkey != null;
        switch (fetchkey) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }



        // if there is no music playing, and the user pressed "alarm on"
        // music should start playing
        if (!this.musPlay && startId == 1) {

            this.musPlay = true;
            this.startId = 0;

            // set up the start command for the notification
            notify_manager.notify(0, notification_popup);


            if (ringtonechosen == 0) {
                // play a ringtone randomnly

                int minNum = 1;
                int maxNum = 7;

                Random randomNum = new Random();
                int spinNum = randomNum.nextInt(maxNum + minNum);



                if (spinNum == 1) {
                    media_song = MediaPlayer.create(this, R.raw.bright_idea);
                    media_song.setLooping(true);
                    media_song.start();
                }
                else if (spinNum == 2) {

                    media_song = MediaPlayer.create(this, R.raw.christmas);
                    media_song.setLooping(true);

                    media_song.start();
                }
                else if (spinNum == 3) {
                    media_song = MediaPlayer.create(this, R.raw.computer_bounce);
                    media_song.setLooping(true);
                    media_song.start();
                }
                else if (spinNum == 4) {
                    media_song = MediaPlayer.create(this, R.raw.holiday);
                    media_song.setLooping(true);
                    media_song.start();
                }
                else if (spinNum == 5) {
                    media_song = MediaPlayer.create(this, R.raw.marimba1);
                    media_song.setLooping(true);
                    media_song.start();
                }
                else if (spinNum == 6) {
                    media_song = MediaPlayer.create(this, R.raw.marimba2);
                    media_song.setLooping(true);
                    media_song.start();
                }
                else if (spinNum == 7) {
                    media_song = MediaPlayer.create(this, R.raw.work_hard);
                    media_song.setLooping(true);
                    media_song.start();
                }
                else {
                    media_song = MediaPlayer.create(this, R.raw.marimba1);
                    media_song.setLooping(true);
                    media_song.start();
                }


            }
            else if (ringtonechosen == 1) {
                media_song = MediaPlayer.create(this, R.raw.bright_idea);
                media_song.setLooping(true);
                media_song.start();
            }
            else if (ringtonechosen == 2) {
                media_song = MediaPlayer.create(this, R.raw.christmas);
                media_song.setLooping(true);
                media_song.start();
            }
            else if (ringtonechosen == 3) {
                media_song = MediaPlayer.create(this, R.raw.computer_bounce);
                media_song.setLooping(true);
                media_song.start();
            }
            else if (ringtonechosen == 4) {
                media_song = MediaPlayer.create(this, R.raw.holiday);
                media_song.setLooping(true);
                media_song.start();
            }
            else if (ringtonechosen == 5) {
                media_song = MediaPlayer.create(this, R.raw.marimba1);
                media_song.setLooping(true);
                media_song.start();
            }
            else if (ringtonechosen == 6) {
                media_song = MediaPlayer.create(this, R.raw.marimba2);
                media_song.setLooping(true);
                media_song.start();
            }
            else if (ringtonechosen == 7) {
                media_song = MediaPlayer.create(this, R.raw.work_hard);
                media_song.setLooping(true);
                media_song.start();
            }
            else {
                media_song = MediaPlayer.create(this, R.raw.marimba1);
                media_song.setLooping(true);
                media_song.start();
            }
        }

        // if there is music playing, and the user pressed "alarm off"
        // music should stop playing
        else if (this.musPlay && startId == 0) {
            // stop the ringtone
            media_song.stop();
            //reset ringtone
            media_song.reset();

            this.musPlay = false;
            this.startId = 0;
        }


        // if there is music playing and the user pressed "alarm on"
        // do nothing
        else if (this.musPlay) {
            this.startId = 1;
        }


        // if there is no music playing, and the user pressed "alarm off"
        // do nothing
        else {
            this.startId = 0;
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // what happens when destroyed (informing user)
        Log.e("onDestroy func called", "destroyed!!!!");

        super.onDestroy();

        this.musPlay = false;
    }


}