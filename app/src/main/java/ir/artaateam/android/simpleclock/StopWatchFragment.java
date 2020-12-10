package ir.artaateam.android.simpleclock;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class StopWatchFragment extends Fragment {
    final long MAXLONG = Long.MAX_VALUE;
    final int E60000 = 60000;
    final int E3600000 = 3600000;
    final int E1000 = 1000;
    final int E100 = 100;
    final int E60 = 60;
    final int E24 = 24;
    final int E10 = 10;
    final int E1 = 1;
    final int E0 = 0;

    int milliSecondIntF001;
    int milliSecondIntF010;
    int milliSecondIntF100;
    int secondInt;
    int minuteInt;
    int hourInt;

    boolean firstMilliSecondF001 = true;
    boolean firstMilliSecondF010 = true;
    boolean firstMilliSecondF100 = true;
    boolean firstSecond = true;
    boolean firstMinute = true;
    boolean firstHour = true;



    CountDownTimer millisecondTimerF001;
    CountDownTimer millisecondTimerF010;
    CountDownTimer millisecondTimerF100;
    CountDownTimer secondTimer;
    CountDownTimer minuteTimer;
    CountDownTimer hourTimer;

    TextView text;
    Button startButton;
    Button stopButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.stop_watch_layout, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        configure();
        setTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        cancelTimer();
        stopButton.setEnabled(false);
        startButton.setEnabled(true);
    }

    private void findViews(View view) {
        text = view.findViewById(R.id.stop_watch_time_passed);
        startButton = view.findViewById(R.id.stop_watch_start_btn);
        stopButton = view.findViewById(R.id.stop_watch_stop_btn);
    }

    private void configure() {
        text.setText(getString(R.string.stopwatch_time_format, 0, 0, 0, 0));
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstMilliSecondF001 = true;
                firstMilliSecondF010 = true;
                firstMilliSecondF100 = true;
                firstSecond = true;
                firstMinute = true;
                firstHour = true;
                cancelTimer();
                milliSecondIntF001 = E0;
                milliSecondIntF010 = E0;
                milliSecondIntF100 = E0;
                secondInt = E0;
                minuteInt = E0;
                hourInt = E0;
                millisecondTimerF001.start();
                millisecondTimerF010.start();
                millisecondTimerF100.start();
                secondTimer.start();
                minuteTimer.start();
                hourTimer.start();
                stopButton.setEnabled(true);
                startButton.setEnabled(false);
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTimer();
                stopButton.setEnabled(false);
                startButton.setEnabled(true);
            }
        });
    }

    private void setTimer() {
        millisecondTimerF001 = new CountDownTimer(MAXLONG, E1) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (firstMilliSecondF001) {
                    firstMilliSecondF001 = false;
                    return;
                }
                ++milliSecondIntF001;
                if (milliSecondIntF001 >= E10) {
                    milliSecondIntF001 = E0;
                }
                text.setText(getString(R.string.stopwatch_time_format, hourInt, minuteInt, secondInt, (milliSecondIntF001 + milliSecondIntF010 + milliSecondIntF100)));
            }

            @Override
            public void onFinish() {
            }
        };
        millisecondTimerF010 = new CountDownTimer(MAXLONG, E10) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (firstMilliSecondF010) {
                    firstMilliSecondF010 = false;
                    return;
                }
                milliSecondIntF010 += E10;
                if (milliSecondIntF010 >= E100) {
                    milliSecondIntF010 = E0;
                }
            }

            @Override
            public void onFinish() {
            }
        };
        millisecondTimerF100 = new CountDownTimer(MAXLONG, E100) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (firstMilliSecondF100) {
                    firstMilliSecondF100 = false;
                    return;
                }
                milliSecondIntF100 += E100;
                if (milliSecondIntF100 >= E1000) {
                    milliSecondIntF100 = E0;
                }
            }

            @Override
            public void onFinish() {
            }
        };

        secondTimer = new CountDownTimer(MAXLONG, E1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (firstSecond) {
                    firstSecond = false;
                    return;
                }
                ++secondInt;
                if (secondInt == E60) secondInt = E0;
            }

            @Override
            public void onFinish() {
            }
        };
        minuteTimer = new CountDownTimer(MAXLONG, E60000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (firstMinute) {
                    firstMinute = false;
                    return;
                }
                ++minuteInt;
                if (minuteInt == E60) minuteInt = E0;
            }

            @Override
            public void onFinish() {
            }
        };
        hourTimer = new CountDownTimer(MAXLONG, E3600000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (firstHour) {
                    firstHour = false;
                    return;
                }
                ++hourInt;
                if (hourInt == E24) hourInt = E0;
            }

            @Override
            public void onFinish() {
            }
        };
    }

    private void cancelTimer() {
        millisecondTimerF001.cancel();
        millisecondTimerF010.cancel();
        millisecondTimerF100.cancel();
        secondTimer.cancel();
        minuteTimer.cancel();
        hourTimer.cancel();
    }
}
