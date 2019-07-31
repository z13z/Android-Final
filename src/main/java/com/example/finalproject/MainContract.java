package com.example.finalproject;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

import com.example.finalproject.model.dtos.HistoryEntryDTO;
import com.example.finalproject.model.dtos.MessageDTO;
import com.example.finalproject.model.entities.Message;

public interface MainContract {

    String HISTORY_ENTRY_KEY = "historyEntryKey";

    //        todo zaza call
    interface Controller {

        void discoverPeers();

        void createServerSocket();

        void createClientSocket(String address);

        void connectionEstablished();

        void connectionFinished();

        void readMessage(String message);

        void writeMessage(String message);

        void setCurrentPhoneName(String phoneName);
    }

    interface Presenter {

        void registerBroadcastReceiver(BroadcastReceiver receiver, IntentFilter intentFilter);

        void showMessage(MessageDTO dto);

        void showChatHistory(HistoryEntryDTO historyEntry);

        void writeMessage(String message);

    }

    interface ChatView{

        void showMessage(MessageDTO message);
    }

    interface ChatModel{

        void chatStarted();

        Message saveMessage(String message, boolean fromMe);
    }
}
