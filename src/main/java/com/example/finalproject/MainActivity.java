package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.example.finalproject.connector.Controller;
import com.example.finalproject.model.dtos.HistoryEntryDTO;
import com.example.finalproject.model.dtos.MessageDTO;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.Presenter, NavigationView.OnNavigationItemSelectedListener {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private MainContract.Controller controller;

    private MainContract.ChatView chatView;

    private boolean searchPeers = false;

    private BroadcastReceiver receiver;

    private IntentFilter receiverIntentFilter;

    private boolean receiverRegistered;

    private boolean onHistoryPage = true;

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
        this.receiver = receiver;
        this.receiverIntentFilter= intentFilter;
    }

    @Override
    public void showMessage(final MessageDTO messageDTO) {
        if (chatView != null) {
            Handler mainHandler = new Handler(Looper.getMainLooper());
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    chatView.showMessage(messageDTO);
                }
            });
        } else {
            controller.closeConnection();
        }
    }

    @Override
    public void writeMessage(String message) {
        controller.writeMessage(message);
    }

    @Override
    public void chatFinished() {
        if (chatView != null) {
            chatView = null;
            navOnHistoryFragment(R.id.action_nav_chat_to_nav_history);
            showAlert(getString(R.string.connection_closed));
        }
    }

    @Override
    public void showChat(final HistoryEntryDTO historyEntry, boolean historyMode) {
        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.deleteHistoryEntities(Collections.singletonList(historyEntry.getId()));
                navOnHistoryFragment(R.id.action_nav_chat_to_nav_history);
            }
        });
        Bundle args = new Bundle();
        args.putSerializable(MainContract.HISTORY_ENTRY_KEY, historyEntry);
        args.putBoolean(MainContract.HISTORY_MODE_KEY, historyMode);
        searchPeers = false;
        onHistoryPage = false;
        Navigation.findNavController(this, R.id.navigation_controller).navigate(R.id.nav_chat, args);
    }

    @Override
    public void deleteHistoryEntities(List<Long> deleteHistoryEntities) {
        controller.deleteHistoryEntities(deleteHistoryEntities);
    }

    @Override
    public List<HistoryEntryDTO> getHistoryEntities() {
        return controller.getHistoryEntities();
    }

    @Override
    public void showAlert(final String message) {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setChatView(MainContract.ChatView chatView) {
        this.chatView = chatView;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_history_item) {
            findViewById(R.id.deleteButton).setVisibility(View.INVISIBLE);
            onHistoryPage = true;
            Navigation.findNavController(this, R.id.navigation_controller).popBackStack(R.id.nav_history, false);
        } else if (id == R.id.nav_peer_search_item) {
            if (!receiverRegistered) {
                registerReceiver(receiver, receiverIntentFilter);
                receiverRegistered = true;
            }
            Navigation.findNavController(this, R.id.navigation_controller).navigate(R.id.nav_peer_search, new Bundle());
            searchPeers = true;
            onHistoryPage = false;
            controller.discoverPeers();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navOnHistoryFragment(int actionId){
        onHistoryPage = true;
        findViewById(R.id.deleteButton).setVisibility(View.INVISIBLE);
        Navigation.findNavController(this, R.id.navigation_controller).navigate(actionId);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (chatView != null) {
                controller.closeConnection();
            } else if (searchPeers) {
                if (receiverRegistered) {
                    unregisterReceiver(receiver);
                    receiverRegistered = false;
                }
                controller.stopSearchForPeers();
                searchPeers = false;
                navOnHistoryFragment(R.id.action_nav_peer_search_to_nav_history);
            } else if (!onHistoryPage) {
//                todo zaza check back from chat
                onHistoryPage = true;
                findViewById(R.id.deleteButton).setVisibility(View.INVISIBLE);
                Navigation.findNavController(this, R.id.navigation_controller).popBackStack(R.id.nav_history, false);
            } else {
                System.exit(0);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receiverRegistered) {
            unregisterReceiver(receiver);
            receiverRegistered = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!receiverRegistered) {
            registerReceiver(receiver, receiverIntentFilter);
            receiverRegistered = true;
        }
    }

    @Override
    public void updateTitle(String title, String subtitle) {
        ActionBar toolbar= getSupportActionBar();
        if (toolbar != null) {
            toolbar.setTitle(title);
            toolbar.setSubtitle(subtitle);
        }
    }

    @Override
    public String getStringFromResources(int id){
        return getString(id);
    }

    @Override
    public Drawable getDrawableFromResources(int id) {
        return getDrawable(id);
    }
}
