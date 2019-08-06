package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;

import com.example.finalproject.model.dtos.HistoryEntryDTO;
import com.example.finalproject.model.dtos.MessageDTO;
import com.example.finalproject.model.entities.HistoryWithMessages;
import com.example.finalproject.model.entities.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public interface MainContract {

    String HISTORY_ENTRY_KEY = "historyEntryKey";

    String HISTORY_MODE_KEY = "historyMode";

    @SuppressLint("ConstantLocale")
    DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss", Locale.getDefault());

    interface Controller {

        void discoverPeers();

        void createServerSocket();

        void createClientSocket(String address);

        void connectionEstablished();

        void connectionFinished();

        void closeConnection();

        void readMessage(String message);

        void writeMessage(String message);

        void setCurrentPhoneName(String phoneName);

        void deleteHistoryEntities(List<Long> deleteHistoryEntities);

        List<HistoryEntryDTO> getHistoryEntities();

        void showAlert(String message);

        void stopSearchForPeers();
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

        void updateTitle(String title, String subtitle);

        String getStringFromResources(int id);

        Drawable getDrawableFromResources(int id);
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
