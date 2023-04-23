package pl.shadxw.driver.exceptions;

public class CommandNotFoundException extends Exception {

    public CommandNotFoundException(String command){
        super("Could not find '" + command + "' command. Check 'help' for commands list.");
    }

}
