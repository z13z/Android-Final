package com.example.finalproject.model.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class HistoryWithMessages {

    @Embedded
    private HistoryEntry history;

    @Relation(parentColumn = "id", entity = Message.class, entityColumn = "history_id")
    private List<Message> messages = new ArrayList<>();

    public HistoryEntry getHistory() {
        return history;
    }

    public void setHistory(HistoryEntry history) {
        this.history = history;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
