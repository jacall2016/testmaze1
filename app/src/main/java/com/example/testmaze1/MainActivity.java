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

    private Chronometer chronometer;
    private boolean running;
    long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);
        chronometer = findViewById(R.id.chronometer);
        startTime = SystemClock.elapsedRealtime();
        startTimer();
    }

    public void startTimer()
    {
        if (!running)
        {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            running = true;
        }
    }

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


    public Long mainMenu() {
        long elapsedTime;
        elapsedTime = ((startTime - SystemClock.elapsedRealtime()) * -1);
        System.out.println("elapsed time in menu: " + elapsedTime);
        //Intent myIntent = new Intent(this, MenuActivity.class);
        //myIntent.putExtra("Time", elapsedTime);
        //startActivity(myIntent);
return elapsedTime;
    }
}