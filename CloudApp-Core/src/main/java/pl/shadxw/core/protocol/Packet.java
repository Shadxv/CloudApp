package pl.shadxw.core.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;
import pl.shadxw.core.models.IPacket;

import static pl.shadxw.core.utils.ProtocolUtilities.*;

public abstract class Packet implements IPacket {

    @Getter @Setter protected ByteBuf byteBuf;

    @Getter protected Integer packetSize;
    @Getter protected ChannelHandlerContext ctx;


    public Packet(ByteBuf byteBuf, ChannelHandlerContext ctx){
        this.ctx = ctx;
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
