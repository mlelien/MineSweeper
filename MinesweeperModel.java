package com.example.emilylien.minesweeper;

import android.util.Log;

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
    public final static short TAPPED_MINE = 4;

    public final static short BOARD_SIZE = 5;

    private final static short NUM_MINES = 3;

    private short[][] board = {
            {FREE, FREE, FREE, FREE, FREE},
            {FREE, FREE, FREE, FREE, FREE},
            {FREE, FREE, FREE, FREE, FREE},
            {FREE, FREE, FREE, FREE, FREE},
            {FREE, FREE, FREE, FREE, FREE},
    };

    private short[][] adjacentMinesBoard = {
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
    };

    public void setMines() {
        Random random = new Random(System.currentTimeMillis());
        int row = random.nextInt(5);
        int col = random.nextInt(5);

        for (int i = 0; i < NUM_MINES; i++) {
            while (board[row][col] == MINE) {
                row = random.nextInt(5);
                col = random.nextInt(5);
            }

            board[row][col] = MINE;
        }
    }

    public boolean checkMine(int row, int col) {
         if (board[row][col] == MINE)
             return true;
         return false;
    }

    public void setFlag(int x, int y) {
        if (board[x][y] == FLAGGED)
            board[x][y] = FREE;
        else if (board[x][y] == MINE)
            board[x][y] = FLAGGED_MINE;
        else if (board[x][y] == FLAGGED_MINE)
            board[x][y] = MINE;
        else if (board[x][y] == FREE)
            board[x][y] = FLAGGED;
    }

    public void setTapped(int row, int col) {
        if (checkMine(row, col)) {
            board[row][col] = TAPPED_MINE;
            return;
        }

        board[row][col] = TAPPED;

        short mineAdjacent = 0;

        for (int i = row-1; i <= row+1; i++) {
            for (int j = col-1; j <= col+1; j++) {
                if (i >= 0 && i < BOARD_SIZE && j >= 0 && j < BOARD_SIZE && (board[i][j] == MINE || board[i][j] == FLAGGED_MINE))
                    mineAdjacent++;
            }
        }

        if (mineAdjacent > 0)
            adjacentMinesBoard[row][col] = mineAdjacent;
    }

    public short getCell(int row, int col) {
        return board[row][col];
    }

    public short getAdjacentMines(int row, int col) {
        return adjacentMinesBoard[row][col];
    }

    public boolean checkWin() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == FLAGGED)
                    return false;

                if (board[i][j] == MINE)
                    return false;
            }
        }

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == FREE)
                    board[i][j] = TAPPED;
            }
        }
        return true;
    }
}
