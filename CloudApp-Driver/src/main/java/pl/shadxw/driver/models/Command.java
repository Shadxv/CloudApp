package pl.shadxw.driver.models;

import pl.shadxw.driver.console.Console;
import pl.shadxw.driver.managers.CommandManager;

public abstract class Command {

    public Command(String name, CommandManager manager){
        manager.getRegisteredCommands().put(name, this);
    }

    public abstract boolean execute(Console console);

}
