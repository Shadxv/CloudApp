package pl.shadxw.master.protocol.login;

import pl.shadxw.core.network.PacketListener;

public interface PacketLoginInListener extends PacketListener {

    void handleLoginStart(PacketLoginInStart packetLoginInStart);

}
