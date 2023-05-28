package pl.shadxw.master.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.Getter;
import lombok.Setter;
import pl.shadxw.api.protocol.PacketEncoder;
import pl.shadxw.core.console.IConsole;
import pl.shadxw.core.console.MessageType;
import pl.shadxw.core.server.Server;
import pl.shadxw.driver.CloudAppDriver;
import pl.shadxw.master.configuration.MasterConfiguration;
import pl.shadxw.master.network.NetworkManager;
import pl.shadxw.master.protocol.MasterPacketDecoder;
import pl.shadxw.master.server.listeners.HandshakeListener;

import java.io.IOException;

public class MinecraftServer extends Server implements Runnable{

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelFuture channelFuture;

    @Getter private final Thread thread;
    @Getter private final IConsole console;
    @Getter private final MasterConfiguration configuration;
    @Getter @Setter
    private StatusResponseBuilder statusResponseBuilder;

    public MinecraftServer(MasterConfiguration configuration, IConsole console) {
        super(configuration.getServerPort());
        this.console = console;
        this.configuration = configuration;
        this.thread = new Thread(this);
        try {
            this.statusResponseBuilder = new StatusResponseBuilder(
                    this.configuration.getMaxPlayers(),
                    this.configuration.getOnline(),
                    this.configuration.getMotd(),
                    this.configuration.getIconPath()
            );
        } catch (IOException e){
            this.getConsole().writeLine(e.getMessage(), MessageType.ERROR);
            CloudAppDriver.getApp().shutdown(true, true);
        }
    }

    @Override
    public void runServer() throws Exception{
        this.console.writeLine("Running Minecraft Server on port " + this.getPort(), MessageType.NORMAL);
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();
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

            this.channelFuture = b.bind(super.getPort()).sync();
            this.console.writeLine("Minecraft Server has been started properly!", MessageType.NORMAL);
            this.channelFuture.channel().closeFuture().sync();
        } finally {
            this.workerGroup.shutdownGracefully();
            this.bossGroup.shutdownGracefully();
        }
    }



    @Override
    public void stop() throws InterruptedException {
        this.console.writeLine("Stopping Minecraft Server...", MessageType.NORMAL);
        this.workerGroup.shutdownGracefully().sync();
        this.bossGroup.shutdownGracefully().sync();
        this.console.writeLine("Minecraft Server has been stopped correctly!", MessageType.NORMAL);
    }

    @Override
    public void run() {
        try {
            this.runServer();
        } catch (Exception e) {
            this.console.writeLine(e.getMessage(), MessageType.ERROR);
        }
    }
}
