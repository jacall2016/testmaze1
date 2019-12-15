package com.example.testmaze1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


/*
 * This class allows the users to interact with the Menu of the game.
 * Included in here are the different buttons needed to start the game and allows the user to start and stop the music.
 * Also has the geting the Score for the player that will display when they finish the game.
 * Included for each Text/button are animations for them.
 * */

//Game menu class that allows the user to interact with the buttons and start the game
public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private long highScoreTime = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        startService(new Intent(this, JService.class));

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        long restoredLong = prefs.getLong("Score", 999999999);
        System.out.println("this is the value of the HS: " + restoredLong);




        //Text of the name of the Game
        TextView name =  findViewById(R.id.Name);
        Animation scale = AnimationUtils.loadAnimation(this, R.anim.myscale);
        name.setAnimation(scale);

        //Start button with animation
        Button start = findViewById(R.id.StartGame);
        start.setOnClickListener(this);
        Animation startB = AnimationUtils.loadAnimation(this, R.anim.start);
        start.setAnimation(startB);

        //Off Button with animation
        Button off = findViewById(R.id.off);
        off.setOnClickListener(this);
        Animation offB = AnimationUtils.loadAnimation(this, R.anim.f);
        off.setAnimation(offB);
        //On Button with animation
        Button on = findViewById(R.id.on);
        on.setOnClickListener(this);
        Animation onB = AnimationUtils.loadAnimation(this, R.anim.on);
        on.setAnimation(onB);

        //Gets the intent's data of elapsed time to be able to display Score
        highScoreTime = getIntent().getLongExtra("Time", 999999999);
        //System.out.println("intent HS: "+ highScoreTime);


        if(restoredLong < 1000)
            restoredLong *= 1000;
        System.out.println(restoredLong);



        //System.out.println("THIS IS THE RESTORED LONNNNNG: " + restoredLong);
        //System.out.println("THIS IS THE HIGHSCORE TIME: " + highScoreTime);

        //If statements to determine whether the restored HighScore or the new highscore should be displayed
        if (restoredLong != 999999999 && restoredLong < highScoreTime || restoredLong == highScoreTime && restoredLong != 999999999) {
            if(restoredLong >= 1000)
                restoredLong /= 1000;
            String s4 = Long.toString(restoredLong);
            TextView scree = findViewById(R.id.TextView1);
            scree.setText("TIME: " + s4 +" seconds");

        }else if(highScoreTime == 999999999){
            TextView theTextView = findViewById(R.id.TextView1);
            theTextView.setText("TIME:");
            Animation timeA = AnimationUtils.loadAnimation(this, R.anim.time);
            theTextView.setAnimation(timeA);

        }else if(highScoreTime < restoredLong){

            if (highScoreTime >= 1000)
                highScoreTime /= 1000;
            if (highScoreTime >= 60) {
                long seconds = highScoreTime % 60;
                long minute = (highScoreTime - seconds) / 60;
                // System.out.println(minute);
                //System.out.println(seconds);
                String s2 = Long.toString(minute);
                String s3 = Long.toString(seconds);
                TextView minutesS = findViewById(R.id.TextView1);
                minutesS.setText("Time: " + s2 + " Min " + s3 + " Sec");
                Animation timeA = AnimationUtils.loadAnimation(this, R.anim.time);
                minutesS.setAnimation(timeA);
                SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                editor.putLong("Score", highScoreTime); // applies the highscore time to the shared preferences so that it may be pulled out later
                editor.apply();
            }else {
                String s1 = Long.toString(highScoreTime);
                TextView theTextView = findViewById(R.id.TextView1);
                theTextView.setText("TIME: " + s1 + " seconds");
                Animation timeA = AnimationUtils.loadAnimation(this, R.anim.time);
                theTextView.setAnimation(timeA);
                SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                editor.putLong("Score", highScoreTime);// applies the highscore time to the shared preferences so that it may be pulled out later
                editor.apply();
                // System.out.println("HST" + highScoreTime + "RL" + restoredLong);
            }

        }

    }

    //Various switch statements that when a button is clicked by the user will either go into the main activity/ start or stop the music.
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.StartGame:
                Intent intent = new Intent(this, MainActivity.class);
                if(highScoreTime != 999999999 && highScoreTime < 1000) // allows the comparison of data in the next intent
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
