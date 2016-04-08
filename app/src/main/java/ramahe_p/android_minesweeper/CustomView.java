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
    private Rect cell;
    private Paint paintCell;
    private Paint paintLine;

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
        paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);

        paintCell.setColor(Color.BLACK);
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Style.STROKE);
        paintLine.setStrokeWidth(2.0f);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int boardWidth = canvas.getWidth();
        int boardHeight = canvas.getHeight();
        int endWidth = boardWidth / 10;
        int endHeight = boardHeight / 10;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int startWidth = i * endWidth;
                int startHeight = j * endHeight;
                board[i][j] = new Rect(startWidth, startHeight, startWidth + endWidth, startHeight + endHeight);

                canvas.drawRect(board[i][j], paintLine);
                canvas.drawRect(board[i][j], paintCell);
            }
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
