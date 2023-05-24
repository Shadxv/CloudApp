package pl.shadxw.master.exceptions;

import pl.shadxw.core.console.MessageType;
import pl.shadxw.driver.CloudAppDriver;

public class IncorrectValueException extends RuntimeException{

    public IncorrectValueException(String msg){
        super(msg);
        CloudAppDriver.getApp().getConsole().writeLine(msg, MessageType.ERROR);
    }

}
