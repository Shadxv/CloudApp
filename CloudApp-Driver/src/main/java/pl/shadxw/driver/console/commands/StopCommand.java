package pl.shadxw.driver.console.commands;

import pl.shadxw.driver.CloudAppDriver;
import pl.shadxw.driver.console.Console;
import pl.shadxw.driver.managers.CommandManager;
import pl.shadxw.driver.models.Command;

import java.util.List;

public class StopCommand extends Command {

    public StopCommand(String name, List<String> aliases, CommandManager manager) {
        super(name, aliases, manager);
    }

    @Override
    public boolean execute(Console console, List<String> args) {
        try {
            CloudAppDriver.getApp().shutdown(false, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
