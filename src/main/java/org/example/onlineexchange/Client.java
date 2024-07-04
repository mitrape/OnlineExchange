package org.example.onlineexchange;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;

    public Client(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            Main.go();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void close() {
//        try {
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        new Client("127.0.0.1",9090);
    }

}
