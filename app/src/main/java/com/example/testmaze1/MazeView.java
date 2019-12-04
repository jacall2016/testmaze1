package com.example.testmaze1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;



//displays the area of the maze close to the character, and moves the maze around the character.
public class MazeView extends View{
    public float x, y;
    private Maze maze;
    public int width;
    public int height;
    public PresentMaze presentMaze;

    public MazeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
        canvas.drawColor(Color.GRAY);
        canvas.translate(presentMaze.hMargin,presentMaze.vMargin);
        presentMaze.drawMaze(canvas);
    }

    public void drawRect(Canvas canvas, float left, float top, float right, float bottom, Paint color) {
        canvas.drawRect(left, top, right, bottom, color);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        //x = event.getX();
        //y = event.getY();
        String direction = "";

        if (action == MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();
        }

        if (action == MotionEvent.ACTION_UP) {
            float finnalX = event.getX();
            float finnalY = event.getY();
            //System.out.println("Finnal x: " + finnalX + " X: " + x);
            //System.out.println("difference: " + (finnalX - x));
            if (x < finnalX && (x - finnalX) < 100) {
                direction = "RIGHT";
                presentMaze.moveCells(direction);
                invalidate();
            }
            if (x > finnalX && (x - finnalX) > 100) {
                direction = "LEFT";
                presentMaze.moveCells(direction);
                invalidate();
            }
            if (y < finnalY && (y - finnalY) < 100) {
                direction = "DOWN";
                presentMaze.moveCells(direction);
                invalidate();
            }
            if (y > finnalY && (y - finnalY) > 100) {
                direction = "UP";
                presentMaze.moveCells(direction);
                invalidate();
            }
        }
        return true;
    }
}