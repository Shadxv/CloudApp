package pl.shadxw.core.network;

import pl.shadxw.core.protocol.Packet;

public interface Connection {

    void handle();

    void setListener(PacketListener packetListener);

    void send(Packet<?> packet);

}
