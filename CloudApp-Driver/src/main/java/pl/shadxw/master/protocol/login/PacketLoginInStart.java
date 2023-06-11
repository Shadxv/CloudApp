package pl.shadxw.master.protocol.login;

import lombok.Getter;
import pl.shadxw.api.util.PacketDataManager;
import pl.shadxw.core.protocol.Packet;

import java.util.Optional;
import java.util.UUID;

public class PacketLoginInStart implements Packet<PacketLoginInListener> {

    @Getter private final PacketDataManager packetDataManager;
    @Getter private final String name;
    @Getter private final boolean hasUUID;
    @Getter private final Optional<UUID> uuid;

    public PacketLoginInStart(PacketDataManager packetDataManager){
        this.packetDataManager = packetDataManager;
        this.name = packetDataManager.readUtf(255);
        this.hasUUID = (packetDataManager.readVarInt() != 0);
        if(this.hasUUID) this.uuid = Optional.of(packetDataManager.readUUID());
        else this.uuid = Optional.empty();
    }

    @Override
    public void handle(PacketLoginInListener t0) {
        t0.handleLoginStart(this);
    }

}
