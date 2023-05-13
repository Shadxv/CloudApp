package pl.shadxw.driver.console.commands;

import pl.shadxw.driver.console.Console;
import pl.shadxw.driver.managers.CommandManager;
import pl.shadxw.driver.models.Command;
import pl.shadxw.core.console.MessageType;

public class PongCommand extends Command {

    public PongCommand(String name, CommandManager manager) {
        super(name, manager);
    }

    @Override
    public boolean execute(Console console) {
        console.writeLine("Pong", MessageType.NORMAL);
        return false;
    }
}
