package com.example.finalproject.connector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;

//todo zaza
//from https://developer.android.com/guide/topics/connectivity/wifip2p
public class P2PBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;

    public P2PBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
    }

//    todo zaza implement
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == null) {
            return;
        }
        switch (action) {
            case WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION:
                break;
            case WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION:
                break;
            case WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION:
                break;
            case WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION:
                break;
        }
    }

}
