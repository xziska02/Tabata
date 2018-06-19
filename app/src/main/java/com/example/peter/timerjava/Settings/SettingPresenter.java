package com.example.peter.timerjava.Settings;

import android.content.Intent;
import android.util.Log;

import com.example.peter.timerjava.Data.DatabaseManager;
import com.example.peter.timerjava.Data.Training;
import com.example.peter.timerjava.Utils.Util;

public class SettingPresenter implements SettingContract.PresenterInterface {

    private SettingActivity mActivity;
    private Training training;

    public SettingPresenter(SettingActivity activity) {
        mActivity = activity;
    }

    public SettingPresenter() {
    }

    @Override
    public boolean saveTraining() {
        return DatabaseManager.getInstance().saveTraining(training);
    }


    @Override
    public void setWorkoutTime(long duration) {
        if (!DatabaseManager.getInstance().isTrainingInDB(training.getId())){
            training.setWorkoutTime(duration);
        }else{
            DatabaseManager.getInstance().updateWorkoutTime(training.getId(),duration);
        }
        mActivity.showWorkoutTime(Util.getInstance().getStringTimeFormat(duration));
    }

    @Override
    public void setRestTime(long duration) {
        if (!DatabaseManager.getInstance().isTrainingInDB(training.getId())){
            training.setRestTime(duration);
        }else{
            DatabaseManager.getInstance().updateRestTime(training.getId(),duration);
        }
        mActivity.showRestTime(Util.getInstance().getStringTimeFormat(duration));
    }

    @Override
    public void setTrainingNameAndSet(String name,int set) {
        if (!DatabaseManager.getInstance().isTrainingInDB(training.getId())){
            training.setName(name);
            training.setSets(set);
        }else{
            DatabaseManager.getInstance().updateName(training.getId(),name);
            DatabaseManager.getInstance().updateSets(training.getId(),set);
        }
    }

    @Override
    public void setExercisesNumber(int exercises) {
        if (!DatabaseManager.getInstance().isTrainingInDB(training.getId())){
            training.setExercises(exercises);
        }else{
            DatabaseManager.getInstance().updateExercises(training.getId(),exercises);
        }
        mActivity.showNumberOfExercises(Integer.toString(exercises));
    }

    @Override
    public void setTrainingSets(int set) {
        training.setSets(set);
    }

    @Override
    public void finish() {
        mActivity.finish();
    }

    @Override
    public void getExtra(Intent intent) {
        int id = intent.getIntExtra("TRAINING_ID",0);
        boolean newData = intent.getBooleanExtra("NEW_DATA",true);
        if (newData){
            training = new Training();
            training.setId(id);
        }else{
            training = DatabaseManager.getInstance().getTraining(id);
        }
        mActivity.showTrainingInfo(training);
        Log.e("REST", "DATA " + training.getRestTime());
    }



}
