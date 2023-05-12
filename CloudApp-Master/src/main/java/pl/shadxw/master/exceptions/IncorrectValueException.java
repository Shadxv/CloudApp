package pl.shadxw.master.exceptions;

public class IncorrectValueException extends RuntimeException{

    public IncorrectValueException(String msg){
        super(msg);
        //CloudAppDriver.getDriver().getConsole().writeLine(msg, MessageType.ERROR);
        System.out.println(msg);
    }

}
