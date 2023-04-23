package pl.shadxw.driver.console;

import lombok.Getter;
import lombok.Setter;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;
import pl.shadxw.driver.utils.ConsoleColor;
import pl.shadxw.driver.models.Sender;
import pl.shadxw.driver.utils.MessageType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class Console extends Sender implements IConsole {

    @Getter private final Terminal terminal;
    @Getter private final LineReader lineReader;
    @Getter @Setter private boolean isRunning;

    private final ConsoleReader consoleReader;
    private final SimpleDateFormat timeFormater;

    public Console() throws IOException {
        super("CloudApp-Driver");
        this.timeFormater = new SimpleDateFormat("HH:mm:ss");

        AnsiConsole.systemInstall();

        this.terminal = TerminalBuilder.builder()
                .system(true)
                .encoding(StandardCharsets.UTF_8.name())
                .build();
        this.isRunning = true;
        this.lineReader = LineReaderBuilder.builder()
                .terminal(this.terminal)
                .build();
        (this.consoleReader = new ConsoleReader(this)).start();

    }

    @Override
    public String formatMsg(String text, MessageType type){
        StringBuilder formatted = new StringBuilder();
        String currentTime = timeFormater.format(Date.from(Instant.now()));
        formatted.append(type.getColor())
                .append("[")
                .append(currentTime)
                .append("] ")
                .append("[")
                .append(this.getName());
        if(!type.equals(MessageType.NORMAL))
            formatted.append("/")
                .append(type.name());
        formatted.append("] ").append(text);

        return formatted.toString();
    }

    @Override
    public void writeRaw(String rawText) {
        this.print(ConsoleColor.toColouredString('&', rawText));
    }

    @Override
    public void forceWrite(String text) {
        this.writeRaw(Ansi.ansi().eraseLine(Ansi.Erase.ALL).toString() + '\r' + text + ConsoleColor.DEFAULT);
    }

    @Override
    public void forceWriteLine(String text) {
        text = ConsoleColor.toColouredString('&', text);
        if (!text.endsWith(System.lineSeparator())) {
            text += System.lineSeparator();
        }

        this.print(Ansi.ansi().eraseLine(Ansi.Erase.ALL).toString() + '\r' + text + Ansi.ansi().reset().toString());
    }

    @Override
    public void write(String text, MessageType type) {
        this.forceWrite(formatMsg(text, type));
    }

    @Override
    public void writeLine(String text, MessageType type) {
        this.forceWriteLine(formatMsg(text, type));
    }

    @Override
    public void changeName(String name) {
        this.setName(name);
    }

    @Override
    public String getName(){
        return super.getName();
    }

    private void print(String text) {
        this.lineReader.getTerminal().puts(InfoCmp.Capability.carriage_return);
        this.lineReader.getTerminal().puts(InfoCmp.Capability.clr_eol);
        this.lineReader.getTerminal().writer().print(text);
        this.lineReader.getTerminal().writer().flush();

        this.redisplay();
    }

    private void redisplay() {
        if (!this.lineReader.isReading()) {
            return;
        }

        this.lineReader.callWidget(LineReader.REDRAW_LINE);
        this.lineReader.callWidget(LineReader.REDISPLAY);
    }

    @Override
    public void close() throws Exception {
        this.setRunning(false);
        this.terminal.close();
        AnsiConsole.systemUninstall();
    }
}
