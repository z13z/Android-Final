package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.finalproject.connector.Controller;
import com.example.finalproject.model.dtos.HistoryEntryDTO;
import com.example.finalproject.model.dtos.MessageDTO;

public class MainActivity extends AppCompatActivity implements MainContract.Presenter {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private MainContract.Controller controller;

    private MainContract.ChatView chatView;

    public static Context getContext() {
        return context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        NavigationView navView = findViewById(R.id.navigation_view);
        NavController navController = Navigation.findNavController(this, R.id.navigation_controller);
        NavigationUI.setupWithNavController(navView, navController);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                        .setDrawerLayout((DrawerLayout) findViewById(R.id.drawer_layout))
                        .build();
        initConnector();
    }

    private void initConnector(){
        WifiP2pManager manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        WifiP2pManager.Channel channel = manager.initialize(this, getMainLooper(), null);
        controller = new Controller(manager, channel, this);
    }

    @Override
    public void registerBroadcastReceiver(BroadcastReceiver receiver, IntentFilter intentFilter) {
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public void showMessage(MessageDTO messageDTO) {
        if (chatView != null) {
            chatView.showMessage(messageDTO);
        } else {
            controller.connectionFinished();
        }
    }

    //todo zaza call
    @Override
    public void writeMessage(String message) {
        controller.writeMessage(message);
    }

    @Override
    public void showChatHistory(HistoryEntryDTO historyEntry){
        Bundle args = new Bundle();
        args.putSerializable(MainContract.HISTORY_ENTRY_KEY, historyEntry);
        Navigation.findNavController(this, R.id.navigation_controller).navigate(R.id.action_historyFragment_to_chatFragment2, args);
    }

    public void setChatView(MainContract.ChatView chatView) {
        this.chatView = chatView;
    }
}
