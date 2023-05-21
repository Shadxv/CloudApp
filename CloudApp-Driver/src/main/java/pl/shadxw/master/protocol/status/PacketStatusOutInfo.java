package pl.shadxw.master.protocol.status;

import pl.shadxw.core.protocol.Packet;
import pl.shadxw.core.protocol.PacketValue;

public class PacketStatusOutInfo implements Packet<PacketStatusOutListener> {

    @PacketValue
    public final int packetID = 0;
    @PacketValue
    public final String jsonResponse;

    public PacketStatusOutInfo(String jsonResponse){
        this.jsonResponse = jsonResponse;
    }

    @Override
    public void handle(PacketStatusOutListener t0) {
        t0.handle();
    }

}
