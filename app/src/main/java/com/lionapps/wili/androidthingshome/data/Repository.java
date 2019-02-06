package com.lionapps.wili.androidthingshome.data;

import android.content.Context;

import com.lionapps.wili.androidthingshome.data.database.AppDatabase;
import com.lionapps.wili.androidthingshome.data.database.AppExecutors;
import com.lionapps.wili.androidthingshome.data.database.Measurement;

import java.util.List;

import androidx.lifecycle.LiveData;

public class Repository {
    private AppDatabase db;
    private final static  Object LOCK = new Object();
    private static Repository sINSTANCE;

    private Repository(Context context) {
        db = AppDatabase.getDatabase(context);
    }
    public static Repository getInstance(Context context){
        if (sINSTANCE == null){
            synchronized (LOCK){
                sINSTANCE = new Repository(context);
            }
        }
        return sINSTANCE;
    }
    public LiveData<List<Measurement>> getAllMeasurement() {
        return db.measurementDao().getAll();
    }

    public void insertMeasurement(final Measurement measurement) {
        AppExecutors.getsInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.measurementDao().insertMeasurement(measurement);
            }
        });
    }

    public void deleteTable() {
        AppExecutors.getsInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.measurementDao().deleteTable();
            }
        });
    }
}
