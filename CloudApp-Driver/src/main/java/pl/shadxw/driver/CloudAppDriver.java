package pl.shadxw.driver;

import lombok.Getter;
import lombok.SneakyThrows;
import pl.shadxw.core.console.IConsole;
import pl.shadxw.core.console.MessageType;
import pl.shadxw.core.models.ConsoleApp;
import pl.shadxw.driver.configuration.DriverConfiguration;
import pl.shadxw.driver.console.Console;
import pl.shadxw.driver.managers.SetupManager;
import pl.shadxw.driver.models.Sender;
import pl.shadxw.master.CloudAppMaster;

public class CloudAppDriver extends ConsoleApp {


    @Getter private static ConsoleApp app;

    public static void setApp(ConsoleApp newApp){
        if(app != null) {
            try {
                app.shutdown(false, false);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        app = newApp;
        ((Sender)app.getConsole()).setName(app.getAppName());
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
        super.getConsole().writeLine("Starting CloudApp Driver! It may take a few seconds...", MessageType.NORMAL);
        this.driverConfiguration = new DriverConfiguration();
        this.driverConfiguration.loadConfig();

        try{
            switch (this.driverConfiguration.getType()){
                case MASTER -> {
                    this.getConsole().writeLine("Detected that this CloudApp is configured as Master. Changing type...", MessageType.NORMAL);
                    setApp(new CloudAppMaster(super.getConsole()));
                    app.init();
                }
                case DRIVER -> {
                    new SetupManager(super.getConsole(), this.driverConfiguration)
                            .startSetup();
                }
                case UNKNOWN -> {
                    //ERROR, Unknown type
                }
                default -> {
                    //ERROR, Config has been loaded incorrectly
                }
            }
        } finally {
            ((Console) app.getConsole()).getConsoleReader().start();
        }
    }

    @Override
    @SneakyThrows
    public void shutdown(boolean force, boolean closeConsole) {
        CloudAppDriver.getApp().getConsole().writeLine("Shutting down CloudApp Driver...", MessageType.NORMAL);
        if(force) System.exit(0);

        if(closeConsole) {
            CloudAppDriver.getApp().getConsole().close();
        }
    }

    @Override
    public String getAppName(){
        return "CloudApp-Driver";
    }
}
