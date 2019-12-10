package com.example.testmaze1;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;


/*
* This class allows the users to interact with the Menu of the game.
* Included in here are the different buttons needed to start the game and allows the user to start and stop the music.
* Also has the geting the Score for the player that will display when they finish the game.
* Included for each Text/button are animations for them.
* */

//Game menu class that allows the user to interact with the buttons and start the game
public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private long highScoreTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        startService(new Intent(this, JService.class));

        //Text of the name of the Game
        TextView name =  findViewById(R.id.Name);
        Animation scale = AnimationUtils.loadAnimation(this, R.anim.myscale);
        name.setAnimation(scale);

        //Start button with animation
        Button start = findViewById(R.id.StartGame);
        start.setOnClickListener(this);
        Animation startB = AnimationUtils.loadAnimation(this,R.anim.start);
        start.setAnimation(startB);

        //Off Button with animation
        Button off = findViewById(R.id.off);
        off.setOnClickListener(this);
        Animation offB = AnimationUtils.loadAnimation(this,R.anim.f);
        off.setAnimation(offB);
        //On Button with animation
        Button on = findViewById(R.id.on);
        on.setOnClickListener(this);
        Animation onB = AnimationUtils.loadAnimation(this,R.anim.on);
        on.setAnimation(onB);

        //Gets the intent's data of elapsed time to be able to display Score
        highScoreTime = getIntent().getLongExtra("Time", 999999999);
        System.out.println("highscoreS:"+highScoreTime);
        if(highScoreTime == 999999999){
            TextView theTextView = (TextView) findViewById(R.id.TextView1);
            theTextView.setText("TIME:");
            Animation timeA = AnimationUtils.loadAnimation(this,R.anim.time);
            theTextView.setAnimation(timeA);

        } else{
            if (highScoreTime > 1000)
                highScoreTime /= 1000;
            String s1 = Long.toString(highScoreTime);
            TextView theTextView =  findViewById(R.id.TextView1);
            theTextView.setText("TIME: " + s1);
            Animation timeA = AnimationUtils.loadAnimation(this,R.anim.time);
            theTextView.setAnimation(timeA);


        }
    }

    //Various switch statements that when a button is clicked by the user will either go into the main activity/ start or stop the music.
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.StartGame:
                Intent intent = new Intent(this, MainActivity.class);
                if(highScoreTime != 999999999)
                    highScoreTime *= 1000;
                intent.putExtra("Highscore", highScoreTime);
                startActivity(intent);
                break;
            case R.id.on:
                startService(new Intent(MenuActivity.this, JService.class));
            case R.id.off:
                stopService(new Intent(MenuActivity.this, JService.class));
        }
    }
}
