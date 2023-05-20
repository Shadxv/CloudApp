package pl.shadxw.driver.managers;

import lombok.Getter;
import lombok.SneakyThrows;
import pl.shadxw.core.console.MessageType;
import pl.shadxw.core.models.ConfigFile;
import pl.shadxw.driver.CloudAppDriver;
import pl.shadxw.driver.console.Console;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class ConfigManager extends ConfigFile {

    private static final String CONFIG_FILE_NAME = "config.properties";

    private final String finalPath;
    private final Properties properties = new Properties();
    private FileInputStream fileInputStream;

    public ConfigManager(String path, boolean canCreateIfNotExists) {
        super(path, canCreateIfNotExists);
        this.finalPath = path + "/" + CONFIG_FILE_NAME;
        CloudAppDriver.getApp().getConsole().writeLine("Checking configutarion files...", MessageType.NORMAL);
        try {
            loadPropertiesFile();
        } catch (IOException e) {
            CloudAppDriver.getApp().getConsole().writeLine("Could not find configuration files!", MessageType.WARNING);
            if (super.canCreateIfNotExists()) create();
            else throw new RuntimeException();
        }
    }

    @SneakyThrows
    @Override
    protected void forceCreate() {
        File configFolder = new File(super.getPath());
        if(!configFolder.isDirectory()) configFolder.mkdir();
        try {
            InputStream templateStream = this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_NAME);
            File config = new File(this.finalPath);
            config.createNewFile();
            FileOutputStream configStream = new FileOutputStream(config);
            configStream.write(templateStream.readAllBytes());

            templateStream.close();
            configStream.close();

            this.loadPropertiesFile();
        } catch (FileNotFoundException e) {
            CloudAppDriver.getApp().getConsole().writeLine("CloudApp could not find '" + CONFIG_FILE_NAME + "' template in resources!", MessageType.ERROR);
            CloudAppDriver.getApp().shutdown();
        } catch (IOException e) {
            CloudAppDriver.getApp().getConsole().writeLine("CloudApp could not create or read '" + CONFIG_FILE_NAME + "' !", MessageType.ERROR);
            CloudAppDriver.getApp().shutdown();
        }

    }

    @SneakyThrows
    @Override
    protected void create() {
        CloudAppDriver.getApp().getConsole().write("CloudApp needs to create configuration files. Do you agree to create them there? (Y/n): ", MessageType.NORMAL);
        String input;
        do {
            input = CloudAppDriver.getApp().getConsole().getScanner().nextLine();
        } while (!input.isEmpty()
                && !input.equalsIgnoreCase(" ")
                && !input.equalsIgnoreCase("y")
                && !input.equalsIgnoreCase("n"));
        if(input.equalsIgnoreCase("n")){
            CloudAppDriver.getApp().getConsole().writeLine("Could not create configuration files...", MessageType.WARNING);
            CloudAppDriver.getApp().shutdown();
        } else {
            this.forceCreate();
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
