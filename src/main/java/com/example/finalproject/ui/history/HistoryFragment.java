package com.example.finalproject.ui.history;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalproject.R;
import com.example.finalproject.model.Database;
import com.example.finalproject.model.helpers.HistoryHelper;

public class HistoryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View historyView = inflater.inflate(R.layout.fragment_history, container, false);
        RecyclerView recyclerView = historyView.findViewById(R.id.historyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new HistoryRecycleViewAdapter(HistoryHelper.getDtos(Database.getInstance().dataDao().getHistoryEntries())));
        return historyView;
    }

}
