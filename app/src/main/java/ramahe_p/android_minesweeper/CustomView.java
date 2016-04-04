package ramahe_p.android_minesweeper;

import android.content.Context;
import android.util.AttributeSet;
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
}
