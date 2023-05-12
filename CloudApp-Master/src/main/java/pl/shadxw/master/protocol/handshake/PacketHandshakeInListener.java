package pl.shadxw.master.protocol.handshake;

import pl.shadxw.core.network.PacketListener;

public interface PacketHandshakeInListener extends PacketListener {

    void handleHandshake(PacketHandshakeIn packetHandshakeIn);

}
