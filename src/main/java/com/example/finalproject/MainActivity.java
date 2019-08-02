package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import androidx.navigation.Navigation;

import com.example.finalproject.connector.Controller;
import com.example.finalproject.model.dtos.HistoryEntryDTO;
import com.example.finalproject.model.dtos.MessageDTO;

public class MainActivity extends AppCompatActivity implements MainContract.Presenter, NavigationView.OnNavigationItemSelectedListener {

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_history_item) {

        } else if (id == R.id.nav_peer_search_item) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
