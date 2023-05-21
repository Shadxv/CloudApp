package pl.shadxw.master.protocol.status;

import lombok.Getter;
import pl.shadxw.api.util.PacketDataManager;
import pl.shadxw.core.protocol.Packet;

public class PacketStatusInPing implements Packet<PacketStatusInListener> {

    @Getter private final PacketDataManager packetDataManager;
    @Getter private final long time;

    public PacketStatusInPing(PacketDataManager packetDataManager){
        this.packetDataManager = packetDataManager;
        this.time = packetDataManager.readLong();
    }

    @Override
    public void handle(PacketStatusInListener t0) {
        t0.handlePingRequest(this);
    }
}
