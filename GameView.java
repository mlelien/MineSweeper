package com.example.emilylien.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by emilylien on 3/1/18.
 */

public class GameView extends View {

    private Paint backgroundBrush;
    private Paint tappedBrush;
    private Paint flagBrush;
    private Paint textBrush;
    private Paint mineBrush;

    private boolean gameOn;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        gameOn = true;

        backgroundBrush = new Paint();
        backgroundBrush.setColor(Color.parseColor("#F2F2F2"));
        backgroundBrush.setStrokeWidth(10);

        tappedBrush = new Paint();
        tappedBrush.setColor(Color.parseColor("#524848"));

        flagBrush = new Paint();
        flagBrush.setColor(Color.WHITE);

        textBrush = new Paint();
        textBrush.setColor(Color.WHITE);
        textBrush.setTextSize(80);

        mineBrush = new Paint();
        mineBrush.setColor(Color.RED);

        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {0xFFFF6A6A, 0xFFFFB823});
        setBackground(gradientDrawable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBackground(canvas);
        drawTapped(canvas);
        drawAdjacentMines(canvas);
    }

    private void drawBackground(Canvas canvas) {
        // five vertical lines
        canvas.drawLine(0, 0, 0, getHeight(), backgroundBrush);
        canvas.drawLine(getWidth() / 5, 0, getWidth() / 5, getHeight(), backgroundBrush);
        canvas.drawLine(getWidth() * 2 / 5, 0, getWidth() * 2/ 5, getHeight(), backgroundBrush);
        canvas.drawLine(getWidth() * 3 / 5, 0, getWidth() * 3 / 5, getHeight(), backgroundBrush);
        canvas.drawLine(getWidth() * 4 / 5, 0, getWidth() * 4 / 5, getHeight(), backgroundBrush);
        canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), backgroundBrush);

        // five horizontal lines
        canvas.drawLine(0, 0, getWidth(), 0, backgroundBrush);
        canvas.drawLine(0, getHeight() / 5, getWidth(), getHeight() / 5, backgroundBrush);
        canvas.drawLine(0, getHeight() * 2 / 5, getWidth(), getHeight() * 2 / 5, backgroundBrush);
        canvas.drawLine(0, getHeight() * 3 / 5, getWidth(), getHeight() * 3 / 5, backgroundBrush);
        canvas.drawLine(0, getHeight() * 4 / 5, getWidth(), getHeight() * 4 / 5, backgroundBrush);
        canvas.drawLine(0, getHeight(), getHeight(), getWidth(), backgroundBrush);
    }

    private void drawTapped(Canvas canvas) {
        for (int row = 0; row < MinesweeperModel.BOARD_SIZE; row++) {
            for (int col = 0; col < MinesweeperModel.BOARD_SIZE; col++) {
                short cell = MinesweeperModel.getInstance().getCell(row, col);

                if (cell == MinesweeperModel.TAPPED) {
                    canvas.drawRect(getWidth() * col / 5, getHeight() * row / 5,
                            getWidth() * (col + 1) / 5, getHeight() * (row + 1) / 5, tappedBrush);
                }

                else if (cell == MinesweeperModel.FLAGGED || cell == MinesweeperModel.FLAGGED_MINE) {
                    int radius = getWidth() / 5 / 4;
                    int cellWidth = getWidth() / 5;
                    int cellHeight = getHeight() / 5;
                    canvas.drawCircle(cellWidth * col + cellWidth / 2,  cellHeight * row + cellHeight / 2, radius,  flagBrush);
                }

                else if (cell == MinesweeperModel.TAPPED_MINE) {
                    gameOn = false;
                    canvas.drawRect(getWidth() * col / 5, getHeight() * row / 5,
                            getWidth() * (col + 1) / 5, getHeight() * (row + 1) / 5, mineBrush);
                    ((MainActivity) getContext()).toastItUp("LOST");
                    return;
                }

                if (MinesweeperModel.getInstance().checkWin()) {
                    gameOn = false;
                    ((MainActivity) getContext()).toastItUp("WIN");
                }
            }
        }
    }

    private void drawAdjacentMines(Canvas canvas) {
        for (int row = 0; row < MinesweeperModel.BOARD_SIZE; row++) {
            for (int col = 0; col < MinesweeperModel.BOARD_SIZE; col++) {
                if (MinesweeperModel.getInstance().getCell(row, col) == MinesweeperModel.TAPPED
                        && MinesweeperModel.getInstance().getAdjacentMines(row, col) > 0) {
                    int cellWidth = getWidth() / 5;
                    int cellHeight = getHeight() / 5;

                    canvas.drawText("" + MinesweeperModel.getInstance().getAdjacentMines(row, col),
                            cellWidth * col + cellWidth / 2 - cellWidth / 15, cellHeight * row + cellHeight * 2 / 3, textBrush);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gameOn)
            return gestureDetector.onTouchEvent(event);
        return true;
    }

    final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            int col = ((int) e.getX()) / (getWidth() / 5);
            int row = ((int) e.getY()) / (getHeight() / 5);

            MinesweeperModel.getInstance().setTapped(row, col);

            invalidate();

            return true;
        }

        public void onLongPress(MotionEvent event) {
            int col = ((int) event.getX()) / (getWidth() / 5);
            int row = ((int) event.getY()) / (getHeight() / 5);

            MinesweeperModel.getInstance().setFlag(row, col);

            invalidate();

            super.onLongPress(event);
       }
    });

}
