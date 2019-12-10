package com.example.testmaze1;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

/*
 * This class is implementation of the Music for the game. It allows for starting and stopping the music.
 * */
public class JService extends Service {
    MediaPlayer player;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Music Started", Toast.LENGTH_SHORT).show();
        player = MediaPlayer.create(this, R.raw.fantasymenu);
        player.setLooping(true);
    }
    @Override
    public void onStart (Intent intent, int startid) {
        Toast.makeText(this, "Music Started", Toast.LENGTH_SHORT).show();
        player.start();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Music Stopped", Toast.LENGTH_SHORT).show();
        player.stop();
    }


}
