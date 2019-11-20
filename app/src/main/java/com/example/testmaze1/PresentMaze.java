package com.example.testmaze1;

import android.graphics.Canvas;

import com.google.gson.Gson;

public class PresentMaze {

    private Maze maze;
    private Cell[][] cells;
    public Cell player, exit;
    private int COLS, ROWS;
    public float cellSize, hMargin, vMargin;
    public float left, top, right, bottom;
    private MazeView mazeView;

    public PresentMaze(MazeView mazeView,int height, int width) {
        this.mazeView = mazeView;
        LoadJson loadJson = new LoadJson();
        String file = loadJson.loadJSONFromAsset(mazeView.getContext(),"maze.json");
        Gson gson = new Gson();
        maze = gson.fromJson(file,Maze.class);
        //System.out.println(file);
        COLS = maze.cols1;
        ROWS = maze.rows1;

        /*
        if (width / height < COLS / ROWS) {
            cellSize = width / (ROWS + 1);
        } else {
            cellSize = height / (COLS + 1);
        }
        */
        cellSize = width / (ROWS + 1);

        hMargin = (width - COLS * cellSize) / 2;
        vMargin = (height - ROWS * cellSize) / 2;
    }

    public void createMaze () {
        cells = new Cell[maze.rows1][maze.cols1];
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
                        cells[gx][gy].top += cellSize;
                        cells[gx][gy].bottom += cellSize;
                    }
                    if (direction == "DOWN") {
                        cells[gx][gy].top -= cellSize;
                        cells[gx][gy].bottom -= cellSize;
                    }
                    if (direction == "RIGHT") {
                        cells[gx][gy].right += cellSize;
                        cells[gx][gy].left += cellSize;
                        //System.out.println("direction: " + "inside");
                        System.out.println("right2: " + cells[gx][gy].right + "left2: " + cells[gx][gy].left);
                    }
                    if (direction == "LEFT") {
                        cells[gx][gy].right -= cellSize;
                        cells[gx][gy].left -= cellSize;
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