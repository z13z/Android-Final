package com.example.finalproject.ui.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.finalproject.R;

public class MessageHolder extends RecyclerView.ViewHolder {

    private TextView messageContent;

    private TextView sentTime;

    public MessageHolder(@NonNull View itemView) {
        super(itemView);
        messageContent=itemView.findViewById(R.id.phoneNameLabel);
        sentTime= itemView.findViewById(R.id.historyTimeLabel);
    }

    public TextView getMessageContent() {
        return messageContent;
    }

    public TextView getSentTime() {
        return sentTime;
    }
}
