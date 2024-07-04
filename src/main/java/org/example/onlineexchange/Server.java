package org.example.onlineexchange;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientManager clientManager = new ClientManager(clientSocket);
                Thread clientThread = new Thread(clientManager);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void stopServer() {
//        try {
//            serverSocket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer(9090);
    }
}
