package com.example.finalproject.model;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.finalproject.model.entities.HistoryEntry;
import com.example.finalproject.model.entities.HistoryWithMessages;
import com.example.finalproject.model.entities.Message;

@Dao
public interface DataDao {

    @Transaction
    @Query("SELECT * FROM HistoryEntry")
    List<HistoryWithMessages> getHistoryEntries();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMessage(Message message);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertHistory(HistoryEntry history);
}
