package pl.shadxw.driver.configuration;

import lombok.Getter;
import pl.shadxw.core.console.MessageType;
import pl.shadxw.core.models.ConfigFile;
import pl.shadxw.driver.CloudAppDriver;
import pl.shadxw.driver.managers.ConfigManager;

public class DriverConfiguration {

    @Getter private final ConfigFile configFile;
    @Getter private CloudAppType type;

    public DriverConfiguration(){
        this.configFile = new ConfigManager("config", true);
    }

    public void loadConfig(){
        CloudAppDriver.getApp().getConsole().writeLine("Reading config...", MessageType.NORMAL);
        if(this.configFile.exists("cloudapp-type"))
            this.type = CloudAppType.getTypeByName(this.configFile.readValue("cloudapp-type"));
    }

}
