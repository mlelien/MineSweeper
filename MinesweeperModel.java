package com.example.emilylien.minesweeper;

import java.util.Random;

/**
 * Created by emilylien on 3/2/18.
 */

public class MinesweeperModel {
    private static MinesweeperModel instance = null;

    public static MinesweeperModel getInstance() {
        if (instance == null) {
            instance = new MinesweeperModel();
        }

        return instance;
    }

    public final static short MINE = -1;
    public final static short FREE = 0;
    public final static short TAPPED = 1;
    public final static short FLAGGED = 2;
    public final static short FLAGGED_MINE = 3;

    public final static short BOARD_SIZE = 5;

    private final static short NUM_MINES = 3;

    private short[][] board = {
            {FREE, FREE, FREE, FREE, FREE},
            {FREE, FREE, FREE, FREE, FREE},
            {FREE, FREE, FREE, FREE, FREE},
            {FREE, FREE, FREE, FREE, FREE},
            {FREE, FREE, FREE, FREE, FREE},
    };

    private void setMines() {
        for (int i = 0; i < NUM_MINES; i++) {
            int[] cell = getRandomBoardCell();
            while (board[cell[0]][cell[1]] != MINE)
                cell = getRandomBoardCell();

            board[cell[0]][cell[1]] = MINE;
        }
    }

    public short checkMine(int x, int y) {
        return board[x][y];
    }

    public void setFlag(int x, int y) {
        if (board[x][y] == FLAGGED)
            board[x][y] = FREE;
        else if (board[x][y] == FLAGGED_MINE)
            board[x][y] = MINE;
        else if (board[x][y] == FREE)
            board[x][y] = FLAGGED;
    }

    public int setTapped(int x, int y) {
        board[x][y] = TAPPED;

        int mineAdjacent = 0;

        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j < y+1; j++) {
                if (board[i][j] == MINE)
                    mineAdjacent++;
            }
        }

        return mineAdjacent;
    }

    private int[] getRandomBoardCell() {
        Random random = new Random();
        return new int[] {random.nextInt(NUM_MINES), random.nextInt(NUM_MINES)};
    }

    public short getCell(int x, int y) {
        return board[x][y];
    }
}
