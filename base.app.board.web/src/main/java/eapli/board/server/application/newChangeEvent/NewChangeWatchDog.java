package eapli.board.server.application.newChangeEvent;

import eapli.base.board.domain.Board;
import eapli.board.SBProtocol;
import eapli.board.client.ClientServer;
import eapli.board.server.domain.Client;
import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.pubsub.EventHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewChangeWatchDog implements EventHandler {
    public static HashMap<Board, List<Client>> subs = new HashMap<>();


    @Override
    public void onEvent(DomainEvent domainEvent) {

        Socket sock;
        assert domainEvent instanceof NewChangeEvent;


        NewChangeEvent event = (NewChangeEvent) domainEvent;

        SBProtocol send = event.message();
        Board board = event.boardChanged();

        for (Client m : subs.get(board)) {
            try {
                sock = new Socket(m.inetAddress(), ClientServer.LISTEN_SERVER);
                SendMessageThread th = new SendMessageThread(sock,send);
                th.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized static void addSub(Board board, Client c){
        List<Client> tmp = subs.get(board);
        if(tmp==null){
            subs.put(board,new ArrayList<>());
            tmp = subs.get(board);
        }
        tmp.add(c);
    }
    public synchronized static void removeSub(Board board, Client c){
        List<Client> tmp = subs.get(board);
        if(tmp==null){
            return;
        }
        tmp = subs.get(board);
        tmp.remove(c);
    }
}
