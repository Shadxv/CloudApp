package pl.shadxw.core.exceptions;

public class IncorrectMethodUsageException extends Exception{

    public IncorrectMethodUsageException(){
        super("Method has been used in improper way!");
        this.printStackTrace();
    }

}
