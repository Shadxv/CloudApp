package pl.shadxw.master;

import lombok.Getter;
import pl.shadxw.core.console.MessageType;
import pl.shadxw.driver.CloudAppDriver;
import pl.shadxw.master.server.MinecraftServer;
import pl.shadxw.core.console.IConsole;
import pl.shadxw.core.models.ConsoleApp;

public class CloudAppMaster extends ConsoleApp {

    @Getter private MinecraftServer minecraftServer;

    public CloudAppMaster(IConsole console) {
        super(console);
    }

    @Override
    public void init() {
        this.getConsole().writeLine("Starting CloudApp Master...", MessageType.NORMAL);
        this.minecraftServer = new MinecraftServer(25565, "localhost", super.getConsole());
        this.minecraftServer.getThread().start();
    }

    @Override
    public void shutdown(boolean force, boolean closeConsole) throws Exception {
        CloudAppDriver.getApp().getConsole().writeLine("Shutting down CloudApp Master...", MessageType.NORMAL);
        if(force) System.exit(0);

        this.minecraftServer.stop();
        if(closeConsole) {
            super.getConsole().close();
        }
    }

    @Override
    public String getAppName(){
        return "CloudApp-Master";
    }

}
