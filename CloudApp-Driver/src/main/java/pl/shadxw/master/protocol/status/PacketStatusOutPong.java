package pl.shadxw.master.protocol.status;

import pl.shadxw.core.protocol.Packet;
import pl.shadxw.core.protocol.PacketValue;

public class PacketStatusOutPong implements Packet<PacketStatusOutListener> {

    @PacketValue
    public final int packetID = 1;
    @PacketValue
    public final long time;

    public PacketStatusOutPong(long time){
        this.time = time;
    }

    @Override
    public void handle(PacketStatusOutListener t0) {
        t0.handle();
    }

}
