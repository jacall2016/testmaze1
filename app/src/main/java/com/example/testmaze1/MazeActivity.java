package com.example.testmaze1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MazeActivity extends AppCompatActivity {

    Intent intent;
    PresentMaze presentMaze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);
    }

    public void menuButton(View view) {
        //return to main menu
        mainMenu();
    }

    public void mainMenu() {
        intent = new Intent(this, MainActivity.class);

    }
}