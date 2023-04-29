package pl.shadxw.core.protocol.minecraft.serverbound;

import io.netty.buffer.ByteBuf;
import pl.shadxw.core.models.Packet;

public class HandshakePacket extends Packet {

    public HandshakePacket(ByteBuf byteBuf) {
        super(byteBuf);
    }

    @Override
    public void encode() {

    }

    @Override
    public void decode() {

    }
}
