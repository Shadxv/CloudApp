package pl.shadxw.core.console;

public interface IConsole extends AutoCloseable {

    void writeRaw(String rawText);

    void forceWrite(String text);

    void forceWriteLine(String text);

    void write(String text, MessageType type);

    void writeLine(String text, MessageType type);

    void changeName(String name);

    String getName();

    String formatMsg(String text, MessageType type);
}
