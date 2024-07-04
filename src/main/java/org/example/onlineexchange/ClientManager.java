package org.example.onlineexchange;

import java.net.Socket;

public class ClientManager implements Runnable {
    private Socket clientSocket;

    public ClientManager(Socket clientSocket)  {
        this.clientSocket = clientSocket;
    }
    @Override
    public void run() {

    }
}
