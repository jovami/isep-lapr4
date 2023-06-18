package eapli.server.application.newChangeEvent;

import eapli.base.board.domain.Board;
import eapli.board.SBProtocol;
import eapli.server.domain.Client;
import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.pubsub.EventHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewChangeWatchDog implements EventHandler {
    public static HashMap<String, ArrayList<Client>> subs = new HashMap<>();
    @Override
    public void onEvent(DomainEvent domainEvent) {

        Socket sock;
        assert domainEvent instanceof NewChangeEvent;


        NewChangeEvent event = (NewChangeEvent) domainEvent;

        SBProtocol send = event.message();
        String board = event.boardChanged();

        if (subs.get(board)==null)
            return;
        for (Client m : subs.get(board)) {
            try {
                sock = new Socket(m.inetAddress(), m.port());
                SendMessageThread th = new SendMessageThread(sock,send);
                th.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized static void addSub(String board, Client c){
        subs.computeIfAbsent(board, k -> new ArrayList<>());
        subs.get(board).add(c);

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
