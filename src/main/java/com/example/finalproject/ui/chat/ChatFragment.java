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

import com.example.finalproject.R;
import com.example.finalproject.model.dtos.MessageDTO;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View chatView = inflater.inflate(R.layout.fragment_chat, container, false);
        RecyclerView recyclerView = chatView.findViewById(R.id.chatRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //todo zaza get messages list from action
        recyclerView.setAdapter(new ChatRecycleViewAdapter(new ArrayList<MessageDTO>()));
        return chatView;
    }
}
