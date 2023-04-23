package pl.shadxw.driver.managers;

import lombok.Getter;
import pl.shadxw.driver.console.commands.PongCommand;
import pl.shadxw.driver.console.commands.StopCommand;
import pl.shadxw.driver.exceptions.CommandNotFoundException;
import pl.shadxw.driver.models.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    @Getter private final Map<String, Command> registeredCommands;

    public CommandManager(){
        this.registeredCommands = new HashMap<>();
        registerCommands();
    }

    private void registerCommands(){
        new PongCommand("ping", this);
        new StopCommand("stop", this);
    }

    public Command getCommand(String name) throws CommandNotFoundException {
        if(registeredCommands.containsKey(name)){
            return registeredCommands.get(name);
        } else throw new CommandNotFoundException(name);
    }

}
