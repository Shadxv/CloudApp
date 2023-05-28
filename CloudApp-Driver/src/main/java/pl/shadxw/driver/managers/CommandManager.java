package pl.shadxw.driver.managers;

import lombok.Getter;
import pl.shadxw.driver.console.commands.PongCommand;
import pl.shadxw.driver.console.commands.StopCommand;
import pl.shadxw.driver.exceptions.CommandNotFoundException;
import pl.shadxw.driver.models.Command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {

    @Getter private final Map<String, Command> registeredCommands;
    @Getter private final Map<String, String> aliases;

    public CommandManager(){
        this.registeredCommands = new HashMap<>();
        this.aliases = new HashMap<>();
        registerCommands();
    }

    private void registerCommands(){
        new PongCommand("ping", List.of("p"),this);
        new StopCommand("stop", null,this);
    }

    private void unregisterCommand(String name){
        this.registeredCommands.remove(name);
    }

    public Command getCommand(String name) throws CommandNotFoundException {
        if(aliases.containsKey(name)){
            return registeredCommands.get(aliases.get(name));
        } else if(registeredCommands.containsKey(name)){
            return registeredCommands.get(name);
        } else throw new CommandNotFoundException(name);
    }

}
