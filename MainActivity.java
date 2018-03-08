package com.example.emilylien.minesweeper;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#524848"));

        MinesweeperModel.getInstance().setMines();
        Log.d("mines", "" + MinesweeperModel.getInstance().getCell(0, 0)
                + MinesweeperModel.getInstance().getCell(0, 1)
                + MinesweeperModel.getInstance().getCell(0, 2)
                + MinesweeperModel.getInstance().getCell(0, 3)
                + MinesweeperModel.getInstance().getCell(0, 4)
        );

        Log.d("mines", "" + MinesweeperModel.getInstance().getCell(0, 0)
                + MinesweeperModel.getInstance().getCell(1, 1)
                + MinesweeperModel.getInstance().getCell(1, 2)
                + MinesweeperModel.getInstance().getCell(1, 3)
                + MinesweeperModel.getInstance().getCell(1, 4)
        );

        Log.d("mines", "" + MinesweeperModel.getInstance().getCell(2, 0)
                + MinesweeperModel.getInstance().getCell(2, 1)
                + MinesweeperModel.getInstance().getCell(2, 2)
                + MinesweeperModel.getInstance().getCell(2, 3)
                + MinesweeperModel.getInstance().getCell(2, 4)
        );

        Log.d("mines", "" + MinesweeperModel.getInstance().getCell(3, 0)
                + MinesweeperModel.getInstance().getCell(3, 1)
                + MinesweeperModel.getInstance().getCell(3, 2)
                + MinesweeperModel.getInstance().getCell(3, 3)
                + MinesweeperModel.getInstance().getCell(3, 4)
        );

        Log.d("mines", "" + MinesweeperModel.getInstance().getCell(4, 0)
                + MinesweeperModel.getInstance().getCell(4, 1)
                + MinesweeperModel.getInstance().getCell(4, 2)
                + MinesweeperModel.getInstance().getCell(4, 3)
                + MinesweeperModel.getInstance().getCell(4, 4)
        );
    }

    public void toastItUp(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
