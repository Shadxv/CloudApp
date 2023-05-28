package pl.shadxw.driver.console;

import lombok.Getter;
import lombok.Setter;
import org.jline.reader.EndOfFileException;
import org.jline.reader.UserInterruptException;
import pl.shadxw.driver.exceptions.CommandNotFoundException;
import pl.shadxw.driver.managers.CommandManager;
import pl.shadxw.core.console.MessageType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConsoleReader extends Thread {

    private static final String DEFAULT_PROMPT = ">";

    @Getter private final CommandManager commandManager;
    @Getter private final Console console;

    @Getter private String prompt = DEFAULT_PROMPT + " ";
    @Getter @Setter private boolean isListening;
    @Getter @Setter private boolean isWaitingForCommand;

    public ConsoleReader(Console console){
        this.console = console;
        this.commandManager = new CommandManager();
        this.isListening = true;
    }

    public void run(){
        while(console.isRunning()){
            if(isListening){
                this.setWaitingForCommand(true);
                String line = null;
                try {
                    line = this.console.getLineReader().readLine(prompt);
                } catch (UserInterruptException e) {
                    System.exit(0);
                } catch (EndOfFileException ignore) {
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (line == null) continue;
                this.setWaitingForCommand(false);
                if (line.isEmpty()) continue;
                line = line.trim();
                List<String> elements = new ArrayList<>(Arrays.asList(line.split(" ")));
                elements.removeAll(Arrays.asList("", " ", null));
                String command = elements.get(0);
                elements.remove(0);
                //At this point elements contains only arguments!!!
                try{
                    commandManager.getCommand(command.toLowerCase()).execute(this.getConsole(), elements);
                } catch (CommandNotFoundException e){
                    this.getConsole().writeLine(e.getMessage(), MessageType.WARNING);
                }
            }
        }
    }

    public void setPrompt(String prompt){
        StringBuilder _prompt = new StringBuilder();
        if(prompt != null && !prompt.isEmpty() && !prompt.isBlank()) {
            _prompt.append(prompt).append(" ");
        }
        this.prompt = _prompt.append(DEFAULT_PROMPT).append(" ").toString();
    }

}
