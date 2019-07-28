package com.example.finalproject.ui.history;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalproject.R;
import com.example.finalproject.model.dtos.HistoryEntryDTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class HistoryRecycleViewAdapter extends RecyclerView.Adapter<HistoryEntryHolder>{

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy - hh:mm", Locale.getDefault());

    private List<HistoryEntryDTO> historyEntries;

    public HistoryRecycleViewAdapter(List<HistoryEntryDTO> historyEntries) {
        this.historyEntries = historyEntries;
    }

    @NonNull
    @Override
    public HistoryEntryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_history_entry, viewGroup, false);
        return new HistoryEntryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryEntryHolder itemViewHolder, int index) {
        HistoryEntryDTO entry = historyEntries.get(index);
        itemViewHolder.getPhoneName().setText(entry.getPhoneName());
        itemViewHolder.getHistoryPeriod().setText(DATE_FORMAT.format(entry.getStartTime()));
        itemViewHolder.getMessagesCount().setText(entry.getMessages().size());
    }

    @Override
    public int getItemCount() {
        return historyEntries.size();
    }

}