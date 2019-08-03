package com.example.finalproject.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.finalproject.MainActivity;
import com.example.finalproject.model.entities.HistoryEntry;
import com.example.finalproject.model.entities.Message;
import com.example.finalproject.model.helpers.DateConverter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

@android.arch.persistence.room.Database(entities = {HistoryEntry.class, Message.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class Database extends RoomDatabase {

    private static final String DATABASE_NAME = "messages.db";

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

                        .addCallback(new Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                super.onCreate(db);
                                Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        insertTestData();
                                    }
                                });
                            }
                        })

                        .build();
            }
        }
        return INSTANCE;
    }

//    todo zaza remove
    private static void insertTestData(){
        for (HistoryEntry entry : getHistoryEntries()) {
            long insertedId = getInstance().dataDao().insertHistory(entry);
            List<Message> messages = getTestMessages();
            for (Message message : messages) {
                message.setHistoryId(insertedId);
                getInstance().dataDao().insertMessage(message);
            }
        }
    }

    private static List<HistoryEntry> getHistoryEntries(){
        HistoryEntry h0 = new HistoryEntry();
        h0.setStartTime(new Date(System.currentTimeMillis() - 30 * 60 * 60 * 1000));
        h0.setPhoneName("გელა");

        HistoryEntry h1 = new HistoryEntry();
        h0.setStartTime(new Date(System.currentTimeMillis() - 10 * 60 * 60 * 1000));
        h0.setPhoneName("ვალერა");

        HistoryEntry h2 = new HistoryEntry();
        h0.setStartTime(new Date(System.currentTimeMillis() - 20 * 60 * 60 * 1000));
        h0.setPhoneName("ლამზირა");

        return Arrays.asList(h0, h1, h2);
    }

    private static List<Message> getTestMessages(){
        Message m0 = new Message();
        m0.setFromMe(true);
        m0.setContent("hello there");
        m0.setTime(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000));

        Message m1 = new Message();
        m1.setFromMe(false);
        m1.setContent("who the fuck u are?");
        m1.setTime(new Date(System.currentTimeMillis() - 12 * 60 * 60 * 1000));

        Message m2 = new Message();
        m2.setFromMe(true);
        m2.setContent("ვისმიმართავ ეგრებ იჯოოოოო!");
        m2.setTime(new Date(System.currentTimeMillis() - 11 * 60 * 60 * 1000));

        return Arrays.asList(m0, m1, m2);
    }
}
