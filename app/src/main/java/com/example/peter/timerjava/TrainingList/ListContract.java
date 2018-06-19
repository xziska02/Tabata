package com.example.peter.timerjava.TrainingList;

import com.example.peter.timerjava.BaseInterface;

public interface ListContract {

    interface ListPresenter extends BaseInterface{
        void addNewTraining();
    }

    interface ListView{
        void start();
        void setAdapter();
    }

}
