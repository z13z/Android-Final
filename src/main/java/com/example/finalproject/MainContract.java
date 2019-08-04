package com.example.finalproject;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

import com.example.finalproject.model.dtos.HistoryEntryDTO;
import com.example.finalproject.model.dtos.MessageDTO;
import com.example.finalproject.model.entities.HistoryWithMessages;
import com.example.finalproject.model.entities.Message;

import java.util.List;

public interface MainContract {

    String HISTORY_ENTRY_KEY = "historyEntryKey";

    String HISTORY_MODE_KEY = "historyMode";

    interface Controller {

        void discoverPeers();

        void createServerSocket();

        void createClientSocket(String address);

        void connectionEstablished();

        void connectionFinished();

        void closeConnection();

        void stopSearchPeers();

        void readMessage(String message);

        void writeMessage(String message);

        void setCurrentPhoneName(String phoneName);

        void deleteHistoryEntities(List<Long> deleteHistoryEntities);

        List<HistoryEntryDTO> getHistoryEntities();

        void showAlertAndExit(String message);
    }

    interface Presenter {

        void registerBroadcastReceiver(BroadcastReceiver receiver, IntentFilter intentFilter);

        void showMessage(MessageDTO dto);

        void showChat(HistoryEntryDTO historyEntry, boolean historyMode);

        void writeMessage(String message);

        void chatFinished();

        void deleteHistoryEntities(List<Long> deleteHistoryEntities);

        List<HistoryEntryDTO> getHistoryEntities();

        void showAlert(String message);

        void setChatView(ChatView chatView);
    }

    interface ChatView{

        void showMessage(MessageDTO message);
    }

    interface ChatModel{

        void chatStarted();

        Message saveMessage(String message, boolean fromMe);

        HistoryWithMessages getCurrentHistoryEntry();
    }
}
