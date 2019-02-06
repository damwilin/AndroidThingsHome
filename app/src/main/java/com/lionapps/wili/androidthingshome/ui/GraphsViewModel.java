package com.lionapps.wili.androidthingshome.ui;

import android.app.Application;

import com.lionapps.wili.androidthingshome.data.Repository;
import com.lionapps.wili.androidthingshome.data.database.Measurement;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class GraphsViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Measurement>> measurements;

    public GraphsViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        measurements = repository.getAllMeasurement();
    }

    public LiveData<List<Measurement>> getAllMeasurements(){
        return measurements;
    }
}
