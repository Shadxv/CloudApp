package pl.shadxw.driver.console;

import lombok.Getter;
import lombok.Setter;
import org.jline.reader.EndOfFileException;
import org.jline.reader.UserInterruptException;
import pl.shadxw.driver.exceptions.CommandNotFoundException;
import pl.shadxw.driver.managers.CommandManager;
import pl.shadxw.driver.util.MessageType;

import java.util.Arrays;

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
                String[] elements = line.split(" ");
                String command = elements[0];
                String[] args = elements.length == 1 ? null : Arrays.copyOfRange(elements, 1, elements.length-1);
                try{
                    commandManager.getCommand(command).execute(this.getConsole());
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
