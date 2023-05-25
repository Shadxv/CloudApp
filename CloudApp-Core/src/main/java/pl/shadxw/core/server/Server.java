package pl.shadxw.core.server;

import lombok.Getter;

public abstract class Server {

    @Getter private final int port;

    protected Server(int port){
        this.port = port;
    }

    public abstract void runServer() throws Exception;

    public abstract void stop() throws Exception;

}
