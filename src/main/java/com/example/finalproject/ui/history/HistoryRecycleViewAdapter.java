package com.example.finalproject.ui.history;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalproject.MainContract;
import com.example.finalproject.R;
import com.example.finalproject.model.dtos.HistoryEntryDTO;

import java.util.ArrayList;
import java.util.List;

public class HistoryRecycleViewAdapter extends RecyclerView.Adapter<HistoryEntryHolder>{

    private List<HistoryEntryDTO> historyEntries;

    private MainContract.Presenter presenter;

    public HistoryRecycleViewAdapter(List<HistoryEntryDTO> historyEntries, MainContract.Presenter presenter) {
        this.historyEntries = historyEntries;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public HistoryEntryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_history_entry, viewGroup, false);
        return new HistoryEntryHolder(view, presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryEntryHolder itemViewHolder, int index) {
        itemViewHolder.setFields(historyEntries.get(index));
    }

    List<Long> deleteHistoryEntities() {
        List<Long> deletedIds = new ArrayList<>();
        for (HistoryEntryDTO historyEntryDTO : historyEntries) {
            deletedIds.add(historyEntryDTO.getId());
        }
        historyEntries.clear();
        notifyDataSetChanged();
        return deletedIds;
    }

    @Override
    public int getItemCount() {
        return historyEntries.size();
    }

}