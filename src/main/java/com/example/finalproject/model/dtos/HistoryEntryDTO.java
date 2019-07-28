package com.example.finalproject.model.dtos;

import java.util.Date;
import java.util.List;

public class HistoryEntryDTO {

    private String phoneName;

    private Date startTime;

    private List<MessageDTO> messages;

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }
}
