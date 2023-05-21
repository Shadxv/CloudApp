package pl.shadxw.master.protocol.status;

import pl.shadxw.core.protocol.Packet;

public class PacketStatusInInfo implements Packet<PacketStatusInListener> {

    @Override
    public void handle(PacketStatusInListener t0) {
        t0.handleStatusRequest(this);
    }

}
