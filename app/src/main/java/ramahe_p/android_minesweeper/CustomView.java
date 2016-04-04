package ramahe_p.android_minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomView extends View {

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

    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // TODO: fill the method to change the customview as a square
        // TODO: call the setMeasureDimension(width, height)
    }
}
