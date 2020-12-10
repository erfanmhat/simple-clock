package ir.artaateam.android.simpleclock;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class CountDownFragment extends Fragment {
    final long MAXLONG = Long.MAX_VALUE;
    final int E1000 = 1000;
    final int E60 = 60;
    final int E0 = 0;

    int secondInt = 0;
    int minuteInt = 0;
    boolean firstSecond = true;

    CountDownTimer secondCountDownTimer;

    Button startResumeButton;
    Button pauseClearButton;
    EditText minuteEditText;
    EditText secondsEditText;
    TextView endMassageTextView;

    public CountDownFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.count_down_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        configure();
        setTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        cancelTimer();
        startResumeButton.setEnabled(true);
        pauseClearButton.setEnabled(false);
        startResumeButton.setText(R.string.resume);
    }

    private void findViews(View view) {
        startResumeButton = view.findViewById(R.id.count_down_start_resume);
        pauseClearButton = view.findViewById(R.id.count_down_pause_clear);
        minuteEditText = view.findViewById(R.id.minutes_input);
        secondsEditText = view.findViewById(R.id.seconds_input);
        endMassageTextView = view.findViewById(R.id.count_down_end_massage);
    }

    private void configure() {
        startResumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startResumeButtonOnClick();
            }
        });
        pauseClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseClearButtonOnClick();
            }
        });
    }

    private void startResumeButtonOnClick() {
        if (secondsEditText.getText().toString().isEmpty() ||
                minuteEditText.getText().toString().isEmpty()) {
            return;
        }
        getInput();
        makeInputValid();
        pauseClearButton.setEnabled(true);
        startResumeButton.setText(R.string.start);
        secondsEditText.setEnabled(false);
        minuteEditText.setEnabled(false);
        secondCountDownTimer.start();
        startResumeButton.setEnabled(false);
    }

    private void pauseClearButtonOnClick() {
        pauseClearButton.setEnabled(false);
        startResumeButton.setEnabled(true);
        startResumeButton.setText(R.string.resume);
        cancelTimer();
    }

    private void setTimer() {
        secondCountDownTimer = new CountDownTimer(MAXLONG, E1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                onTickCountDown();
                if (isEnded()) {
                    cancelTimer();
                    endMassageTextView.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFinish() {
            }
        };
    }

    @SuppressLint("DefaultLocale")
    private void onTickCountDown() {
        if (firstSecond) {//jeloghiri az kam shodan dar haman lahze start
            firstSecond = false;
            return;
        }
        if (secondInt == E0) {//raftan be daghige baedi
            secondInt = E60;
            --minuteInt;
        }
        --secondInt;
        secondsEditText.setText(String.format("%02d", secondInt));
        minuteEditText.setText(String.format("%02d", minuteInt));
    }

    private void cancelTimer() {
        secondCountDownTimer.cancel();
        firstSecond = true;
    }

    private void getInput() {
        secondInt = Integer.parseInt(secondsEditText.getText().toString());
        minuteInt = Integer.parseInt(minuteEditText.getText().toString());
    }

    private void makeInputValid() {
        minuteInt += secondInt / E60;
        secondInt %= E60;
    }

    private boolean isEnded() {
        return secondInt <= E0 && minuteInt <= E0;
    }
}
