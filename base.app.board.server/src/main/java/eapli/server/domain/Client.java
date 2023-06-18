package eapli.server.domain;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.net.InetAddress;

public class Client {
    private final InetAddress ip;
    private final SystemUser userLoggedIn;
    private int port;

    public Client(InetAddress ip, SystemUser userLoggedIn) {
        this.ip = ip;
        this.userLoggedIn = userLoggedIn;
    }

    public synchronized int port() {
        return this.port;
    }

    public synchronized void setPort(int port) {
        this.port = port;
    }

    public synchronized InetAddress inetAddress() {
        return ip;
    }

    public synchronized SystemUser getUserLoggedIn() {
        return userLoggedIn;
    }

}
