package ramahe_p.android_minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomView extends View {

    private Rect[][] board;
    private Paint paintCell;

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
        board = new Rect[10][10];
        paintCell = new Paint(Paint.ANTI_ALIAS_FLAG);

        paintCell.setColor(Color.WHITE);
        paintCell.setStyle(Style.STROKE);
        paintCell.setStrokeWidth(1);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        paintCell.setStyle(Style.FILL);

        int boardWidth = canvas.getWidth();
        int boardHeight = canvas.getHeight();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = new Rect(boardWidth / (-10), boardHeight / (-10), boardWidth / 10, boardHeight / 10);
                canvas.drawRect(board[i][j], paintCell);
                canvas.translate(boardWidth / 10, 0);
            }
            canvas.translate(boardWidth * -1, boardHeight / 10);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
