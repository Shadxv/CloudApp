package pl.shadxw.master;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import pl.shadxw.core.console.MessageType;
import pl.shadxw.driver.CloudAppDriver;
import pl.shadxw.driver.console.Console;
import pl.shadxw.driver.console.commands.master.ServerCommand;
import pl.shadxw.driver.console.commands.master.SettingsCommand;
import pl.shadxw.driver.managers.CommandManager;
import pl.shadxw.master.configuration.MasterConfiguration;
import pl.shadxw.master.server.MinecraftServer;
import pl.shadxw.core.console.IConsole;
import pl.shadxw.core.models.ConsoleApp;
import pl.shadxw.master.server.StatusResponseBuilder;

import java.io.IOException;
import java.util.List;

public class CloudAppMaster extends ConsoleApp {

    @Getter @Setter
    private MinecraftServer minecraftServer;
    @Getter private MasterConfiguration masterConfiguration;

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
        this.minecraftServer = new MinecraftServer(this.masterConfiguration, super.getConsole());
        this.minecraftServer.getThread().start();
        registerMasterCommands();
    }

    private void registerMasterCommands(){
        CommandManager commandManager = ((Console)this.getConsole()).getConsoleReader().getCommandManager();
        new ServerCommand("server", List.of("serv"), commandManager);
        new SettingsCommand("settings", null, commandManager);
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
