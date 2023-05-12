package pl.shadxw.driver.util;

import lombok.Getter;

public enum MessageType {

    NORMAL("Normal", "&r"),
    ERROR("Error", "&4"),
    WARNING("Warning", "&e"),
    SUGGESTION("Suggestion", "&a");

    @Getter private final String name;
    @Getter private final String color;


    MessageType(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
