package com.example.testmaze1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;



//displays the area of the maze close to the character, and moves the maze around the character.
public class MazeView  extends View {
    public float x, y;
    private Maze maze;
    private Paint wallPaint, playerPaint, exitPaint;
    public int width;
    public int height;
    public PresentMaze presentMaze;

    public MazeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        playerPaint = new Paint();
        playerPaint.setColor(Color.RED);
        exitPaint = new Paint();
        exitPaint.setColor(Color.BLUE);
        maze = new Maze();

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        //System.out.println(height);
        presentMaze = new PresentMaze(this, height, width);
        presentMaze.createMaze();
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        canvas.translate(presentMaze.hMargin,presentMaze.vMargin);
        presentMaze.drawMaze(canvas);
    }

    public void drawWallRect(Canvas canvas, float left, float top, float right, float bottom) {
        canvas.drawRect(left, top, right, bottom, wallPaint);
    }

    public void drawPlayerRect(Canvas canvas, float left, float top, float right, float bottom) {
        canvas.drawRect(left, top, right, bottom, playerPaint);
        System.out.println("left3: " + left + "right3: " + right);
    }

    public void drawExitRect(Canvas canvas, float left, float top, float right, float bottom) {
        canvas.drawRect(left, top, right, bottom, exitPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        String direction = "";
        switch(action) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
            case MotionEvent.ACTION_UP:
                float finnalX = event.getX();
                float finnalY = event.getY();
                System.out.println("Finnal x: " + x + " X: " + x);
                if (x < finnalX) {
                    direction = "LEFT";
                }
                if (x > finnalX) {
                    direction = "RIGHT";
                }
                if (y < finnalY) {
                    direction = "DOWN";
                }
                if (y > finnalY) {
                    direction = "UP";
                }
                break;
        }
        presentMaze.movePlayer(direction);
        invalidate();
        return true;
    }
}