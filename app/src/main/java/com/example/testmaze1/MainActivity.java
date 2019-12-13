package com.example.testmaze1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

import android.os.Bundle;


/*
 * This class starts the game and the core functions needed to handle the game.
 * */
public class MainActivity extends AppCompatActivity {

    // Private member variables used in the timer
    private Chronometer chronometer;
    private boolean running;
    long startTime;

    /* creates the Activity, displays the chronometer, starts the chronometer, and at the same time gets the
    * start time of how long the user is in the Activity
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);
        chronometer = findViewById(R.id.chronometer);
        startTime = SystemClock.elapsedRealtime();
        startTimer();
    }

    // Determines if the chronometer is running and if it is calculates the time
    public void startTimer()
    {
        if (!running)
        {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            running = true;
        }
    }

    // Gets the time the user took to complete the maze. Coverts it from milliseconds and displays it
    public void menuButton(View view) {
        long highScoreTime = getIntent().getLongExtra("Highscore", 999999999);
        System.out.println("HS:" + highScoreTime);

        long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
        System.out.println(elapsedMillis);
        if(highScoreTime > elapsedMillis)
            highScoreTime = elapsedMillis;
            System.out.println("asdf:" + highScoreTime);
        Intent myIntent = new Intent(this, MenuActivity.class);
        myIntent.putExtra("Time", highScoreTime);
        startActivity(myIntent);
    }
}