package pl.shadxw.core.models;

public interface IServer {

    void run() throws Exception;
    void sendPacket(Packet packet);

}
