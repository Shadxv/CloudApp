package pl.shadxw.driver.console.commands.master;

import pl.shadxw.core.console.MessageType;
import pl.shadxw.driver.CloudAppDriver;
import pl.shadxw.driver.console.Console;
import pl.shadxw.driver.managers.CommandManager;
import pl.shadxw.driver.models.Command;
import pl.shadxw.master.CloudAppMaster;
import pl.shadxw.master.configuration.MasterConfiguration;
import pl.shadxw.master.configuration.SettingValue;
import pl.shadxw.master.server.MinecraftServer;
import pl.shadxw.master.server.StatusResponseBuilder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class SettingsCommand extends Command {

    public SettingsCommand(String name, List<String> aliases, CommandManager manager) {
        super(name, aliases, manager);
    }

    @Override
    public boolean execute(Console console, List<String> args) {

        if(args == null || args.isEmpty()){
            CloudAppDriver.getApp().getConsole().writeLine("Usage of 'settings' command:", MessageType.SUGGESTION);
            CloudAppDriver.getApp().getConsole().writeLine("- settings list - showing list of settings with their current values", MessageType.SUGGESTION);
            CloudAppDriver.getApp().getConsole().writeLine("- settings set <name> <value> - changing settings values", MessageType.SUGGESTION);
            CloudAppDriver.getApp().getConsole().writeLine("- settings reload - reloading configuration files", MessageType.SUGGESTION);
        } else {
            if(args.size() == 1){
                if(args.get(0).equalsIgnoreCase("list")){
                    CloudAppDriver.getApp().getConsole().writeLine("Master Server Settings List:", MessageType.NORMAL);

                    Class<?> clazz = MasterConfiguration.class;
                    List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                            .filter(field -> field.isAnnotationPresent(SettingValue.class))
                            .toList();
                    for(Field f : fields) {
                        String key = f.getAnnotation(SettingValue.class).value();
                        try {
                            Method getter = clazz.getMethod("get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1));
                            CloudAppDriver.getApp().getConsole().writeLine("- " + key.toUpperCase() + ": " + getter.invoke(((CloudAppMaster) CloudAppDriver.getApp()).getMasterConfiguration()).toString().replace("\n", "\\n"), MessageType.NORMAL);
                        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ignore) {
                            ignore.printStackTrace();
                        }
                    }
                } else if(args.get(0).equalsIgnoreCase("reload")){
                    try {
                        MasterConfiguration masterConfiguration = ((CloudAppMaster) CloudAppDriver.getApp()).getMasterConfiguration();
                        masterConfiguration.getConfigFile().loadConfigFile();
                        masterConfiguration.loadConfig();
                        ((CloudAppMaster) CloudAppDriver.getApp()).getMinecraftServer().setStatusResponseBuilder(
                                new StatusResponseBuilder(
                                        masterConfiguration.getMaxPlayers(),
                                        masterConfiguration.getOnline(),
                                        masterConfiguration.getMotd(),
                                        masterConfiguration.getIconPath()
                                )
                        );
                    } catch (IllegalAccessException | IOException e) {
                        CloudAppDriver.getApp().getConsole().writeLine("An error occurred while loading configuration!\n" + e.getMessage(), MessageType.ERROR);
                    }
                }
            } else if(args.size() == 3){
                if(args.get(0).equalsIgnoreCase("set")){
                    MasterConfiguration masterConfiguration = ((CloudAppMaster) CloudAppDriver.getApp()).getMasterConfiguration();
                    if(masterConfiguration.getConfigFile().exists(args.get(1))){
                        try {
                            masterConfiguration.getConfigFile().updateValue(args.get(1), args.get(2));
                            CloudAppDriver.getApp().getConsole().writeLine("Successfully updated value! To apply changes run 'settings reload'", MessageType.SUCCESS);
                        } catch (IOException | URISyntaxException e) {
                            CloudAppDriver.getApp().getConsole().writeLine("An error occurred while updating value!\n" + e.getMessage(), MessageType.ERROR);
                        }
                    } else {
                        CloudAppDriver.getApp().getConsole().writeLine("Cloud not find option called '" + args.get(2) + "'!", MessageType.WARNING);
                    }
                } else {
                    CloudAppDriver.getApp().getConsole().writeLine("Usage of 'settings' command:", MessageType.SUGGESTION);
                    CloudAppDriver.getApp().getConsole().writeLine("- settings list - showing list of settings with their current values", MessageType.SUGGESTION);
                    CloudAppDriver.getApp().getConsole().writeLine("- settings set <name> <value> - changing settings values", MessageType.SUGGESTION);
                    CloudAppDriver.getApp().getConsole().writeLine("- settings reload - reloading configuration files", MessageType.SUGGESTION);
                }
            } else {
                CloudAppDriver.getApp().getConsole().writeLine("Usage of 'settings' command:", MessageType.SUGGESTION);
                CloudAppDriver.getApp().getConsole().writeLine("- settings list - showing list of settings with their current values", MessageType.SUGGESTION);
                CloudAppDriver.getApp().getConsole().writeLine("- settings set <name> <value> - changing settings values", MessageType.SUGGESTION);
                CloudAppDriver.getApp().getConsole().writeLine("- settings reload - reloading configuration files", MessageType.SUGGESTION);
            }
        }

        return false;
    }
}
