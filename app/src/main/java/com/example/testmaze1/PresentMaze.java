package com.example.testmaze1;

import android.graphics.Canvas;

import com.google.gson.Gson;

public class PresentMaze {

    private Maze maze;
    private Cell[][] cells;
    private String[][] CURRENT_ARRAY;
    private int COLS, ROWS;
    public float cellSize, hMargin, vMargin;
    private MazeView mazeView;

    public PresentMaze(MazeView mazeView,int height, int width) {
        this.mazeView = mazeView;
        LoadJson loadJson = new LoadJson();
        String file = loadJson.loadJSONFromAsset(mazeView.getContext(),"maze.json");
        Gson gson = new Gson();
        maze = gson.fromJson(file,Maze.class);
        //System.out.println(file);

        int level = 3;

        COLS = maze.cols1;
        ROWS = maze.rows1;
        /*
        switch(level) {
            case 1:
                CURRENT_ARRAY = maze.array1;

                break;
            case 2:
                CURRENT_ARRAY = maze.array2;
                COLS = maze.cols2;
                ROWS = maze.rows2;
                break;
            case 3:
                CURRENT_ARRAY = maze.array3;
                COLS = maze.cols3;
                ROWS = maze.rows3;
                break;
        }
        */

        if (width / height < COLS / ROWS) {
            cellSize = width / (ROWS + 1);
        } else {
            cellSize = height / (COLS + 1);
        }

        hMargin = (width - COLS * cellSize) / 2;
        vMargin = (height - ROWS * cellSize) / 2;
    }

    public void createMaze () {
        cells = new Cell[COLS][ROWS];
        for (int gx = 0; gx < maze.array1.length; gx++) {
            for (int gy = 0; gy < maze.array1[gx].length; gy++) {
                Cell cell = new Cell();
                cell.left = gx * cellSize;
                cell.top = gy * cellSize;
                cell.right = ((gx * cellSize) + cellSize - 1);
                cell.bottom = ((gy * cellSize) + cellSize - 1);
                if (maze.array1[gx][gy].contentEquals("W")) {
                    cell.wall = true;
                    cells[gx][gy] = cell;
                } else if (maze.array1[gx][gy].contentEquals("P")) {
                    cell.player = true;
                    //System.out.println("left: " + cell.left);
                    cells[gx][gy] = cell;
                } else if (maze.array1[gx][gy].contentEquals("E")) {
                    cell.exit = true;
                    cells[gx][gy] = cell;
                } else {
                    cells[gx][gy] = cell;
                }
            }
        }
    }

    public void drawMaze(Canvas canvas) {
        for (int gx = 0; gx < cells.length; gx++) {
            for (int gy = 0; gy < cells[gx].length; gy++) {
                if (cells[gx][gy].wall) {
                    mazeView.drawWallRect(canvas, cells[gx][gy].left, cells[gx][gy].top, cells[gx][gy].right, cells[gx][gy].bottom);
                } else if (cells[gx][gy].player) {
                    mazeView.drawPlayerRect(canvas, cells[gx][gy].left, cells[gx][gy].top, cells[gx][gy].right, cells[gx][gy].bottom);
                } else if (cells[gx][gy].exit) {
                    mazeView.drawExitRect(canvas, cells[gx][gy].left, cells[gx][gy].top, cells[gx][gy].right, cells[gx][gy].bottom);
                }
            }
        }
    }

    public void movePlayer(String direction) {
        for (int gx = 0; gx < cells.length; gx++) {
            for (int gy = 0; gy < cells[gx].length; gy++) {
                //System.out.println("direction: " + direction);
                System.out.println("right1: " + cells[gx][gy].right + "left1: " + cells[gx][gy].left);
                if (cells[gx][gy].player) {
                    if (direction == "UP") {
                        if(!cells[gx][gy-1].wall) {
                            cells[gx][gy].player = false;
                            cells[gx][gy-1].player = true;
                            direction = "";
                        }
                    }
                    if (direction == "DOWN") {
                        if(!cells[gx][gy+1].wall) {
                            cells[gx][gy].player = false;
                            cells[gx][gy+1].player = true;
                            direction = "";
                        }
                    }
                    if (direction == "RIGHT") {
                        if(!cells[gx+1][gy].wall) {
                            cells[gx][gy].player = false;
                            cells[gx+1][gy].player = true;
                            direction = "";
                        }
                    }
                    if (direction == "LEFT") {
                        if(!cells[gx-1][gy].wall) {
                            cells[gx][gy].player = false;
                            cells[gx-1][gy].player = true;
                            direction = "";
                        }

                    }
                }
            }
        }
    }

    private class Cell {
        boolean
                wall = false,
                player = false,
                exit = false;
        float
            left = 0,
            right = 0,
            top = 0,
            bottom = 0;
        int col, row;
    }
}