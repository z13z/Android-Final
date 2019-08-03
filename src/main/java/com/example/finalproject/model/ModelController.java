package com.example.finalproject.model;

import com.example.finalproject.MainContract;
import com.example.finalproject.model.entities.HistoryEntry;
import com.example.finalproject.model.entities.HistoryWithMessages;
import com.example.finalproject.model.entities.Message;

import java.util.ArrayList;
import java.util.Date;

public class ModelController implements MainContract.ChatModel {

    private String phoneName;

    private HistoryWithMessages currentHistory;

    public ModelController(String phoneName){
        this.phoneName = phoneName;
    }

    @Override
    public void chatStarted() {
        currentHistory = Database.getInstance().dataDao().getHistoryEntry(phoneName);
        if (currentHistory == null) {
            currentHistory = new HistoryWithMessages();
            HistoryEntry historyEntry = new HistoryEntry();
            historyEntry.setPhoneName(phoneName);
            historyEntry.setStartTime(new Date());
            historyEntry.setId(Database.getInstance().dataDao().insertHistory(historyEntry));
            currentHistory.setHistory(historyEntry);
            currentHistory.setMessages(new ArrayList<Message>());
        }
    }

    @Override
    public Message saveMessage(String messageStr, boolean fromMe) {
        Message message = new Message();
        message.setContent(messageStr);
        message.setFromMe(fromMe);
        message.setHistoryId(currentHistory.getHistory().getId());
        message.setId(Database.getInstance().dataDao().insertMessage(message));
        currentHistory.getMessages().add(message);
        return message;
    }

    @Override
    public HistoryWithMessages getCurrentHistoryEntry(){
        return currentHistory;
    }
}
