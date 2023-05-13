package pl.shadxw.core.models;

import lombok.Getter;
import pl.shadxw.core.console.IConsole;

public abstract class ConsoleApp {

    @Getter private IConsole console;

    public ConsoleApp(IConsole console){
        this.console = console;
    }

    public abstract void shutdown() throws Exception;

}
