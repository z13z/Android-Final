package com.example.finalproject.ui.history;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.finalproject.MainContract;
import com.example.finalproject.R;
import com.example.finalproject.model.dtos.HistoryEntryDTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class HistoryEntryHolder extends RecyclerView.ViewHolder {

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy - hh:mm", Locale.getDefault());

    private HistoryEntryDTO historyEntry;

    private TextView phoneName;

    private TextView historyPeriod;

    private TextView messagesCount;

    public HistoryEntryHolder(@NonNull View itemView, final MainContract.Presenter presenter) {
        super(itemView);
        phoneName = itemView.findViewById(R.id.phoneNameLabel);
        historyPeriod = itemView.findViewById(R.id.historyTimeLabel);
        messagesCount = itemView.findViewById(R.id.messagesCountLabel);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showChatHistory(historyEntry);
            }
        });
    }

    public void setFields(HistoryEntryDTO historyEntry) {
        this.historyEntry = historyEntry;
        phoneName.setText(historyEntry.getPhoneName());
        historyPeriod.setText(DATE_FORMAT.format(historyEntry.getStartTime()));
        messagesCount.setText(historyEntry.getMessages().size());
    }
}
