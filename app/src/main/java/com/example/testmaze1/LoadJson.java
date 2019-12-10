package com.example.testmaze1;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;


/*
 * Class that loads the maze files to be displayd and played in the game.
 * Reads the data that is stored in our assets file.
 * */
public class LoadJson {
    public String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
