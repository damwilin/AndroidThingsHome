package com.lionapps.wili.androidthingshome.data.database;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Measurement {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "temperature")
    public float temperature;

    @ColumnInfo(name = "humidity")
    public float humidity;

    @ColumnInfo(name = "date")
    public LocalDateTime date;

    public Measurement() {
    }

    private Measurement(MeasurementBuilder builder){
        this.date = builder.localDateTime;
        this.humidity = builder.humidity;
        this.temperature = builder.temperature;
    }

    public int getId() {
        return id;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }


    public LocalDateTime getDate() {
        return date;
    }

    public static class MeasurementBuilder {
        private float temperature;
        private float humidity;
        private LocalDateTime localDateTime;

        public MeasurementBuilder setTemperature(float temperature){
            this.temperature = temperature;
            return this;
        }

        public MeasurementBuilder setHumidity(float humidity){
            this.humidity = humidity;
            return this;
        }

        public MeasurementBuilder setBerlinLocalDateTime(boolean isSettedCurrentDateTime){
            if (isSettedCurrentDateTime == true){
                localDateTime = ZonedDateTime.now(ZoneId.of("Europe/Berlin")).toLocalDateTime();
            }
            return this;
        }

        public MeasurementBuilder setLocalDateTime(LocalDateTime localDateTime){
            this.localDateTime = localDateTime;
            return this;
        }

        public Measurement build(){
            return new Measurement(this);
        }
    }
}
