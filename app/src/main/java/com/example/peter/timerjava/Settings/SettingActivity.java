package com.example.peter.timerjava.Settings;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.peter.timerjava.Data.Training;
import com.example.peter.timerjava.R;
import com.example.peter.timerjava.TrainingList.ListViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity implements SettingContract.ViewInterface{

    private SettingContract.PresenterInterface presenter;

    @BindView(R.id.setting_workout_time_layout)
    ConstraintLayout mWorkoutTimeLayout;
    @BindView(R.id.setting_name_layout)
    ConstraintLayout mNameLayout;
    @BindView(R.id.setting_rest_time_layout)
    ConstraintLayout mRestTimeLayout;
    @BindView(R.id.setting_number_set_layout)
    ConstraintLayout mNumberSetLayout;
    @BindView(R.id.setting_number_exercises_layout)
    ConstraintLayout mNumberExercisesLayout;

    @BindView(R.id.text_name)
    EditText mTextName;
    @BindView(R.id.text_workout_time)
    TextView mTextWorkout;
    @BindView(R.id.text_rest)
    TextView mTextRest;
    @BindView(R.id.text_sets)
    EditText mTextSet;
    @BindView(R.id.text_exercises)
    EditText mTextExercises;

    @BindView(R.id.saveTrainingFloatingBtn)
    FloatingActionButton saveBtn;
    @BindView(R.id.exitTrainingFloatingBtn)
    FloatingActionButton exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        ButterKnife.bind(this);
        presenter = new SettingPresenter(this);
        presenter.getExtra(getIntent());
        init();
    }

    private void init(){
        addNameClick();
        addWorkoutTimeClick();
        addRestTimeClick();
        addSetNumberClick();
        addExercisesNumberClick();
        setSaveBtn();
        setExitBtn();
    }

    private void addExercisesNumberClick() {
        mNumberExercisesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mTextExercises.requestFocus();
            mTextExercises.setFocusableInTouchMode(true);
            mTextExercises.setSelection(mTextExercises.getText().length());
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mTextExercises, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    private void setExitBtn() {
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            ListViewActivity.getInstance().setAdapter();
            presenter.finish();
            }
        });
    }

    private void setSaveBtn(){
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String name = String.valueOf(mTextName.getText());
            int exercises = Integer.valueOf(String.valueOf(mTextExercises.getText()));
            int set = Integer.valueOf(String.valueOf(mTextSet.getText()));
            presenter.setExercisesNumber(exercises);
            presenter.setTrainingNameAndSet(name,set);
            presenter.saveTraining();
            ListViewActivity.getInstance().setAdapter();
            presenter.finish();
            }
        });
    }

    private void addSetNumberClick() {
        mNumberSetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mTextSet.requestFocus();
            mTextSet.setFocusableInTouchMode(true);
            mTextSet.setSelection(mTextSet.getText().length());
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mTextSet, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    private void addRestTimeClick() {
        mRestTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            TimePicker timePicker = new TimePicker();
            timePicker.setChooseMode(TimePicker.ChosenItem.REST_TIME);
            timePicker.show(getFragmentManager(), "dialog");
            }
        });

    }

    private void addWorkoutTimeClick() {
        mWorkoutTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            TimePicker timePicker = new TimePicker();
            timePicker.setChooseMode(TimePicker.ChosenItem.WORKOUT_TIME);
            timePicker.show(getFragmentManager(), "dialog");
            }
        });
    }

    private void addNameClick() {
        mNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextName.requestFocus();
                mTextName.setFocusableInTouchMode(true);
                mTextName.setSelection(mTextName.getText().length());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mTextName, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    @Override
    public void showTrainingName(String name) {
        mTextWorkout.setText(name);
    }

    @Override
    public void showWorkoutTime(String time) {
        mTextWorkout.setText(time);
    }

    @Override
    public void showRestTime(String restTime) {
        mTextRest.setText(restTime);
    }

    @Override
    public void showNumberOfSets(String sets) {
        mTextSet.setText(sets);
    }

    @Override
    public void showTrainingInfo(Training training) {
        mTextName.setText(training.getName());
        mTextSet.setText(Integer.toString(training.getSets()));
        mTextWorkout.setText(training.getWorkoutTimeString());
        mTextRest.setText(training.getRestTimeString());
        mTextExercises.setText(Integer.toString(training.getExercises()));
    }

    @Override
    public void showNumberOfExercises(String exercises) {
        mTextExercises.setText(exercises);
    }


}
