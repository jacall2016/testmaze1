package com.example.testmaze1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;


import androidx.annotation.Nullable;


//displays the area of the maze close to the character, and moves the maze around the character.
public class MazeView extends View {
    public float x, y;
    private Maze maze;
    public int width;
    public int height;
    public PresentMaze presentMaze;

    // Takes the Json data and re-creates it into a version that is used to create the maze
    public MazeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        maze = new Maze();

    }

    // Gets the height and width of the screen and creates the maze in a custom view
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        //System.out.println(height);
        presentMaze = new PresentMaze(this, height, width);
        presentMaze.createMaze();
    }

    // Sets the background of the custom view
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        canvas.translate(presentMaze.hMargin, presentMaze.vMargin);
        presentMaze.drawMaze(canvas);
    }

    // Draws all rectangles on the screen
    public void drawRect(Canvas canvas, float left, float top, float right, float bottom, Paint color) {
        canvas.drawRect(left, top, right, bottom, color);
    }

    /* Gets the direction the user is trying to swipe and dependant
on that information, moves the player on the screen.
*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        String direction;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float finalX = event.getX();
                float finalY = event.getY();
                if (Math.abs(x - finalX) > Math.abs(y - finalY)) {
                    if (x < finalX) {
                        direction = "RIGHT";
                    } else {
                        direction = "LEFT";
                    }
                } else {
                    if (y > finalY) {
                        direction = "UP";
                    } else {
                        direction = "DOWN";
                    }
                }
                presentMaze.moveCells(direction);
                invalidate();
                break;
        }
        return true;
    }
}

