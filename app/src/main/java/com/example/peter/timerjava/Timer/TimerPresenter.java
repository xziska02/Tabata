package com.example.peter.timerjava.Timer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.peter.timerjava.Data.DatabaseManager;
import com.example.peter.timerjava.Data.Training;
import com.example.peter.timerjava.R;
import com.example.peter.timerjava.Utils.Util;

import static com.example.peter.timerjava.R.*;

public class TimerPresenter implements TimerContract.TimerPresenterInterface{

    private static TimerActivity timerActivity;
    private static CountDownTimer countDownTimer;
    private static Training actualTraining;
    private static TimerProcess timerProcess = TimerProcess.START;
    private static TimerMode timerMode = TimerMode.STOP;
    private static long workingTime = 10000L;
    private static long remainingTime;
    private static long countTime;
    private static int setCount = 0;
    private static long fullTimeExercise;
    private static int exerciseCount = 0;
    private static long fullTimeHolder = 0;
    private final MediaPlayer mp;

    public TimerPresenter(TimerActivity timerActivity) {
        int trainingID = timerActivity.getIntent().getIntExtra("CHOSEN_TRAINING_ID",1);
        actualTraining = DatabaseManager.getInstance().getTraining(trainingID);
        timerProcess = TimerProcess.START;
        timerMode = TimerMode.STOP;
        fullTimeExercise = actualTraining.getFullExerciseTime();
        this.timerActivity = timerActivity;
        initData();
        mp = MediaPlayer.create(timerActivity, raw.beep);

    }

    @Override
    public void startTimer() {

        remainingTime = workingTime;
        switch (timerProcess){
            case START:     //10 seconds start
                timerActivity.showMotivationString(timerActivity.getResources().getString(string.start));
                timerActivity.changeProgressColor(timerActivity.getResources().getColor(color.colorStart));
                workingTime = 10000L;
                timerProcess = TimerProcess.START;
                setCount = actualTraining.getSets();
                exerciseCount = actualTraining.getExercises();
                break;
            case TRAIN:     //actualTraining.getWorkoutTime
                workingTime = actualTraining.getWorkoutTime();
                timerProcess = TimerProcess.TRAIN;
                break;
            case REST:      //actualTraining.getRestTime
                workingTime = actualTraining.getRestTime();
                timerProcess = TimerProcess.REST;
                break;
        }
        initTimer();
    }

    @Override
    public void pauseTimer() {
        if (timerMode == TimerMode.PLAY) {
            countDownTimer.cancel();
        }
        timerMode = TimerMode.PAUSE;
    }

    @Override
    public void stopTimer() {
        initData();
        workingTime = 10000L;   // 10 seconds for preparation phase
        if (timerMode == TimerMode.PLAY)
            countDownTimer.cancel();
        timerMode = TimerMode.STOP;
        timerProcess = TimerProcess.START;
    }

    @Override
    public void resumeTimer() {
        remainingTime = countTime;
        timerMode = TimerMode.PLAY;
        initTimer();
    }

    private void initTimer(){
        timerMode = TimerMode.PLAY;
        countDownTimer =  new CountDownTimer( remainingTime, 1) {
            @Override
            public void onTick(long l) {
                countTime = l;
                long toUseTime = workingTime - l;
                timerActivity.showProgress(toUseTime);
                if (((remainingTime / 1000) % 1 ) == 0) {
                    if (timerProcess != TimerProcess.START){
                        fullTimeExercise = actualTraining.getFullExerciseTime() - fullTimeHolder - (workingTime - l) + 1000;
                        timerActivity.showFullExerciseTime(Util.getInstance().getStringTimeFormat(fullTimeExercise));
                    }
                    timerActivity.showTimeProgress(Util.getInstance().getStringTimeFormat(l+1000));
                }

            }

            @Override
            public void onFinish() {
                mp.start();
                switch (timerProcess){
                    case START:
                        timerActivity.showMotivationString(timerActivity.getResources().getString(string.workingOut));
                        timerActivity.changeProgressColor(timerActivity.getResources().getColor(color.colorTimer));
                        workingTime = actualTraining.getWorkoutTime();
                        timerProcess = TimerProcess.TRAIN;

                        break;
                    case TRAIN:
                        timerActivity.showMotivationString(timerActivity.getResources().getString(string.rest));
                        timerActivity.changeProgressColor(timerActivity.getResources().getColor(color.colorRest));
                        workingTime = actualTraining.getRestTime();
                        timerProcess = TimerProcess.REST;
                        fullTimeHolder += workingTime;
                        break;
                    case REST:
                        timerActivity.showMotivationString(timerActivity.getResources().getString(string.workingOut));
                        timerActivity.changeProgressColor(timerActivity.getResources().getColor(color.colorTimer));
                        workingTime = actualTraining.getWorkoutTime();
                        timerProcess = TimerProcess.TRAIN;
                        exerciseCount--;
                        fullTimeHolder += workingTime;
                        break;
                }
                if (exerciseCount == 0){
                    setCount--;
                    exerciseCount = actualTraining.getExercises();
                    if (setCount == 0){
                        stopTimer();
                        return;
                    }

                }
                timerActivity.showSetCount(Util.getInstance().getNormalNumberFormat(setCount));
                timerActivity.showExercisesCount(Util.getInstance().getNormalNumberFormat(exerciseCount));
                timerActivity.showProgress(0);
                timerActivity.initProgressBar(workingTime);
                remainingTime = workingTime;
                initTimer();

            }
        }.start();
    }

    @Override
    public TimerMode getTimerMode() {
        return timerMode;
    }

    private void initData(){
        remainingTime = workingTime;
        workingTime = 10000L;
        fullTimeExercise = actualTraining.getFullExerciseTime();
        timerActivity.initProgressBar(workingTime);
        timerActivity.changeProgressColor(timerActivity.getResources().getColor(color.colorAccent));
        timerActivity.showProgress(workingTime);
        timerActivity.showTimeProgress(Util.getInstance().getStringTimeFormat(workingTime));
        timerActivity.showMotivationString(timerActivity.getResources().getString(string.start));
        timerActivity.showExercisesCount(Util.getInstance().getNormalNumberFormat(actualTraining.getExercises()));
        timerActivity.showFullExerciseTime(Util.getInstance().getStringTimeFormat(actualTraining.getFullExerciseTime()));
        timerActivity.showSetCount(Util.getInstance().getNormalNumberFormat(actualTraining.getSets()));
        fullTimeHolder = 0;
    }

    @Override
    public void finish() {
        DatabaseManager.getInstance().closeDB();
    }
}
