package com.example.finalproject.ui.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalproject.MainContract;
import com.example.finalproject.R;
import com.example.finalproject.model.dtos.HistoryEntryDTO;
import com.example.finalproject.model.dtos.MessageDTO;

public class ChatFragment extends Fragment implements MainContract.ChatView {

    private ChatRecycleViewAdapter viewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View chatView = inflater.inflate(R.layout.fragment_chat, container, false);
        RecyclerView recyclerView = chatView.findViewById(R.id.chatRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(MainContract.HISTORY_ENTRY_KEY)) {
                HistoryEntryDTO historyEntry = (HistoryEntryDTO) args.getSerializable(MainContract.HISTORY_ENTRY_KEY);
                if (historyEntry != null) {
                    viewAdapter = new ChatRecycleViewAdapter(historyEntry.getMessages());
                    recyclerView.setAdapter(viewAdapter);
                }
            }
        }
        return chatView;
    }

    @Override
    public void showMessage(MessageDTO message) {
        viewAdapter.addMessage(message);
    }
}