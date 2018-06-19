package com.example.peter.timerjava.Data;

import io.realm.Realm;
import io.realm.RealmResults;

public class DatabaseManager {

    private static DatabaseManager instance;
    private Realm realm;

    private DatabaseManager(){
        realm = Realm.getDefaultInstance();
    }

    public static DatabaseManager getInstance(){
        if (instance == null){
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void closeDB(){
        realm.close();
    }

    public boolean saveTraining(final Training training){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(training);
            }
        });
        return true;
    }

    public Training getTraining(int id){
        Training training = realm.where(Training.class).equalTo("id",id).findFirst();
        return training;
    }

    public boolean deleteTraining(final int id){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
            RealmResults<Training> result = realm.where(Training.class).equalTo("id",id).findAll();
            result.deleteAllFromRealm();
            }
        });
        return true;
    }

    public int getMaxId(){
        if (realm.isEmpty()){
            return 0;
        }
        int id =  realm.where(Training.class).max("id").intValue() + 1;
        return id;
    }

    public RealmResults<Training> getTrainings() {
        RealmResults<Training> trainings = realm.where(Training.class).findAll();
        return trainings;
    }

    public boolean isTrainingInDB(int id){
        Training training = realm.where(Training.class).equalTo("id",id).findFirst();
        return (training == null) ? false : true;
    }

    public void updateName(final int id, final String name){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
            Training training = getTraining(id);
            training.setName(name);
            }
        });
    }

    public void updateWorkoutTime(final int id, final long duration){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
            Training training = getTraining(id);
            training.setWorkoutTime(duration);
            }
        });
    }

    public void updateRestTime(final int id, final long duration){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
            Training training = getTraining(id);
            training.setRestTime(duration);
            }
        });
    }

    public void updateSets(final int id, final int sets){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
            Training training = getTraining(id);
            training.setSets(sets);
            }
        });
    }

    public void updateExercises(final int id, final int exercises){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
            Training training = getTraining(id);
            training.setExercises(exercises);
            }
        });
    }


    public int getDBsize(){
        return realm.where(Training.class).findAll().size();
    }
}
