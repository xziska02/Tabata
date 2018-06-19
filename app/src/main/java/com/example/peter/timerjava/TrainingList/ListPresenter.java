package com.example.peter.timerjava.TrainingList;

import com.example.peter.timerjava.Data.DatabaseManager;
import com.example.peter.timerjava.Data.Training;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ListPresenter implements ListContract.ListPresenter {

    private static ListViewActivity view;
    private static RealmResults<Training> trainings;

    public ListPresenter(ListViewActivity v) {
        view = v;
        trainings = DatabaseManager.getInstance().getTrainings();
        trainings.addChangeListener(new RealmChangeListener<RealmResults<Training>>() {
            @Override
            public void onChange(RealmResults<Training> trainings) {
                view.setAdapter();
            }
        });
    }

    @Override
    public void addNewTraining() {

    }

    @Override
    public void finish() {
        DatabaseManager.getInstance().closeDB();
    }
}
