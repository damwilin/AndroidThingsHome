package com.lionapps.wili.androidthingshome.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Measurement.class}, version = 3)
    @TypeConverters({Conventers.class})
    public abstract class AppDatabase extends RoomDatabase{
    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context){
        if (INSTANCE ==null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "measurement-db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
        public abstract MeasurementDao measurementDao();
}
