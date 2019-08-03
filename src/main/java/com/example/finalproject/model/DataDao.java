package com.example.finalproject.model;

import java.util.List;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.example.finalproject.model.entities.HistoryEntry;
import com.example.finalproject.model.entities.HistoryWithMessages;
import com.example.finalproject.model.entities.Message;

@Dao
public interface DataDao {

    @Transaction
    @Query("SELECT * FROM HistoryEntry")
    List<HistoryWithMessages> getHistoryEntries();

    @Transaction
    @Query("SELECT * FROM HistoryEntry e WHERE e.phoneName=:phoneName")
    HistoryWithMessages getHistoryEntry(String phoneName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMessage(Message message);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertHistory(HistoryEntry history);
}
