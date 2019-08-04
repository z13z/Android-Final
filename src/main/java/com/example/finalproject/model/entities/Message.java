package com.example.finalproject.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = HistoryEntry.class, parentColumns = "id", childColumns = "history_id", onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = "history_id")})
public class Message {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "history_id")
    private long historyId;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(long historyId) {
        this.historyId = historyId;
    }
}
