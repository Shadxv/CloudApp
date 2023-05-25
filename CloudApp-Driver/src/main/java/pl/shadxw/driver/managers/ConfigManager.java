package pl.shadxw.driver.managers;

import lombok.SneakyThrows;
import pl.shadxw.core.console.MessageType;
import pl.shadxw.core.models.ConfigFile;
import pl.shadxw.driver.CloudAppDriver;

import java.io.*;
import java.util.Properties;

public class ConfigManager extends ConfigFile {

    private static final String CONFIG_FILE_NAME = "config.properties";

    private final String finalPath;
    private final Properties properties = new Properties();
    private FileInputStream fileInputStream;

    @SneakyThrows
    public ConfigManager(String path, boolean canCreateIfNotExists) {
        super(path, canCreateIfNotExists);
        this.finalPath = path + "/" + CONFIG_FILE_NAME;
        CloudAppDriver.getApp().getConsole().writeLine("Checking configuration files...", MessageType.NORMAL);
        try {
            loadPropertiesFile();
        } catch (IOException e) {
            CloudAppDriver.getApp().getConsole().writeLine("Could not find configuration files!", MessageType.WARNING);
            if (super.canCreateIfNotExists()) create();
            else {
                CloudAppDriver.getApp().getConsole().writeLine("CloudApp could not create files!", MessageType.ERROR);
                CloudAppDriver.getApp().shutdown(true, true);
            }
        }
    }

    @SneakyThrows
    @Override
    protected void forceCreate(String templateFileName) {
        File configFolder = new File(super.getPath());
        if(!configFolder.isDirectory()) configFolder.mkdir();
        try {
            InputStream templateStream = this.getClass().getClassLoader().getResourceAsStream(templateFileName);
            File config = new File(this.finalPath);
            config.createNewFile();
            FileOutputStream configStream = new FileOutputStream(config);
            configStream.write(templateStream.readAllBytes());

            templateStream.close();
            configStream.close();

            this.loadPropertiesFile();
        } catch (FileNotFoundException e) {
            CloudAppDriver.getApp().getConsole().writeLine("CloudApp could not find '" + CONFIG_FILE_NAME + "' template in resources!", MessageType.ERROR);
            CloudAppDriver.getApp().shutdown(true, true);
        } catch (IOException e) {
            CloudAppDriver.getApp().getConsole().writeLine("CloudApp could not create or read '" + CONFIG_FILE_NAME + "' !", MessageType.ERROR);
            CloudAppDriver.getApp().shutdown(true, true);
        }

    }

    @SneakyThrows
    @Override
    protected void create() {
        CloudAppDriver.getApp().getConsole().write("CloudApp needs to create configuration files. Do you agree to create them there? (Y/n): ", MessageType.NORMAL);
        String input = null;
        do {
            if(input != null) CloudAppDriver.getApp().getConsole().write("You entered wrong value. Do you agree to create configuration files there? (Y/n): ", MessageType.NORMAL);
            input = CloudAppDriver.getApp().getConsole().getScanner().nextLine();
        } while (!input.isEmpty()
                && !input.equalsIgnoreCase(" ")
                && !input.equalsIgnoreCase("y")
                && !input.equalsIgnoreCase("n"));
        if(input.equalsIgnoreCase("n")){
            CloudAppDriver.getApp().getConsole().writeLine("Could not create configuration files...", MessageType.WARNING);
            CloudAppDriver.getApp().shutdown(true, true);
        } else {
            this.forceCreate("template_" + CONFIG_FILE_NAME);
        }
    }

    private void loadPropertiesFile() throws IOException {
        CloudAppDriver.getApp().getConsole().writeLine("Loading configuration files...", MessageType.NORMAL);
        File configFile = new File(this.finalPath);
        this.fileInputStream = new FileInputStream(configFile);
        this.properties.load(fileInputStream);
    }

    @Override
    public String readValue(String key) {
        if(this.exists(key)) return this.properties.getProperty(key);
        return null;
    }

    @Override
    public void write(String key, String value) {

    }

    @Override
    public void updateValue(String key, String value) {

    }

    @Override
    public void remove(String key) {

    }

    @Override
    public boolean exists(String key) {
        return this.properties.containsKey(key);
    }
}
