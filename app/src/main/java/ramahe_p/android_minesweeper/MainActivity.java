package ramahe_p.android_minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button resetButton;
    private CustomView cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cv = (CustomView) findViewById(R.id.custom_view);
        resetButton = (Button) findViewById(R.id.button_reset);

        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                cv.resetGame();
            }
        });
    }
}
