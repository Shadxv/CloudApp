package pl.shadxw.master;

import lombok.Getter;
import lombok.SneakyThrows;
import pl.shadxw.core.console.MessageType;
import pl.shadxw.driver.CloudAppDriver;
import pl.shadxw.master.configuration.MasterConfiguration;
import pl.shadxw.master.server.MinecraftServer;
import pl.shadxw.core.console.IConsole;
import pl.shadxw.core.models.ConsoleApp;
import pl.shadxw.master.server.StatusResponseBuilder;

import java.io.IOException;

public class CloudAppMaster extends ConsoleApp {

    @Getter private MinecraftServer minecraftServer;
    @Getter private MasterConfiguration masterConfiguration;

    @Getter private StatusResponseBuilder statusResponseBuilder;

    public CloudAppMaster(IConsole console) {
        super(console);
    }

    @Override
    public void init() {
        this.getConsole().writeLine("Starting CloudApp Master...", MessageType.NORMAL);

        this.masterConfiguration = new MasterConfiguration();
        try {
            if(!this.masterConfiguration.loadConfig()) {
                this.masterConfiguration.fixConfig();
                this.masterConfiguration.loadConfig();
            }
        } catch (Exception e) {
            super.getConsole().writeLine(e.getMessage(), MessageType.ERROR);
            super.getConsole().writeLine("Configuration file could not have been loaded! If this problem still occurs, contact with CloudApp developer.", MessageType.ERROR);
            this.shutdown(true, true);
        }
        try {
            this.statusResponseBuilder = new StatusResponseBuilder(
                    this.masterConfiguration.getMaxPlayers(),
                    this.masterConfiguration.getOnline(),
                    this.masterConfiguration.getMotd(),
                    this.masterConfiguration.getIconPath()
            );
        } catch (IOException e){
            this.getConsole().writeLine(e.getMessage(), MessageType.ERROR);
            this.shutdown(true, true);
        }
        this.minecraftServer = new MinecraftServer(this.masterConfiguration.getServerPort(), super.getConsole());
        this.minecraftServer.getThread().start();
    }

    @Override
    @SneakyThrows
    public void shutdown(boolean force, boolean closeConsole) {
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
