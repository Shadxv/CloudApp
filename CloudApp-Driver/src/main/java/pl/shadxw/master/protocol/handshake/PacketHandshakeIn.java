package pl.shadxw.master.protocol.handshake;

import lombok.Getter;
import pl.shadxw.api.util.PacketDataManager;
import pl.shadxw.core.protocol.Packet;

public class PacketHandshakeIn implements Packet<PacketHandshakeInListener> {

    @Getter private final PacketDataManager packetDataManager;
    @Getter private final int protocolVersion;
    @Getter private final String hostname;
    @Getter private final int port;
    @Getter private final int state;

    public PacketHandshakeIn(PacketDataManager packetDataManager){
        this.packetDataManager = packetDataManager;
        this.protocolVersion = packetDataManager.readVarInt();
        this.hostname = packetDataManager.readUtf(255);
        this.port = packetDataManager.readUnsignedShort();
        this.state = packetDataManager.readVarInt();
    }

    @Override
    public void handle(PacketHandshakeInListener t0) {
        t0.handleHandshake(this);
    }
}
