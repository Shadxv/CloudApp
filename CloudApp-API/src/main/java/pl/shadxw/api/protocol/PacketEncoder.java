package pl.shadxw.api.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToByteEncoder;
import pl.shadxw.api.util.PacketDataManager;
import pl.shadxw.core.protocol.Packet;
import pl.shadxw.core.protocol.PacketValue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PacketEncoder extends MessageToByteEncoder<Packet<?>> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet<?> msg, ByteBuf out) throws Exception {
        PacketDataManager packetDataManager = new PacketDataManager(Unpooled.buffer());
        Class<?> packetClass = msg.getClass();
        for(Field field : packetClass.getFields()){
            if(field.isAnnotationPresent(PacketValue.class)){
                Class<?> type = field.getType();
                switch (type.getName()) {
                    case "int" -> {
                        packetDataManager.writeVarInt(field.getInt(msg));
                    }
                    case "long" -> {
                        packetDataManager.writeLong(field.getLong(msg));
                    }
                    case "java.lang.String" -> {
                        packetDataManager.writeUtf((String) field.get(msg));
                    }
                    default -> {
                        throw new EncoderException("Could not encode packet. Unknown type: " + type.getName());
                    }
                }
            }
        }
        PacketDataManager encoded = new PacketDataManager(out);
        encoded.writeVarInt(packetDataManager.readableBytes());
        encoded.writeBytes(packetDataManager.getSource());
    }

}
