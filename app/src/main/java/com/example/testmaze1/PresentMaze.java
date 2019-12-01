package com.example.testmaze1;

import android.graphics.Canvas;

import com.google.gson.Gson;
import java.util.Random;

public class PresentMaze {

    private Maze maze;
    private Cell[][] cells;
    private String[][] CURRENT_ARRAY;
    private int COLS, ROWS;
    public float cellSize, hMargin, vMargin;
    private MazeView mazeView;
    private int level;
    private float HIEGHT;
    private float WIDTH;

    public PresentMaze(MazeView mazeView,int height, int width) {
        this.mazeView = mazeView;
        LoadJson loadJson = new LoadJson();
        String file = loadJson.loadJSONFromAsset(mazeView.getContext(), "maze.json");
        Gson gson = new Gson();
        maze = gson.fromJson(file, Maze.class);
        //System.out.println(file);
        HIEGHT = height;
        WIDTH = width;
        level = 1;
        presentLevel();
    }

    void presentLevel() {
        //System.out.println("Currentlevel: " + level);

        switch(level) {
            case 1:
                CURRENT_ARRAY = maze.array1;
                COLS = maze.cols1;
                ROWS = maze.rows1;
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
            case 4:
                CURRENT_ARRAY = maze.array4;
                COLS = maze.cols4;
                ROWS = maze.rows4;
                break;
        }

        if (WIDTH / HIEGHT < COLS / ROWS) {
            cellSize = WIDTH / (ROWS + 1);
        } else {
            cellSize = HIEGHT / (COLS + 1);
        }

        hMargin = (WIDTH - COLS * cellSize) / 2;
        vMargin = (HIEGHT - ROWS * cellSize) / 2;
        createMaze();
    }

    public void createMaze () {
        cells = new Cell[maze.rows4][maze.cols4];
        for (int gx = 0; gx < CURRENT_ARRAY.length; gx++) {
            for (int gy = 0; gy < CURRENT_ARRAY[gx].length; gy++) {
                Cell cell = new Cell();
                cell.left = gx * cellSize;
                cell.top = gy * cellSize;
                cell.right = ((gx * cellSize) + cellSize - 1);
                cell.bottom = ((gy * cellSize) + cellSize - 1);
                if (CURRENT_ARRAY[gx][gy].contentEquals("W")) {
                    cell.wall = true;
                    cells[gx][gy] = cell;
                } else if (CURRENT_ARRAY[gx][gy].contentEquals("P")) {
                    cell.player = true;
                    //System.out.println("left: " + cell.left);
                    cells[gx][gy] = cell;
                }  else if (CURRENT_ARRAY[gx][gy].contentEquals("M")) {
                    cell.monster = true;
                    //System.out.println("left: " + cell.left);
                    cells[gx][gy] = cell;
                } else if (CURRENT_ARRAY[gx][gy].contentEquals("E")) {
                    cell.exit = true;
                    cells[gx][gy] = cell;
                } else if (CURRENT_ARRAY[gx][gy].contentEquals("L")) {
                    cell.lazer = true;
                    cells[gx][gy] = cell;
                } else {
                    cells[gx][gy] = cell;
                }
            }
        }
    }

    public void drawMaze(Canvas canvas) {
        for (int gx = 0; gx < ROWS; gx++) {
            for (int gy = 0; gy < COLS; gy++) {
                if (cells[gx][gy].player) {
                    mazeView.drawPlayerRect(canvas, cells[gx][gy].left, cells[gx][gy].top, cells[gx][gy].right, cells[gx][gy].bottom);
                } else if (cells[gx][gy].exit) {
                    mazeView.drawExitRect(canvas, cells[gx][gy].left, cells[gx][gy].top, cells[gx][gy].right, cells[gx][gy].bottom);
                } else if (cells[gx][gy].wall) {
                    mazeView.drawWallRect(canvas, cells[gx][gy].left, cells[gx][gy].top, cells[gx][gy].right, cells[gx][gy].bottom);
                } else if (cells[gx][gy].monster) {
                    mazeView.drawMonsterRect(canvas, cells[gx][gy].left, cells[gx][gy].top, cells[gx][gy].right, cells[gx][gy].bottom);
                } else if (cells[gx][gy].lazer) {
                    mazeView.drawLazerRect(canvas, cells[gx][gy].left, cells[gx][gy].top, cells[gx][gy].right, cells[gx][gy].bottom);
                }
            }
        }
    }

    public void moveCells(String direction) {
        for (int gx = 0; gx < ROWS; gx++) {
            for (int gy = 0; gy < COLS; gy++) {
                //System.out.println("direction: " + direction);
                //System.out.println("right1: " + cells[gx][gy].right + "left1: " + cells[gx][gy].left);

                if (cells[gx][gy].player && cells[gx][gy].lazer) {
                    for (int j = 0; j < ROWS; j++) {
                        for (int i = 0; i < COLS; i++) {
                            cells[gx][gy].lazer = false;
                            if (cells[j][gy].monster || cells[gx][i].monster) {
                                cells[j][gy].lazer = true;
                                cells[gx][i].lazer = true;
                                cells[j][gy].monster = false;
                                cells[gx][i].monster = false;
                            }
                        }
                    }
                }

                if(cells[gx][gy].exit && cells[gx][gy].player) {
                    if (level < 4) {
                        level += 1;
                    } else {
                        level = 1;
                    }
                    presentLevel();
                }

                if (cells[gx][gy].player && cells[gx][gy].monster) {
                    if (level < 4) {
                        presentLevel();
                    }
                }

                if (cells[gx][gy].player) {
                    if (direction == "UP") {
                        if (!cells[gx][gy - 1].wall) {
                            cells[gx][gy].player = false;
                            cells[gx][gy - 1].player = true;
                            direction = "";
                        }
                    }
                    if (direction == "DOWN") {
                        if (!cells[gx][gy + 1].wall) {
                            cells[gx][gy].player = false;
                            cells[gx][gy + 1].player = true;
                            direction = "";
                        }
                    }
                    if (direction == "RIGHT") {
                        if (!cells[gx + 1][gy].wall) {
                            cells[gx][gy].player = false;
                            cells[gx + 1][gy].player = true;
                            direction = "";
                        }
                    }
                    if (direction == "LEFT") {
                        if (!cells[gx - 1][gy].wall) {
                            cells[gx][gy].player = false;
                            cells[gx - 1][gy].player = true;
                            direction = "";
                        }
                    }

                }
                if (cells[gx][gy].exit) {
                    Random monsterDirection = new Random();
                    int randDirection = monsterDirection.nextInt(14);
                    switch (randDirection) {
                        case 0: //up
                            if (!cells[gx][gy - 1].wall && !cells[gx][gy - 1].monster && !cells[gx][gy - 1].exit && !cells[gx][gy - 1].player) {
                                cells[gx][gy].exit = false;
                                cells[gx][gy - 1].exit = true;
                            }
                            break;
                        case 1: // down
                            if (!cells[gx][gy + 1].wall && !cells[gx][gy + 1].monster && !cells[gx][gy + 1].exit && !cells[gx][gy + 1].player) {
                                cells[gx][gy].exit = false;
                                cells[gx][gy + 1].exit = true;
                            }
                            break;
                        case 2: // right
                            if (!cells[gx - 1][gy].wall && !cells[gx - 1][gy].monster && !cells[gx - 1][gy].exit && !cells[gx - 1][gy].player) {
                                cells[gx][gy].exit = false;
                                cells[gx - 1][gy].exit = true;
                            }
                            break;
                        case 3: // left
                            if (!cells[gx + 1][gy].wall && !cells[gx + 1][gy].monster && !cells[gx + 1][gy].exit && !cells[gx + 1][gy].player) {
                                cells[gx][gy].exit = false;
                                cells[gx + 1][gy].exit = true;
                            }
                            break;
                        default:
                            break;
                    }
                }

                if (cells[gx][gy].monster) {
                    Random monsterDirection = new Random();
                    int randDirection = monsterDirection.nextInt(15);
                    switch (randDirection) {
                        case 0: //up
                            if (!cells[gx][gy - 1].wall && !cells[gx][gy - 1].monster && !cells[gx][gy - 1].exit) {
                                cells[gx][gy].monster = false;
                                cells[gx][gy - 1].monster = true;
                            }
                            break;
                        case 1: // down
                            if (!cells[gx][gy + 1].wall && !cells[gx][gy + 1].monster && !cells[gx][gy + 1].exit) {
                                cells[gx][gy].monster = false;
                                cells[gx][gy + 1].monster = true;
                            }
                            break;
                        case 2: // right
                            if (!cells[gx - 1][gy].wall && !cells[gx - 1][gy].monster && !cells[gx - 1][gy].exit) {
                                cells[gx][gy].monster = false;
                                cells[gx - 1][gy].monster = true;
                            }
                            break;
                        case 3: // left
                            if (!cells[gx + 1][gy].wall && !cells[gx + 1][gy].monster && !cells[gx + 1][gy].exit) {
                                cells[gx][gy].monster = false;
                                cells[gx + 1][gy].monster = true;
                            }
                            break;
                        case 4: // spawn
                            Random rand = new Random();
                            int spawnChance = rand.nextInt(12);
                            if (spawnChance == 0) {
                                if (!cells[gx][gy - 1].wall && !cells[gx][gy - 1].monster && !cells[gx][gy - 1].exit && !cells[gx][gy - 1].player && (gx - 1) >= 0) {
                                    cells[gx][gy - 1].monster = true;
                                    break;
                                }
                                if (!cells[gx][gy + 1].wall && !cells[gx][gy + 1].monster && !cells[gx][gy + 1].exit && !cells[gx][gy + 1].player && (gx + 1) < ROWS) {
                                    cells[gx][gy + 1].monster = true;
                                    break;
                                }
                                if (!cells[gx - 1][gy].wall && !cells[gx - 1][gy].monster && !cells[gx - 1][gy].exit && !cells[gx - 1][gy].player && (gy - 1) > 0) {
                                    cells[gx][gy - 1].monster = true;
                                    break;
                                }
                                if (!cells[gx + 1][gy].wall && !cells[gx + 1][gy].monster && !cells[gx + 1][gy].exit && !cells[gx + 1][gy].player && (gy + 1) < COLS) {
                                    cells[gx + 1][gy].monster = true;
                                    break;
                                }
                            }
                            break;
                        default :
                            break;
                    }
                }
                if (cells[gx][gy].player && cells[gx][gy].lazer) {
                    for (int j = 0; j < ROWS; j++) {
                        for (int i = 0; i < COLS; i++) {
                            cells[gx][gy].lazer = false;
                            if (cells[j][gy].monster || cells[gx][i].monster) {
                                cells[j][gy].lazer = true;
                                cells[gx][i].lazer = true;
                                cells[j][gy].monster = false;
                                cells[gx][i].monster = false;
                            }
                        }
                    }
                }

                if(cells[gx][gy].exit && cells[gx][gy].player) {
                    if (level < 4) {
                        level += 1;
                    } else {
                        level = 1;
                    }
                    presentLevel();
                    createMaze();
                }

                if (cells[gx][gy].player && cells[gx][gy].monster) {
                    presentLevel();
                    createMaze();
                }
            }
        }
    }

    private class Cell {
        boolean
                wall = false,
                player = false,
                exit = false,
                monster = false,
                lazer = false;
        float
            left = 0,
            right = 0,
            top = 0,
            bottom = 0;
        int col, row;
    }
}