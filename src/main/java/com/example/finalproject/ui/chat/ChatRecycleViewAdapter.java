package com.example.finalproject.ui.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.finalproject.model.dtos.HistoryEntryDTO;
import com.example.finalproject.model.dtos.MessageDTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ChatRecycleViewAdapter extends RecyclerView.Adapter<MessageHolder>{

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy - hh:mm", Locale.getDefault());

    private List<MessageDTO> messages;

    public ChatRecycleViewAdapter(List<MessageDTO> messages) {
        this.messages = messages;
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
        itemViewHolder.getSentTime().setText(DATE_FORMAT.format(entry.getTime()));
        //todo zaza redraw
        itemViewHolder.setFromMe(entry.isFromMe());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(MessageDTO messageDTO) {
        messages.add(messageDTO);
        notifyDataSetChanged();
    }
}