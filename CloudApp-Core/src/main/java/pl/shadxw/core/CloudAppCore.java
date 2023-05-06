package pl.shadxw.core;

import lombok.Getter;
import pl.shadxw.core.managers.MinecraftClientManager;
import pl.shadxw.core.protocol.minecraft.MinecraftClientState;

public class CloudAppCore {

    @Getter private static MinecraftClientManager minecraftClientManager = new MinecraftClientManager();

}
