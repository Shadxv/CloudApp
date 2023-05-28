package pl.shadxw.driver.models;

import pl.shadxw.driver.console.Console;
import pl.shadxw.driver.managers.CommandManager;

import java.util.List;

public abstract class Command {

    public Command(String name, List<String> aliases, CommandManager manager){
        manager.getRegisteredCommands().put(name, this);
        if(aliases != null && !aliases.isEmpty())
            for(String alias : aliases) manager.getAliases().put(alias, name);
    }

    public abstract boolean execute(Console console, List<String> args);

}
