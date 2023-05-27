package pl.shadxw.driver.managers;

import pl.shadxw.core.console.IConsole;
import pl.shadxw.core.console.MessageType;
import pl.shadxw.driver.CloudAppDriver;
import pl.shadxw.driver.configuration.DriverConfiguration;

public class SetupManager {

    private final IConsole console;
    private final DriverConfiguration configuration;

    public SetupManager(IConsole console, DriverConfiguration configuration){
        this.console = console;
        this.configuration = configuration;
    }

    public void startSetup(){
        this.console.writeLine("This CloudApp is not configured. Starting setup process...", MessageType.WARNING);
        this.console.writeLine("Specific type of this app:", MessageType.NORMAL);
        this.console.writeLine("1. Driver (still not configured)", MessageType.NORMAL);
        this.console.writeLine("2. Master", MessageType.NORMAL);

        this.console.write("Which option do you choose: ", MessageType.NORMAL);

        String input = null;
        do {
            if(input != null) CloudAppDriver.getApp().getConsole().write("You entered wrong value. Which option do you choose:  ", MessageType.NORMAL);
            input = CloudAppDriver.getApp().getConsole().getScanner().nextLine();
        } while (!input.isEmpty()
                && !input.equalsIgnoreCase("1")
                && !input.equalsIgnoreCase("2"));
        switch (Integer.parseInt(input)) {
            case 1 -> {
                this.console.writeLine("You picked Driver. Ending setup process...", MessageType.NORMAL);
                CloudAppDriver.getApp().shutdown(false, true);
            }
            case 2 -> {
                this.console.writeLine("You picked Master. Updating configuration file...", MessageType.NORMAL);
                this.configuration.getConfigFile().forceCreate("template_master_config.yaml", true);
                this.console.writeLine("Ending setup process... To change other settings edit '/config/config.yaml'", MessageType.NORMAL);
                CloudAppDriver.getApp().shutdown(false, true);
            }
        }
    }

}
