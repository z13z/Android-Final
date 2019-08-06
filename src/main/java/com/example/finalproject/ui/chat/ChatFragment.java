package com.example.finalproject.ui.chat;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finalproject.MainContract;
import com.example.finalproject.R;
import com.example.finalproject.model.dtos.HistoryEntryDTO;
import com.example.finalproject.model.dtos.MessageDTO;

public class ChatFragment extends Fragment implements MainContract.ChatView, View.OnClickListener {

    private ChatRecycleViewAdapter viewAdapter;

    private MainContract.Presenter presenter;

    private TextView messageField;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View chatView = inflater.inflate(R.layout.fragment_chat, container, false);
        RecyclerView recyclerView = chatView.findViewById(R.id.chatRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle args = getArguments();
        HistoryEntryDTO historyEntry = null;
        presenter = (MainContract.Presenter) getActivity();
        if (args != null) {
            if (args.containsKey(MainContract.HISTORY_ENTRY_KEY)) {
                historyEntry = (HistoryEntryDTO) args.getSerializable(MainContract.HISTORY_ENTRY_KEY);
                if (historyEntry != null) {
                    viewAdapter = new ChatRecycleViewAdapter(historyEntry.getMessages(), presenter);
                    recyclerView.setAdapter(viewAdapter);
                }
            }
        }


        if (args == null || args.getBoolean(MainContract.HISTORY_MODE_KEY)) {
            hideChatElements(chatView);
            Activity activity = getActivity();
            if (activity != null) {
                activity.findViewById(R.id.deleteButton).setVisibility(View.VISIBLE);
            }
            if (historyEntry != null) {
                presenter.updateTitle(historyEntry.getPhoneName(), MainContract.DATE_FORMAT.format(historyEntry.getStartTime()));
            }
        } else {
            if (presenter != null) {
                chatView.findViewById(R.id.sendMessageButton).setOnClickListener(this);
                presenter.setChatView(this);
                if (historyEntry != null) {
                    presenter.updateTitle(historyEntry.getPhoneName(), null);
                }
            }
        }

        messageField = chatView.findViewById(R.id.messageField);

        return chatView;
    }

    private void hideChatElements(View chatView) {
        ConstraintLayout layout = chatView.findViewById(R.id.chatInnerLayout);
        ConstraintSet constraints = new ConstraintSet();
        constraints.clone(layout);
        constraints.clear(R.id.chatRecyclerView, ConstraintSet.BOTTOM);
        constraints.connect(R.id.chatRecyclerView, ConstraintSet.BOTTOM, R.id.chatInnerLayout, ConstraintSet.BOTTOM);
        constraints.applyTo(layout);
        chatView.findViewById(R.id.sendMessageButton).setVisibility(View.INVISIBLE);
        chatView.findViewById(R.id.messageField).setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMessage(MessageDTO message) {
        viewAdapter.addMessage(message);
    }

    @Override
    public void onClick(View v) {
        CharSequence charSequence = messageField.getText();
        if (charSequence != null && charSequence.length() > 0) {
            presenter.writeMessage(String.valueOf(charSequence));
            messageField.setText("");
        }
    }
}
