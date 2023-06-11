package eapli.board.server.domain;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.net.InetAddress;

public class Client {
    private final InetAddress ip;
    private final SystemUser userLoggedIn;
    public Client(InetAddress ip, SystemUser userLoggedIn){
        this.ip = ip;
        this.userLoggedIn = userLoggedIn;
    }
    public InetAddress inetAddress() {
        return ip;
    }

    public SystemUser getUserLoggedIn() {
        return userLoggedIn;
    }

}
