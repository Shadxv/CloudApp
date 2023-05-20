package pl.shadxw.master;

import lombok.Getter;
import lombok.Setter;
import pl.shadxw.core.console.IConsole;
import pl.shadxw.core.models.ConsoleApp;
import pl.shadxw.master.server.MinecraftServer;

public class CloudAppMaster extends ConsoleApp {

    @Getter private MinecraftServer minecraftServer;

    public CloudAppMaster(IConsole console) throws Exception {
        super(console);
        this.minecraftServer = new MinecraftServer(25565, "localhost", console);
    }

    @Override
    public void init() {

    }

    @Override
    public void shutdown() throws Exception {
        this.minecraftServer.stop();
        super.getConsole().close();
    }

}
