package eapli.board.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientServerChanges extends Thread {
    public static final int LISTEN_SERVER = 7010;
    private static String[] dataContent;

    public ClientServerChanges(String[] board) {
        dataContent = board;
    }

    @Override
    public void run() {
        try {

            ServerSocket serverSock = new ServerSocket(LISTEN_SERVER);

            Socket cliSock;
            while (true) {
                cliSock = serverSock.accept();
                MenuChanges menu = new MenuChanges(cliSock);
                menu.start();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
