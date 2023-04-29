package pl.shadxw.core.models;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

import static pl.shadxw.core.utils.ProtocolUtilities.*;

public abstract class Packet implements IPacket{

    @Getter @Setter protected ByteBuf byteBuf;

    @Getter protected Integer packetSize;


    public Packet(ByteBuf byteBuf){
        this.byteBuf = byteBuf;
        this.packetSize = readVarInt(byteBuf);
    }

    public void decodePacketByID(){
        int packetID = readVarInt(byteBuf);
        if(packetID < 0){
            //CloudAppPackets
        } else {
            //MinecraftPackets
        }
    }
}
