package ramahe_p.android_minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CustomView extends View {

    // The states of the cells to facilitate the change of the cell
    private enum cellState {
        INIT,
        COVERED,
        MARKED,
        ONE,
        TWO,
        THREE,
        MINE,
        MINE_CLICKED
    }

    /**
     * Class to simplify the content of the cells
     * We use the cellState enumeration to control the state then the content
     */
    public static class Cell {
        cellState state;

        public Cell() {
            state = cellState.INIT;
        }

        public void setCellState(cellState state) {
            this.state = state;
        }

        public cellState getCellState() {
            return this.state;
        }
    }

    private Cell[][] board;
    private Paint paintCell, paintLine, paintCovered, paintMarked, paintMine;
    private Paint paintOne, paintTwo, paintThree;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet as) {
        super(context, as);
        init();
    }

    public CustomView(Context context, AttributeSet as, int default_style) {
        super(context, as, default_style);
        init();
    }

    private void init() {

        board = new Cell[10][10];

        paintCell = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCovered = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintMarked = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintMine = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintOne = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintTwo = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintThree = new Paint(Paint.ANTI_ALIAS_FLAG);

        paintCell.setColor(Color.BLACK);
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Style.STROKE);
        paintLine.setStrokeWidth(2.0f);
        paintCovered.setColor(Color.GRAY);
        paintMarked.setColor(Color.YELLOW);
        paintMine.setColor(Color.RED);
        paintOne.setColor(Color.BLUE);
        paintTwo.setColor(Color.GREEN);
        paintThree.setColor(Color.YELLOW);

        fillBoard();
    }

    private void putMines() {
        Random rand = new Random();

        int count = 0;
        while (count < 20) {
            for (int mine = 0; mine < 20; mine++) {
                int width = rand.nextInt(9);
                int height = rand.nextInt(9);

                if (board[width][height].getCellState() != cellState.MINE) {
                    board[width][height].setCellState(cellState.MINE);
                    count++;
                }
                // if there's already 20 mines we stop
                if (count == 20) {
                    break;
                }
            }
            System.out.println("count: " + count);
        }
    }

    /**
     * Fills the board on its initial state
     * Used when we launch or reset the game
     */
    private void fillBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = new Cell();
                board[i][j].setCellState(cellState.INIT);
            }
        }
        putMines();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int boardWidth = canvas.getWidth(); // 720 ==> width of the custom view/canvas
        int boardHeight = canvas.getHeight(); // 720 ==> height of the custom view/canvas
        int endWidth = boardWidth / 10; // 72 ==> width of a cell
        int endHeight = boardHeight / 10; // 72 ==> height of a cell

        // Loop to fill the CustomView with cells
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int startWidth = i * endWidth;
                int startHeight = j * endHeight;

                canvas.drawRect(startWidth, startHeight, startWidth + endWidth, startHeight + endHeight, paintLine);

                if (board[i][j].getCellState() == cellState.INIT || board[i][j].getCellState() == cellState.MINE) {
                    canvas.drawRect(startWidth, startHeight, startWidth + endWidth, startHeight + endHeight, paintCell);
                } else if (board[i][j].state == cellState.COVERED) {
                    canvas.drawRect(startWidth, startHeight, startWidth + endWidth, startHeight + endHeight, paintCovered);
                } else if (board[i][j].state == cellState.MINE_CLICKED) {
                    canvas.drawRect(startWidth, startHeight, startWidth + endWidth, startHeight + endHeight, paintMine);
                }
            }
        }
        canvas.restore();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {

            // get the exact cell I click by getting the values of the cell array
            // not by getting the coordinates on the custom view
            int boardWidth = (int) event.getX() * 10 / getMeasuredWidth();
            int boardHeight = (int) event.getY() * 10 / getMeasuredHeight();

//            System.out.println("board: " + board[boardWidth][boardHeight].getCellState());

            // Basic cover of the cell
            if (board[boardWidth][boardHeight].getCellState() == cellState.MINE || board[boardWidth][boardHeight].getCellState() == cellState.MINE_CLICKED) {
                board[boardWidth][boardHeight].setCellState(cellState.MINE_CLICKED);
                invalidate();
                return true;
            } else {
                board[boardWidth][boardHeight].setCellState(cellState.COVERED);
                invalidate();
                return true;
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
