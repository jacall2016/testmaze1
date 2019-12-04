package com.example.testmaze1;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;


public class JService extends Service {
    private static final String TAG = "MyService";
    MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "My JService Created", Toast.LENGTH_LONG).show();
        player = MediaPlayer.create(this, R.raw.fantasymenu);
        player.setLooping(true);
    }
    @Override
    public void onStart (Intent intent, int startid) {
        Toast.makeText(this, "My JService Started", Toast.LENGTH_LONG).show();
        player.start();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "My JService Stopped", Toast.LENGTH_LONG).show();
        player.stop();
    }


}
