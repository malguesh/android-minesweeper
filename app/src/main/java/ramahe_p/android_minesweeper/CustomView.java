package ramahe_p.android_minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class CustomView extends View {

    // The states of the cells to facilitate the change of the cell
    private enum cellState {
        INIT,
        COVERED,
        MARKED,
        MINE, MINE_CLICKED,
        ONE, ONE_CLICKED,
        TWO, TWO_CLICKED,
        THREE, THREE_CLICKED,
        FOUR, FOUR_CLICKED,
        FIVE, FIVE_CLICKED,
        SIX, SIX_CLICKED,
        SEVEN, SEVEN_CLICKED,
        EIGHT, EIGHT_CLICKED

    }

    /**
     * Class to simplify the content of the cells
     * We use the cellState enumeration to control the state then the content
     */
    public static class Cell {
        cellState state;
        boolean isMine = false;
        boolean isNumber = false;
        boolean isClicked = false;

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

    private boolean touch = true;
    private Cell[][] board;
    private Paint paintCell, paintLine, paintCovered, paintMarked, paintMine;
    private Paint paintTextMine, paintTextOne, paintTextTwo, paintTextThree, paintTextFour;
    private int minesNumber = 0;
    public boolean isMarkingMode = false;
    public int markedMines = 0;

    // last state of a cell to go back in it when we erase a marked cell
    cellState lastState;

    // -- LifeCycle
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
        paintTextMine = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintTextOne = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintTextTwo = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintTextThree = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintTextFour = new Paint(Paint.ANTI_ALIAS_FLAG);

        paintCell.setColor(Color.BLACK);
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Style.STROKE);
        paintLine.setStrokeWidth(2.0f);
        paintCovered.setColor(Color.GRAY);
        paintMarked.setColor(Color.YELLOW);
        paintMine.setColor(Color.RED);

        // TODO: change the paintTexts size to be bigger
        paintTextMine.setColor(Color.BLACK);
        paintTextOne.setColor(Color.BLUE);
        paintTextTwo.setColor(Color.GREEN);
        paintTextThree.setColor(Color.YELLOW);
        paintTextFour.setColor(Color.RED);

        fillBoard();
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

                // Check the state of the cell to put the good thing
                if (board[i][j].getCellState() == cellState.MARKED) {
                    canvas.drawRect(startWidth, startHeight, startWidth + endWidth, startHeight + endHeight, paintMarked);
                } else if (board[i][j].getCellState() == cellState.COVERED) {
                    canvas.drawRect(startWidth, startHeight, startWidth + endWidth, startHeight + endHeight, paintCovered);
                } else if (board[i][j].getCellState() == cellState.MINE_CLICKED) {
                    canvas.drawRect(startWidth, startHeight, startWidth + endWidth, startHeight + endHeight, paintMine);
                    canvas.drawText("M", startWidth + endWidth / 2, startHeight + endHeight / 2, paintTextMine);
                } else if (board[i][j].getCellState() == cellState.ONE_CLICKED) {
                    canvas.drawRect(startWidth, startHeight, startWidth + endWidth, startHeight + endHeight, paintCovered);
                    canvas.drawText("1", startWidth + endWidth / 2, startHeight + endHeight / 2, paintTextOne);
                } else if (board[i][j].getCellState() == cellState.TWO_CLICKED) {
                    canvas.drawRect(startWidth, startHeight, startWidth + endWidth, startHeight + endHeight, paintCovered);
                    canvas.drawText("2", startWidth + endWidth / 2, startHeight + endHeight / 2, paintTextTwo);
                } else if (board[i][j].getCellState() == cellState.THREE_CLICKED) {
                    canvas.drawRect(startWidth, startHeight, startWidth + endWidth, startHeight + endHeight, paintCovered);
                    canvas.drawText("3", startWidth + endWidth / 2, startHeight + endHeight / 2, paintTextThree);
                } else if (board[i][j].getCellState() == cellState.FOUR_CLICKED) {
                    canvas.drawRect(startWidth, startHeight, startWidth + endWidth, startHeight + endHeight, paintCovered);
                    canvas.drawText("4", startWidth + endWidth / 2, startHeight + endHeight / 2, paintTextFour);
                } else if (board[i][j].getCellState() == cellState.FIVE_CLICKED) {
                    canvas.drawRect(startWidth, startHeight, startWidth + endWidth, startHeight + endHeight, paintCovered);
                    canvas.drawText("5", startWidth + endWidth / 2, startHeight + endHeight / 2, paintTextFour);
                } else if (board[i][j].getCellState() == cellState.SIX_CLICKED) {
                    canvas.drawRect(startWidth, startHeight, startWidth + endWidth, startHeight + endHeight, paintCovered);
                    canvas.drawText("6", startWidth + endWidth / 2, startHeight + endHeight / 2, paintTextFour);
                } else if (board[i][j].getCellState() == cellState.SEVEN_CLICKED) {
                    canvas.drawRect(startWidth, startHeight, startWidth + endWidth, startHeight + endHeight, paintCovered);
                    canvas.drawText("7", startWidth + endWidth / 2, startHeight + endHeight / 2, paintTextFour);
                } else if (board[i][j].getCellState() == cellState.EIGHT_CLICKED) {
                    canvas.drawRect(startWidth, startHeight, startWidth + endWidth, startHeight + endHeight, paintCovered);
                    canvas.drawText("8", startWidth + endWidth / 2, startHeight + endHeight / 2, paintTextFour);
                } else {
                    canvas.drawRect(startWidth, startHeight, startWidth + endWidth, startHeight + endHeight, paintCell);
                }
            }
        }

    }

    // -- Events
    public boolean onTouchEvent(MotionEvent event) {
        if (touch) {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {

                // get the exact cell I click by getting the values of the cell array
                // not by getting the coordinates on the custom view
                int boardWidth = (int) event.getX() * 10 / getMeasuredWidth();
                int boardHeight = (int) event.getY() * 10 / getMeasuredHeight();
                Cell touchedCell = board[boardWidth][boardHeight];

                if (isMarkingMode) {
                    if (touchedCell.getCellState() != cellState.MARKED) {
                        if (!touchedCell.isClicked) {
                            lastState = touchedCell.getCellState();
                            touchedCell.setCellState(cellState.MARKED);
                            markedMines += 1;
                            invalidate();
                            return true;
                        }
                    } else {
                        touchedCell.setCellState(lastState);
                        if (markedMines > 0)
                            markedMines -= 1;
                        invalidate();
                        return true;
                    }
                } else {
                    if (!touchedCell.isClicked) {
                        if (touchedCell.getCellState() != cellState.MARKED) {
                            if (touchedCell.getCellState() == cellState.MINE || touchedCell.getCellState() == cellState.MINE_CLICKED) {
                                touchedCell.setCellState(cellState.MINE_CLICKED);
                                touchedCell.isClicked = true;
                                touch = false;
                                invalidate();
                                return true;
                            } else if (touchedCell.getCellState() == cellState.ONE || touchedCell.getCellState() == cellState.ONE_CLICKED) {
                                board[boardWidth][boardHeight].setCellState(cellState.ONE_CLICKED);
                                touchedCell.isClicked = true;
                                invalidate();
                                return true;
                            } else if (touchedCell.getCellState() == cellState.TWO || touchedCell.getCellState() == cellState.TWO_CLICKED) {
                                board[boardWidth][boardHeight].setCellState(cellState.TWO_CLICKED);
                                touchedCell.isClicked = true;
                                invalidate();
                                return true;
                            } else if (touchedCell.getCellState() == cellState.THREE || touchedCell.getCellState() == cellState.THREE_CLICKED) {
                                touchedCell.setCellState(cellState.THREE_CLICKED);
                                touchedCell.isClicked = true;
                                invalidate();
                                return true;
                            } else if (touchedCell.getCellState() == cellState.FOUR || touchedCell.getCellState() == cellState.FOUR_CLICKED) {
                                touchedCell.setCellState(cellState.FOUR_CLICKED);
                                touchedCell.isClicked = true;
                                invalidate();
                                return true;
                            } else if (touchedCell.getCellState() == cellState.FIVE || touchedCell.getCellState() == cellState.FIVE_CLICKED) {
                                touchedCell.setCellState(cellState.FIVE_CLICKED);
                                touchedCell.isClicked = true;
                                invalidate();
                                return true;

                            } else if (touchedCell.getCellState() == cellState.SIX || touchedCell.getCellState() == cellState.SIX_CLICKED) {
                                touchedCell.setCellState(cellState.SIX_CLICKED);
                                touchedCell.isClicked = true;
                                invalidate();
                                return true;

                            } else if (touchedCell.getCellState() == cellState.SEVEN || touchedCell.getCellState() == cellState.SEVEN_CLICKED) {
                                touchedCell.setCellState(cellState.SEVEN_CLICKED);
                                touchedCell.isClicked = true;
                                invalidate();
                                return true;

                            } else if (touchedCell.getCellState() == cellState.EIGHT || touchedCell.getCellState() == cellState.EIGHT_CLICKED) {
                                touchedCell.setCellState(cellState.EIGHT_CLICKED);
                                touchedCell.isClicked = true;
                                invalidate();
                                return true;

                            } else {
                                touchedCell.setCellState(cellState.COVERED);
                                touchedCell.isClicked = true;
                                invalidate();
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    // -- Utilities

    /**
     * Put randomly 20 mines on the board
     */

    private void putMines() {
        Random rand = new Random();

        int count = 0;
        while (count < 20) {
            for (int mine = 0; mine < 20; mine++) {
                int width = rand.nextInt(9);
                int height = rand.nextInt(9);

                if (board[width][height].getCellState() != cellState.MINE) {
                    board[width][height].setCellState(cellState.MINE);
                    board[width][height].isMine = true;
                    count++;
                }
                // if there's already 20 mines we stop
                if (count == 20) {
                    break;
                }
            }
        }
    }

    /**
     * Get the exact number of mine around every cells
     *
     * @param width
     * @param height
     * @return
     */
    private int getMinesAround(int width, int height) {
        if (board[width][height].getCellState() != cellState.MINE) {
            minesNumber = 0;

            if (width > 0) {
                Cell left = board[width - 1][height];
                if (left.getCellState() == cellState.MINE || left.getCellState() == cellState.MINE_CLICKED)
                    minesNumber++;
            }
            if (height > 0) {
                Cell up = board[width][height - 1];
                if (up.getCellState() == cellState.MINE || up.getCellState() == cellState.MINE_CLICKED)
                    minesNumber++;
            }
            if (width < 9) {
                // 2 1
                Cell right = board[width + 1][height];
                if (right.getCellState() == cellState.MINE || right.getCellState() == cellState.MINE_CLICKED)
                    minesNumber++;
            }
            if (height < 9) {
                Cell down = board[width][height + 1];
                if (down.getCellState() == cellState.MINE || down.getCellState() == cellState.MINE_CLICKED)
                    minesNumber++;
            }
            if (width > 0 && height > 0) {
                Cell diagLeftUp = board[width - 1][height - 1];
                if (diagLeftUp.getCellState() == cellState.MINE || diagLeftUp.getCellState() == cellState.MINE_CLICKED)
                    minesNumber++;
            }
            if (width < 9 && height < 9) {
                Cell diagRightDown = board[width + 1][height + 1];
                if (diagRightDown.getCellState() == cellState.MINE || diagRightDown.getCellState() == cellState.MINE_CLICKED)
                    minesNumber++;
            }
            if (width > 0 && height < 9) {
                Cell diagLeftDown = board[width - 1][height + 1];
                if (diagLeftDown.getCellState() == cellState.MINE || diagLeftDown.getCellState() == cellState.MINE_CLICKED) {
                    minesNumber++;
                }
            }
            if (width < 9 && height > 0) {
                Cell diagRightUp = board[width + 1][height - 1];
                if (diagRightUp.getCellState() == cellState.MINE || diagRightUp.getCellState() == cellState.MINE_CLICKED) {
                    minesNumber++;
                }
            }
        }
        return minesNumber;
    }

    /**
     * Fill the board with the state (mine, number...)
     * corresponding on the content of the cell
     */
    private void putNumbers() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j].getCellState() != cellState.MINE) {
                    minesNumber = getMinesAround(i, j);
                    switch (minesNumber) {
                        case 0:
                            board[i][j].setCellState(cellState.INIT);
                            board[i][j].isNumber = true;
                            break;
                        case 1:
                            board[i][j].setCellState(cellState.ONE);
                            board[i][j].isNumber = true;
                            break;
                        case 2:
                            board[i][j].setCellState(cellState.TWO);
                            board[i][j].isNumber = true;
                            break;
                        case 3:
                            board[i][j].setCellState(cellState.THREE);
                            board[i][j].isNumber = true;
                            break;
                        case 4:
                            board[i][j].setCellState(cellState.FOUR);
                            board[i][j].isNumber = true;
                            break;
                        case 5:
                            board[i][j].setCellState(cellState.FIVE);
                            board[i][j].isNumber = true;
                            break;
                        case 6:
                            board[i][j].setCellState(cellState.SIX);
                            board[i][j].isNumber = true;
                            break;
                        case 7:
                            board[i][j].setCellState(cellState.SEVEN);
                            board[i][j].isNumber = true;
                            break;
                        case 8:
                            board[i][j].setCellState(cellState.EIGHT);
                            board[i][j].isNumber = true;
                            break;

                    }
                }
            }
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
        putNumbers();
    }

    /**
     * Reset game when clicking on the reset button
     */
    public void resetGame() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j].setCellState(cellState.INIT);
            }
        }
        init();
        touch = true;
        invalidate();
    }

    public void setMarkingMode() {
        if (!isMarkingMode) {
            isMarkingMode = true;
        } else
            isMarkingMode = false;
    }
}
