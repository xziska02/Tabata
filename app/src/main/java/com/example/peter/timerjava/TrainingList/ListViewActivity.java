package com.example.peter.timerjava.TrainingList;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.peter.timerjava.Data.Training;
import com.example.peter.timerjava.R;
import com.example.peter.timerjava.Settings.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListViewActivity extends AppCompatActivity implements ListContract.ListView{

    private ListContract.ListPresenter mPresenter;
    @BindView(R.id.addTraining)
    FloatingActionButton mAddTrainingBtn;
    @BindView(R.id.training_list)
    RecyclerView mTrainingRecycler;
    private static ListContract.ListView instance;
    private Context mContext;

    public static ListContract.ListView getInstance(){
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new ListPresenter(this);
        instance = this;
        mContext = this;
        init();
    }

    @Override
    public void start() {

    }

    private void init(){
        setAddTrainingBtn();
        setAdapter();
    }

    private void setAddTrainingBtn() {
        mAddTrainingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(mContext, SettingActivity.class);
            Training training = new Training();
            training.setId();
            intent.putExtra("TRAINING_ID", training.getId());
            intent.putExtra("NEW_DATA",true);
            startActivity(intent);
            }
        });
    }

    @Override
    public void setAdapter(){
        TrainingAdapter adapter = new TrainingAdapter(this);
        mTrainingRecycler.setAdapter(adapter);
        mTrainingRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.finish();
    }
}
