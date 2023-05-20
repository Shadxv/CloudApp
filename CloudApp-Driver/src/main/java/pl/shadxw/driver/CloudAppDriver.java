package pl.shadxw.driver;

import lombok.Getter;
import pl.shadxw.core.console.IConsole;
import pl.shadxw.core.console.MessageType;
import pl.shadxw.core.models.ConsoleApp;
import pl.shadxw.driver.configuration.DriverConfiguration;
import pl.shadxw.driver.console.Console;

public class CloudAppDriver extends ConsoleApp {


    @Getter private static ConsoleApp app;

    public static void setApp(ConsoleApp newApp){
        if(app != null) {
            try {
                app.shutdown();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        app = newApp;
    }

    public static void main(String[] args){
        try{
            app = new CloudAppDriver();
            app.init();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Getter private DriverConfiguration driverConfiguration;

    public CloudAppDriver() throws Exception {
        super(new Console());
    }

    public CloudAppDriver(IConsole console){
        super(console);
    }

    @Override
    public void init() {
        super.getConsole().writeLine("Starting CloudApp Driver! It may take a few minutes...", MessageType.NORMAL);
        this.driverConfiguration = new DriverConfiguration();
        this.driverConfiguration.loadConfig();
        super.getConsole().writeLine("Type: " + this.driverConfiguration.getType().getValue(), MessageType.SUGGESTION);

        ((Console) super.getConsole()).getConsoleReader().start();
    }

    @Override
    public void shutdown() throws Exception {
        CloudAppDriver.getApp().getConsole().writeLine("Shutting down CloudApp Driver...", MessageType.NORMAL);
        CloudAppDriver.getApp().getConsole().close();
        System.exit(0);
    }
}
