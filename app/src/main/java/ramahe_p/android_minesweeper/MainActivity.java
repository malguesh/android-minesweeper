package ramahe_p.android_minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CustomView cv;
    private TextView markedMines;
    private Button resetButton;
    private Button markingButton;
    private String textMarkedMines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cv = (CustomView) findViewById(R.id.custom_view);
        resetButton = (Button) findViewById(R.id.button_reset);
        markingButton = (Button) findViewById(R.id.button_marking);

        // -- Events
        markedMines = (TextView) findViewById(R.id.tv_marked_mines);


        cv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    textMarkedMines = "Marked mines: " + Integer.toString(cv.markedMines);
                    System.out.println("Marked mine ==> " + Integer.toString(cv.markedMines));
                    markedMines.setText(textMarkedMines);
                    return true;
                }
                return false;
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                cv.markedMines = 0;
                textMarkedMines = "Marked mines: " + Integer.toString(0);
                System.out.println("Marked mine ==> " + Integer.toString(cv.markedMines));
                markedMines.setText(textMarkedMines);
                cv.resetGame();
            }
        });

        markingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                cv.setMarkingMode();
                if (!cv.isMarkingMode) {
                    markingButton.setText(R.string.marking_mode);
                } else
                    markingButton.setText(R.string.uncover_mode);
            }
        });

    }
}
