package com.example.finalproject.model.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = HistoryEntry.class, parentColumns = "id", childColumns = "message_id", onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE))
public class Message {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "history_id")
    private int historyId;

    private String content;

    private Date time;

    private boolean fromMe;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isFromMe() {
        return fromMe;
    }

    public void setFromMe(boolean fromMe) {
        this.fromMe = fromMe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }
}
