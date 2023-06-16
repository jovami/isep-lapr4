package eapli.board.server.application;

import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.board.SBProtocol;
import eapli.board.server.SBPServerApp;
import eapli.board.server.domain.Client;
import eapli.framework.infrastructure.authz.application.AuthenticationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserSession;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.io.IOException;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;


public class AuthRequestHandler extends AbstractSBServerHandler {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private final MyUserService myUserService = new MyUserService();
    private final AuthenticationService authenticationService = AuthzRegistry.authenticationService();

    public AuthRequestHandler(Socket socket, SBProtocol authRequest) {
        super(socket, authRequest);
    }

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public void run() {
        try {

            String[] auth = request.getContentAsString().split("\0");

            String name = auth[0];
            String pass = auth[1];


            for (Client client : SBPServerApp.activeAuths.values()) {
                if (client.getUserLoggedIn().identity().equals(name)) {
                    System.out.printf("[AUTH] LOG IN FAILED: User %s\tIP: %s\n", name, sock.getInetAddress());
                    SBProtocol responseSent = new SBProtocol();
                    responseSent.setCode(SBProtocol.ERR);
                    responseSent.setContentFromString("User already logged in");
                    responseSent.send(outS);
                    return;
                }
            }

            Optional<UserSession> userSession = authenticate(name, pass);


            SBProtocol responseSent = new SBProtocol();
            if (userSession.isEmpty()) {
                System.out.printf("[AUTH] LOG IN FAILED: User %s\tIP: %s\n", name, sock.getInetAddress());
                responseSent.setCode(SBProtocol.ERR);
                responseSent.setContentFromString("User not recognized");
                responseSent.send(outS);
                return;
            }

            SystemUser systemUser = myUserService.currentUser();
            ShareBoardService srv = new ShareBoardService();

            if (!hasBoardPermissions(systemUser, srv)) {
                System.out.printf("[AUTH] LOG IN FAILED: User %s\tIP: %s\n", name, sock.getInetAddress());
                responseSent.setCode(SBProtocol.ERR);
                responseSent.setContentFromString("User do not has permission to access boards");
                responseSent.send(outS);
                return;
            }

            //TODO: store token
            authenticateUser(responseSent, systemUser);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String authenticateUser(SBProtocol responseSent, SystemUser systemUser) throws IOException {
        String token = generateNewToken();

        //TODO:TOKEN
        Client c = new Client(sock.getInetAddress(), systemUser);
        if (SBPServerApp.activeAuths.putIfAbsent(sock.getInetAddress(), c) == null) {
            System.out.printf("[AUTH] LOGGED IN: User: %s\tIP: %s\n",
                    systemUser.username(), sock.getInetAddress().toString());
            responseSent.setCode(SBProtocol.TOKEN);
            responseSent.setContentFromString(token);
            responseSent.send(outS);
            return token;
        } else {
            responseSent.setCode(SBProtocol.ERR);
            responseSent.setContentFromString("User already logged in");
            responseSent.send(outS);
        }
        return null;
    }

    private boolean hasBoardPermissions(SystemUser systemUser, ShareBoardService srv) throws IOException {
        return (!srv.listBoardsUserOwns(systemUser).isEmpty())
                || (!srv.getBoardsByParticipant(systemUser).isEmpty());
    }

    //TODO: authService won't be a problem when multiple users trying to logIn????
    private synchronized Optional<UserSession> authenticate(final String username, final String rawPassword) {
        return authenticationService.authenticate(username, rawPassword, BaseRoles.allRoles());
    }
}
