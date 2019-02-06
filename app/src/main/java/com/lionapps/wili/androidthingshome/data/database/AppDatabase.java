package com.lionapps.wili.androidthingshome.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Measurement.class}, version = 3)
    @TypeConverters({Conventers.class})
    public abstract class AppDatabase extends RoomDatabase{
    private static AppDatabase INSTANCE;
    private static Object LOCK = new Object();
    private static final String DATABASE_NAME = "measurement-db";

    public static AppDatabase getDatabase(Context context){
        if (INSTANCE ==null) {
            synchronized (LOCK) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return INSTANCE;
    }
        public abstract MeasurementDao measurementDao();
}
