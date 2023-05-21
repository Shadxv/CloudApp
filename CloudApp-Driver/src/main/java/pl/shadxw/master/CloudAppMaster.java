package pl.shadxw.master;

import lombok.Getter;
import pl.shadxw.master.server.MinecraftServer;
import pl.shadxw.core.console.IConsole;
import pl.shadxw.core.models.ConsoleApp;

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
        System.exit(0);
    }

}
