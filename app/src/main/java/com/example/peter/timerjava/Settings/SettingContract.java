package com.example.peter.timerjava.Settings;

import android.content.Intent;

import com.example.peter.timerjava.BaseInterface;
import com.example.peter.timerjava.Data.Training;

public interface SettingContract {

    interface PresenterInterface extends BaseInterface{
        boolean saveTraining();
        void setWorkoutTime(long duration);
        void setRestTime(long duration);
        void setTrainingNameAndSet(String name,int set);
        void setTrainingSets(int set);
        void getExtra(Intent intent);
        void setExercisesNumber(int exercises);
    }

    interface ViewInterface{
        void showTrainingName(String name);
        void showWorkoutTime(String time);
        void showRestTime(String restTime);
        void showNumberOfSets(String sets);
        void showTrainingInfo(Training training);
        void showNumberOfExercises(String exercises);
    }
}
