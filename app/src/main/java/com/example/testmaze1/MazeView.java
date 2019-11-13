package com.example.testmaze1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

//displays the area of the maze close to the character, and moves the maze around the character.
public class MazeView  extends View {
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
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        presentMaze = new PresentMaze(this, height, width);
        presentMaze.createMaze();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        canvas.translate(presentMaze.hMargin,presentMaze.vMargin);
        presentMaze.drawMaze();
    }

    public void drawWallRect() {
        canvas.drawRect(presentMaze.left, presentMaze.top, presentMaze.right, presentMaze.bottom, wallPaint);
    }

    public void drawPlayerRect() {
        canvas.drawRect(presentMaze.left, presentMaze.top, presentMaze.right, presentMaze.bottom, playerPaint);
    }

    public void drawExitRect() {
        canvas.drawRect(presentMaze.left, presentMaze.top, presentMaze.right, presentMaze.bottom,  exitPaint);
    }
}