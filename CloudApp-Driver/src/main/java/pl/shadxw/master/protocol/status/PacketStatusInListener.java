package pl.shadxw.master.protocol.status;

import pl.shadxw.core.network.PacketListener;

public interface PacketStatusInListener extends PacketListener {

    void handleStatusRequest(PacketStatusInInfo packetStatusInStatus);

    void handlePingRequest(PacketStatusInPing packetStatusInPing);

}
