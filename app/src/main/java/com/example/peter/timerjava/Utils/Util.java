package com.example.peter.timerjava.Utils;

public class Util {

    private static Util instance;

    private Util(){}

    public static Util getInstance(){
        if (instance == null){
            instance = new Util();
        }
        return instance;
    }

    public String getStringTimeFormat(long duration){
        long timeInSeconds = duration/1000;
        long minutes = timeInSeconds / 60;
        long remainingSeconds = timeInSeconds % 60;
        String minuteStr;
        String secondsStr;
        if (minutes < 10){
            minuteStr = "0" + Long.toString(minutes);
        }else{
            minuteStr = Long.toString(minutes);
        }
        if (remainingSeconds < 10){
            secondsStr = "0" + Long.toString(remainingSeconds);
        }else{
            secondsStr = Long.toString(remainingSeconds);
        }
        return minuteStr + ":" + secondsStr;
    }

    public String getNormalNumberFormat(int count){
        String output;
        if (count < 10){
            output = "0" + Integer.toString(count);
        }else{
            output = Long.toString(count);
        }
        return output;
    }
}
