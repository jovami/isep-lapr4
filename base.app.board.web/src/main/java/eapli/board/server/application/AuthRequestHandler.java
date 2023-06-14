package eapli.board.server.application;

import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.board.SBProtocol;
import eapli.board.server.MenuRequest;
import eapli.framework.infrastructure.authz.application.AuthenticationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserSession;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.validations.Preconditions;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;


public class AuthRequestHandler implements Runnable {
    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe
    private DataOutputStream outS;
    private final Socket sock;
    private final SBProtocol authRequest;
    private final MyUserService myUserService = new MyUserService();
    private final AuthenticationService authenticationService = AuthzRegistry.authenticationService();

    public AuthRequestHandler(Socket socket, SBProtocol authRequest) {
        Preconditions.areEqual(authRequest.getCode(), SBProtocol.AUTH);
        this.authRequest = authRequest;
        this.sock = socket;
    }


    public void run() {
        boolean logged = false;
        try {
            outS = new DataOutputStream(sock.getOutputStream());

            String message = authRequest.getContentAsString();
            String[] auth = message.split("\0");

            String name = auth[0];
            String pass = auth[1];

            Optional<UserSession> userSession = authenticate(name, pass);

            SBProtocol responseSent = new SBProtocol();
            if (userSession.isEmpty()) {
                responseSent.setCode(SBProtocol.ERR);
                responseSent.setContentFromString("User not recognized");
                responseSent.send(outS);
                return;
            }

            SystemUser systemUser = myUserService.currentUser();
            ShareBoardService srv = new ShareBoardService();

            if (!hasBoardPermissions(responseSent, systemUser, srv)) return;

            String token = authenticateUser(logged, responseSent, systemUser);

            if (token==null) {
                responseSent.setCode(SBProtocol.ERR);
                responseSent.send(outS);
                System.out.printf("[AUTH] LOG IN FAILED: User %s\tIP: %s\n", name, sock.getInetAddress());
            }

        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String authenticateUser(boolean logged, SBProtocol responseSent, SystemUser systemUser) throws IOException {


        String token = generateNewToken();
        if (MenuRequest.addInetAddress(systemUser, sock.getInetAddress())) {
            System.out.printf("[AUTH] LOGGED IN: User: %s\tIP: %s\n",
                    systemUser.username(), sock.getInetAddress().toString());
            responseSent.setCode(SBProtocol.TOKEN);
            responseSent.setContentFromString(token);
            responseSent.send(outS);
            return token;
        }
        return null;
    }

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    private boolean hasBoardPermissions(SBProtocol responseSent, SystemUser systemUser, ShareBoardService srv) throws IOException {
        if ((srv.listBoardsUserOwns(systemUser).isEmpty())
                && (srv.getBoardsByParticipant(systemUser).isEmpty())) {

            responseSent.setCode(SBProtocol.ERR);
            responseSent.setContentFromString("User do not has permission to access boards");
            responseSent.send(outS);
            return false;
        }
        return true;
    }

    //TODO: authService won't be a problem when multiple users trying to logIn????
    private Optional<UserSession> authenticate(final String username, final String rawPassword) {
        return authenticationService.authenticate(username, rawPassword, BaseRoles.allRoles());
    }
}
