package com.example.finalproject.model.helpers;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Long to(Date date){
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Date from(Long dbDate) {
        return new Date(dbDate);
    }
}
