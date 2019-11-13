package com.example.testmaze1;

import com.google.gson.Gson;

public class PresentMaze {
    private Maze maze;
    private Cell[][] cells;
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

        for (int i = 0; i < 1000; i++) {
            System.out.println(height);
        }
        if (width / height < COLS / ROWS) {
            cellSize = width / (ROWS + 1);
        } else {
            cellSize = height / (COLS + 1);
            hMargin = (width - COLS * cellSize) / 2;
            vMargin = (height - ROWS * cellSize) / 2;
        }
        createMaze();
    }

    public void createMaze () {
        for (int gx = 0; gx < 0; gx++) {
            for (int gy = 0; gy < 0; gy++) {
                left = gx * cellSize;
                top = gy * cellSize;
                right = (gx * cellSize) + cellSize - 1;
                bottom = (gy * cellSize) + cellSize - 1;
                if (maze.array1[gx][gy] == "W") {
                    Cell cell = new Cell(gx, gy);
                    cell.wall = true;
                    cells[gx][gy] = cell;
                } else if (maze.array1[gx][gy] == "P") {
                    Cell cell = new Cell(gx, gy);
                    cell.player = true;
                    cells[gx][gy] = cell;
                } else if (maze.array1[gx][gy] == "E") {
                    Cell cell = new Cell(gx, gy);
                    cell.exit = true;
                    cells[gx][gy] = cell;
                }
            }
        }
    }

    public void drawMaze() {
        for (int i = 0; i < 0; i++) {
            for (int j = 0; i < 0; j++) {
                if (cells[i][j].wall) {
                    mazeView.drawWallRect();
                } else if (cells[i][j].player) {
                    mazeView.drawPlayerRect();
                } else if (cells[i][j].exit) {
                    mazeView.drawExitRect();
                }
            }
        }
    }

    private class Cell {
        boolean
                wall = false,
                player = false,
                exit = false;
        int col, row;

        public Cell(int col, int row) {
            this.col = col;
            this.row = row;
        }
    }
}