package com.lionapps.wili.androidthingshome.ui;

import android.app.Application;

import com.lionapps.wili.androidthingshome.repository.Measurement;
import com.lionapps.wili.androidthingshome.repository.Repository;
import com.lionapps.wili.androidthingshome.task.Measure;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainViewModel extends AndroidViewModel {
    private Repository repository;
    private Measure measure;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        measure = new Measure(repository);
    }

    public void startMeasuring(){
        measure.startMeasuring(60000l);
    }

    public void stopMeasuring(){
        measure.stopMeasuring();
    }

    public LiveData<List<Measurement>> getAllMeasurements(){
        return repository.getAllMeasurement();
    }


    public void deleteTable(){
        repository.deleteTable();
    }
}
