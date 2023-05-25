package pl.shadxw.master.configuration;

import lombok.Getter;
import pl.shadxw.core.models.ConfigFile;
import pl.shadxw.driver.managers.ConfigManager;

public class MasterConfiguration {

    @Getter private final ConfigFile configFile;

    @SettingValue("server-port")
    @Getter private int serverPort;

    @SettingValue("motd")
    @Getter private String motd;
    @SettingValue("max-players")
    @Getter private int maxPlayers;
    @SettingValue("online")
    @Getter private int online;
    @SettingValue("icon-path")
    @Getter private String iconPath;

    public MasterConfiguration(){
        this.configFile = new ConfigManager("config", false);
    }


}
