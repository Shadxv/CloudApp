package pl.shadxw.master.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;
import lombok.Setter;
import pl.shadxw.api.util.JSONChat;
import pl.shadxw.core.console.MessageType;
import pl.shadxw.core.network.PacketListener;
import pl.shadxw.core.protocol.Packet;
import pl.shadxw.driver.CloudAppDriver;
import pl.shadxw.master.protocol.ClientState;
import pl.shadxw.master.protocol.login.PacketLoginOutDisconnect;

public class NetworkManager extends SimpleChannelInboundHandler<Packet<?>> {

    @Getter @Setter private PacketListener packetListener;
    @Getter private Channel channel;
    @Getter @Setter private int protocolVersion;
    @Getter @Setter private ClientState clientState;

    public NetworkManager(){
        this.clientState = ClientState.HANDSHAKE;
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
        throwable.printStackTrace();
    }

    public void sendPacket(Packet<?> packet) {
        if(this.channel.isOpen()){
            this.channel.writeAndFlush(packet);
        }
    }

    public void disconnect(String reason) {
        if (this.channel.isOpen()) {
            this.sendPacket(new PacketLoginOutDisconnect(reason));
            this.channel.close().awaitUninterruptibly();
        }
    }

    public static <T extends PacketListener> void handlePacket(Packet<T> packet, PacketListener listener){
        packet.handle((T) listener);
    }

}
