package com.example.finalproject.model;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.finalproject.MainActivity;
import com.example.finalproject.model.entities.HistoryEntry;
import com.example.finalproject.model.entities.Message;
import com.example.finalproject.model.helpers.DateConverter;

@androidx.room.Database(entities = {HistoryEntry.class, Message.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class Database extends RoomDatabase {

    private static final String DATABASE_NAME = "app_database";

    private static Database INSTANCE;

    private static final Object lock = new Object();

    public abstract DataDao dataDao();

    public static Database getInstance(){
        synchronized (lock){
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                        MainActivity.getContext(),
                        Database.class,
                        DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return INSTANCE;
    }

}
