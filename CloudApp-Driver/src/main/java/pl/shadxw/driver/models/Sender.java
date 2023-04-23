package pl.shadxw.driver.models;

import lombok.Getter;
import lombok.Setter;

public abstract class Sender {

    @Getter @Setter private String name;

    protected Sender(String name) {
        this.name = name;
    }
}
