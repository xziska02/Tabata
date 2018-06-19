package com.example.peter.timerjava.TrainingList;

public interface ListContract {

    interface ListPresenter{
        void addNewTraining();
    }

    interface ListView{
        void start();
        void setAdapter();
    }

}
