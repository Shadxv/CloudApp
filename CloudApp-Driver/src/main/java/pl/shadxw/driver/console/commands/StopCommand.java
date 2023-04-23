package pl.shadxw.driver.console.commands;

import pl.shadxw.driver.CloudAppDriver;
import pl.shadxw.driver.console.Console;
import pl.shadxw.driver.managers.CommandManager;
import pl.shadxw.driver.models.Command;

public class StopCommand extends Command {

    public StopCommand(String name, CommandManager manager) {
        super(name, manager);
    }

    @Override
    public boolean execute(Console console) {
        try {
            CloudAppDriver.getDriver().getConsole().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
