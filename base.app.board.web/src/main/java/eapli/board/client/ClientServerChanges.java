package eapli.board.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static eapli.board.client.ClientServerAjax.LISTEN_SERVER;

public class ClientServerChanges extends Thread {
    //private static String[] dataContent;

    public ClientServerChanges() {
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
