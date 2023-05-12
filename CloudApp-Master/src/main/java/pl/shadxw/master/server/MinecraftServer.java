package pl.shadxw.master.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import pl.shadxw.api.protocol.PacketEncoder;
import pl.shadxw.master.network.NetworkManager;
import pl.shadxw.core.server.Server;
import pl.shadxw.master.protocol.MasterPacketDecoder;
import pl.shadxw.master.server.listeners.HandshakeListener;

public class MinecraftServer extends Server {


    public MinecraftServer(int port, String address) {
        super(port, address);
    }

    @Override
    public void run() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            NetworkManager networkManager = new NetworkManager();
                            networkManager.setPacketListener(new HandshakeListener(networkManager));
                            ch.pipeline()
                                    .addLast(new ReadTimeoutHandler(30))
                                    .addLast(new PacketEncoder())
                                    .addLast(new MasterPacketDecoder(networkManager))
                                    .addLast(networkManager);
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(super.getPort()).sync();

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {

    }
}
