package pl.shadxw.driver.console.commands;

import pl.shadxw.driver.console.Console;
import pl.shadxw.driver.managers.CommandManager;
import pl.shadxw.driver.models.Command;
import pl.shadxw.core.console.MessageType;

import java.util.List;

public class PongCommand extends Command {

    public PongCommand(String name, List<String> aliases, CommandManager manager) {
        super(name, aliases, manager);
    }

    @Override
    public boolean execute(Console console, List<String> args) {
        console.writeLine("Pong", MessageType.NORMAL);
        return false;
    }
}
