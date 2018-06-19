package com.example.peter.timerjava.Timer;

import android.graphics.Color;

public interface TimerContract {

    interface TimerPresenterInterface{
        void startTimer();
        void pauseTimer();
        void stopTimer(); //reinitialisation of timer
        void resumeTimer();
        TimerMode getTimerMode();
    }

    interface TimerViewInterface{
        void showProgress(long progress);
        void initProgressBar(long maxValue);
        void changeProgressColor(int color);
        void showSnack(String msg);
        void showTimeProgress(String progressTime);
        void showMotivationString(String msg);
        void showSetCount(String setCount);
        void showExercisesCount(String exerciseCount);
        void showFullExerciseTime(String time);
    }
}
