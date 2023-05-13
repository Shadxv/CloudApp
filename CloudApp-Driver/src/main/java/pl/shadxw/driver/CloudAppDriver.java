package pl.shadxw.driver;

import lombok.Getter;
import pl.shadxw.core.console.IConsole;
import pl.shadxw.core.models.ConsoleApp;
import pl.shadxw.driver.console.Console;
import pl.shadxw.master.CloudAppMaster;

public class CloudAppDriver extends ConsoleApp {


    @Getter private static ConsoleApp app;

    public static void main(String[] args){
        try{
            app = new CloudAppMaster(new Console());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public CloudAppDriver() throws Exception {
        super(new Console());
    }

    public CloudAppDriver(IConsole console){
        super(console);
    }

    @Override
    public void shutdown() throws Exception {
        CloudAppDriver.getApp().getConsole().close();
    }
}
