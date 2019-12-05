package com.example.testmaze1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);
        chronometer = findViewById(R.id.chronometer);
        startTimer();
    }

    public void startTimer()
    {
        if (!running)
        {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    public void menuButton(View view) {
        //return to main menu
        mainMenu();
    }

    public void mainMenu() {
        intent = new Intent(this, MenuActivity.class);
        startActivity(intent);

    }
}