package pl.shadxw.driver.console.commands.master;

import pl.shadxw.core.console.MessageType;
import pl.shadxw.driver.CloudAppDriver;
import pl.shadxw.driver.console.Console;
import pl.shadxw.driver.managers.CommandManager;
import pl.shadxw.driver.models.Command;
import pl.shadxw.master.CloudAppMaster;
import pl.shadxw.master.server.MinecraftServer;

import java.util.List;

public class ServerCommand extends Command {

    public ServerCommand(String name, List<String> aliases, CommandManager manager) {
        super(name, aliases, manager);
    }

    @Override
    public boolean execute(Console console, List<String> args) {

        if(args == null || args.isEmpty()){
            CloudAppDriver.getApp().getConsole().writeLine("Usage of 'server' command:", MessageType.SUGGESTION);
            CloudAppDriver.getApp().getConsole().writeLine("- server restart - restarting main minecraft server", MessageType.SUGGESTION);
        } else {
            if(args.size() == 1){
                if(args.get(0).equalsIgnoreCase("restart")){
                    CloudAppDriver.getApp().getConsole().writeLine("Restarting minecraft server. It may takes a few seconds...", MessageType.NORMAL);
                    try {
                        ((CloudAppMaster) CloudAppDriver.getApp()).getMinecraftServer().stop();
                        ((CloudAppMaster) CloudAppDriver.getApp()).setMinecraftServer(new MinecraftServer(
                                ((CloudAppMaster) CloudAppDriver.getApp()).getMasterConfiguration(),
                                CloudAppDriver.getApp().getConsole()
                        ));
                        ((CloudAppMaster) CloudAppDriver.getApp()).getMinecraftServer().getThread().start();
                    } catch (InterruptedException e) {
                        CloudAppDriver.getApp().getConsole().writeLine(e.getMessage(), MessageType.ERROR);
                        CloudAppDriver.getApp().shutdown(true, true);
                    }
                } else {
                    CloudAppDriver.getApp().getConsole().writeLine("Usage of 'server' command:", MessageType.SUGGESTION);
                    CloudAppDriver.getApp().getConsole().writeLine("- server restart - restarting main minecraft server", MessageType.SUGGESTION);
                }
            } else {
                CloudAppDriver.getApp().getConsole().writeLine("Usage of 'server' command:", MessageType.SUGGESTION);
                CloudAppDriver.getApp().getConsole().writeLine("- server restart - restarting main minecraft server", MessageType.SUGGESTION);

            }
        }

        return false;
    }
}
