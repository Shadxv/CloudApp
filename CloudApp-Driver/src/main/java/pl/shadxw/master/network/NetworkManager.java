package pl.shadxw.master.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;
import lombok.Setter;
import pl.shadxw.core.console.MessageType;
import pl.shadxw.core.network.PacketListener;
import pl.shadxw.core.protocol.Packet;
import pl.shadxw.driver.CloudAppDriver;

public class NetworkManager extends SimpleChannelInboundHandler<Packet<?>> {

    @Getter @Setter private boolean recivedHandshake;
    @Getter @Setter private PacketListener packetListener;
    @Getter private Channel channel;
    @Getter @Setter private int protocolVersion;

    public NetworkManager(){
        this.recivedHandshake = false;
    }

    public void channelActive(ChannelHandlerContext ctx){
        this.channel = ctx.channel();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet<? extends PacketListener> msg) throws Exception {
        this.channel = ctx.channel();
        if(this.channel.isOpen()){
            try{
                handlePacket(msg, this.packetListener);
            } catch (Exception e){
                CloudAppDriver.getApp().getConsole().writeLine(e.getMessage(), MessageType.ERROR);
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelhandlercontext, Throwable throwable) {
        CloudAppDriver.getApp().getConsole().writeLine(throwable.getMessage(), MessageType.ERROR);
    }

    public void sendPacket(Packet<?> packet) {
        if(this.channel.isOpen()){
            this.channel.writeAndFlush(packet);
        }
    }

    public void disconnect() {
        if (this.channel.isOpen()) {
            this.channel.close().awaitUninterruptibly();
        }
    }

    public static <T extends PacketListener> void handlePacket(Packet<T> packet, PacketListener listener){
        packet.handle((T) listener);
    }

}
