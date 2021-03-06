package com.lionapps.wili.androidthingshome.ui;

import android.app.Application;

import com.lionapps.wili.androidthingshome.arduino.task.Measure;
import com.lionapps.wili.androidthingshome.data.Repository;
import com.lionapps.wili.androidthingshome.data.database.Measurement;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainViewModel extends AndroidViewModel {
    private Repository repository;
    private Measure measure;
    private LiveData<List<Measurement>> measurements;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        measure = new Measure(repository);
        measurements = repository.getAllMeasurement();
    }

    public void startMeasuring(){
        measure.startMeasuring(60000l);
    }

    public void stopMeasuring(){
        measure.stopMeasuring();
    }

    public LiveData<List<Measurement>> getAllMeasurements(){
        return measurements;
    }


    public void deleteTable(){
        repository.deleteTable();
    }
}
