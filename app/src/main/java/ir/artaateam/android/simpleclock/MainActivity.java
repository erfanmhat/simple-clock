package ir.artaateam.android.simpleclock;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button stopWatchButton;
    Button countDownButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        init();
    }

    private void findViews() {
        stopWatchButton = findViewById(R.id.stop_watch_btn);
        countDownButton = findViewById(R.id.count_down_button);
    }

    private void init() {
        stopWatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StopWatchFragment stopWatchFragment = new StopWatchFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.main_frame_layout, stopWatchFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        countDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountDownFragment countDownFragment = new CountDownFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.main_frame_layout, countDownFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
