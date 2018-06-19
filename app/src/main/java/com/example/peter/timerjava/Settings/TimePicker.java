package com.example.peter.timerjava.Settings;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.peter.timerjava.R;

import mobi.upod.timedurationpicker.TimeDurationPicker;
import mobi.upod.timedurationpicker.TimeDurationPickerDialogFragment;

public class TimePicker extends TimeDurationPickerDialogFragment {

    private SettingPresenter presenter;
    private ChosenItem mChosenItem;

    public TimePicker() {
        presenter = new SettingPresenter();
    }

    public void setChooseMode(ChosenItem item){
        mChosenItem = item;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    protected long getInitialDuration() {
        return 0;
    }


    @Override
    protected int setTimeUnits() {
        return TimeDurationPicker.MM_SS;
    }


    @Override
    public void onDurationSet(TimeDurationPicker view, long duration) {
        switch (mChosenItem){
            case REST_TIME:
                presenter.setRestTime(duration);
                break;
            case WORKOUT_TIME:
                presenter.setWorkoutTime(duration);
                break;
        }
    }

    public enum ChosenItem{
        WORKOUT_TIME,
        REST_TIME,
    }
}