package com.example.rahul.gameoflife;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CanvasGridActivity extends View {

    private int cellWidth;
    private int cellHeight;
    private boolean[][] currentState;
    private boolean[][] nextState;
    private Paint liveCell = new Paint();
    private Paint deadCell = new Paint();
    private Paint drawLine = new Paint();
    private Paint background = new Paint();

    public void setCellWidth(int width) {
        this.cellWidth = width;
    }

    public int getCellWidth() {
        return this.cellWidth;
    }

    public void setCellHeight(int height) {
        this.cellHeight = height;
    }

    public int getCellHeight() {
        return this.cellHeight;
    }

    /* --Default Constructors-- */
    public CanvasGridActivity(Context context) {
        super(context);

        createGrid();
        setWillNotDraw(false);
    }

    public CanvasGridActivity(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        background.setStyle(Paint.Style.FILL_AND_STROKE);
        background.setColor(Color.BLUE);

        drawLine.setStyle(Paint.Style.FILL_AND_STROKE);
        drawLine.setColor(Color.WHITE);
        drawLine.setStrokeWidth(2);

        liveCell.setStyle(Paint.Style.FILL_AND_STROKE);
        liveCell.setColor(Color.RED);

        deadCell.setStyle(Paint.Style.FILL_AND_STROKE);
        deadCell.setColor(Color.BLUE);
    }

    /* --Default Constructors-- */

    /* Override OnDraw to draw on View using Canvas*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Paint background with blue
        canvas.drawColor(Color.BLUE);
        int width = getWidth();
        int height = getHeight();

        int cellWidth = getCellWidth();
        int cellHeight = getCellHeight();

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                if (currentState[i][j]) {
                    canvas.drawRect(i * cellWidth, j * cellHeight, (i +1) * cellWidth, (j+1) * cellHeight, liveCell);
                } else {
                    canvas.drawRect(i * cellWidth, j * cellHeight, (i +1) * cellWidth, (j+1) * cellHeight, deadCell);
                }
            }
        }

        for (int i = 1; i < 12; i ++) {
            canvas.drawLine(i * cellWidth, 0, i * cellWidth, height, drawLine);
        }

        for (int i = 1; i < 12; i++) {
            canvas.drawLine(0, i * cellHeight, width, i * cellHeight, drawLine);
        }
    }

    private void createGrid() {
        //Get View's Width & Height
        int width = getWidth();
        int height = getHeight();

        //Compute a Cell Width & Height
        width /= 12;
        height /=12;

        //Initialize GridSize Array
        currentState = new boolean[12][12];

        setCellWidth(width);
        setCellHeight(height);

        invalidate();
    }

    //Orientation Change
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createGrid();
    }

    // Touch Events for each grid
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int width = getCellWidth();
        int height = getCellHeight();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int column = (int) (event.getX() / width);
            int row = (int) (event.getY() / height);

            // Change state from Dead to Live or from Live to Dead by setting the opposite flag
            currentState[column][row] = !currentState[column][row];
        }

        invalidate();
        return true;
    }

    public void nextState() {
        int neighbors;
        nextState = new boolean[12][12];

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                 neighbors = checkNeighbours(currentState, i, j);

                if (neighbors > 3 || neighbors < 2) {
                    nextState[i][j] = false;
                } else if (neighbors == 3) {
                    nextState[i][j] = true;
                } else if (currentState[i][j]) {
                    if (neighbors >=2 && neighbors <= 3) {
                        nextState[i][j] = true;
                    }
                }
            }
        }

        currentState = nextState;
        invalidate();
    }

    private int checkNeighbours(boolean[][] grid, int i, int j) {
        int c = 0;

        if (grid[i][j]) {
            c = -1;
        }

        for (int x = Math.max(i-1, 0); x <= Math.min(i+1, 11); x++) {
            for (int y = Math.max(j-1, 0); y <= Math.min(j+1, 11); y++) {
                if (grid[x][y]) {
                    c++;
                }
            }
        }
        return c;
    }

    public void resetGrid() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Alert !");
        alertDialog.setMessage("Do You want to Reset the Grid?");

        // Clear Grid
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentState = new boolean[12][12];
                invalidate();
                dialog.dismiss();
            }
        });

        //Dismiss Alert and stay on current grid position
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        //Show Alert
        alertDialog.show();
    }
}
