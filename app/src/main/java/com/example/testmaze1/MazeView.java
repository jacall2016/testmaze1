package com.example.testmaze1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

//displays the area of the maze close to the character, and moves the maze around the character.
public class MazeView  extends View implements GestureDetector.OnGestureListener {
    private final GestureDetector gestureDetector;
    private Maze maze;
    private Paint wallPaint, playerPaint, exitPaint;
    public Canvas canvas;
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

        gestureDetector = new GestureDetector(this);
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        canvas.translate(presentMaze.hMargin,presentMaze.vMargin);
        presentMaze.drawMaze(canvas);
    }

    public void drawWallRect(Canvas canvas) {
        canvas.drawRect(presentMaze.left, presentMaze.top, presentMaze.right, presentMaze.bottom, wallPaint);
    }

    public void drawPlayerRect(Canvas canvas) {
        canvas.drawRect(presentMaze.left, presentMaze.top, presentMaze.right, presentMaze.bottom, playerPaint);
    }

    public void drawExitRect(Canvas canvas) {
        canvas.drawRect(presentMaze.left, presentMaze.top, presentMaze.right, presentMaze.bottom,  exitPaint);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
        
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {
        boolean result = false;
        float diffY = moveEvent.getY() - downEvent.getY();
       float diffX = moveEvent.getX() - downEvent.getX();

       if (Math.abs(diffX) > Math.abs(diffY))
       {
        //Right or left swipe..

            if (Math.abs(diffX) > 100 && Math.abs(velocityX) > 100)
            {
                if (diffX > 0)
                {
                    onSwipeRight();
                }
                else
                {
                    onSwipeLeft();
                }
                result = true;
            }
       }
       else
       {
           // up or down Swipe...
           if (Math.abs(diffY) > 100 && Math.abs(velocityY) > 100)
           {
               if (diffY > 0)
               {
                   onSwipeBottom();
               }
               else
               {
                   onSwipeTop();
               }

               result = true;
           }
       }
        return result;
    }

    private void onSwipeLeft() {
        Log.d("Left", "USer Swiped LEFT!!!!");
    }

    private void onSwipeRight() {
        Log.d("Right", "USer Swiped RIght!!!!");
    }

    private void onSwipeBottom() {
        Log.d("down", "USer Swiped down!!!!");
    }

    private void onSwipeTop() {
        Log.d("Up", "USer Swiped UP!!!!");

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}