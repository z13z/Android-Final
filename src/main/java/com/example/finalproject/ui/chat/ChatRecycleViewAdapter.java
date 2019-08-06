package com.example.finalproject.ui.chat;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finalproject.MainContract;
import com.example.finalproject.R;
import com.example.finalproject.model.dtos.MessageDTO;

import java.util.List;

public class ChatRecycleViewAdapter extends RecyclerView.Adapter<MessageHolder> {

    private List<MessageDTO> messages;

    private MainContract.Presenter presenter;

    ChatRecycleViewAdapter(List<MessageDTO> messages, MainContract.Presenter presenter) {
        this.messages = messages;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_chat_message, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView messageTimeLabel = view.findViewById(R.id.messageTime);
                messageTimeLabel.setVisibility(messageTimeLabel.isShown() ? View.INVISIBLE : View.VISIBLE);
            }
        });
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder itemViewHolder, int index) {
        MessageDTO entry = messages.get(index);
        itemViewHolder.getMessageContent().setText(entry.getContent());
        itemViewHolder.getSentTime().setText(MainContract.DATE_FORMAT.format(entry.getTime()));
        Drawable background = presenter.getDrawableFromResources(entry.isFromMe() ? R.drawable.shape_message_background_pink : R.drawable.shape_message_background_tan);
        itemViewHolder.setFromMe(entry.isFromMe(), background);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    void addMessage(MessageDTO messageDTO) {
        messages.add(messageDTO);
        notifyDataSetChanged();
    }
}