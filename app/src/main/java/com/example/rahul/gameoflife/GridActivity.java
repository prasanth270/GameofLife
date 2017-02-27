package com.example.rahul.gameoflife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GridActivity extends AppCompatActivity {

    private Button nextState;
    private Button resetGrid;
    private CanvasGridActivity canvasGridActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        canvasGridActivity = (CanvasGridActivity) findViewById(R.id.grid);
        nextState = (Button) findViewById(R.id.nextButton);
        resetGrid = (Button) findViewById(R.id.resetButton);

        nextState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvasGridActivity.nextState();
            }
        });

        resetGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvasGridActivity.resetGrid();
            }
        });
    }
}
