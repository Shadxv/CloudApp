package pl.shadxw.driver;

import lombok.Getter;
import pl.shadxw.core.CloudAppCore;
import pl.shadxw.driver.console.Console;
import pl.shadxw.driver.console.IConsole;

import java.io.IOException;

public class CloudAppDriver extends CloudAppCore {


    @Getter private static CloudAppDriver driver;

    public static void main(String[] args){
        try{
            driver = new CloudAppDriver();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Getter private IConsole console;

    public CloudAppDriver() throws IOException {
        this.console = new Console();
    }

}
