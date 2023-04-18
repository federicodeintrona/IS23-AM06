package it.polimi.ingsw.server.Messages;

public class ServerInfoMessage extends Message{

    private int ip;

    private int port;

    public int getIp() {
        return ip;
    }

    public void setIp(int ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
