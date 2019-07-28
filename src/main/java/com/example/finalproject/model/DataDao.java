package com.example.finalproject.model;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.finalproject.model.entities.HistoryEntry;
import com.example.finalproject.model.entities.HistoryWithMessages;
import com.example.finalproject.model.entities.Message;

@Dao
public interface DataDao {

    @Query("SELECT * FROM HistoryEntry")
    List<HistoryWithMessages> getHistoryEntries();

    @Query("SELECT history_id, COUNT(id) FROM Message GROUP BY history_id")
    List<Message> getMessageCounts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMessage(Message message);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHistory(HistoryEntry history);
}
