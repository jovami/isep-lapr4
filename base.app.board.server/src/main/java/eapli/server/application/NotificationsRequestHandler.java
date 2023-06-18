package eapli.server.application;

import eapli.base.board.domain.BoardParticipant;
import eapli.base.board.repositories.BoardParticipantRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.board.SBProtocol;
import eapli.server.SBServerApp;

import java.io.IOException;
import java.net.Socket;

public class NotificationsRequestHandler extends AbstractSBServerHandler {

    public BoardParticipantRepository repo = PersistenceContext.repositories().boardParticipants();

    public NotificationsRequestHandler(Socket sock, SBProtocol protocol) {
        super(sock, protocol);

    }

    @Override
    public void run() {

        StringBuilder b = new StringBuilder();
        Iterable<BoardParticipant> iter = repo.byUser(SBServerApp.activeAuths.get(authToken).getUserLoggedIn());
        SBProtocol sendPermissions = new SBProtocol();


        if (iter == null) {
            sendPermissions.setCode(SBProtocol.ERR);
            sendPermissions.setContentFromString("Not allowed to view boards");
        } else {
            for (BoardParticipant bp : iter) {
                b.append(bp.board().getBoardTitle().title());
                b.append("#&&#");
                b.append(bp.permission().toString());
                b.append("\0");
            }
            sendPermissions.setContentFromString(b.toString());
        }

        try {
            sendPermissions.send(outS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
