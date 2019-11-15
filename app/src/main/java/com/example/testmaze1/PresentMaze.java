package com.example.testmaze1;

import android.graphics.Canvas;

import com.google.gson.Gson;

public class PresentMaze {

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

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


        if (width / height < COLS / ROWS) {
            cellSize = width / (ROWS + 1);
        } else {
            cellSize = height / (COLS + 1);
        }

        hMargin = (width - COLS * cellSize) / 2;
        vMargin = (height - ROWS * cellSize) / 2;

        createMaze();
    }

    public void createMaze () {
        cells = new Cell[maze.rows1][maze.cols1];
        for (int gx = 0; gx < maze.array1.length; gx++) {
            for (int gy = 0; gy < maze.array1[gx].length; gy++) {
                if (maze.array1[gx][gy].contentEquals("W")) {
                    Cell cell = new Cell(gx, gy);
                    cell.wall = true;
                    cells[gx][gy] = cell;
                } else if (maze.array1[gx][gy].contentEquals("P")) {
                    Cell cell = new Cell(gx, gy);
                    cell.player = true;
                    cells[gx][gy] = cell;
                } else if (maze.array1[gx][gy].contentEquals("E")) {
                    Cell cell = new Cell(gx, gy);
                    cell.exit = true;
                    cells[gx][gy] = cell;
                } else {
                    Cell cell = new Cell(gx, gy);
                    cells[gx][gy] = cell;
                }
            }
        }
    }

    public void drawMaze(Canvas canvas) {
        for (int gx = 0; gx < maze.array1.length; gx++) {
            for (int gy = 0; gy < maze.array1[gx].length; gy++) {
                cells[gx][gy].left = gx * cellSize;
                cells[gx][gy].top = gy * cellSize;
                cells[gx][gy].right = ((gx * cellSize) + cellSize - 1);
                cells[gx][gy].bottom = ((gy * cellSize) + cellSize - 1);

                if (cells[gx][gy].wall) {
                    mazeView.drawWallRect(canvas, cells[gx][gy].left, cells[gx][gy].right, cells[gx][gy].top, cells[gx][gy].bottom);
                }
                if (cells[gx][gy].player) {
                    mazeView.drawPlayerRect(canvas, cells[gx][gy].left, cells[gx][gy].right, cells[gx][gy].top, cells[gx][gy].bottom);
                }
                if (cells[gx][gy].exit) {
                    mazeView.drawExitRect(canvas, cells[gx][gy].left, cells[gx][gy].right, cells[gx][gy].top, cells[gx][gy].bottom);
                }
            }
        }

    }

    public void movePlayer(Direction direction) {
        for (int gx = 0; gx < cells.length; gx++) {
            for (int gy = 0; gy < cells[gx].length; gy++) {
                if (cells[gx][gy].player) {
                    if (direction == direction.UP) {
                        cells[gx][gy].top += cellSize;
                        cells[gx][gy].bottom -= cellSize;
                    }
                    if (direction == direction.DOWN) {
                        cells[gx][gy].top -= cellSize;
                        cells[gx][gy].bottom += cellSize;
                    }
                    if (direction == direction.RIGHT) {
                        cells[gx][gy].right += cellSize;
                        cells[gx][gy].left -= cellSize;
                    }
                    if (direction == direction.LEFT) {
                        cells[gx][gy].right -= cellSize;
                        cells[gx][gy].left += cellSize;
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

        public Cell(int col, int row) {
            this.col = col;
            this.row = row;
        }
    }
}