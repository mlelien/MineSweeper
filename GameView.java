package com.example.emilylien.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by emilylien on 3/1/18.
 */

public class GameView extends View {

    private Paint backgroundBrush;
    private Paint tappedBrush;

    private ImageView flagImage;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        backgroundBrush = new Paint();
        tappedBrush = new Paint();

        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {0xFFFF6A6A, 0xFFFFB823});
        setBackground(gradientDrawable);

        flagImage = new ImageView(getContext());
        flagImage.setImageResource(R.drawable.flag);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBackground(canvas);
        drawTapped(canvas);
    }

    private void drawBackground(Canvas canvas) {
        backgroundBrush.setColor(Color.parseColor("#F2F2F2"));
        backgroundBrush.setStrokeWidth(10);

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
        tappedBrush.setColor(Color.parseColor("#524848"));

        for (int row = 0; row < MinesweeperModel.BOARD_SIZE; row++) {
            for (int col = 0; col < MinesweeperModel.BOARD_SIZE; col++) {
                short cell = MinesweeperModel.getInstance().getCell(row, col);

                if (cell == MinesweeperModel.TAPPED) {
                    canvas.drawRect(getWidth() * col / 5, getHeight() * row / 5,
                            getWidth() * (col + 1) / 5, getHeight() * (row + 1) / 5, tappedBrush);
                }

                else if (cell == MinesweeperModel.FLAGGED || cell == MinesweeperModel.FLAGGED_MINE) {
                    canvas.drawRect(getWidth() * col / 5, getHeight() * row / 5,
                            getWidth() * (col + 1) / 5, getHeight() * (row + 1) / 5, tappedBrush);

//                    LinearLayout linearLayout = new LinearLayout(this);
//                    linearLayout.setOrientation(LinearLayout.VERTICAL);
//
//                    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

                    flagImage.setLayoutParams(new LinearLayout.LayoutParams(getWidth() / 5, getHeight() / 5));


                }
            }
        }
    }
}
