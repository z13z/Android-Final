package com.example.finalproject.model.dtos;

import java.io.Serializable;
import java.util.Date;

public class MessageDTO implements Serializable {

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
}
