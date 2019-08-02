package com.example.finalproject.connector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;

import com.example.finalproject.MainContract;

import java.util.HashMap;
import java.util.Map;

//todo zaza
//from https://developer.android.com/guide/topics/connectivity/wifip2p
//https://developer.android.com/training/connect-devices-wirelessly/wifi-direct
public class P2PBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;

    private MainContract.Controller connector;

    public P2PBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, MainContract.Controller connector) {
        super();
        this.manager = manager;
        this.channel = channel;
        this.connector = connector;
    }

    public void discoverPeers(){
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(int reason) {
//                todo zaza implement
                System.exit(0);
            }
        });
    }

    private void connectWithPeer(String address, final String phoneName) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = address;
        manager.connect(channel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                connector.setCurrentPhoneName(phoneName);
            }

            @Override
            public void onFailure(int reason) {
//                todo zaza implement
                System.exit(0);
            }
        });
    }

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
                peersChangedAction();
                break;
            case WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION:
                connectionChangedAction(intent);
                break;
            case WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION:
                break;
        }
    }

    public void connectionChangedAction(Intent intent){
        NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
        if (networkInfo.isConnected()) {
            manager.requestConnectionInfo(channel, new WifiP2pManager.ConnectionInfoListener() {
                @Override
                public void onConnectionInfoAvailable(WifiP2pInfo info) {
                    if (info.groupFormed && info.isGroupOwner) {
                        connector.createServerSocket();
                    } else if (info.groupFormed) {
                        connector.createClientSocket(info.groupOwnerAddress.getHostAddress());
                    } else {
//                        todo zaza implement
                        System.exit(0);
                    }
                }
            });
        }
    }

    private void peersChangedAction(){
        manager.requestPeers(channel, new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peers) {
                if (peers != null && peers.getDeviceList() != null && peers.getDeviceList().isEmpty()) {
                    WifiP2pDevice device = peers.getDeviceList().iterator().next();
                    connectWithPeer(device.deviceAddress, device.deviceName);
                }
            }
        });
    }
}