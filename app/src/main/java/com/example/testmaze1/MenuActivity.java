package com.example.testmaze1;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        startService(new Intent(this, JService.class));

        Button start = (Button) findViewById(R.id.StartGame);
        start.setOnClickListener(this);
        TextView name = (TextView) findViewById(R.id.Name);
        Button off = (Button)findViewById(R.id.off);
        off.setOnClickListener(this);
        Button on = (Button)findViewById(R.id.on);
        on.setOnClickListener(this);
        Button level = (Button) findViewById(R.id.Level);
        level.setOnClickListener(this);

        long elapsedTime = getIntent().getLongExtra("Time", 999999999);
        if (elapsedTime < 999999999) {
            elapsedTime /= 1000;
            String s1 = Long.toString(elapsedTime);
            TextView theTextView = (TextView) findViewById(R.id.TextView1);
            theTextView.setText("Highscore: " + s1);
        }
        else if (elapsedTime == 999999999){
            TextView theTextView = (TextView) findViewById(R.id.TextView1);
            theTextView.setText("Highscore:");

        }


      //  Animation scale = AnimationUtils.loadAnimation(this, R.anim.myscale);
        //scale.setFillAfter(true);
        //scale.setStartOffset(0);
        //name.setAnimation(scale);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.StartGame:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.on:
                startService(new Intent(MenuActivity.this, JService.class));
            case R.id.off:
                stopService(new Intent(MenuActivity.this, JService.class));
        }
    }
}
