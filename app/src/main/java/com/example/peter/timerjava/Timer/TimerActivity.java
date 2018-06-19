package com.example.peter.timerjava.Timer;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.peter.timerjava.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;


public class TimerActivity extends AppCompatActivity implements TimerContract.TimerViewInterface{

    private TimerContract.TimerPresenterInterface presenterInterface;
    @BindView(R.id.progress_countdown)
    MaterialProgressBar progressBar;
    @BindView(R.id.fab_play)
    FloatingActionButton playBtn;
    @BindView(R.id.fab_stop)
    FloatingActionButton stopbtn;
    private Context mContext;
    @BindView(R.id.progres_countdown_text)
    TextView progres_countdown_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);
        mContext = this;
        ButterKnife.bind(this);
        if (presenterInterface == null)
            presenterInterface = new TimerPresenter(this);
        setFabPlay();
        setFabStop();
    }

    private void setFabPlay(){
        FloatingActionButton fab_play = (FloatingActionButton) findViewById(R.id.fab_play);
        fab_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (presenterInterface.getTimerMode()){
                    case PAUSE:
                        //resume timer
                        presenterInterface.resumeTimer();
                        playBtn.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_pause));
                        break;
                    case STOP:
                        //start timer
                        presenterInterface.startTimer();
                        playBtn.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_pause));
                        break;
                    case PLAY:
                        //pause timer
                        presenterInterface.pauseTimer();
                        playBtn.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_play));
                        break;
                }
            }
        });
    }

    private void setFabStop(){
        FloatingActionButton fab_stop = (FloatingActionButton) findViewById(R.id.fab_stop);
        fab_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (presenterInterface.getTimerMode()){
                    case STOP:  //do nothing
                        Snackbar.make(view, "NOTHING", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;
                    case PAUSE:
                    case PLAY:  //stop counter
                        Snackbar.make(view, "RESETED", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        presenterInterface.stopTimer();
                        playBtn.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_play));

                }

            }
        });
    }

    @Override
    public void showProgress(long progress) {
        progressBar.setProgress((int)progress);
    }

    @Override
    public void initProgressBar(long maxValue) {
        progressBar.setMax((int) maxValue);
    }

    @Override
    public void changeProgressColor(int color) {
        progressBar.setBackgroundColor(color);
    }

    @Override
    public void showSnack(String msg){
        ConstraintLayout layout = findViewById(R.id.mainContent);
        Snackbar.make(layout, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void showTimeProgress(String progressTime) {
        progres_countdown_text.setText(progressTime);
        Log.e("SHOW TIME", progressTime);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO find out if display is turned or not
        //presenterInterface.stopTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (presenterInterface.getTimerMode() != TimerMode.STOP)
            presenterInterface.pauseTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenterInterface.getTimerMode() == TimerMode.PLAY)
            presenterInterface.resumeTimer();
    }
}
