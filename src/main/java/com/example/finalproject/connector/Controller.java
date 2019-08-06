package com.example.finalproject.connector;

import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;

import com.example.finalproject.MainContract;
import com.example.finalproject.model.Database;
import com.example.finalproject.model.ModelController;
import com.example.finalproject.model.dtos.HistoryEntryDTO;
import com.example.finalproject.model.helpers.HistoryHelper;
import com.example.finalproject.model.helpers.MessageHelper;

import java.util.List;
import java.util.UUID;

public class Controller implements MainContract.Controller {

    private P2PBroadcastReceiver broadcastReceiver;

    private MainContract.Presenter presenter;

    private Connector connector;

    private MainContract.ChatModel model;

    private boolean searchForPeers;


    public Controller(WifiP2pManager manager, WifiP2pManager.Channel channel, MainContract.Presenter presenter, WifiManager wifiManager) {
        this.presenter = presenter;
        initBroadcastReceiver(manager, channel, wifiManager);
    }

    private void initBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, WifiManager wifiManager) {
        broadcastReceiver = new P2PBroadcastReceiver(manager, channel, this, wifiManager);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        presenter.registerBroadcastReceiver(broadcastReceiver, intentFilter);
    }

    public void discoverPeers() {
        searchForPeers = true;
        broadcastReceiver.discoverPeers();
    }


    @Override
    public void createServerSocket() {
        if (connector != null) {
            connector.interrupt();
            closeConnection();
        }
        connector = new Connector(null, this);
        connector.start();
    }

    @Override
    public void createClientSocket(String address) {
        if (connector != null) {
            connector.interrupt();
            closeConnection();
        }
        connector = new Connector(address, this);
        connector.start();
    }

    @Override
    public void connectionEstablished() {
        if (model == null) {
            model = new ModelController(UUID.randomUUID().toString().substring(0, 8));
        }
        model.chatStarted();
        if (searchForPeers) {
            presenter.showChat(HistoryHelper.getDto(model.getCurrentHistoryEntry()), false);
        } else {
            closeConnection();
        }
    }

    @Override
    public void connectionFinished() {
        model = null;
        connector = null;
        presenter.chatFinished();
    }

    @Override
    public void closeConnection() {
        if (connector != null) {
            connector.closeConnection();
            broadcastReceiver.clearConnectedGroup();
        } else {
            presenter.chatFinished();
        }
    }

    @Override
    public void readMessage(String message) {
        presenter.showMessage(MessageHelper.getDto(model.saveMessage(message, false)));
    }

    @Override
    public void writeMessage(String message) {
        if (connector != null) {
            connector.writeMessage(message);
            presenter.showMessage(MessageHelper.getDto(model.saveMessage(message, true)));
        }
    }

    @Override
    public void setCurrentPhoneName(String phoneName) {
        if (model == null) {
            model = new ModelController(phoneName);
        }
    }

    @Override
    public void deleteHistoryEntities(List<Long> deleteHistoryEntities) {
        Database.getInstance().dataDao().deleteHistoryMessages(deleteHistoryEntities);
        Database.getInstance().dataDao().deleteHistoryEntries(deleteHistoryEntities);
    }

    @Override
    public List<HistoryEntryDTO> getHistoryEntities() {
        return HistoryHelper.getDtos(Database.getInstance().dataDao().getHistoryEntries());
    }

    @Override
    public void showAlert(String message) {
        presenter.showAlert(message);
    }

    @Override
    public void stopSearchForPeers() {
        searchForPeers = false;
        if (connector != null) {
            connector.interrupt();
            connector.closeConnection();
            connector = null;
        }
    }
}
