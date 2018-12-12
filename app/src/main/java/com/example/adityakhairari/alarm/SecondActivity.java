package com.example.adityakhairari.alarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class SecondActivity extends AppCompatActivity {

    Context context;

    private TextView question;
    private Button a;
    private Button b;
    private Button c;
    private Button d;
    private final String TAG = "MainActivity";
    private String currentQuestion;
    private String correctAnswer;
    private int numberOfQuestions = 7;
    private  boolean bool = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        this.context = this;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();

        question = (TextView) findViewById(R.id.tvQuestion);
        a = (Button) findViewById(R.id.btna);
        b = (Button) findViewById(R.id.btnb);
        c = (Button) findViewById(R.id.btnc);
        d = (Button) findViewById(R.id.btnd);

        a.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if (a.getText().equals(correctAnswer)) {
                    question.setText("CORRECT");

                    final Intent intentOff = new Intent(context, Alarm_Receiver.class);
                    intentOff.putExtra("stringkey", "alarm off");
                    intentOff.putExtra("intkey", 2);
                    sendBroadcast(intentOff);

                    Intent thirdintent = new Intent (SecondActivity.this, MainActivity.class);
                    startActivity(thirdintent);

                } else {
                    question.setText("WRONG");
                    //myRef.child("update").setValue(!bool);
                    final Intent secIntent = new Intent (SecondActivity.this, ThirdActivity.class);
                    new Timer().schedule(
                            new TimerTask() {
                                public void run() {
                                    startActivity(secIntent);
                                }
                            },
                            1000
                    );
                }
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b.getText().equals(correctAnswer)) {
                    question.setText("CORRECT");

                    final Intent intentOff = new Intent(context, Alarm_Receiver.class);
                    intentOff.putExtra("stringkey", "alarm off");
                    intentOff.putExtra("intkey", 2);
                    sendBroadcast(intentOff);

                    Intent thirdintent = new Intent (SecondActivity.this, MainActivity.class);
                    startActivity(thirdintent);

                } else {
                    question.setText("WRONG");
                    final Intent secIntent = new Intent (SecondActivity.this, ThirdActivity.class);
                    new Timer().schedule(
                            new TimerTask() {
                                public void run() {
                                    startActivity(secIntent);
                                }
                            },
                            1000
                    );
                }
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c.getText().equals(correctAnswer)) {
                    question.setText("CORRECT");

                    final Intent intentOff = new Intent(context, Alarm_Receiver.class);
                    intentOff.putExtra("stringkey", "alarm off");
                    intentOff.putExtra("intkey", 2);
                    sendBroadcast(intentOff);

                    Intent thirdintent = new Intent (SecondActivity.this, MainActivity.class);
                    startActivity(thirdintent);

                } else {
                    question.setText("WRONG");
                    final Intent secIntent = new Intent (SecondActivity.this, ThirdActivity.class);
                    new Timer().schedule(
                            new TimerTask() {
                                public void run() {
                                    startActivity(secIntent);
                                }
                            },
                            1000
                    );
                }
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (d.getText().equals(correctAnswer)) {
                    question.setText("CORRECT");

                    final Intent intentOff = new Intent(context, Alarm_Receiver.class);
                    intentOff.putExtra("stringkey", "alarm off");
                    intentOff.putExtra("intkey", 2);
                    sendBroadcast(intentOff);

                    Intent thirdintent = new Intent (SecondActivity.this, MainActivity.class);
                    startActivity(thirdintent);

                } else {
                    question.setText("WRONG");
                    final Intent secIntent = new Intent (SecondActivity.this, ThirdActivity.class);
                    new Timer().schedule(
                            new TimerTask() {
                                public void run() {
                                    startActivity(secIntent);
                                }
                            },
                            1000
                    );

                }
            }
        });

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String questionNumber = getRandomQuestion();
                currentQuestion = questionNumber;

                //everytime a randome position is generated, it is added to this list so that that pos. isn't used again
                List<String> questionPositions = new ArrayList<>();

                question.setText(String.valueOf(dataSnapshot.child(questionNumber).child("question").getValue()));

                String first = randomPlacement();
                questionPositions.add(first);
                a.setText(String.valueOf(dataSnapshot.child(questionNumber).child(first).getValue()));
                if (first.equals("correct_answer")) {
                    correctAnswer = String.valueOf(dataSnapshot.child(questionNumber).child(first).getValue());
                }

                String second = randomPlacement();
                while (questionPositions.contains(second)) {
                    second = randomPlacement();
                }
                b.setText(String.valueOf(dataSnapshot.child(questionNumber).child(second).getValue()));
                questionPositions.add(second);
                if (second.equals("correct_answer")) {
                    correctAnswer = String.valueOf(dataSnapshot.child(questionNumber).child(second).getValue());
                }

                String third = randomPlacement();
                while (questionPositions.contains(third)) {
                    third = randomPlacement();
                }
                c.setText(String.valueOf(dataSnapshot.child(questionNumber).child(third).getValue()));
                questionPositions.add(third);
                if (third.equals("correct_answer")) {
                    correctAnswer = String.valueOf(dataSnapshot.child(questionNumber).child(third).getValue());
                }

                String fourth = randomPlacement();
                while (questionPositions.contains(fourth)) {
                    fourth = randomPlacement();
                }
                d.setText(String.valueOf(dataSnapshot.child(questionNumber).child(fourth).getValue()));
                if (fourth.equals("correct_answer")) {
                    correctAnswer = String.valueOf(dataSnapshot.child(questionNumber).child(fourth).getValue());
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

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

    public String getRandomQuestion() {
        Random rand = new Random();
        int n = rand.nextInt(numberOfQuestions) + 1;
        return "question_" + n;
    }

    //places questions in random order
    public String randomPlacement() {
        Random rand = new Random();
        int n = rand.nextInt(4) + 1;
        if (n == 4) {
            return "A";
        } else if (n == 3) {
            return "B";
        } else if (n == 2) {
            return "C";
        } else {
            return "correct_answer";
        }

    }
}
