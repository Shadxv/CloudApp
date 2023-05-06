package pl.shadxw.core.managers;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;
import pl.shadxw.core.protocol.minecraft.MinecraftClient;
import pl.shadxw.core.protocol.minecraft.MinecraftClientState;

import java.util.HashMap;
import java.util.Map;

public class MinecraftClientManager {

    private final Map<ChannelHandlerContext, MinecraftClient> clients;

    public MinecraftClientManager(){
        this.clients = new HashMap<>();
    }

    @Nullable
    public MinecraftClient getClientByChannel(ChannelHandlerContext ctx){
        return this.clients.getOrDefault(ctx, null);
    }

    public void createClient(ChannelHandlerContext ctx, int id){
        MinecraftClientState state = MinecraftClientState.getStateByID(id).orElse(MinecraftClientState.STATE_NOT_FOUND);
        this.clients.put(ctx, new MinecraftClient(ctx, state));
    }

    public void removeClient(ChannelHandlerContext ctx){
        this.clients.remove(ctx);
    }

}
