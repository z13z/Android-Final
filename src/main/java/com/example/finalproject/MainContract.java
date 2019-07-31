package com.example.finalproject;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

import com.example.finalproject.model.dtos.MessageDTO;
import com.example.finalproject.model.entities.Message;

public interface MainContract {

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

    interface Viewer {

        void registerBroadcastReceiver(BroadcastReceiver receiver, IntentFilter intentFilter);

        void addMessage(MessageDTO dto);
    }

    interface ChatModel{

        void chatStarted();

        Message saveMessage(String message, boolean fromMe);
    }
}
