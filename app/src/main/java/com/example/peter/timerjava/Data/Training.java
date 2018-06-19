package com.example.peter.timerjava.Data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Training extends RealmObject {

    @PrimaryKey
    private int id ;
    private String name = "Tabata";
    private long mWorkoutTime = 30000L;
    private long mRestTime = 10000L;
    private int mSets = 6;
    private int exercises = 8;
    private long timestamp;

    public int getExercises() {
        return exercises;
    }

    public void setExercises(int exercises) {
        this.exercises = exercises;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setNewTimestamp() {
        this.timestamp = System.currentTimeMillis()/1000;
    }

    public Training() {
    }

    public Training(String name, int workoutTime) {
        this.name = name;
        this.mWorkoutTime = workoutTime;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(){
        id = DatabaseManager.getInstance().getMaxId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWorkoutTime() {
        return mWorkoutTime;
    }

    public void setWorkoutTime(long mWorkoutTime) {
        this.mWorkoutTime = mWorkoutTime;
    }

    public long getRestTime() {
        return mRestTime;
    }

    public void setRestTime(long mRestTime) {
        this.mRestTime = mRestTime;
    }

    public int getSets() {
        return mSets;
    }

    public void setSets(int mSets) {
        this.mSets = mSets;
    }

    public String getRestTimeString(){
        return getStringTimeFormat(mRestTime);
    }

    public String getWorkoutTimeString(){
        return getStringTimeFormat(mWorkoutTime);
    }

    private String getStringTimeFormat(long duration){
        long timeInSeconds = duration/1000;
        long minutes = timeInSeconds / 60;
        long remainingSeconds = timeInSeconds % 60;
        String outputTime = Long.toString(minutes) + ":" + Long.toString(remainingSeconds);
        return outputTime;
    }

    public long getFullExerciseTime(){
        long oneExerciseTime = mWorkoutTime + mRestTime;
        return oneExerciseTime * exercises * mSets;
    }
}
