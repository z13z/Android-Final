package com.example.finalproject.connector;

import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;

import com.example.finalproject.MainContract;
import com.example.finalproject.model.Database;
import com.example.finalproject.model.ModelController;
import com.example.finalproject.model.dtos.HistoryEntryDTO;
import com.example.finalproject.model.helpers.HistoryHelper;
import com.example.finalproject.model.helpers.MessageHelper;

import java.util.List;

public class Controller implements MainContract.Controller {

    private static final long FATAL_ALERT_DURATION = 5000;

    private P2PBroadcastReceiver broadcastReceiver;

    private MainContract.Presenter presenter;

    private Connector connector;

    private MainContract.ChatModel model;

    public Controller(WifiP2pManager manager, WifiP2pManager.Channel channel, MainContract.Presenter presenter){
        this.presenter = presenter;
        initBroadcastReceiver(manager,channel);
    }

    private void initBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel){
        broadcastReceiver = new P2PBroadcastReceiver(manager, channel, this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        presenter.registerBroadcastReceiver(broadcastReceiver,intentFilter);
    }

    public void discoverPeers(){
        broadcastReceiver.discoverPeers();
    }


    @Override
    public void createServerSocket() {
        connector = new Connector(null, this);
        connector.start();
    }

    @Override
    public void createClientSocket(String address) {
        connector = new Connector(address, this);
        connector.start();
    }

    @Override
    public void connectionEstablished() {
        model.chatStarted();
        presenter.showChat(HistoryHelper.getDto(model.getCurrentHistoryEntry()), false);
    }

    @Override
    public void connectionFinished() {
        model = null;
        connector = null;
        presenter.chatFinished();
    }

    @Override
    public void closeConnection(){
        connector.closeConnection();
    }

    @Override
    public void stopSearchPeers(){
//        todo zaza implement
    }

    @Override
    public void readMessage(String message) {
        presenter.showMessage(MessageHelper.getDto(model.saveMessage(message, true)));
    }

    @Override
    public void writeMessage(String message){
        if (connector != null) {
            connector.writeMessage(message);
            presenter.showMessage(MessageHelper.getDto(model.saveMessage(message, true)));
        }
    }

    @Override
    public void setCurrentPhoneName(String phoneName) {
        model = new ModelController(phoneName);
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
    public void showAlertAndExit(String message) {
        presenter.showAlert(message);
    }
}
