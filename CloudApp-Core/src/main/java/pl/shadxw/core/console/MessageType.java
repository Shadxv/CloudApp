package pl.shadxw.core.console;

import lombok.Getter;

public enum MessageType {

    NORMAL("Normal", "&r"),
    ERROR("Error", "&4"),
    WARNING("Warning", "&e"),
    SUGGESTION("Suggestion", "&b");

    @Getter private final String name;
    @Getter private final String color;


    MessageType(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
