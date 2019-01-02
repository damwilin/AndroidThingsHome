package com.lionapps.wili.androidthingshome.repository;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class Repository {
    private AppDatabase db;

    public Repository(Context context) {
        db = AppDatabase.getDatabase(context);
    }

    public LiveData<List<Measurement>> getAllMeasurement(){
        return db.measurementDao().getAll();
    }

    public void insertMeasurement(final Measurement measurement){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                db.measurementDao().insertMeasurement(measurement);
            }
        });

    }

    public void deleteTable(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                db.measurementDao().deleteTable();
            }
        });
    }
}
