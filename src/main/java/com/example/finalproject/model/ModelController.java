package com.example.finalproject.model;

import com.example.finalproject.MainContract;
import com.example.finalproject.model.entities.HistoryEntry;
import com.example.finalproject.model.entities.Message;

import java.util.Date;

public class ModelController implements MainContract.ChatModel {

    private String phoneName;

    private HistoryEntry historyEntry;

    public ModelController(String phoneName){
        this.phoneName = phoneName;
    }

    @Override
    public void chatStarted() {
        historyEntry = new HistoryEntry();
        historyEntry.setPhoneName(phoneName);
        historyEntry.setStartTime(new Date());
        historyEntry.setId(Database.getInstance().dataDao().insertHistory(historyEntry));
    }

    @Override
    public Message saveMessage(String messageStr, boolean fromMe) {
        Message message = new Message();
        message.setContent(messageStr);
        message.setFromMe(fromMe);
        message.setHistoryId(historyEntry.getId());
        message.setId(Database.getInstance().dataDao().insertMessage(message));
        return message;
    }
}
