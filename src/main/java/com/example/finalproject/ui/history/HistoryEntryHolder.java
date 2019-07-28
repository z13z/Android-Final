package com.example.finalproject.ui.history;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.finalproject.R;

public class HistoryEntryHolder extends RecyclerView.ViewHolder {

    private TextView phoneName;

    private TextView historyPeriod;

    private TextView messagesCount;

    public HistoryEntryHolder(@NonNull View itemView) {
        super(itemView);
        phoneName=itemView.findViewById(R.id.phoneNameLabel);
        historyPeriod= itemView.findViewById(R.id.historyTimeLabel);
        messagesCount= itemView.findViewById(R.id.messagesCountLabel);
    }

    public TextView getPhoneName() {
        return phoneName;
    }

    public TextView getHistoryPeriod() {
        return historyPeriod;
    }

    public TextView getMessagesCount() {
        return messagesCount;
    }
}
