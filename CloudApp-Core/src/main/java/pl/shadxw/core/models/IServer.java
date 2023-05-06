package pl.shadxw.core.models;

import pl.shadxw.core.protocol.Packet;

public interface IServer {

    void run() throws Exception;
    void sendPacket(Packet packet);

}
