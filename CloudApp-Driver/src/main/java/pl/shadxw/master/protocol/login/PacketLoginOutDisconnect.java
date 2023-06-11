package pl.shadxw.master.protocol.login;

import pl.shadxw.core.protocol.Packet;
import pl.shadxw.core.protocol.PacketValue;

public class PacketLoginOutDisconnect implements Packet<PacketLoginOutListener> {

    @PacketValue
    public final int packetID = 0;
    @PacketValue
    public final String reason;

    public PacketLoginOutDisconnect(String reason){
        this.reason = reason;
    }


    @Override
    public void handle(PacketLoginOutListener t0) {}

}
