package com.example.finalproject.ui.chat;

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

    //used https://developer.android.com/reference/android/support/constraint/ConstraintSet
    public void setFromMe(boolean fromMe) {
        ConstraintSet constraints = new ConstraintSet();
        constraints.clone((ConstraintLayout) itemView);
        constraints.clear(R.id.messageBackground, fromMe ? ConstraintSet.START : ConstraintSet.END);
        constraints.applyTo((ConstraintLayout) itemView);
    }
}
