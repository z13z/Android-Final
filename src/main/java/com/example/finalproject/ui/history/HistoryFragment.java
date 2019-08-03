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
import android.widget.Button;

import com.example.finalproject.MainContract;
import com.example.finalproject.R;
import com.example.finalproject.model.Database;
import com.example.finalproject.model.dtos.HistoryEntryDTO;
import com.example.finalproject.model.helpers.HistoryHelper;

import java.util.List;

public class HistoryFragment extends Fragment implements View.OnClickListener {

    private HistoryRecycleViewAdapter adapter;

    private MainContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View historyView = inflater.inflate(R.layout.fragment_history, container, false);
        presenter = (MainContract.Presenter) getActivity();

        RecyclerView recyclerView = historyView.findViewById(R.id.historyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<HistoryEntryDTO> historyEntries = presenter.getHistoryEntities();
        adapter = new HistoryRecycleViewAdapter(historyEntries, presenter);

        recyclerView.setAdapter(adapter);
        Button clearHistoryButton = historyView.findViewById(R.id.clearHistoryButton);
        clearHistoryButton.setOnClickListener(this);
        if (historyEntries.isEmpty()) {
            clearHistoryButton.setVisibility(View.INVISIBLE);
        } else {
            historyView.findViewById(R.id.historyMotFoundLabel).setVisibility(View.INVISIBLE);
        }
        return historyView;
    }

    @Override
    public void onClick(View v) {
        presenter.deleteHistoryEntities(adapter.deleteHistoryEntities());
    }
}
