package pl.shadxw.core.protocol.minecraft.serverbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import pl.shadxw.core.CloudAppCore;
import pl.shadxw.core.exceptions.IncorrectMethodUsageException;
import pl.shadxw.core.protocol.Packet;

import static pl.shadxw.core.utils.ProtocolUtilities.*;

public class HandshakePacket extends Packet {

    @Getter private int protocolVersion;
    @Getter private String serverAddress;
    @Getter private short port;
    @Getter private int state;

    public HandshakePacket(ByteBuf byteBuf, ChannelHandlerContext ctx) {
        super(byteBuf, ctx);
        decode();
    }

    @Override
    public void encode() {
        throw new RuntimeException(new IncorrectMethodUsageException());
    }

    @Override
    public void decode() {
        this.protocolVersion = readVarInt(super.byteBuf);
        this.serverAddress = readUTF8(super.byteBuf);
        this.port = super.byteBuf.readShort();
        this.state = readVarInt(super.byteBuf);
    }

    @Override
    public boolean execute() {
        CloudAppCore.getMinecraftClientManager().createClient(ctx, state);
        return true;
    }

    @Override
    public boolean respond() {
        return false;
    }
}
