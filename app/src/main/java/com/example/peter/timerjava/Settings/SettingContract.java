package com.example.peter.timerjava.Settings;

import android.content.Intent;

import com.example.peter.timerjava.Data.Training;

public interface SettingContract {

    interface PresenterInterface{
        boolean saveTraining();
        void setWorkoutTime(long duration);
        void setRestTime(long duration);
        void setTrainingNameAndSet(String name,int set);
        void setTrainingSets(int set);
        void finish();
        void getExtra(Intent intent);
    }

    interface ViewInterface{
        void showTrainingName(String name);
        void showWorkoutTime(String time);
        void showRestTime(String restTime);
        void showNumberOfSets(String sets);
        void showTrainingInfo(Training training);
    }
}
