package pl.shadxw.core.protocol.minecraft;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class MinecraftClient {

    @Getter private final ChannelHandlerContext ctx;
    private final Map<String, String> data;


    @Getter @Setter private MinecraftClientState state;

    public MinecraftClient(ChannelHandlerContext ctx, MinecraftClientState state){
        this.ctx =  ctx;
        this.state = state;
        this.data = new HashMap<>();
    }

    public void addData(String key, String data){
        this.data.put(key, data);
    }

    @Nullable
    public String getData(String key){
        return this.data.getOrDefault(key, null);
    }

    public void removeData(String key){
        this.data.remove(key);
    }
}
