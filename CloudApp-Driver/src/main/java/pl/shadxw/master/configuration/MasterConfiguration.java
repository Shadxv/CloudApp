package pl.shadxw.master.configuration;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;
import pl.shadxw.core.console.MessageType;
import pl.shadxw.core.models.ConfigFile;
import pl.shadxw.driver.CloudAppDriver;
import pl.shadxw.driver.managers.ConfigManager;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

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
        this.configFile = new ConfigManager("config", "template_master_config.yaml", false);
    }

    public boolean loadConfig() throws IllegalAccessException {
        CloudAppDriver.getApp().getConsole().writeLine("Loading config...", MessageType.NORMAL);
        boolean allExists = true;

        Class<?> clazz = this.getClass();
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(SettingValue.class))
                .toList();
        for(Field f : fields){
            String key = f.getAnnotation(SettingValue.class).value();
            if(this.configFile.exists(key)){
                Class<?> type = f.getType();
                switch (type.getName()) {
                    case "int" -> {
                        f.set(this, Integer.parseInt(this.configFile.readValue(key).toString()));
                    }
                    case "long" -> {
                        f.set(this, Long.parseLong(this.configFile.readValue(key).toString()));
                    }
                    case "java.lang.String" -> {
                        f.set(this, this.configFile.readValue(key).toString());
                    }
                }
            } else {
                allExists = false;
            }
        }
        return allExists;
    }

    public void fixConfig() throws IllegalAccessException, IOException, URISyntaxException {
        CloudAppDriver.getApp().getConsole().writeLine("Configuration file was found being corrupted!", MessageType.WARNING);
        CloudAppDriver.getApp().getConsole().writeLine("CloudApp started restoring configuration file...", MessageType.WARNING);

        this.configFile.forceCreate("template_master_config.yaml", false);

        Class<?> clazz = this.getClass();
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(SettingValue.class))
                .toList();
        for (Field f : fields) {
            String key = f.getAnnotation(SettingValue.class).value();
            @Nullable Object value = f.get(this);
            if (value != null)
                this.configFile.updateValue(key, String.valueOf(value));
        }
        this.configFile.loadConfigFile();
        CloudAppDriver.getApp().getConsole().writeLine("Restoring has been finished!", MessageType.SUCCESS);
    }

}
