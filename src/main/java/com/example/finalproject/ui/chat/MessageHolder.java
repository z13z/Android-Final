package com.example.finalproject.ui.chat;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.finalproject.R;

public class MessageHolder extends RecyclerView.ViewHolder {

    private TextView messageContent;

    private TextView sentTime;

    public MessageHolder(@NonNull View itemView) {
        super(itemView);
        messageContent = itemView.findViewById(R.id.messageText);
        sentTime = itemView.findViewById(R.id.messageTime);
        sentTime.setVisibility(View.INVISIBLE);
    }

    public TextView getMessageContent() {
        return messageContent;
    }

    public TextView getSentTime() {
        return sentTime;
    }

    public void setFromMe(boolean fromMe, Drawable background) {
        ConstraintSet constraints = new ConstraintSet();
        constraints.clone((ConstraintLayout) itemView);
        itemView.findViewById(R.id.messageBackground).setBackground(background);
        constraints.clear(R.id.messageBackground, fromMe ? ConstraintSet.START : ConstraintSet.END);
        constraints.applyTo((ConstraintLayout) itemView);

    }
}
