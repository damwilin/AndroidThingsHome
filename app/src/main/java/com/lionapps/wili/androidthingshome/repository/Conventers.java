package com.lionapps.wili.androidthingshome.repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import androidx.room.TypeConverter;

public class Conventers {
    @TypeConverter
    public static LocalDateTime fromTimestamp(Long value) {
        return value == null ? null : Instant.ofEpochMilli(value).atZone(ZoneId.of("Europe/Berlin")).toLocalDateTime();
    }

    @TypeConverter
    public static Long dateToTimestamp(LocalDateTime date) {
        return date == null ? null : date.atZone(ZoneId.of("Europe/Berlin")).toInstant().toEpochMilli();
    }
}
