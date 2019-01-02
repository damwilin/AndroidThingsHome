package com.lionapps.wili.androidthingshome.repository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MeasurementDao {
    @Query("SELECT * FROM measurement")
    LiveData<List<Measurement>> getAll();

    @Insert
    void insertMeasurement(Measurement measurement);

    @Query("DELETE FROM measurement")
    void deleteTable();
}
