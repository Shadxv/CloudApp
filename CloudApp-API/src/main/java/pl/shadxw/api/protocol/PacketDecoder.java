package pl.shadxw.api.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import pl.shadxw.api.util.PacketDataManager;
import pl.shadxw.core.protocol.Decoder;

import java.io.IOException;
import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder implements Decoder {

    protected PacketDataManager dataManager;
    protected List<Object> out;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        this.dataManager = new PacketDataManager(in);
        this.out = out;

        int length = this.dataManager.readVarInt();
        if(length <= 0){
            throw new IOException("Handled empty packet");
        }

        int packetID = this.dataManager.readVarInt();
        if(packetID < 0) decodeCloudAppPacket(packetID);
        else decodeMinecraftPacket(packetID);
    }

    @Override
    public void decodeCloudAppPacket(int packetID) {

    }

    @Override
    public void decodeMinecraftPacket(int packetID) {

    }



}
