package eapli.board.server.application;

import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.board.SBPMessage;
import eapli.board.server.MenuRequest;
import eapli.framework.infrastructure.authz.application.AuthenticationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserSession;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.validations.Preconditions;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;


public class AuthRequestHandler implements Runnable {
    private DataOutputStream outS;
    private final Socket sock;
    private final SBPMessage authRequest;
    private final MyUserService myUserService = new MyUserService();

    private final AuthenticationService authenticationService = AuthzRegistry.authenticationService();

    public AuthRequestHandler(Socket socket, SBPMessage authRequest) {
        Preconditions.areEqual(authRequest.getCode(), SBPMessage.AUTH);
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

            SBPMessage responseSent = new SBPMessage();
            if (userSession.isEmpty()) {
                responseSent.setCode(SBPMessage.ERR);
                responseSent.setContentFromString("User not recognized");
                responseSent.send(outS);
                return;
            }

            SystemUser systemUser = myUserService.currentUser();

            logged = authenticateUser(logged, responseSent, systemUser);

            if (!logged) {
                responseSent.setCode(SBPMessage.ERR);
                responseSent.send(outS);
                System.out.printf("[AUTH] LOG IN FAILED: User %s\tIP: %s\n", name, sock.getInetAddress());
            }

        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean authenticateUser(boolean logged, SBPMessage responseSent, SystemUser systemUser) throws IOException {
        if (MenuRequest.addInetAddress(systemUser, sock.getInetAddress())) {
            System.out.printf("[AUTH] LOGGED IN: User: %s\tIP: %s\n",
                    systemUser.username(), sock.getInetAddress().toString());
            responseSent.setCode(SBPMessage.ACK);
            responseSent.send(outS);
            logged = true;
        }
        return logged;
    }


    //TODO: authService won't be a problem when multiple users trying to logIn????
    private Optional<UserSession> authenticate(final String username, final String rawPassword) {
        return authenticationService.authenticate(username, rawPassword, BaseRoles.allRoles());
    }
}
