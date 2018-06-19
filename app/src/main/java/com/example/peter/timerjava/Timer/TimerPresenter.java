package com.example.peter.timerjava.Timer;

import android.os.CountDownTimer;
import android.util.Log;

import com.example.peter.timerjava.Data.Training;
import com.example.peter.timerjava.Utils.Util;

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

    public TimerPresenter(TimerActivity timerActivity) {
        actualTraining = new Training();
        timerProcess = TimerProcess.START;
        timerMode = TimerMode.STOP;
        this.timerActivity = timerActivity;
        timerActivity.initProgressBar(workingTime);

    }

    @Override
    public void startTimer() {

        remainingTime = workingTime;
        switch (timerProcess){
            case START:     //10 seconds start
                workingTime = 10000L;
                timerProcess = TimerProcess.START;
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
        remainingTime = workingTime;
        timerActivity.initProgressBar(workingTime);
        timerActivity.showProgress(0);
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
                if ((remainingTime % 1000) == 0) {
                    Log.e("REAMINING", "TIME: "+ toUseTime);
                    timerActivity.showTimeProgress(Util.getInstance().getStringTimeFormat(l+1000));
                }

            }

            @Override
            public void onFinish() {
                timerActivity.showSnack("Finished");
                switch (timerProcess){
                    case START:
                        workingTime = actualTraining.getWorkoutTime();
                        break;
                    case TRAIN:
                        workingTime = actualTraining.getRestTime();
                        break;
                    case REST:
                        workingTime = actualTraining.getWorkoutTime();
                        break;
                }
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


}
