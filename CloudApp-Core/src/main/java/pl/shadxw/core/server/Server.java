package pl.shadxw.core.server;

import lombok.Getter;

public abstract class Server {

    @Getter private final int port;
    @Getter private final String address;

    protected Server(int port, String address){
        this.port = port;
        this.address = address;
    }

    public abstract void runServer() throws Exception;

    public abstract void stop() throws Exception;

}
