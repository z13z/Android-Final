package com.example.finalproject.connector;

import android.util.Log;

import com.example.finalproject.MainContract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Connector extends Thread {

    private static final int CONNECTION_PORT = 10024;

    private static final int CONNECTION_TIMEOUT = 60 * 60 * 1000;

    private String serverAddress;

    private ServerSocket  serverSocket;

    private Socket socket;

    private InputStream is;

    private BufferedReader reader;

    private PrintWriter writer;

    private OutputStream os;

    private MainContract.Controller controller;

    private ThreadPoolExecutor writersThreadPool = new ThreadPoolExecutor(1, 1, CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    //null if serverSocket must be created
    public Connector(String serverAddress, MainContract.Controller controller){
        this.controller = controller;
        this.serverAddress = serverAddress;
    }

    @Override
    public void run(){
        try {
            if (serverAddress != null) {
                createClientSocket();
            } else {
                createServerSocket();
            }
            readLoop();
        } catch (IOException e) {
            Log.e("connection_problem", "error wile creating server/client socket", e);
            controller.showAlertAndExit("error wile creating server/client socket");
        }
    }

    public void writeMessage(final String message) {
        writersThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                writer.write(message + '\n');
                writer.flush();
            }
        });
    }

    public void closeConnection() {
        try {
            writersThreadPool.awaitTermination(0, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Log.e("connection_problem", "can't stop write threads pool", e);
            controller.showAlertAndExit("can't stop write threads pool");
        }
        if (writer != null) {
            writer.close();
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                Log.e("connection_problem", "can't close reader", e);
            }
        }
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                Log.e("connection_problem", "can't close input stream", e);
            }
        }
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                Log.e("connection_problem", "can't close input stream", e);
            }
        }
        if (socket != null) {
            if (socket.isConnected()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    Log.e("connection_problem", "can't close socket", e);
                }
            }
        }
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                Log.e("connection_problem", "can't close server socket", e);
            }
        }
        controller.connectionFinished();
    }

    private void readLoop() throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            controller.readMessage(line);
        }
        Log.i("connection_info", "connection closed");
        controller.connectionFinished();
        closeConnection();
    }

    private void createClientSocket() throws IOException {
        socket = new Socket();
        socket.bind(null);
        socket.connect(new InetSocketAddress(serverAddress, CONNECTION_PORT), CONNECTION_TIMEOUT);
        os = socket.getOutputStream();
        writer = new PrintWriter(os);
        is = socket.getInputStream();
        reader = new BufferedReader(new InputStreamReader(is));
        controller.connectionEstablished();
    }

    private void createServerSocket() throws IOException {
        serverSocket = new ServerSocket(CONNECTION_PORT);
        socket = serverSocket.accept();
        os = socket.getOutputStream();
        writer = new PrintWriter(os);
        is = socket.getInputStream();
        reader = new BufferedReader(new InputStreamReader(is));
        controller.connectionEstablished();
    }
}
